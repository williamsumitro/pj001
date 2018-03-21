package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 15/03/2018.
 */

public class Review {
    private String namauser, review, tanggal;
    private int gambaruser;
    private double rating;
    public Review(String namauser, String review, String tanggal, int gambaruser, double rating){
        this.namauser = namauser;
        this.review = review;
        this.tanggal = tanggal;
        this.gambaruser = gambaruser;
        this.rating = rating;
    }

    public String getNamauser() {
        return namauser;
    }

    public void setNamauser(String namauser) {
        this.namauser = namauser;
    }

    public String getReview() {
        return review;
    }

    public int getGambaruser() {
        return gambaruser;
    }

    public void setGambaruser(int gambaruser) {
        this.gambaruser = gambaruser;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
