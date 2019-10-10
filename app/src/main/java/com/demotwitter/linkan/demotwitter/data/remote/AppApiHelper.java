package com.demotwitter.linkan.demotwitter.data.remote;

import android.util.Log;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public class AppApiHelper implements ApiHelper {

  private static final String TAG = AppApiHelper.class.getSimpleName();


  @Inject
  public AppApiHelper() {

  }


  @Override
  public Observable<User> fetchUserDetail(long userId) {
    return Observable.create(subscriber ->
      new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession())
      .getCustomService()
      .show(userId)
      .enqueue(getGenericCallback(subscriber)));
  }

  @Override
  public Observable<List<Tweet>> fetchTimelineFeed(Integer count, Long sinceId, Long maxId, Boolean trimUser,
                                             Boolean excludeReplies, Boolean contributeDetails, Boolean includeEntities) {


    return Observable.create(subscriber ->
      new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession())
      .getCustomService()
      .fetchTimelinePost(count, sinceId, maxId, trimUser,
        excludeReplies, contributeDetails, includeEntities)
      .enqueue(getGenericCallback(subscriber)));

  }



  private <T> Callback<T> getGenericCallback(ObservableEmitter<T> subscriber){

    return new Callback<T>() {
      @Override
      public void success(Result<T> result) {
        Log.i(TAG, "Got the tweets, buddy!");
        subscriber.onNext(result.data);
        subscriber.onComplete();
      }

      @Override
      public void failure(TwitterException e) {
        Log.e(TAG, e.getMessage(), e);
        subscriber.onError(e);
      }
    };
  }



}
