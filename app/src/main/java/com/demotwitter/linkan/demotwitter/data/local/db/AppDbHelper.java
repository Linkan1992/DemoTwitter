package com.demotwitter.linkan.demotwitter.data.local.db;


import com.demotwitter.linkan.demotwitter.data.local.dao.AppDatabase;
import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class AppDbHelper implements DbHelper {

  private AppDatabase appDatabase;

  @Inject
  public AppDbHelper(AppDatabase appDatabase) {
    this.appDatabase = appDatabase;
  }

  @Override
  public Observable<Boolean> insertTweetFeed(TimelineFeed timelineFeed) {
    return Observable.fromCallable(() -> {
      appDatabase.feedDao().insertTweetFeed(timelineFeed);
      return true;
    });
  }

  @Override
  public Observable<Boolean> insertMultipleListFeed(List<TimelineFeed> feedList) {
    return Observable.fromCallable(() -> {
      appDatabase.feedDao().insertMultipleListFeed(feedList);
      return true;
    });
  }

  @Override
  public Observable<TimelineFeed> findTweetById(Long tweet_id) {
    return Observable.fromCallable(() -> {
      TimelineFeed timelineFeed = appDatabase.feedDao().findTweetById(tweet_id);
      //.. if feed is not empty if empty return new empty timeline object
      return timelineFeed != null ? timelineFeed : new TimelineFeed();
    });
  }

  @Override
  public Observable<List<TimelineFeed>> loadAllTweetFeed() {
    return Observable.fromCallable(() -> appDatabase.feedDao().loadAllTweetFeed());
  }

  @Override
  public Observable<Boolean> deleteTweetFeed(TimelineFeed timelineFeed) {
    return Observable.fromCallable(() -> {
      appDatabase.feedDao().deleteTweetFeed(timelineFeed);
      return true;
    });
  }

  @Override
  public Observable<Boolean> deleteFeedRecord() {
    return Observable.fromCallable(() -> {
      appDatabase.feedDao().deleteFeedRecord();
      return true;
    });
  }

  @Override
  public Observable<List<TimelineFeed>> paginateTweetFeed(int limitCount, int offset) {
    return Observable.fromCallable(() -> appDatabase.feedDao().paginateTweetFeed(limitCount, offset));
  }

}
