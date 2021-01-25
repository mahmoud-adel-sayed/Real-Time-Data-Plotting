package com.digis.android.task.implementation.kotlin.data.source.remote

import android.app.Application
import com.digis.android.task.R
import java.io.IOException

object ErrorHandler {
    @JvmStatic
    fun getMessage(app: Application, throwable: Throwable): String? {
        return if (throwable is IOException) {
            app.getString(R.string.err_no_internet_connection)
        } else {
            throwable.message
        }
    }
}