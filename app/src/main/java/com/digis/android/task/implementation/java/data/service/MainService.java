package com.digis.android.task.implementation.java.data.service;

import com.digis.android.task.implementation.java.data.model.NetInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainService {
    @GET("random")
    Call<NetInfo> getNetInfo();
}
