package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class Checkout {
    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("receiver_name")
    @Expose
    private String receiver_name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("province")
    @Expose
    private String province;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("postal_code")
    @Expose
    private String postal_code;

    @SerializedName("use_point")
    @Expose
    private String use_point;

    @SerializedName("courier")
    @Expose
    private ArrayList<Checkout_Courier> courier;

    public Checkout(String token, String receiver_name, String address, String province, String city, String phone_number, String postal_code, String use_point, ArrayList<Checkout_Courier> courier){
        this.token = token;
        this.receiver_name = receiver_name;
        this.address = address;
        this.province = province;
        this.city = city;
        this.phone_number = phone_number;
        this.postal_code = postal_code;
        this.use_point = use_point;
        this.courier = courier;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getUse_point() {
        return use_point;
    }

    public void setUse_point(String use_point) {
        this.use_point = use_point;
    }

    public ArrayList<Checkout_Courier> getCourier() {
        return courier;
    }

    public void setCourier(ArrayList<Checkout_Courier> courier) {
        this.courier = courier;
    }
}
