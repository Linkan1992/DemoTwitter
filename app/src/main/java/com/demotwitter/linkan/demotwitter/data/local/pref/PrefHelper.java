package com.demotwitter.linkan.demotwitter.data.local.pref;

public interface PrefHelper {

  void setUsername(String userName);

  void setUserID(long userID);

  void setUserAvatarUrl(String profileUrl);

  void setOAuthToken(String OauthToken);

  void setOAuthTokenSecret(String OauthTokenSecret);

  void setIsLoggedIn(boolean isLoggedIn);

  void clearSession();

  String getUsername();

  long getUserID();

  String getUserAvatarUrl();

  String getOAuthToken();

  String getOAuthTokenSecret();

  boolean getIsLoggedIn();

}
