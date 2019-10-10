package com.demotwitter.linkan.demotwitter;

import android.app.Activity;
import android.app.Application;

import com.demotwitter.linkan.demotwitter.di.component.DaggerAppComponent;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class BaseApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
        Fresco.initialize(this);

        Twitter.initialize(
                new TwitterConfig
                        .Builder(this)
                        .twitterAuthConfig(
                                new TwitterAuthConfig(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_SECRET)
                        )
                        .build()
        );

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

    }
}
