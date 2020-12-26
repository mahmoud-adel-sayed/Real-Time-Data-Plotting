package com.digis.android.task.data.source.remote;

import androidx.annotation.NonNull;

/**
 * Communicates responses from a remote request.
 *
 * @param <T> Successful response type.
 */
public interface RemoteCallback<T> {
    /**
     * Invoked when the request has been succeeded.
     *
     * @param httpResponse The response
     */
    void onResponse(@NonNull HttpResponse<T> httpResponse);

    /**
     * Invoked when the request has been failed.
     *
     * @param throwable The throwable
     */
    void onFailure(Throwable throwable);

    /**
     * Invoked when the request has been canceled.
     */
    default void onCanceled() { }
}
