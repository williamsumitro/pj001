package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CourierService {

    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("courier_id")
    @Expose
    private String courierId;
    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("alias_name")
    @Expose
    private String aliasName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("cost")
    @Expose
    private List<Cost> cost = null;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }
    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<Cost> getCost() {
        return cost;
    }

    public void setCost(List<Cost> cost) {
        this.cost = cost;
    }
}
