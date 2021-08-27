package com.example.android.rtdp.implementation.java.di;

import com.example.android.rtdp.implementation.java.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
@SuppressWarnings("unused")
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
