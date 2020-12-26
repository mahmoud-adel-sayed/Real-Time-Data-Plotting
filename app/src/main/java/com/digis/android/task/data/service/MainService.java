package com.digis.android.task.data.service;

import com.digis.android.task.data.model.NetInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainService {
    @GET("random")
    Call<NetInfo> getNetInfo();
}
