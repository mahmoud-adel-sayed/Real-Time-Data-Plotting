package com.digis.android.task;

import com.digis.android.task.data.NetInfoRepository;
import com.digis.android.task.data.model.NetInfo;
import com.digis.android.task.data.source.remote.Response;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public final class MainViewModel extends ViewModel {
    private final NetInfoRepository mRepository;

    private final MutableLiveData<String> mNetInfo;
    private final LiveData<Response<NetInfo>> mNetInfoObservable;

    @Inject
    public MainViewModel(NetInfoRepository repository) {
        mRepository = repository;
        mNetInfo = new MutableLiveData<>();
        mNetInfoObservable = Transformations.switchMap(mNetInfo, input -> repository.getNetInfo());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository.onUnsubscribe();
    }

    public void getNetInfo() {
        mNetInfo.setValue("");
    }

    public LiveData<Response<NetInfo>> getNetInfoObservable() {
        return mNetInfoObservable;
    }
}
