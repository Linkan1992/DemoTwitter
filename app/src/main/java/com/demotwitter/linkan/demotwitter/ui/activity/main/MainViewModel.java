package com.demotwitter.linkan.demotwitter.ui.activity.main;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.base.BaseActivity;
import com.demotwitter.linkan.demotwitter.base.BaseViewModel;
import com.demotwitter.linkan.demotwitter.data.DataManager;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.utils.AppConstants;
import com.demotwitter.linkan.demotwitter.utils.UtilFunction;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewModel extends BaseViewModel<MainNavigator> {


    private final MutableLiveData<List<TimelineFeed>> timeLineFeedLiveData = new MutableLiveData<>();

    private final ObservableList<TimelineFeed> feedListObservable = new ObservableArrayList<>();

    /**
     * syncDbCache - TRUE -> start caching into local db
     * syncDbCache - FALSE -> clear all cached data from local db
     * fetchedFromDb --> describes whether fetched from DB or not
     */
    private boolean syncDbCache = false;

    public boolean noMoreDataAvailable = false;

    public boolean fetchedFromDb = false;


    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        // hide profile pic
        MainViewModel.super.userProfileVisible.set(true);

        //fetch user detail
        showUser();
    }


    public void showUser() {
        getCompositeDisposable().add(
                getDataManager()
                        .fetchUserDetail(TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId())
                        .compose(provideScheduler())
                        .subscribe(user -> {
                                    MainViewModel.super.userAvatarUrl.set(user.profileImageUrlHttps);
                                    getDataManager().setUserAvatarUrl(user.profileImageUrlHttps);
                                    getNavigator().showToast("fetchUserDetail Success => " + user.name);
                                },
                                error -> {
                                    MainViewModel.super.userAvatarUrl.set(getDataManager().getUserAvatarUrl());
                                }
                        ));
    }


    public void fetchTimelineFeeds(Long sinceId, Long maxId, boolean isPaging, int offset) {

        if ((((BaseActivity) getNavigator()).isNetworkConnected())) {
            /**
             * if not paging then only show main loader
             */
            setIsLoading(!isPaging);
            getDataManager()
                    .fetchTimelineFeed(AppConstants.FEED_COUNT_PER_PAGE, null, maxId, null, null, null, null)
                    .map(this::getTimelineFeedList)
                    .compose(provideScheduler())
                    .subscribe(new BaseObserver<List<TimelineFeed>>() {
                        @Override
                        protected void result(List<TimelineFeed> tweetList) {
                            timeLineFeedLiveData.setValue(tweetList);
                            if (tweetList.size() > 0) {
                                /**
                                 * on every loading from API on app launch delete all record from
                                 * thus clear cache
                                 */
                                if (!syncDbCache)
                                    clearOfflineTweet(tweetList);

                                // syncing tweets to dbcache
                                if (syncDbCache)
                                    syncDbCache(tweetList);

                                syncDbCache = true;
                                noMoreDataAvailable = false;
                                //   getNavigator().showToast("fetchTimelinePost Success => " + tweetList.toString());
                            } else
                                noMoreDataAvailable = true;
                        }
                    });
        } else {

//      if (!fetchedFromDb)
//        fetchOfflineTweet();

            paginateOfflineTweet(AppConstants.FEED_COUNT_PER_PAGE, offset);

            getNavigator().showToast("No Internet Connection");
        }
    }

    private void syncDbCache(List<TimelineFeed> tweetList) {
        getCompositeDisposable().add(
                getDataManager()
                        .insertMultipleListFeed(tweetList)
                        .compose(provideScheduler())
                        .subscribe(
                                response -> {
                                    //getNavigator().showToast("cache sync Success");
                                },
                                error -> {
                                    //getNavigator().showToast("cache sync failed");
                                }
                        ));
    }


    public void fetchOfflineTweet() {
        getCompositeDisposable().add(
                getDataManager()
                        .loadAllTweetFeed()
                        .compose(provideScheduler())
                        .subscribe(
                                tweetList -> {
                                    fetchedFromDb = true;
                                    timeLineFeedLiveData.setValue(tweetList);
                                    // getNavigator().showToast("offline fetch Success");
                                },
                                error -> {
                                    //getNavigator().showToast("offline fetch failed");
                                }
                        ));
    }


    public void paginateOfflineTweet(int feedCountPerPage, int offset) {
        getCompositeDisposable().add(
                getDataManager()
                        .paginateTweetFeed(feedCountPerPage, offset)
                        .compose(provideScheduler())
                        .subscribe(
                                tweetList -> {
                                    /**
                                     *  syncing tweets to dbcache when it will come back to online
                                     *  and onLoadMore will be in progress
                                     */
                                    syncDbCache = true;

                                    timeLineFeedLiveData.setValue(tweetList);
                                    if (tweetList.size() > 0) {
                                        noMoreDataAvailable = false;
                                        // getNavigator().showToast("pagination offline fetch Success");
                                    } else {
                                        noMoreDataAvailable = true;
                                        getNavigator().changeRetryVisibility(true);
                                    }

                                },
                                error -> {
                                    getNavigator().showToast("pagination offline fetch failed");
                                }
                        ));
    }


    public void clearOfflineTweet(List<TimelineFeed> tweetList) {
        getCompositeDisposable().add(
                getDataManager()
                        .deleteFeedRecord()
                        .compose(provideScheduler())
                        .subscribe(
                                isTweetListDeleted -> {
                                    //  getNavigator().showToast("offline all record deleted");

                                    // syncing tweets to dbcache
                                    if (syncDbCache)
                                        syncDbCache(tweetList);
                                },
                                error -> {
                                    //getNavigator().showToast("offline record delete failed");
                                }
                        ));
    }


    private List<TimelineFeed> getTimelineFeedList(List<Tweet> tweetList) {

        List<TimelineFeed> feedList = new ArrayList<>();
        for (Tweet tweet : tweetList) {
            TimelineFeed timelineFeed = new TimelineFeed();

            timelineFeed.setTweet_id(tweet.id);

            timelineFeed.setTweet_id_str(tweet.idStr);
            timelineFeed.setProfile_url(tweet.user.profileImageUrlHttps);
            timelineFeed.setName(tweet.user.name);
            timelineFeed.setScreen_name(tweet.user.screenName);
            timelineFeed.setTweet_time(UtilFunction.getTweetCreatedAt(tweet.createdAt, DateTime.now()));
            timelineFeed.setTweet_text(tweet.text);
            timelineFeed.setTweet_description(tweet.user.description);

            for (int i = 0; i < tweet.extendedEntities.media.size(); i++) {

                if (i == 0) {
                    timelineFeed.setMedia_url1(tweet.extendedEntities.media.get(i).mediaUrlHttps);
                    timelineFeed.setMedia_type(tweet.extendedEntities.media.get(i).type);
                    timelineFeed.setVideo_length(
                            tweet.extendedEntities.media.get(i).videoInfo != null
                                    ? tweet.extendedEntities.media.get(i).videoInfo.durationMillis
                                    : 0);
                } else if (i == 1)
                    timelineFeed.setMedia_url2(tweet.extendedEntities.media.get(i).mediaUrlHttps);
                else if (i == 2)
                    timelineFeed.setMedia_url3(tweet.extendedEntities.media.get(i).mediaUrlHttps);
                else if (i == 3)
                    timelineFeed.setMedia_url4(tweet.extendedEntities.media.get(i).mediaUrlHttps);
            }

            timelineFeed.setFavourite_count(String.valueOf(tweet.favoriteCount));
            timelineFeed.setReTweet_count(String.valueOf(tweet.retweetCount));

            feedList.add(timelineFeed);

        }

        // sorting the list
        Collections.sort(feedList);

        return feedList;
    }


    public void setFeedListObservable(List<TimelineFeed> timelineFeedList) {

        this.feedListObservable.clear();
        this.feedListObservable.addAll(timelineFeedList);
    }

    public MutableLiveData<List<TimelineFeed>> getTimeLineFeedLiveData() {
        return timeLineFeedLiveData;
    }

    public ObservableList<TimelineFeed> getFeedListObservable() {
        return feedListObservable;
    }

}
