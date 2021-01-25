package com.digis.android.task.implementation.kotlin.data.source.remote

/**
 * Communicates responses from a remote request.
 *
 * @param <T> Successful response type.
 */
interface RemoteCallback<T> {
    /**
     * Invoked with [httpResponse] when the request has been succeeded.
     */
    fun onResponse(httpResponse: HttpResponse<T>)

    /**
     * Invoked with [throwable] when the request has been failed.
     */
    fun onFailure(throwable: Throwable)

    /**
     * Invoked when the request has been canceled.
     */
    fun onCanceled() { }
}