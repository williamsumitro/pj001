package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class SubmitReviewRating {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("transaction_id")
    @Expose
    private String transaction_id;
    @SerializedName("store_id")
    @Expose
    private String store_id;
    @SerializedName("store_rating")
    @Expose
    private String store_rating;
    @SerializedName("product_rating")
    @Expose
    private ArrayList<ProductRating> product_rating;

    public SubmitReviewRating(String token, String transaction_id, String store_id, String store_rating, ArrayList<ProductRating> product_rating){
        this.token = token;
        this.transaction_id = transaction_id;
        this.store_id = store_id;
        this.store_rating = store_rating;
        this.product_rating = product_rating;
    }

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

    public String getStore_rating() {
        return store_rating;
    }

    public void setStore_rating(String store_rating) {
        this.store_rating = store_rating;
    }
}
