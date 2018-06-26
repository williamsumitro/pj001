package com.example.williamsumitro.dress.view.model;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class CheckApproveOrderProduct {
    private String transaction_id;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    public CheckApproveOrderProduct(String transaction_id, ArrayList<ApproveOrderProduct> approveOrderProductArrayList){
        this.transaction_id = transaction_id;
        this.approveOrderProductArrayList = approveOrderProductArrayList;
    }
    public ArrayList<ApproveOrderProduct> getApproveOrderProductArrayList() {
        return approveOrderProductArrayList;
    }

    public void setApproveOrderProductArrayList(ArrayList<ApproveOrderProduct> approveOrderProductArrayList) {
        this.approveOrderProductArrayList = approveOrderProductArrayList;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
