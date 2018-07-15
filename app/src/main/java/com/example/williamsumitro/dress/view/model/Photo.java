package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class Photo implements Serializable {
    @SerializedName("file_path")
    @Expose
    private String filePath;

    public String getFilePath() {
        return apiUtils.getUrlImage() + filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
