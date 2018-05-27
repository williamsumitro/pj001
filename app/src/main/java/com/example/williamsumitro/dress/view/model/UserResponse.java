package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 14/05/2018.
 */

public class UserResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private UserDetails userdetails;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserDetails getUserDetails() {
        return userdetails;
    }

    public void setUserDetails(UserDetails userdetails) {
        this.userdetails = userdetails;
    }
}
