package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CourierService implements Serializable{

    @SerializedName("courier_id")
    @Expose
    private String courierId;
    @SerializedName("courier_name")
    @Expose
    private String courierName;
    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("cost")
    @Expose
    private List<Cost> cost = null;


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
