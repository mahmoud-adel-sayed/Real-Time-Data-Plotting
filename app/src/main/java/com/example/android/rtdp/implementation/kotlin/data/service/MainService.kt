package com.example.android.rtdp.implementation.kotlin.data.service

import androidx.lifecycle.LiveData
import com.example.android.rtdp.implementation.kotlin.data.model.NetInfo
import com.example.android.rtdp.implementation.kotlin.data.source.remote.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {
    @GET("netInfo/{id}")
    fun getNetInfo(@Path("id") id: Int): LiveData<Response<NetInfo>>
}