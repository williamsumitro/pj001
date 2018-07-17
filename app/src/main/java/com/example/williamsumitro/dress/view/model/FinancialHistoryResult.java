package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class FinancialHistoryResult implements Serializable {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("transaction")
    @Expose
    private String transaction;
    @SerializedName("debit")
    @Expose
    private Integer debit;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("note")
    @Expose
    private String note;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Integer getDebit() {
        return debit;
    }

    public void setDebit(Integer debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
