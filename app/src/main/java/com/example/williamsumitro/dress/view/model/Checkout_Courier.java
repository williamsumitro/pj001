package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class Checkout_Courier {
    @SerializedName("store_id")
    @Expose
    private String store_id;
    @SerializedName("courier_id")
    @Expose
    private String courier_id;
    @SerializedName("courier_service")
    @Expose
    private String courier_service;
    @SerializedName("fee")
    @Expose
    private String fee;
    @SerializedName("note")
    @Expose
    private String note;

    public Checkout_Courier(String store_id, String courier_id, String courier_service, String fee, String note){
        this.store_id = store_id;
        this.courier_id = courier_id;
        this.courier_service = courier_service;
        this.fee = fee;
        this.note = note;
    }
    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getCourier_service() {
        return courier_service;
    }

    public void setCourier_service(String courier_service) {
        this.courier_service = courier_service;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
