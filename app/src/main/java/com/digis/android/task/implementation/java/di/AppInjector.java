package com.digis.android.task.implementation.java.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.digis.android.task.implementation.java.App;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * AppInjector is a helper class to automatically inject fragments if they implement
 * {@link Injectable}.
 */
public final class AppInjector {
    private static AppComponent appComponent;

    private AppInjector() { }

    public static void init(App app) {
        appComponent = DaggerAppComponent.builder().application(app).build();
        appComponent.inject(app);
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) { }

            @Override
            public void onActivityResumed(@NonNull Activity activity) { }

            @Override
            public void onActivityPaused(@NonNull Activity activity) { }

            @Override
            public void onActivityStopped(@NonNull Activity activity) { }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity,
                                                    @NonNull Bundle outState) { }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) { }
        });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(
                                        @NonNull FragmentManager fm,
                                        @NonNull Fragment fragment,
                                        Bundle savedInstanceState) {
                                    if (fragment instanceof Injectable) {
                                        AndroidSupportInjection.inject(fragment);
                                    }
                                }
                            }, true);
        }
    }

    @SuppressWarnings("unused")
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}