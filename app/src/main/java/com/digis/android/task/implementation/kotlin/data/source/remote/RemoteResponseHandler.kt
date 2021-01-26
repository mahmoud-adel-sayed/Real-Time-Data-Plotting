package com.digis.android.task.implementation.kotlin.data.source.remote

import android.app.Application
import androidx.lifecycle.MutableLiveData

open class RemoteResponseHandler<T>(
        private val app: Application,
        private val liveData: MutableLiveData<Response<T>>
) : RemoteCallback<T> {
    override fun onResponse(httpResponse: HttpResponse<T>) {
        liveData.value = Response.create(httpResponse)
    }

    override fun onFailure(throwable: Throwable) {
        liveData.value = Response.create(app, throwable, null)
    }
}