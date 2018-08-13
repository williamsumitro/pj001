package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private ArrayList<NotificationResult> result = null;
    @SerializedName("count_unread")
    @Expose
    private Integer countUnread;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<NotificationResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<NotificationResult> result) {
        this.result = result;
    }

    public Integer getCountUnread() {
        return countUnread;
    }

    public void setCountUnread(Integer countUnread) {
        this.countUnread = countUnread;
    }
}
