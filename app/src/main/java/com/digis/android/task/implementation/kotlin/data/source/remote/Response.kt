package com.digis.android.task.implementation.kotlin.data.source.remote

import android.app.Application

sealed class Response<out D> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String?) : Response<Nothing>()

    companion object {
        @JvmStatic
        fun <T> create(httpResponse: HttpResponse<T>): Response<T> {
            return if (httpResponse.isSuccessful) {
                Success(data = httpResponse.data!!)
            } else {
                Error(message = null)
            }
        }

        @JvmStatic
        fun <T> create(app: Application, throwable: Throwable): Response<T> =
                Error(message = ErrorHandler.getMessage(app, throwable))

        private val <T> HttpResponse<T>.isSuccessful get() = (code in 200..299) && data != null
    }
}