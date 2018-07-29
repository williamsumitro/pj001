package com.example.williamsumitro.dress.view.view.checkout.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.PaymentResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.adapter.BankRVAdapter;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

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
    private final static String CHECKOUT = "CHECKOUT";

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
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(CHECKOUT, "CHECKOUT");
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
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(PAYMENTRESPONSE)){
            Gson gson = new Gson();
            String json = getIntent().getStringExtra(PAYMENTRESPONSE);
            paymentResponse = gson.fromJson(json, PaymentResponse.class);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
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

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
