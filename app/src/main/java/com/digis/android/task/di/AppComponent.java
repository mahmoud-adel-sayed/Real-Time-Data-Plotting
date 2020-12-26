package com.digis.android.task.di;

import android.app.Application;

import com.digis.android.task.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ViewModelModule.class,
        ActivityModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application app);
        AppComponent build();
    }

    void inject(App app);
}