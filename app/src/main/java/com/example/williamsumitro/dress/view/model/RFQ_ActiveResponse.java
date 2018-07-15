package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class RFQ_ActiveResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<RFQ_ActiveResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<RFQ_ActiveResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<RFQ_ActiveResult> result) {
        this.result = result;
    }
}
