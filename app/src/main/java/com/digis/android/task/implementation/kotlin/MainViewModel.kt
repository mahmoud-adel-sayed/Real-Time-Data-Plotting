package com.digis.android.task.implementation.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.digis.android.task.implementation.kotlin.data.NetInfoRepository
import com.digis.android.task.implementation.kotlin.data.model.NetInfo
import com.digis.android.task.implementation.kotlin.data.source.remote.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: NetInfoRepository
) : ViewModel() {

    private val netInfo: MutableLiveData<String> = MutableLiveData()
    val netInfoObservable: LiveData<Response<NetInfo>> = netInfo.switchMap {
        repository.getNetInfo()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onUnsubscribe()
    }

    fun getNetInfo() {
        netInfo.value = ""
    }
}