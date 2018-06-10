package com.example.williamsumitro.dress.view.model.dress_attribute;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Patterntype {
    @SerializedName("patterntype_id")
    @Expose
    private Integer patterntypeId;
    @SerializedName("patterntype_name")
    @Expose
    private String patterntypeName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getPatterntypeId() {
        return patterntypeId;
    }

    public void setPatterntypeId(Integer patterntypeId) {
        this.patterntypeId = patterntypeId;
    }

    public String getPatterntypeName() {
        return patterntypeName;
    }

    public void setPatterntypeName(String patterntypeName) {
        this.patterntypeName = patterntypeName;
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
