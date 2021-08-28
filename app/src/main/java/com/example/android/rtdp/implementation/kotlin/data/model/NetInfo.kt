package com.example.android.rtdp.implementation.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class NetInfo(
    @field:SerializedName("RSRP")
    val RSRP: Int,

    @field:SerializedName("RSRQ")
    val RSRQ: Int,

    @field:SerializedName("SINR")
    val SINR: Int
)