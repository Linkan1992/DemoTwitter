package com.demotwitter.linkan.demotwitter.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.demotwitter.linkan.demotwitter.BR;
import com.demotwitter.linkan.demotwitter.R;
import com.demotwitter.linkan.demotwitter.base.BaseActivity;
import com.demotwitter.linkan.demotwitter.base.BaseRecyclerViewAdapter;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.databinding.ActivityMainBinding;
import com.demotwitter.linkan.demotwitter.ui.adapter.OnLoadMoreListener;
import com.demotwitter.linkan.demotwitter.ui.adapter.TimelineFeedAdapter;
import com.demotwitter.linkan.demotwitter.utils.AppConstants;
import com.twitter.sdk.android.core.TwitterException;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, BaseRecyclerViewAdapter.BaseRecyclerViewAdapterListener {

    private int offset = -1;
    private Long sinceId = null;
    private Long maxId = null;

    @Inject
    MainViewModel mainViewModel;
    @Inject
    TimelineFeedAdapter timelineFeedAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected String getActivityName() {
        return getResources().getString(R.string.home);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getSupportedToolbar() {
        return getViewDataBinding().includedAppBar.toolbar;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }


    @Override
    protected void permissionGranted(int requestPhoneStateCode) {

    }

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }


    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {

         //.. To Hide the home back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        getViewModel().setNavigator(this);
        timelineFeedAdapter.setListener(this);
        getViewModel().fetchTimelineFeeds(null, null, false, offset);

        setUpRecyclerView();
        setUpRecyclerViewScroll();
        subscribeToFeedLiveData();
    }


    private void setUpRecyclerView() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getViewDataBinding().tweetRecyclerView.setLayoutManager(mLayoutManager);
        getViewDataBinding().tweetRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getViewDataBinding().tweetRecyclerView.setAdapter(timelineFeedAdapter);
    }


    @Override
    public void handleError(Throwable throwable) {
        if (throwable instanceof TwitterException) {
            getViewModel().paginateOfflineTweet(AppConstants.FEED_COUNT_PER_PAGE, true, offset);
        }
        super.handleError(throwable);
    }

    private void subscribeToFeedLiveData() {

        getViewModel().getTimeLineFeedLiveData().observe(this, getViewModel().new BaseObserver<List<TimelineFeed>>() {
            @Override
            protected void result(List<TimelineFeed> timelineFeedList) {
                getViewModel().setFeedListObservable(timelineFeedList);
            }
        });

    }


    private void setUpRecyclerViewScroll() {

        /**
         * Timeline tweetList is sorted in ascending order
         * Hence  data.get(data.size() - 1).tweet_id --> Returns Highest Tweet ID
         * data.get(data.size() - AppConstants.FEED_COUNT_PER_PAGE).tweet_id --> Returns Lowest Tweet ID
         * Previous Request Returns Highest Tweet ID --> sinceId for Next Request
         * Previous Request Returns Lowest Tweet ID --> maxId for Next Request
         */

        /**
         * Timeline tweetList is sorted in descending order
         * Hence  data.get(data.size() - 1).tweet_id --> Returns Lowest Tweet ID
         * data.get(data.size() - AppConstants.FEED_COUNT_PER_PAGE).tweet_id --> Returns Highest Tweet ID
         * Previous Request Returns Highest Tweet ID --> sinceId for Next Request
         * Previous Request Returns Lowest Tweet ID --> maxId for Next Request
         */

        timelineFeedAdapter.setRecyclerView(getViewDataBinding().tweetRecyclerView, new OnLoadMoreListener() {
            @Override
            public void onLoadMore(List<TimelineFeed> data) {
                if (offset == -1)
                    offset = 0;

                offset += AppConstants.FEED_COUNT_PER_PAGE;

                if (data != null && data.size() >= AppConstants.FEED_COUNT_PER_PAGE) {

                    maxId = data.get(data.size() - 1).tweet_id - 1;
                    sinceId = data.get(data.size() - AppConstants.FEED_COUNT_PER_PAGE).tweet_id;

                    getViewModel().fetchTimelineFeeds(
                            sinceId,
                            maxId,
                            true,
                            offset
                    );
                }
            }

            @Override
            public void onShowLoader() {
                timelineFeedAdapter.setProgressItem();
            }
        });

    }


    @Override
    public void onRetryClick() {
        getViewModel().fetchTimelineFeeds(null, null, false, offset);
    }

    @Override
    public void changeRetryVisibility(boolean visibility) {
        timelineFeedAdapter.setRetryVisibility(visibility);
    }
}
