package com.digis.android.task.data.source.remote;

import java.io.IOException;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class RemoteDataSource<V> {
    private Call<V> mCall;
    private RemoteCallback<V> mCallback;

    public RemoteDataSource(@NonNull Call<V> call) {
        mCall = call;
    }

    public void execute(@NonNull RemoteCallback<V> callback) {
        mCallback = callback;
        if (!mCall.isExecuted()) {
            mCall.enqueue(new ResponseCallback());
        }
    }

    @SuppressWarnings("unused")
    public void retry(@NonNull RemoteCallback<V> callback) {
        mCallback = callback;
        mCall = mCall.clone();
        mCall.enqueue(new ResponseCallback());
    }

    public void cancel() {
        if (mCall != null && mCall.isExecuted()) {
            mCall.cancel();
        }
        mCallback.onCanceled();
    }

    private final class ResponseCallback implements Callback<V> {
        @Override
        public void onResponse(Call<V> call, Response<V> response) {
            V value = null;
            String errorBody = null;
            if (response.isSuccessful()) {
                value = response.body();
            }
            else {
                try { errorBody = response.errorBody().string(); }
                catch (IOException ignore) { }
            }
            HttpResponse<V> httpResponse = new HttpResponse<>(
                    response.code(), response.headers(), value, errorBody
            );
            mCallback.onResponse(httpResponse);
        }

        @Override
        public void onFailure(Call<V> call, Throwable throwable) {
            if (!call.isCanceled()) {
                mCallback.onFailure(throwable);
            }
        }
    }
}