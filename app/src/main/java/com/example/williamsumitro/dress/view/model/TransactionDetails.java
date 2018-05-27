package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 26/05/2018.
 */

public class TransactionDetails {
    private String date, transaction, note;
    private long debit, credit, balance;
    public TransactionDetails(String date, String transaction, String note, long debit, long credit, long balance){
        this.date = date;
        this.transaction = transaction;
        this.note = note;
        this.debit = debit;
        this.credit = credit;
        this.balance = balance;
    }
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getDebit() {
        return debit;
    }

    public void setDebit(long debit) {
        this.debit = debit;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
