package com.digis.android.task.implementation.kotlin.data.service

import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import retrofit2.Call
import retrofit2.http.GET

interface MainService {
    @GET("random")
    fun getNetInfo(): Call<NetInfo>
}