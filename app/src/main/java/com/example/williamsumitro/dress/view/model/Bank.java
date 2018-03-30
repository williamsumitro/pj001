package com.example.williamsumitro.dress.view.model;

/**
 * Created by WilliamSumitro on 24/03/2018.
 */

public class Bank {
    private int image;
    private String bankname, accountnumber, accountholder;
    public Bank(String bankname, String accountholder, String accountnumber){
        this.bankname = bankname;
        this.accountholder = accountholder;
        this.accountnumber = accountnumber;
    }
    public Bank(int image){
        this.image = image;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccountholder() {
        return accountholder;
    }

    public void setAccountholder(String accountholder) {
        this.accountholder = accountholder;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
}
