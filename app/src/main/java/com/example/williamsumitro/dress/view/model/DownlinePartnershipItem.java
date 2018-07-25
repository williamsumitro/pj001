package com.example.williamsumitro.dress.view.model;

import java.util.ArrayList;

/**
 * Created by William Sumitro on 7/8/2018.
 */

public class DownlinePartnershipItem {
    private String store_name_partner;
    private ProductInfo product;
    public DownlinePartnershipItem(String store_name_partner, ProductInfo product){
        this.store_name_partner = store_name_partner;
        this.product = product;
    }


    public String getStore_name_partner() {
        return store_name_partner;
    }

    public void setStore_name_partner(String store_name_partner) {
        this.store_name_partner = store_name_partner;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }
}
