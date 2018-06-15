package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamSumitro on 15/06/2018.
 */

public class Data {
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("bank")
    @Expose
    private ArrayList<Bank> bank = null;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Bank> getBank() {
        return bank;
    }

    public void setBank(ArrayList<Bank> bank) {
        this.bank = bank;
    }
}
