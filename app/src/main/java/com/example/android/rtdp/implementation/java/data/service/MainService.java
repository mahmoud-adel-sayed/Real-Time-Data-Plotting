package com.example.android.rtdp.implementation.java.data.service;

import com.example.android.rtdp.implementation.java.data.model.NetInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MainService {
    @GET("netInfo/{id}")
    Call<NetInfo> getNetInfo(@Path("id") int id);
}
