package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Neckline {
    @SerializedName("neckline_id")
    @Expose
    private Integer necklineId;
    @SerializedName("neckline_name")
    @Expose
    private String necklineName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getNecklineId() {
        return necklineId;
    }

    public void setNecklineId(Integer necklineId) {
        this.necklineId = necklineId;
    }

    public String getNecklineName() {
        return necklineName;
    }

    public void setNecklineName(String necklineName) {
        this.necklineName = necklineName;
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
