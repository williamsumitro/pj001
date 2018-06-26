package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class ApproveOrder {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;
    @SerializedName("store_id")
    @Expose
    private String store_id;
    @SerializedName("product")
    @Expose
    private ArrayList<ApproveOrderProduct> product;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public ArrayList<ApproveOrderProduct> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ApproveOrderProduct> product) {
        this.product = product;
    }
}
