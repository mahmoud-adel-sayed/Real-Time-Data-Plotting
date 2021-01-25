package com.digis.android.task.implementation.java.data;

import android.app.Application;

import com.digis.android.task.implementation.java.data.source.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

class BaseRepository {
    private final Application mApp;
    private final List<RemoteDataSource<?>> mRemoteDataSources;

    BaseRepository(Application app) {
        mApp = app;
        mRemoteDataSources = new ArrayList<>(0);
    }

    final Application getApp() {
        return mApp;
    }

    final void addRemoteDataSource(RemoteDataSource<?> remoteSource) {
        mRemoteDataSources.add(remoteSource);
    }

    public void onUnsubscribe() {
        for (RemoteDataSource<?> remoteDataSource : mRemoteDataSources)
            remoteDataSource.cancel();
    }
}
