package com.digis.android.task.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;

@SuppressWarnings("unused")
public class NetInfo {

    @SerializedName("RSRP")
    private int RSRP;

    @SerializedName("RSRQ")
    private int RSRQ;

    @SerializedName("SINR")
    private int SINR;

    public int getRSRP() {
        return RSRP;
    }

    public int getRSRQ() {
        return RSRQ;
    }

    public int getSINR() {
        return SINR;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        NetInfo other = (NetInfo) o;
        return (this.RSRP == other.RSRP) && (this.RSRQ == other.RSRQ) && (this.SINR == other.SINR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RSRP, RSRQ, SINR);
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
