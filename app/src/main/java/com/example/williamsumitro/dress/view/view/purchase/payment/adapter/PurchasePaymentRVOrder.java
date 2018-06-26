package com.example.williamsumitro.dress.view.view.purchase.payment.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 16/06/2018.
 */

public class PurchasePaymentRVOrder extends RecyclerView.Adapter<PurchasePaymentRVOrder.ViewHolder> {
    private Context context;
    private ArrayList<OrderStore> orderStoreArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private CheckoutProductRVAdapter rvadapter;

    public PurchasePaymentRVOrder(Context context, ArrayList<OrderStore> orderStoreArrayList){
        this.context = context;
        this.orderStoreArrayList = orderStoreArrayList;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public PurchasePaymentRVOrder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pporderdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PurchasePaymentRVOrder.ViewHolder holder, int position) {
        OrderStore orderStore = orderStoreArrayList.get(position);
        holder.ordernumber.setText(orderStore.getOrderNumber());
        holder.storename.setText("From " + orderStore.getStoreName() + " Store");
        holder.shippingaddress.setText("Shipping Address : " + orderStore.getAddress());
        holder.total.setText("Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderStore.getTotalPrice()))));
        holder.subtotal.setText("Sub Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderStore.getSubtotalPrice()))));
        holder.shipping.setText("Shipping Fee : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderStore.getShippingPrice()))));
        if (orderStore.getNote()== null)
            holder.note.setText("No note");
        else
            holder.note.setText("Note : " + orderStore.getNote());
        rvadapter = new CheckoutProductRVAdapter(orderStore.getProduct(), context);
        SnapHelper snapHelper = new LinearSnapHelper();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(rvadapter);
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return orderStoreArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_pporderdetail_tv_total) TextView total;
        @BindView(R.id.item_pporderdetail_tv_subtotal) TextView subtotal;
        @BindView(R.id.item_pporderdetail_tv_storename) TextView storename;
        @BindView(R.id.item_pporderdetail_tv_shippingaddress) TextView shippingaddress;
        @BindView(R.id.item_pporderdetail_tv_shipping) TextView shipping;
        @BindView(R.id.item_pporderdetail_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_pporderdetail_tv_note) TextView note;
        @BindView(R.id.item_pporderdetail_rv) RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
