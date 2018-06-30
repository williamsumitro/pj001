package com.example.williamsumitro.dress.view.view.purchase.payment.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.payment.adapter.PurchasePaymentRVOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PP_InvoiceDetail extends AppCompatActivity {
    @BindView(R.id.purchasepaymentdetail_rv) RecyclerView recyclerView;
    @BindView(R.id.purchasepaymentdetail_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasepaymentdetail_tv_grandtotal) TextView grandtotal;
    @BindView(R.id.purchasepaymentdetail_tv_invoicenumber) TextView invoicenumber;


    private Context context;
    private apiService service;
    private String token, invoice_number, grand_total;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;


    private final static String ORDERSTORELIST = "ORDERSTORELIST";
    private final static String INVOICENUMBER = "INVOICENUMBER";
    private final static String GRANDTOTAL = "GRANDTOTAL";
    private ArrayList<OrderStore> orderStoreArrayList;
    private PurchasePaymentRVOrder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_invoicedetail);
        initView();
        setuptoolbar();
        initGetIntent();
        initData();
        setupRV();
    }

    private void initData() {
        grandtotal.setText("Grand Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(grand_total))));
        invoicenumber.setText("Invoice Number : " + invoice_number);
    }

    private void setupRV() {
        adapter = new PurchasePaymentRVOrder(context, orderStoreArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderStoreArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Invoice Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(ORDERSTORELIST)){
            orderStoreArrayList = (ArrayList<OrderStore>) getintent.getSerializableExtra(ORDERSTORELIST);
            invoice_number = getintent.getStringExtra(INVOICENUMBER);
            grand_total = getintent.getStringExtra(GRANDTOTAL);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
}
