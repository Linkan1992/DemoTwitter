package com.demotwitter.linkan.demotwitter.data.local.db;


import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {


  Observable<Boolean> insertTweetFeed(final TimelineFeed timelineFeed);

  Observable<Boolean> insertMultipleListFeed(List<TimelineFeed> feedList);

  Observable<TimelineFeed> findTweetById(Long tweet_id);

  Observable<List<TimelineFeed>> loadAllTweetFeed();

  Observable<Boolean> deleteTweetFeed(TimelineFeed timelineFeed);

  Observable<Boolean> deleteFeedRecord();

  Observable<List<TimelineFeed>> paginateTweetFeed(int limitCount, int offset);

}
