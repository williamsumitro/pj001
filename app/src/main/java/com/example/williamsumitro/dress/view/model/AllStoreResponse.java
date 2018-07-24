package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 24/07/2018.
 */

public class AllStoreResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("store_info")
    @Expose
    private ArrayList<StoreInfo> storeInfo = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<StoreInfo> getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(ArrayList<StoreInfo> storeInfo) {
        this.storeInfo = storeInfo;
    }
}
