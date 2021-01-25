package com.digis.android.task.implementation.java.data.source.remote;

import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Headers;

@SuppressWarnings("unused")
public final class HttpResponse<D> {

    private final int code;
    private final Headers headers;
    private final D data;
    private final String errorBody;

    HttpResponse(int code, Headers headers, @Nullable D data, @Nullable String errorBody) {
        this.code = code;
        this.headers = headers;
        this.data = data;
        this.errorBody = errorBody;
    }

    public int getCode() {
        return code;
    }

    public Headers getHeaders() {
        return headers;
    }

    @Nullable
    public D getData() {
        return data;
    }

    @Nullable
    public String getErrorBody() {
        return errorBody;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
