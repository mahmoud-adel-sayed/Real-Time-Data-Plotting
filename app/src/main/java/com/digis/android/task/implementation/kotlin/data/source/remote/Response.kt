package com.digis.android.task.implementation.kotlin.data.source.remote

import android.app.Application

class Response<out D> private constructor(
        val data: D?,
        val message: String?,
        val isSuccessful: Boolean
) {
    companion object {
        @JvmStatic
        fun <T> create(httpResponse: HttpResponse<T>): Response<T> {
            return Response(
                    data = httpResponse.data,
                    message = null,
                    isSuccessful = (httpResponse.data != null)
            )
        }

        @JvmStatic
        fun <T> create(app: Application, throwable: Throwable, data: T?): Response<T> {
            return Response(
                    data = data,
                    message = ErrorHandler.getMessage(app, throwable),
                    isSuccessful = false
            )
        }
    }
}