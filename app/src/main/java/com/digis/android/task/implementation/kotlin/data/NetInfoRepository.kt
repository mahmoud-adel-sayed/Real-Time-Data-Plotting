package com.digis.android.task.implementation.kotlin.data

import androidx.lifecycle.LiveData
import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import com.digis.android.task.implementation.kotlin.data.service.MainService
import com.digis.android.task.implementation.kotlin.data.source.remote.Response
import java.util.*
import javax.inject.Inject

class NetInfoRepository @Inject constructor(private val service: MainService) {
    fun getNetInfo(): LiveData<Response<NetInfo>> =
            service.getNetInfo(id = Random().nextInt(BOUND) + 1)
}

private const val BOUND = 30