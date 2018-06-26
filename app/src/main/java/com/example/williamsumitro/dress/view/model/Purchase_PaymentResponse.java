package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 16/06/2018.
 */

public class Purchase_PaymentResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<Purchase_PaymentResult> purchasePaymentResult = null;
    @SerializedName("bank")
    @Expose
    private ArrayList<Bank> bank = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<Purchase_PaymentResult> getPurchasePaymentResult() {
        return purchasePaymentResult;
    }

    public void setPurchasePaymentResult(ArrayList<Purchase_PaymentResult> purchasePaymentResult) {
        this.purchasePaymentResult = purchasePaymentResult;
    }

    public ArrayList<Bank> getBank() {
        return bank;
    }

    public void setBank(ArrayList<Bank> bank) {
        this.bank = bank;
    }
}
