package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 01/04/2018.
 */

public class ProductRating {
    private int image, price;
    private Double rate;
    private String name, review;
    public ProductRating(int image, String name, int price){
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
