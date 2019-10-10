package com.demotwitter.linkan.demotwitter.di.module;


import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.demotwitter.linkan.demotwitter.RxScheduler.AppSchedulerProvider;
import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.data.AppDataManager;
import com.demotwitter.linkan.demotwitter.data.DataManager;
import com.demotwitter.linkan.demotwitter.data.local.dao.AppDatabase;
import com.demotwitter.linkan.demotwitter.data.local.db.AppDbHelper;
import com.demotwitter.linkan.demotwitter.data.local.db.DbHelper;
import com.demotwitter.linkan.demotwitter.data.local.pref.AppPreferenceHelper;
import com.demotwitter.linkan.demotwitter.data.local.pref.PrefHelper;
import com.demotwitter.linkan.demotwitter.data.remote.ApiHelper;
import com.demotwitter.linkan.demotwitter.data.remote.AppApiHelper;
import com.demotwitter.linkan.demotwitter.data.remote.MyTwitterApiClient;
import com.demotwitter.linkan.demotwitter.utils.AppConstants;
import com.demotwitter.linkan.demotwitter.di.annotation.DatabaseInfo;
import com.demotwitter.linkan.demotwitter.di.annotation.PreferenceInfo;
import com.twitter.sdk.android.core.TwitterCore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {


  @Provides
  @Singleton
  Context provideApplicationContext(Application application) {
    return application;
  }


  @Provides
  @Singleton
  DataManager provideDataManager(AppDataManager appDataManager) {
    return appDataManager;
  }


  @Provides
  @Singleton
  AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
    return Room.databaseBuilder(context, AppDatabase.class, dbName).fallbackToDestructiveMigration()
      .build();
  }


  @Provides
  @Singleton
  DbHelper provideDbHelper(AppDbHelper appDbHelper) {
    return appDbHelper;
  }


  @Provides
  @Singleton
  ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
    return appApiHelper;
  }


  @Provides
  @Singleton
  PrefHelper providePrefHelper(AppPreferenceHelper appPrefHelper) {
    return appPrefHelper;
  }


  @Provides
  @DatabaseInfo
  String provideDatabaseName() {
    return AppConstants.DB_NAME;
  }


  @Provides
  @PreferenceInfo
  String providePreferenceName() {
    return AppConstants.PREF_NAME;
  }


  @Provides
  @Singleton
  SchedulerProvider provideScheduleProvider() {
    return new AppSchedulerProvider();
  }

//  @Provides
//  @Singleton
//  MyTwitterApiClient provideMyTwitterApiClient() {
//    return new MyTwitterApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession());
//  }


}
