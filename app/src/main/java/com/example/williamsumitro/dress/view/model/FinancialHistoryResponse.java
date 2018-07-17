package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class FinancialHistoryResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<FinancialHistoryResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<FinancialHistoryResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<FinancialHistoryResult> result) {
        this.result = result;
    }
}
