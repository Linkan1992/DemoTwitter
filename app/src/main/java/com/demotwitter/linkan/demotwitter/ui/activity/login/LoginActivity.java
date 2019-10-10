package com.demotwitter.linkan.demotwitter.ui.activity.login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.demotwitter.linkan.demotwitter.BR;
import com.demotwitter.linkan.demotwitter.R;
import com.demotwitter.linkan.demotwitter.base.BaseActivity;
import com.demotwitter.linkan.demotwitter.databinding.ActivityLoginBinding;
import com.demotwitter.linkan.demotwitter.ui.activity.main.MainActivity;
import com.twitter.sdk.android.core.TwitterException;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

  @Inject
  LoginViewModel loginViewModel;

  @Override
  protected String getActivityName() {
    return getResources().getString(R.string.login);
  }

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  protected Toolbar getSupportedToolbar() {
    return getViewDataBinding().includedAppBar.toolbar;
  }

  @Override
  public int getBindingVariable() {
    return BR.viewModel;
  }

  @Override
  protected void permissionGranted(int requestPhoneStateCode) {

  }

  @Override
  public LoginViewModel getViewModel() {
    return loginViewModel;
  }


  @Override
  public void initOnCreate(@Nullable Bundle savedInstanceState) {

    //.. To Hide the home back button
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    getViewModel().setNavigator(this);
    checkLoginStatus();
  }

  private void checkLoginStatus() {
    if(getViewModel().getDataManager().getIsLoggedIn()){
        navigateToMain();
    }
  }


  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Pass the activity result to the login button.
    getViewDataBinding().loginButton.onActivityResult(requestCode, resultCode, data);
  }


    @Override
    public void AuthSuccess() {
        navigateToMain();
    }

    private void navigateToMain(){
        MainActivity.newIntent(LoginActivity.this);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }
}
