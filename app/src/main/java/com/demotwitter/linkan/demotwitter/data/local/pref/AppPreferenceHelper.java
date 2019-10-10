package com.demotwitter.linkan.demotwitter.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;


import com.demotwitter.linkan.demotwitter.utils.AppConstants;
import com.demotwitter.linkan.demotwitter.di.annotation.PreferenceInfo;
import com.twitter.sdk.android.core.TwitterCore;

import javax.inject.Inject;

public class AppPreferenceHelper implements PrefHelper {

  private static final String DEFAULT_EMPTY_STRING = "";
  private final SharedPreferences mPrefs;


  @Inject
  public AppPreferenceHelper(Context context, @PreferenceInfo String Pref_Name) {
    mPrefs = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
  }


  @Override
  public void setUsername(String userName) {
    mPrefs.edit().putString(AppConstants.Extras.USERNAME, userName).apply();
  }

  @Override
  public void setUserID(long userID) {
    mPrefs.edit().putLong(AppConstants.Extras.USERID, userID).apply();
  }

  @Override
  public void setUserAvatarUrl(String profileUrl) {
    mPrefs.edit().putString(AppConstants.Extras.AVATAR_URL, profileUrl).apply();
  }

  @Override
  public void setOAuthToken(String OauthToken) {
    mPrefs.edit().putString(AppConstants.Extras.OAUTH_TOKEN, OauthToken).apply();
  }

  @Override
  public void setOAuthTokenSecret(String OauthTokenSecret) {
    mPrefs.edit().putString(AppConstants.Extras.OAUTH_TOKEN_SECRET, OauthTokenSecret).apply();
  }

  @Override
  public void setIsLoggedIn(boolean isLoggedIn) {
    mPrefs.edit().putBoolean(AppConstants.Extras.ISLOGGED_IN, isLoggedIn).apply();
  }

  @Override
  public void clearSession() {
    TwitterCore.getInstance().getSessionManager().clearActiveSession();
    mPrefs.edit().clear().apply();
    setIsLoggedIn(false);
  }

  @Override
  public String getUsername() {
    return mPrefs.getString(AppConstants.Extras.USERNAME, DEFAULT_EMPTY_STRING);
  }

  @Override
  public long getUserID() {
    return mPrefs.getLong(AppConstants.Extras.USERNAME, 0);
  }

  @Override
  public String getUserAvatarUrl() {
    return mPrefs.getString(AppConstants.Extras.AVATAR_URL, DEFAULT_EMPTY_STRING);
  }

  @Override
  public String getOAuthToken() {
    return mPrefs.getString(AppConstants.Extras.OAUTH_TOKEN, DEFAULT_EMPTY_STRING);
  }

  @Override
  public String getOAuthTokenSecret() {
    return mPrefs.getString(AppConstants.Extras.OAUTH_TOKEN_SECRET, DEFAULT_EMPTY_STRING);
  }

  @Override
  public boolean getIsLoggedIn() {
    return mPrefs.getBoolean(AppConstants.Extras.ISLOGGED_IN, false);
  }
}
