package com.example.williamsumitro.dress.view.view.checkout.activity;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.PaymentResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PurchasePayment;
import com.example.williamsumitro.dress.view.view.purchase.adapter.BankRVAdapter;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutSuccess extends AppCompatActivity {
    @BindView(R.id.checkout_success_toolbar) Toolbar toolbar;
    @BindView(R.id.checkout_success_tv_total) TextView tv_total;
    @BindView(R.id.checkout_success_tv_invoicenumber) TextView tv_invoicenumber;
    @BindView(R.id.checkout_success_rv) RecyclerView recyclerView;
    @BindView(R.id.checkout_success_btn_confirmpayment) Button confirmpayment;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private PaymentResponse paymentResponse;
    private String invoicenumber, total;
    private ArrayList<Bank> bankArrayList;
    private BankRVAdapter adapter;

    private final static String PAYMENTRESPONSE = "PAYMENTRESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_success);
        initView();
        setuptoolbar();
        initGetIntent();
        initData();
        confirmpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchasePayment.class);
                initanim(intent);
                finish();
                Payment.PAYMENT.finish();
                ShoppingBag.SHOPPINGBAG.finish();
            }
        });
    }

    private void initData() {
        invoicenumber = String.valueOf(paymentResponse.getData().getTransactionId());
        total = paymentResponse.getData().getTotalPrice();
        bankArrayList = paymentResponse.getData().getBank();
        tv_invoicenumber.setText("Invoice Number : " + invoicenumber);
        tv_total.setText("IDR " + formatter.format(Double.parseDouble(total)));
        initRV();

    }

    private void initRV() {
        adapter = new BankRVAdapter(bankArrayList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(PAYMENTRESPONSE)){
            Gson gson = new Gson();
            String json = getIntent().getStringExtra(PAYMENTRESPONSE);
            paymentResponse = gson.fromJson(json, PaymentResponse.class);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        bankArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Shopping Bag");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
