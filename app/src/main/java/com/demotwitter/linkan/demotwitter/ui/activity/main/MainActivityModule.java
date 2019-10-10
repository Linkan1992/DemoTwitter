package com.demotwitter.linkan.demotwitter.ui.activity.main;

import android.support.v7.widget.LinearLayoutManager;

import com.demotwitter.linkan.demotwitter.RxScheduler.SchedulerProvider;
import com.demotwitter.linkan.demotwitter.data.DataManager;
import com.demotwitter.linkan.demotwitter.ui.activity.login.LoginViewModel;
import com.demotwitter.linkan.demotwitter.ui.adapter.TimelineFeedAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

  @Provides
  MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    return new MainViewModel(dataManager, schedulerProvider);
  }

  @Provides
  TimelineFeedAdapter provideFeedAdapter() {
    return new TimelineFeedAdapter(new ArrayList<>());
  }

  @Provides
  LinearLayoutManager provideLinearLayoutManager(MainActivity activity) {
    return new LinearLayoutManager(activity);
  }

}
