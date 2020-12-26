package com.digis.android.task.data.source.remote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class RemoteResponseHandler<T> implements RemoteCallback<T> {
    private final Application mApp;
    private final MutableLiveData<Response<T>> mLiveData;

    public RemoteResponseHandler(@NonNull Application app, @NonNull MutableLiveData<Response<T>> liveData) {
        mApp = app;
        mLiveData = liveData;
    }

    @Override
    public final void onResponse(@NonNull HttpResponse<T> httpResponse) {
        onHandleResponse(httpResponse);
    }

    @Override
    public void onFailure(Throwable throwable) {
        mLiveData.setValue(Response.create(mApp, throwable, null));
    }

    @SuppressWarnings("unused")
    protected final MutableLiveData<Response<T>> getLiveData() {
        return mLiveData;
    }

    protected void onHandleResponse(@NonNull HttpResponse<T> httpResponse) {
        mLiveData.setValue(Response.create(httpResponse));
    }
}
