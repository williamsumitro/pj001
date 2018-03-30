package com.example.williamsumitro.dress.view.model;

import java.util.List;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class Bag {
    private List<Shipping> shippingList;
    private List<Bank> bankList;
    private String namatoko, namaproduk;
    private int gambarproduk, hargaproduk, qtyS, qtyM, qtyL, qtyXL, persen, discount, totalharga, hargaS, hargaM, hargaL, hargaXL, qtySubtotal, hargaSubtotal;
    public Bag(String namatoko, String namaproduk, int gambarproduk, int hargaproduk, int qtyS, int qtyM, int qtyL, int qtyXL, int qtySubtotal, int persen, int discount, int hargaSubtotal, int totalharga, int hargaS, int hargaM, int hargaL, int hargaXL){
        this.namatoko = namatoko;
        this.namaproduk = namaproduk;
        this.gambarproduk = gambarproduk;
        this.hargaproduk = hargaproduk;
        this.qtyS = qtyS;
        this.qtyM = qtyM;
        this.qtyL = qtyL;
        this.qtyXL = qtyXL;
        this.persen = persen;
        this.discount = discount;
        this.totalharga = totalharga;
        this.hargaS = hargaS;
        this.hargaM = hargaM;
        this.hargaL = hargaL;
        this.hargaXL = hargaXL;
        this.qtySubtotal = qtySubtotal;
        this.hargaSubtotal = hargaSubtotal;
    }
    public Bag(String namatoko, String namaproduk, int gambarproduk, int hargaproduk, int qtyS, int qtyM, int qtyL,
               int qtyXL, int qtySubtotal, int persen, int discount, int hargaSubtotal, int totalharga, int hargaS,
               int hargaM, int hargaL, int hargaXL, List<Shipping> shippingList, List<Bank> bankList){
        this.namatoko = namatoko;
        this.namaproduk = namaproduk;
        this.gambarproduk = gambarproduk;
        this.hargaproduk = hargaproduk;
        this.qtyS = qtyS;
        this.qtyM = qtyM;
        this.qtyL = qtyL;
        this.qtyXL = qtyXL;
        this.persen = persen;
        this.discount = discount;
        this.totalharga = totalharga;
        this.hargaS = hargaS;
        this.hargaM = hargaM;
        this.hargaL = hargaL;
        this.hargaXL = hargaXL;
        this.qtySubtotal = qtySubtotal;
        this.hargaSubtotal = hargaSubtotal;
        this.shippingList = shippingList;
        this.bankList = bankList;
    }
    public String getNamatoko() {
        return namatoko;
    }

    public void setNamatoko(String namatoko) {
        this.namatoko = namatoko;
    }

    public String getNamaproduk() {
        return namaproduk;
    }

    public void setNamaproduk(String namaproduk) {
        this.namaproduk = namaproduk;
    }

    public int getGambarproduk() {
        return gambarproduk;
    }

    public void setGambarproduk(int gambarproduk) {
        this.gambarproduk = gambarproduk;
    }

    public int getHargaproduk() {
        return hargaproduk;
    }

    public void setHargaproduk(int hargaproduk) {
        this.hargaproduk = hargaproduk;
    }

    public int getQtyS() {
        return qtyS;
    }

    public void setQtyS(int qtyS) {
        this.qtyS = qtyS;
    }

    public int getQtyM() {
        return qtyM;
    }

    public void setQtyM(int qtyM) {
        this.qtyM = qtyM;
    }

    public int getQtyL() {
        return qtyL;
    }

    public void setQtyL(int qtyL) {
        this.qtyL = qtyL;
    }

    public int getQtyXL() {
        return qtyXL;
    }

    public void setQtyXL(int qtyXL) {
        this.qtyXL = qtyXL;
    }

    public int getPersen() {
        return persen;
    }

    public void setPersen(int persen) {
        this.persen = persen;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalharga() {
        return totalharga;
    }

    public void setTotalharga(int totalharga) {
        this.totalharga = totalharga;
    }

    public int getHargaS() {
        return hargaS;
    }

    public void setHargaS(int hargaS) {
        this.hargaS = hargaS;
    }

    public int getHargaM() {
        return hargaM;
    }

    public void setHargaM(int hargaM) {
        this.hargaM = hargaM;
    }

    public int getHargaL() {
        return hargaL;
    }

    public void setHargaL(int hargaL) {
        this.hargaL = hargaL;
    }

    public int getHargaXL() {
        return hargaXL;
    }

    public void setHargaXL(int hargaXL) {
        this.hargaXL = hargaXL;
    }

    public int getQtySubtotal() {
        return qtySubtotal;
    }

    public void setQtySubtotal(int qtySubtotal) {
        this.qtySubtotal = qtySubtotal;
    }

    public int getHargaSubtotal() {
        return hargaSubtotal;
    }

    public void setHargaSubtotal(int hargaSubtotal) {
        this.hargaSubtotal = hargaSubtotal;
    }

    public List<Shipping> getShippingList() {
        return shippingList;
    }

    public void setShippingList(List<Shipping> shippingList) {
        this.shippingList = shippingList;
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(List<Bank> bankList) {
        this.bankList = bankList;
    }
}
