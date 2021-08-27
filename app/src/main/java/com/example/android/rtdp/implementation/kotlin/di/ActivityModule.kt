package com.example.android.rtdp.implementation.kotlin.di

import com.example.android.rtdp.implementation.kotlin.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("unused")
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}