package com.demotwitter.linkan.demotwitter.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;

import java.util.List;


@Dao
public interface TweetFeedDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertTweetFeed(TimelineFeed timelineFeed);

  @Insert
  void insertMultipleListFeed(List<TimelineFeed> feedList);

  @Delete
  void deleteTweetFeed(TimelineFeed timelineFeed);

  @Query("DELETE FROM timeline_feed")
  void deleteFeedRecord();

  @Query("SELECT * FROM timeline_feed WHERE tweet_id = :tweet_id")
  TimelineFeed findTweetById(Long tweet_id);

  @Query("SELECT * FROM timeline_feed")
  List<TimelineFeed> loadAllTweetFeed();

  @Query("SELECT * FROM timeline_feed ORDER BY tweet_id DESC LIMIT :limitCount OFFSET :offset")
  List<TimelineFeed> paginateTweetFeed(int limitCount, int offset);

}
