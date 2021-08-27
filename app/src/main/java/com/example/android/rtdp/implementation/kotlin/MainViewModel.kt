package com.example.android.rtdp.implementation.kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.android.rtdp.implementation.kotlin.data.NetInfoRepository
import com.example.android.rtdp.implementation.kotlin.data.model.NetInfo
import com.example.android.rtdp.implementation.kotlin.data.source.remote.Response
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NetInfoRepository
) : ViewModel() {

    private val _netInfo: MutableLiveData<String> = MutableLiveData()
    val netInfo: LiveData<Response<NetInfo>> = _netInfo.switchMap { repository.getNetInfo() }

    fun getNetInfo() {
        _netInfo.value = ""
    }
}