package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 16/06/2018.
 */

public class Result {
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("invoice_grand_total")
    @Expose
    private String invoiceGrandTotal;
    @SerializedName("invoice_date")
    @Expose
    private String invoiceDate;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("company_bank_id")
    @Expose
    private String companyBankId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("sender_bank")
    @Expose
    private String senderBank;
    @SerializedName("sender_account_number")
    @Expose
    private String senderAccountNumber;
    @SerializedName("sender_name")
    @Expose
    private String senderName;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("receive_amount")
    @Expose
    private String receiveAmount;
    @SerializedName("reject_comment")
    @Expose
    private String rejectComment;
    @SerializedName("order_store")
    @Expose
    private ArrayList<OrderStore> orderStore = null;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInvoiceGrandTotal() {
        return invoiceGrandTotal;
    }

    public void setInvoiceGrandTotal(String invoiceGrandTotal) {
        this.invoiceGrandTotal = invoiceGrandTotal;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCompanyBankId() {
        return companyBankId;
    }

    public void setCompanyBankId(String companyBankId) {
        this.companyBankId = companyBankId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSenderBank() {
        return senderBank;
    }

    public void setSenderBank(String senderBank) {
        this.senderBank = senderBank;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(String receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

    public ArrayList<OrderStore> getOrderStore() {
        return orderStore;
    }

    public void setOrderStore(ArrayList<OrderStore> orderStore) {
        this.orderStore = orderStore;
    }
}
