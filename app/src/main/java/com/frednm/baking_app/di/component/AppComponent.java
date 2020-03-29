package com.frednm.baking_app.di.component;

import android.app.Application;


import com.frednm.baking_app.App;
import com.frednm.baking_app.di.module.ActivityModule;
import com.frednm.baking_app.di.module.AppModule;
import com.frednm.baking_app.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules={AndroidInjectionModule.class, ActivityModule.class, FragmentModule.class,  AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
