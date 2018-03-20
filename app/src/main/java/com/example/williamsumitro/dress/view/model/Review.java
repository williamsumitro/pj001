package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 15/03/2018.
 */

public class Review {
    private String namaproduk, namauser, review, jangkareview;
    private int gambarproduk, gambaruser, rating;
    public Review(String namaproduk, String namauser, String review, String jangkareview, int gambarproduk, int gambaruser, int rating){
        this.namaproduk = namaproduk;
        this.namauser = namauser;
        this.review = review;
        this.jangkareview = jangkareview;
        this.gambarproduk = gambarproduk;
        this.gambaruser = gambaruser;
        this.rating = rating;
    }

    public String getNamaproduk() {
        return namaproduk;
    }

    public void setNamaproduk(String namaproduk) {
        this.namaproduk = namaproduk;
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

    public String getJangkareview() {
        return jangkareview;
    }

    public void setJangkareview(String jangkareview) {
        this.jangkareview = jangkareview;
    }

    public int getGambarproduk() {
        return gambarproduk;
    }

    public void setGambarproduk(int gambarproduk) {
        this.gambarproduk = gambarproduk;
    }

    public int getGambaruser() {
        return gambaruser;
    }

    public void setGambaruser(int gambaruser) {
        this.gambaruser = gambaruser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
