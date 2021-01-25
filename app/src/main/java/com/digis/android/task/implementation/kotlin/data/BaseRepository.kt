package com.digis.android.task.implementation.kotlin.data

import android.app.Application
import com.digis.android.task.implementation.kotlin.data.source.remote.RemoteDataSource

open class BaseRepository(val app: Application) {
    private val remoteDataSources: MutableList<RemoteDataSource<*>> = ArrayList(0)

    protected fun addRemoteDataSource(remoteSource: RemoteDataSource<*>) {
        remoteDataSources.add(remoteSource)
    }

    fun onUnsubscribe() {
        for (remoteDataSource in remoteDataSources)
            remoteDataSource.cancel();
    }
}