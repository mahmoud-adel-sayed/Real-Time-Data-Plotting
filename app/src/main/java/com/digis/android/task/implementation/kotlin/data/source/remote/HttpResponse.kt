package com.digis.android.task.implementation.kotlin.data.source.remote

import com.google.gson.Gson
import okhttp3.Headers

@Suppress("unused")
class HttpResponse<out D>(
        val code: Int,
        val headers: Headers,
        val data: D?,
        val errorBody: String?
) {
    override fun toString(): String = Gson().toJson(this)
}