package com.digis.android.task.data.source.remote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Response<D> {
    private D data;
    private String message;
    private boolean isSuccessful;

    private Response() { }

    public static <T> Response<T> create(@NonNull HttpResponse<T> httpResponse) {
        Response<T> response = new Response<>();
        response.data = httpResponse.getData();
        response.isSuccessful = (httpResponse.getData() != null);
        return response;
    }

    public static <T> Response<T> create(@NonNull Application app, @NonNull Throwable throwable,
                                         @Nullable T data) {
        Response<T> response = new Response<>();
        response.data = data;
        response.message = ErrorHandler.getMessage(app, throwable);
        response.isSuccessful = false;
        return response;
    }

    public D getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
