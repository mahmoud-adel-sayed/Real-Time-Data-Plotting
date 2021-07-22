package com.digis.android.task.implementation.kotlin.data.service

import androidx.lifecycle.LiveData
import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import com.digis.android.task.implementation.kotlin.data.source.remote.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {
    @GET("netInfo/{id}")
    fun getNetInfo(@Path("id") id: Int): LiveData<Response<NetInfo>>
}