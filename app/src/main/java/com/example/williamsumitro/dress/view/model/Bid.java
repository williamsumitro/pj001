package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 11/04/2018.
 */

public class Bid {
    private String bid_title, bid_storename, expired;
    private int bid_image, bid_accepted, bid_rejected, bid_pending;
    private boolean bid_status;
    public Bid(String bid_title, String bid_storename, int bid_image, int bid_accepted, int bid_rejected, int bid_pending, String expired, boolean bid_status){
        this.bid_title = bid_title;
        this.bid_storename = bid_storename;
        this.bid_image = bid_image;
        this.bid_accepted = bid_accepted;
        this.bid_rejected = bid_rejected;
        this.bid_pending = bid_pending;
        this.expired = expired;
        this.bid_status = bid_status;
    }

    public String getBid_title() {
        return bid_title;
    }

    public void setBid_title(String bid_title) {
        this.bid_title = bid_title;
    }

    public String getBid_storename() {
        return bid_storename;
    }

    public void setBid_storename(String bid_storename) {
        this.bid_storename = bid_storename;
    }

    public int getBid_image() {
        return bid_image;
    }

    public void setBid_image(int bid_image) {
        this.bid_image = bid_image;
    }

    public int getBid_accepted() {
        return bid_accepted;
    }

    public void setBid_accepted(int bid_accepted) {
        this.bid_accepted = bid_accepted;
    }

    public int getBid_rejected() {
        return bid_rejected;
    }

    public void setBid_rejected(int bid_rejected) {
        this.bid_rejected = bid_rejected;
    }

    public int getBid_pending() {
        return bid_pending;
    }

    public void setBid_pending(int bid_pending) {
        this.bid_pending = bid_pending;
    }


    public boolean isBid_status() {
        return bid_status;
    }

    public void setBid_status(boolean bid_status) {
        this.bid_status = bid_status;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
