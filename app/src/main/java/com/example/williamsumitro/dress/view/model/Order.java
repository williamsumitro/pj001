package com.example.williamsumitro.dress.view.model;

/**
 * Created by William Sumitro on 01/04/2018.
 */

public class Order {
    private int image, qtyS, qtyM, qtyL, qtyXL, priceS, priceM, priceL, priceXL, subtotal, price, total;
    private String namapakaian, reason, storename, ordernumber, orderdate, status, receiptnumber;
    public Order(int image, String namapakaian, int qtyS, int qtyM, int qtyL, int qtyXL, int priceS, int priceM, int priceL, int priceXL, int subtotal, int price, String reason){
        this.image = image;
        this.namapakaian = namapakaian;
        this.qtyS = qtyS;
        this.qtyM = qtyM;
        this.qtyL = qtyL;
        this.qtyXL = qtyXL;
        this.priceS = priceS;
        this.priceM = priceM;
        this.priceL = priceL;
        this.priceXL = priceXL;
        this.subtotal = subtotal;
        this.price = price;
        this.reason = reason;
    }
    public Order(String storename, String ordernumber, String orderdate, int total){
        this.ordernumber = ordernumber;
        this.orderdate = orderdate;
        this.total = total;
        this.storename = storename;
    }
    public Order(String storename, String ordernumber, String orderdate, int total, String status){
        this.ordernumber = ordernumber;
        this.orderdate = orderdate;
        this.total = total;
        this.storename = storename;
        this.status = status;
    }
    public Order(String storename, String ordernumber, String orderdate, String receiptnumber){
        this.ordernumber = ordernumber;
        this.orderdate = orderdate;
        this.storename = storename;
        this.receiptnumber = receiptnumber;
    }
    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNamapakaian() {
        return namapakaian;
    }

    public void setNamapakaian(String namapakaian) {
        this.namapakaian = namapakaian;
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

    public int getPriceS() {
        return priceS;
    }

    public void setPriceS(int priceS) {
        this.priceS = priceS;
    }

    public int getPriceM() {
        return priceM;
    }

    public void setPriceM(int priceM) {
        this.priceM = priceM;
    }

    public int getPriceL() {
        return priceL;
    }

    public void setPriceL(int priceL) {
        this.priceL = priceL;
    }

    public int getPriceXL() {
        return priceXL;
    }

    public void setPriceXL(int priceXL) {
        this.priceXL = priceXL;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptnumber() {
        return receiptnumber;
    }

    public void setReceiptnumber(String receiptnumber) {
        this.receiptnumber = receiptnumber;
    }
}
