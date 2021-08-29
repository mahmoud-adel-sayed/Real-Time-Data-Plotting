package com.example.android.rtdp.implementation.java.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.rtdp.implementation.java.data.model.NetInfo;
import com.example.android.rtdp.implementation.java.data.service.MainService;
import com.example.android.rtdp.implementation.java.data.source.remote.RemoteDataSource;
import com.example.android.rtdp.implementation.java.data.source.remote.RemoteResponseHandler;
import com.example.android.rtdp.implementation.java.data.source.remote.Response;

import java.util.Random;

import javax.inject.Inject;

public final class NetInfoRepository extends BaseRepository {
    private static final int BOUND = 30;
    private static final Random RANDOM = new Random();

    private final MainService mService;

    @Inject
    public NetInfoRepository(Application app, MainService service) {
        super(app);
        mService = service;
    }

    public LiveData<Response<NetInfo>> getNetInfo() {
        int id = RANDOM.nextInt(BOUND) + 1;
        MutableLiveData<Response<NetInfo>> liveData = new MutableLiveData<>();
        RemoteDataSource<NetInfo> remoteSource = new RemoteDataSource<>(mService.getNetInfo(id));
        remoteSource.execute(new RemoteResponseHandler<>(getApp(), liveData));
        addRemoteDataSource(remoteSource);
        return liveData;
    }
}
