package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 01/04/2018.
 */

public class ProductRating {
    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("review")
    @Expose
    private String review;
    public ProductRating(String product_id, String rating, String review){
        this.product_id = product_id;
        this.review = review;
        this.rating = rating;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
