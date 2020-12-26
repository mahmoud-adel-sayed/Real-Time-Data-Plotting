package com.digis.android.task.data.source.remote;

import android.app.Application;

import com.digis.android.task.R;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class ErrorHandler {

    private ErrorHandler() { }

    @Nullable
    public static String getMessage(@NonNull Application app, @NonNull Throwable throwable) {
        if (throwable instanceof IOException) {
            return app.getString(R.string.err_no_internet_connection);
        }
        else {
            return throwable.getMessage();
        }
    }
}
