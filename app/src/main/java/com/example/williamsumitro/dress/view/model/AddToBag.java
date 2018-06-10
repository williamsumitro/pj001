package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class AddToBag {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_size_qty")
    @Expose
    private ArrayList<Product_Size_Qty> product_size_qtyArrayList = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public ArrayList<Product_Size_Qty> getProduct_size_qtyArrayList() {
        return product_size_qtyArrayList;
    }

    public void setProduct_size_qtyArrayList(ArrayList<Product_Size_Qty> product_size_qtyArrayList) {
        this.product_size_qtyArrayList = product_size_qtyArrayList;
    }
}
