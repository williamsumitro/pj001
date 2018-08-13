package com.example.williamsumitro.dress.view.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Purchase_RejectResult implements Serializable {
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
    private Integer companyBankId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("amount")
    @Expose
    private Integer amount;
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
    private Object note;
    @SerializedName("receive_amount")
    @Expose
    private Integer receiveAmount;
    @SerializedName("reject_comment")
    @Expose
    private String rejectComment;
    @SerializedName("company_bank_name")
    @Expose
    private String companyBankName;
    @SerializedName("company_bank_branch")
    @Expose
    private String companyBankBranch;
    @SerializedName("company_bank_account_number")
    @Expose
    private String companyBankAccountNumber;
    @SerializedName("company_bank_name_account")
    @Expose
    private String companyBankNameAccount;
    @SerializedName("company_bank_logo")
    @Expose
    private String companyBankLogo;
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

    public Integer getCompanyBankId() {
        return companyBankId;
    }

    public void setCompanyBankId(Integer companyBankId) {
        this.companyBankId = companyBankId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Integer getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Integer receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

    public String getCompanyBankName() {
        return companyBankName;
    }

    public void setCompanyBankName(String companyBankName) {
        this.companyBankName = companyBankName;
    }

    public String getCompanyBankBranch() {
        return companyBankBranch;
    }

    public void setCompanyBankBranch(String companyBankBranch) {
        this.companyBankBranch = companyBankBranch;
    }

    public String getCompanyBankAccountNumber() {
        return companyBankAccountNumber;
    }

    public void setCompanyBankAccountNumber(String companyBankAccountNumber) {
        this.companyBankAccountNumber = companyBankAccountNumber;
    }

    public String getCompanyBankNameAccount() {
        return companyBankNameAccount;
    }

    public void setCompanyBankNameAccount(String companyBankNameAccount) {
        this.companyBankNameAccount = companyBankNameAccount;
    }

    public String getCompanyBankLogo() {
        return companyBankLogo;
    }

    public void setCompanyBankLogo(String companyBankLogo) {
        this.companyBankLogo = companyBankLogo;
    }

    public ArrayList<OrderStore> getOrderStore() {
        return orderStore;
    }

    public void setOrderStore(ArrayList<OrderStore> orderStore) {
        this.orderStore = orderStore;
    }
}
