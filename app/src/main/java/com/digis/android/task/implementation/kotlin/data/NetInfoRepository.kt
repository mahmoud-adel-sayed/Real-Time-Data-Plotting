package com.digis.android.task.implementation.kotlin.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import com.digis.android.task.implementation.kotlin.data.service.MainService
import com.digis.android.task.implementation.kotlin.data.source.remote.RemoteDataSource
import com.digis.android.task.implementation.kotlin.data.source.remote.RemoteResponseHandler
import com.digis.android.task.implementation.kotlin.data.source.remote.Response
import javax.inject.Inject

class NetInfoRepository @Inject constructor(
        app: Application,
        private val service: MainService
) : BaseRepository(app) {

    fun getNetInfo(): LiveData<Response<NetInfo>> {
        val liveData = MutableLiveData<Response<NetInfo>>()
        val remoteSource = RemoteDataSource(service.getNetInfo())
        remoteSource.execute(RemoteResponseHandler(app, liveData))
        addRemoteDataSource(remoteSource)
        return liveData
    }
}