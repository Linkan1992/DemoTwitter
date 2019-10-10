package com.demotwitter.linkan.demotwitter.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.demotwitter.linkan.demotwitter.base.BaseRecyclerViewAdapter;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.databinding.FeedTweetRowLayoutBinding;
import com.demotwitter.linkan.demotwitter.utils.AppConstants;

import java.util.List;

public class TimelineFeedAdapter extends BaseRecyclerViewAdapter<TimelineFeed, TimelineFeedAdapter.TimelineFeedViewHolder> {

  private int visibleThreshold = AppConstants.FEED_COUNT_PER_PAGE / 2;
  private int lastVisibleItem, totalItemCount;

  public TimelineFeedAdapter(List<TimelineFeed> data) {
    super(data);
  }


  public void setRecyclerView(RecyclerView recyclerView, final OnLoadMoreListener onLoadMoreListener) {

    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

      final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          totalItemCount = linearLayoutManager.getItemCount();
          lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
          if (!TimelineFeedAdapter.super.loading
            && totalItemCount > 1
            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            if (onLoadMoreListener != null /*&& totalItemCount == (lastVisibleItem + 1)*/) {
              //   loading = true;
              onLoadMoreListener.onLoadMore(TimelineFeedAdapter.super.data);
              onLoadMoreListener.onShowLoader();
            }
            TimelineFeedAdapter.super.loading = true;
          }
        }
      });

    }
  }


  @Override
  protected TimelineFeedViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
    return new TimelineFeedAdapter.TimelineFeedViewHolder(FeedTweetRowLayoutBinding
      .inflate(LayoutInflater.from(parent.getContext()),
        parent,
        false));
  }


  public class TimelineFeedViewHolder extends BaseRecyclerViewAdapter<TimelineFeed, TimelineFeedAdapter.TimelineFeedViewHolder>.BaseViewHolder implements TweetFeedInterface {

    private FeedTweetRowLayoutBinding mBinding;

    public TimelineFeedViewHolder(FeedTweetRowLayoutBinding binding) {
      super(binding.getRoot());
      mBinding = binding;
    }

    @Override
    protected void onBind(TimelineFeed timelineFeed) {

      TimelineFeedViewModel timelineFeedViewModel = new TimelineFeedViewModel(timelineFeed == null ? new TimelineFeed() : timelineFeed, this);
      mBinding.setViewModel(timelineFeedViewModel);

      // Immediate Binding
      // When a variable or observable changes, the binding will be scheduled to change before
      // the next frame. There are times, however, when binding must be executed immediately.
      // To force execution, use the executePendingBindings() method.
      mBinding.executePendingBindings();
    }

    @Override
    protected void viewDetachedFromWindow() {

    }

    @Override
    public void onTweetClick(TimelineFeed timelineFeed) {

    }

  }

}
