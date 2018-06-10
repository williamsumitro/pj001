package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Sleevelength {
    @SerializedName("sleevelength_id")
    @Expose
    private Integer sleevelengthId;
    @SerializedName("sleevelength_name")
    @Expose
    private String sleevelengthName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getSleevelengthId() {
        return sleevelengthId;
    }

    public void setSleevelengthId(Integer sleevelengthId) {
        this.sleevelengthId = sleevelengthId;
    }

    public String getSleevelengthName() {
        return sleevelengthName;
    }

    public void setSleevelengthName(String sleevelengthName) {
        this.sleevelengthName = sleevelengthName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
