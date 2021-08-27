package com.example.android.rtdp.implementation.java.di;

import android.app.Application;

import com.example.android.rtdp.implementation.java.App;

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
public interface JavaAppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application app);

        JavaAppComponent build();
    }

    void inject(App app);
}