package com.demotwitter.linkan.demotwitter.di.component;
import android.app.Application;

import com.demotwitter.linkan.demotwitter.BaseApplication;
import com.demotwitter.linkan.demotwitter.di.builderClass.ActivityBuilder;
import com.demotwitter.linkan.demotwitter.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Linkan on 09/04/19.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(BaseApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
