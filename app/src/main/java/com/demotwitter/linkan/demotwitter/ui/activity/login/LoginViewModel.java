package com.demotwitter.linkan.demotwitter.ui.activity.login;

import android.util.Log;

import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.base.BaseViewModel;
import com.demotwitter.linkan.demotwitter.data.DataManager;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

  TwitterSession session;
  TwitterAuthToken authToken;

  public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    super(dataManager, schedulerProvider);
    // hide profile picture
    LoginViewModel.super.userProfileVisible.set(false);
  }

  public Callback<TwitterSession> getSignInWithTwitter(){

    return new Callback<TwitterSession>() {
      @Override
      public void success(Result<TwitterSession> result) {

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        authToken = session.getAuthToken();

        // setting Authenticated user data
        getDataManager().setIsLoggedIn(true);
        getDataManager().setUserID(result.data.getId());
        getDataManager().setUsername(result.data.getUserName());
        getDataManager().setOAuthToken(authToken.token);
        getDataManager().setOAuthTokenSecret(authToken.secret);

        getNavigator().AuthSuccess();
        getNavigator().showToast("Successfull authentication");


      }

      @Override
      public void failure(TwitterException exception) {
        Log.e("Failed", exception.toString());
        getNavigator().showToast(exception.getMessage());
      }
    };

  }


  public void showUser(){
    getDataManager()
      .fetchUserDetail(TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId())
      .compose(provideScheduler())
      .subscribe(new BaseObserver<User>() {
        @Override
        protected void result(User user) {
          getNavigator().showToast("fetchUserDetail Success => " + user.name);
        }
      });
  }

  public void fetchTimelineFeed(){
    getDataManager()
      .fetchTimelineFeed(20, null, null, null, null, null, null)
      .compose(provideScheduler())
      .subscribe(new BaseObserver<List<Tweet>>() {
        @Override
        protected void result(List<Tweet> tweetList) {
          getNavigator().showToast("fetchTimelinePost Success => " + tweetList.toString());
        }
      });
  }

}
