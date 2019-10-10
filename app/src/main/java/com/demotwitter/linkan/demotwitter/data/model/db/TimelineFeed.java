package com.demotwitter.linkan.demotwitter.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


// this class must be the POJO class not a Bean Class
@Entity(tableName = "timeline_feed")
public class TimelineFeed implements Comparable<TimelineFeed> {

  @PrimaryKey
  @ColumnInfo(name = "tweet_id")
  public Long tweet_id;

  @ColumnInfo(name = "tweet_id_str")
  public String tweet_id_str;

  @ColumnInfo(name = "profile_url")
  public String profile_url;

  @ColumnInfo(name = "name")
  public String name;

  @ColumnInfo(name = "screen_name")
  public String screen_name;

  @ColumnInfo(name = "tweet_time")
  public String tweet_time;

  @ColumnInfo(name = "tweet_text")
  public String tweet_text;

  @ColumnInfo(name = "tweet_description")
  public String tweet_description;

  @ColumnInfo(name = "media_url1")
  public String media_url1;

  @ColumnInfo(name = "media_url2")
  public String media_url2;

  @ColumnInfo(name = "media_url3")
  public String media_url3;

  @ColumnInfo(name = "media_url4")
  public String media_url4;

  @ColumnInfo(name = "media1_base64")
  public String media1_base64;

  @ColumnInfo(name = "media2_base64")
  public String media2_base64;

  @ColumnInfo(name = "media3_base64")
  public String media3_base64;

  @ColumnInfo(name = "media4_base64")
  public String media4_base64;

  @ColumnInfo(name = "media_type")
  public String media_type;

  // in milliseconds
  @ColumnInfo(name = "video_length")
  public Long video_length;

  @ColumnInfo(name = "favourite_count")
  public String favourite_count;

  @ColumnInfo(name = "retweet_count")
  public String retweet_count;


  // Setter methods


  public void setTweet_id(Long tweet_id) {
    this.tweet_id = tweet_id;
  }

  public void setTweet_id_str(String tweet_id_str) {
    this.tweet_id_str = tweet_id_str;
  }


  public void setProfile_url(String profile_url) {
    this.profile_url = profile_url;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }

  public void setTweet_time(String tweet_time) {
    this.tweet_time = tweet_time;
  }

  public void setTweet_text(String tweet_text) {
    this.tweet_text = tweet_text;
  }

  public void setTweet_description(String tweet_description) {
    this.tweet_description = tweet_description;
  }

  public void setMedia_url1(String media_url1) {
    this.media_url1 = media_url1;
  }

  public void setMedia_url2(String media_url2) {
    this.media_url2 = media_url2;
  }

  public void setMedia_url3(String media_url3) {
    this.media_url3 = media_url3;
  }

  public void setMedia_url4(String media_url4) {
    this.media_url4 = media_url4;
  }

  public void setMedia1_base64(String media1_base64) {
    this.media1_base64 = media1_base64;
  }

  public void setMedia2_base64(String media2_base64) {
    this.media2_base64 = media2_base64;
  }

  public void setMedia3_base64(String media3_base64) {
    this.media3_base64 = media3_base64;
  }

  public void setMedia4_base64(String media4_base64) {
    this.media4_base64 = media4_base64;
  }

  public void setMedia_type(String media_type) {
    this.media_type = media_type;
  }

  public void setVideo_length(Long video_length) {
    this.video_length = video_length;
  }

  public void setFavourite_count(String favourite_count) {
    this.favourite_count = favourite_count;
  }

  public void setReTweet_count(String retweet_count) {
    this.retweet_count = retweet_count;
  }


  /**
   * Sorting in Descending Order
   * @param timelineFeed
   * @return
   */
  @Override
  public int compareTo(TimelineFeed timelineFeed) {
    return (this.tweet_id > timelineFeed.tweet_id ? -1 :
      (this.tweet_id.equals(timelineFeed.tweet_id) ? 0 : 1));

  }

}
