package com.demotwitter.linkan.demotwitter.data;



import com.demotwitter.linkan.demotwitter.data.local.db.DbHelper;
import com.demotwitter.linkan.demotwitter.data.local.pref.PrefHelper;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.data.remote.ApiHelper;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppDataManager implements DataManager {

    private DbHelper dbHelper;
    private ApiHelper apiHelper;
    private PrefHelper prefHelper;

    @Inject
    public AppDataManager(DbHelper dbHelper, ApiHelper apiHelper, PrefHelper prefHelper) {
        this.dbHelper = dbHelper;
        this.apiHelper = apiHelper;
        this.prefHelper = prefHelper;
    }



  // Database Calls
  //----------------------------------------------------------------------------

  @Override
  public Observable<Boolean> insertTweetFeed(TimelineFeed timelineFeed) {
    return dbHelper.insertTweetFeed(timelineFeed);
  }

  @Override
  public Observable<Boolean> insertMultipleListFeed(List<TimelineFeed> feedList) {
    return dbHelper.insertMultipleListFeed(feedList);
  }

  @Override
  public Observable<TimelineFeed> findTweetById(Long tweet_id) {
    return dbHelper.findTweetById(tweet_id);
  }

  @Override
  public Observable<List<TimelineFeed>> loadAllTweetFeed() {
    return dbHelper.loadAllTweetFeed();
  }

  @Override
  public Observable<Boolean> deleteTweetFeed(TimelineFeed timelineFeed) {
    return dbHelper.deleteTweetFeed(timelineFeed);
  }

  @Override
  public Observable<Boolean> deleteFeedRecord() {
    return dbHelper.deleteFeedRecord();
  }

  @Override
  public Observable<List<TimelineFeed>> paginateTweetFeed(int limitCount, int offset) {
    return dbHelper.paginateTweetFeed(limitCount, offset);
  }


  // App Preference Calls
  //----------------------------------------------------------------------------

  @Override
  public void setUsername(String userName) {
    prefHelper.setUsername(userName);
  }

  @Override
  public void setUserID(long userID) {
    prefHelper.setUserID(userID);
  }

  @Override
  public void setUserAvatarUrl(String profileUrl) {
    prefHelper.setUserAvatarUrl(profileUrl);
  }

  @Override
  public void setOAuthToken(String OauthToken) {
    prefHelper.setOAuthToken(OauthToken);
  }

  @Override
  public void setOAuthTokenSecret(String OauthTokenSecret) {
    prefHelper.setOAuthTokenSecret(OauthTokenSecret);
  }

  @Override
  public void setIsLoggedIn(boolean isLoggedIn) {
    prefHelper.setIsLoggedIn(isLoggedIn);
  }

  @Override
  public void clearSession() {
    prefHelper.clearSession();
  }

  @Override
  public String getUsername() {
    return prefHelper.getUsername();
  }

  @Override
  public long getUserID() {
    return prefHelper.getUserID();
  }

  @Override
  public String getUserAvatarUrl() {
    return prefHelper.getUserAvatarUrl();
  }

  @Override
  public String getOAuthToken() {
    return prefHelper.getOAuthToken();
  }

  @Override
  public String getOAuthTokenSecret() {
    return prefHelper.getOAuthTokenSecret();
  }

  @Override
  public boolean getIsLoggedIn() {
    return prefHelper.getIsLoggedIn();
  }


  // API Calls
  //----------------------------------------------------------------------------


  @Override
  public Observable<User> fetchUserDetail(long userId) {
    return apiHelper.fetchUserDetail(userId);
  }

  @Override
  public Observable<List<Tweet>> fetchTimelineFeed(Integer count, Long sinceId, Long maxId, Boolean trimUser, Boolean excludeReplies,
                                             Boolean contributeDetails, Boolean includeEntities) {
    return apiHelper.fetchTimelineFeed(count, sinceId, maxId, trimUser, excludeReplies, contributeDetails, includeEntities);
  }



}
