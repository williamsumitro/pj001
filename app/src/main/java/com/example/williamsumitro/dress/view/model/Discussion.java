package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 21/03/2018.
 */

public class Discussion {
    private int image, countcomment;
    private String name, status, date, comment;
    public Discussion(int image, String name, String status, String date, String comment, int countcomment){
        this.image = image;
        this.name = name;
        this.status = status;
        this.date = date;
        this.comment = comment;
        this.countcomment = countcomment;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCountcomment() {
        return countcomment;
    }

    public void setCountcomment(int countcomment) {
        this.countcomment = countcomment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
