package com.demotwitter.linkan.demotwitter.ui.adapter;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Html;
import android.text.Spanned;

import com.demotwitter.linkan.demotwitter.data.model.db.TimelineFeed;
import com.demotwitter.linkan.demotwitter.utils.UtilFunction;

import java.io.Serializable;

public class TimelineFeedViewModel implements Serializable {

  TimelineFeed timelineFeed;

  private TweetFeedInterface tweetFeedListener;

  public final ObservableField<Long> tweet_id;

  public final ObservableField<String> tweet_id_str;

  public final ObservableField<String> profile_url;

  public final ObservableField<String> name;

  public final ObservableField<String> screen_name;

  public final ObservableField<String> tweet_time;

  public final ObservableField<String> tweet_text;

  public final ObservableField<String> tweet_description;

  public final ObservableField<String> media_url1;

  public final ObservableField<String> media_url2;

  public final ObservableField<String> media_url3;

  public final ObservableField<String> media_url4;

  public final ObservableField<String> media1_base64;

  public final ObservableField<String> media2_base64;

  public final ObservableField<String> media3_base64;

  public final ObservableField<String> media4_base64;

  public final ObservableField<String> media_type;

  public final ObservableField<String> video_length;

  public final ObservableField<String> favourite_count;

  public final ObservableField<String> retweet_count;

  public final ObservableBoolean mediaHide;

  public final ObservableBoolean playIconHide;

  public final ObservableField<Spanned> tweet_link;


  public TimelineFeedViewModel(TimelineFeed timelineFeed, TweetFeedInterface tweetFeedListener) {

    this.tweetFeedListener = tweetFeedListener;

    this.timelineFeed = timelineFeed;

    tweet_id = new ObservableField<>(timelineFeed.tweet_id);

    tweet_id_str = new ObservableField<>(timelineFeed.tweet_id_str);

    profile_url = new ObservableField<>(timelineFeed.profile_url);

    name = new ObservableField<>(timelineFeed.name);

    screen_name = new ObservableField<>('@' + timelineFeed.screen_name);

    tweet_time = new ObservableField<>(timelineFeed.tweet_time);

    tweet_text = new ObservableField<>(timelineFeed.tweet_text);

    tweet_link = new ObservableField<>(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N
      ? Html.fromHtml(timelineFeed.tweet_text == null ? "" : timelineFeed.tweet_text.contains("https")
                                                              ? timelineFeed.tweet_text.substring(timelineFeed.tweet_text.indexOf("https"), timelineFeed.tweet_text.length())
                                                              : "", Html.FROM_HTML_MODE_LEGACY)

      : Html.fromHtml(timelineFeed.tweet_text == null ? "" : timelineFeed.tweet_text.contains("https")
                                                            ? timelineFeed.tweet_text.substring(timelineFeed.tweet_text.indexOf("https"), timelineFeed.tweet_text.length())
                                                            : ""));

    tweet_description = new ObservableField<>(timelineFeed.tweet_description);

    media_url1 = new ObservableField<>(timelineFeed.media_url1);

    media_url2 = new ObservableField<>(timelineFeed.media_url2);

    media_url3 = new ObservableField<>(timelineFeed.media_url3);

    media_url4 = new ObservableField<>(timelineFeed.media_url4);

    media1_base64 = new ObservableField<>(timelineFeed.media1_base64);

    media2_base64 = new ObservableField<>(timelineFeed.media2_base64);

    media3_base64 = new ObservableField<>(timelineFeed.media3_base64);

    media4_base64 = new ObservableField<>(timelineFeed.media4_base64);

    media_type = new ObservableField<>(timelineFeed.media_type);

    //video_length = new ObservableField<>(String.valueOf(timelineFeed.video_length));
    video_length = new ObservableField<>(UtilFunction.getVideoTime(timelineFeed.video_length));

    favourite_count = new ObservableField<>(timelineFeed.favourite_count);

    retweet_count = new ObservableField<>(timelineFeed.retweet_count);

    mediaHide = new ObservableBoolean(timelineFeed.media_url1 == null || timelineFeed.media_url1.length() <= 0);

    playIconHide = new ObservableBoolean(timelineFeed.media_type != null && timelineFeed.media_type.equals("photo"));

  }


  public void onItemClick() {
    tweetFeedListener.onTweetClick(timelineFeed);
  }

}
