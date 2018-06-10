package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Waiseline {
    @SerializedName("waiseline_id")
    @Expose
    private Integer waiselineId;
    @SerializedName("waiseline_name")
    @Expose
    private String waiselineName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getWaiselineId() {
        return waiselineId;
    }

    public void setWaiselineId(Integer waiselineId) {
        this.waiselineId = waiselineId;
    }

    public String getWaiselineName() {
        return waiselineName;
    }

    public void setWaiselineName(String waiselineName) {
        this.waiselineName = waiselineName;
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
