package com.example.android.rtdp.implementation.java.data.source.remote;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.rtdp.R;

import java.io.IOException;

public final class ErrorHandler {

    private ErrorHandler() { }

    @Nullable
    public static String getMessage(@NonNull Application app, @NonNull Throwable throwable) {
        if (throwable instanceof IOException) {
            return app.getString(R.string.err_no_internet_connection);
        } else {
            return throwable.getMessage();
        }
    }
}
