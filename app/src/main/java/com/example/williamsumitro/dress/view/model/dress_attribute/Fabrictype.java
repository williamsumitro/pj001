package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Fabrictype {
    @SerializedName("fabrictype_id")
    @Expose
    private Integer fabrictypeId;
    @SerializedName("fabrictype_name")
    @Expose
    private String fabrictypeName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getFabrictypeId() {
        return fabrictypeId;
    }

    public void setFabrictypeId(Integer fabrictypeId) {
        this.fabrictypeId = fabrictypeId;
    }

    public String getFabrictypeName() {
        return fabrictypeName;
    }

    public void setFabrictypeName(String fabrictypeName) {
        this.fabrictypeName = fabrictypeName;
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
