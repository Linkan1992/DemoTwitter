package com.demotwitter.linkan.demotwitter.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demotwitter.linkan.demotwitter.R;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.ui.adapter.TimelineFeedAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.Console;
import java.util.List;

public class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"tweetAdapter", "noMoreData"})
    public static void addTimelineFeedList(RecyclerView recyclerView, List<TimelineFeed> timelineFeedList, boolean noMoreData) {
        TimelineFeedAdapter adapter = (TimelineFeedAdapter) recyclerView.getAdapter();

        if (adapter != null) {
            // adapter.clearItems();

            if (adapter.getData() != null && adapter.getData().size() >= AppConstants.FEED_COUNT_PER_PAGE)
                adapter.addMoreData(timelineFeedList, noMoreData);
            else
                adapter.addItems(timelineFeedList);

            if (!noMoreData)
                adapter.setLoading(false);
        }
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeView, String imageUrl) {

        Uri uri = Uri.parse(imageUrl == null ? "" : imageUrl);
        draweeView.setImageURI(uri);

    }


    @BindingAdapter("bind:profileUrl")
    public static void setProfileUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .error(R.drawable.tw__composer_logo_white)
                .into(imageView);
    }

    @BindingAdapter({"pullToRefresh", "enableRefreshing"})
    public static void bindSwipeRefreshListener(SwipeRefreshLayout pullToRefresh,
                                                SwipeRefreshLayout.OnRefreshListener refreshListener,
                                                int refreshCount) {
        pullToRefresh.setOnRefreshListener(refreshListener);

        pullToRefresh.setRefreshing(false);

        pullToRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }


    public static class TwitterSessionBindingAdapters {

        @BindingAdapter("signInWithTwitter")
        public static void bindTwitterCallback(TwitterLoginButton loginButton, Callback<TwitterSession> callback) {
            loginButton.setCallback(callback);
        }

    }


}
