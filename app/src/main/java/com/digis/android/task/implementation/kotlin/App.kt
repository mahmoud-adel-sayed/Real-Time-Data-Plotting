package com.digis.android.task.implementation.kotlin

import android.app.Activity
import android.app.Application
import com.digis.android.task.implementation.kotlin.di.AppInjector
import com.facebook.stetho.Stetho
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        Stetho.initializeWithDefaults(this)
    }

    override fun activityInjector() = activityInjector
}