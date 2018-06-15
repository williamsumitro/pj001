package com.example.williamsumitro.dress.view.model;

import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 24/03/2018.
 */

public class Bank {
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("name_in_account")
    @Expose
    private String nameInAccount;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNameInAccount() {
        return nameInAccount;
    }

    public void setNameInAccount(String nameInAccount) {
        this.nameInAccount = nameInAccount;
    }

    public String getLogo() {
        return apiUtils.getUrlImage() + logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
