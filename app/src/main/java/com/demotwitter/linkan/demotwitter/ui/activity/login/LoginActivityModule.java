package com.demotwitter.linkan.demotwitter.ui.activity.login;

import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

  @Provides
  LoginViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    return new LoginViewModel(dataManager, schedulerProvider);
  }


}
