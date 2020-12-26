package com.digis.android.task.data;

import android.app.Application;

import com.digis.android.task.data.model.NetInfo;
import com.digis.android.task.data.service.MainService;
import com.digis.android.task.data.source.remote.RemoteDataSource;
import com.digis.android.task.data.source.remote.RemoteResponseHandler;
import com.digis.android.task.data.source.remote.Response;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public final class NetInfoRepository extends BaseRepository {

    private final MainService mService;

    @Inject
    public NetInfoRepository(Application app, MainService service) {
        super(app);
        mService = service;
    }

    public LiveData<Response<NetInfo>> getNetInfo() {
        MutableLiveData<Response<NetInfo>> liveData = new MutableLiveData<>();
        RemoteDataSource<NetInfo> remoteSource = new RemoteDataSource<>(mService.getNetInfo());
        remoteSource.execute(new RemoteResponseHandler<>(getApp(), liveData));
        addRemoteDataSource(remoteSource);
        return liveData;
    }

}
