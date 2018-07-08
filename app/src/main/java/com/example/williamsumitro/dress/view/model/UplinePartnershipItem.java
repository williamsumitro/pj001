package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;

/**
 * Created by William Sumitro on 7/7/2018.
 */

public class UplinePartnershipItem {
    private String ordernumber, orderdate, productname, storename, image, product_id;
    public UplinePartnershipItem(String ordernumber, String orderdate, String productname, String storename, String image, String product_id){
        this.ordernumber = ordernumber;
        this.orderdate = orderdate;
        this.productname = productname;
        this.storename = storename;
        this.image = image;
        this.product_id = product_id;
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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
