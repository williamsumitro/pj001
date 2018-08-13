package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Purchase_RejectResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<Purchase_RejectResult> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Purchase_RejectResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<Purchase_RejectResult> result) {
        this.result = result;
    }
}
