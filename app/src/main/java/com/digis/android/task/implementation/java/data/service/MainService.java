package com.digis.android.task.implementation.java.data.service;

import com.digis.android.task.implementation.java.data.model.NetInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainService {
    @GET("netInfo/{id}")
    Call<NetInfo> getNetInfo(@Path("id") int id);
}
