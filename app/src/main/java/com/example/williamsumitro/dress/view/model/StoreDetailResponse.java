package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.view.store.activity.DetailStore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by William Sumitro on 7/13/2018.
 */

public class StoreDetailResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private StoreDetails result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StoreDetails getResult() {
        return result;
    }

    public void setResult(StoreDetails result) {
        this.result = result;
    }
}
