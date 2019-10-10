package com.demotwitter.linkan.demotwitter.di.builderClass;


import com.demotwitter.linkan.demotwitter.ui.activity.login.LoginActivity;
import com.demotwitter.linkan.demotwitter.ui.activity.login.LoginActivityModule;
import com.demotwitter.linkan.demotwitter.ui.activity.main.MainActivity;
import com.demotwitter.linkan.demotwitter.ui.activity.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = {MainActivityModule.class})
  public abstract MainActivity provideMainActivity();


  @ContributesAndroidInjector(modules = {LoginActivityModule.class})
  public abstract LoginActivity provideLoginActivity();

}
