package com.demotwitter.linkan.demotwitter.ui.adapter;

import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;

import java.util.List;

public interface OnLoadMoreListener {

  void onLoadMore(List<TimelineFeed> data);

  void onShowLoader();

}
