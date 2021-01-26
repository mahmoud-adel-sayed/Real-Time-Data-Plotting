package com.digis.android.task.implementation.kotlin.data.source.remote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class RemoteDataSource<V>(private var call: Call<V>) {
    private var callback: RemoteCallback<V>? = null

    fun execute(callback: RemoteCallback<V>) {
        this.callback = callback
        if (!call.isExecuted) {
            call.enqueue(ResponseCallback())
        }
    }

    @Suppress("unused")
    fun retry(callback: RemoteCallback<V>) {
        this.callback = callback
        call = call.clone()
        call.enqueue(ResponseCallback())
    }

    fun cancel() {
        if (call.isExecuted) {
            call.cancel()
        }
        callback?.onCanceled()
    }

    private inner class ResponseCallback : Callback<V> {
        override fun onResponse(call: Call<V>?, r: Response<V>?) {
            var value: V? = null
            var errorBody: String? = null
            val response = r!!
            if (response.isSuccessful) {
                value = response.body()
            } else {
                try { errorBody = response.errorBody().string() }
                catch (ignore: IOException) { }
            }
            val httpResponse = HttpResponse(
                    response.code(), response.headers(), value, errorBody
            )
            callback?.onResponse(httpResponse)
        }

        override fun onFailure(call: Call<V>?, t: Throwable?) {
            val isCanceled = call?.isCanceled ?: false
            if (!isCanceled) callback?.onFailure(t!!)
        }
    }
}