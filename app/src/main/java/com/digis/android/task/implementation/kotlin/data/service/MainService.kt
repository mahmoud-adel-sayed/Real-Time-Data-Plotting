package com.digis.android.task.implementation.kotlin.data.service

import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {
    @GET("netInfo/{id}")
    fun getNetInfo(@Path("id") id: Int): Call<NetInfo>
}