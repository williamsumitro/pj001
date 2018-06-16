package com.example.williamsumitro.dress.view.view.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Purchase extends AppCompatActivity {
    @BindView(R.id.purchase_tvTransactionHistory) TextView tv_transactionhistory;
    @BindView(R.id.purchase_tvReviewandrating) TextView tv_reviewandrating;
    @BindView(R.id.purchase_tvReceiptConfirmation) TextView tv_receiptconfirmation;
    @BindView(R.id.purchase_tvPayment) TextView tv_payment;
    @BindView(R.id.purchase_tvOrderStatus) TextView tv_orderstatus;
    @BindView(R.id.purchase_toolbar) Toolbar toolbar;
    @BindView(R.id.purchase_lnTransactionHistory) LinearLayout container_transactionhistory;
    @BindView(R.id.purchase_lnReviewandrating) LinearLayout container_reviewandrating;
    @BindView(R.id.purchase_lnReceiptconfirmation) LinearLayout container_receiptconfirmation;
    @BindView(R.id.purchase_lnPayment) LinearLayout container_payment;
    @BindView(R.id.purchase_lnOrderstatus) LinearLayout container_orderstatus;
    @BindView(R.id.purchase_img_exclmationtransactionhisotry) ImageView ex_transactionhistory;
    @BindView(R.id.purchase_img_exclmationreviewandrating) ImageView ex_reviewandrating;
    @BindView(R.id.purchase_img_exclmationreceiptconfirmation) ImageView ex_receiptconfirmation;
    @BindView(R.id.purchase_img_exclmationpayment) ImageView ex_payment;
    @BindView(R.id.purchase_img_exclmationorderstatus) ImageView ex_orderstatus;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        initView();
        setuptoolbar();
        initData();
        initonClick();
    }

    private void initonClick() {
        container_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchasePayment.class);
                initanim(intent);
            }
        });
        container_orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchaseOrderStatus.class);
                initanim(intent);
            }
        });
        container_receiptconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchaseReceiptConfirmation.class);
                initanim(intent);
            }
        });
        container_reviewandrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchaseReviewRating.class);
                initanim(intent);
            }
        });
        container_transactionhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PurchaseHistory.class);
                initanim(intent);
            }
        });
    }

    private void api_getpayment(){
        service = apiUtils.getAPIService();
        service.req_get_purchase_payment(token).enqueue(new Callback<Purchase_PaymentResponse>() {
            @Override
            public void onResponse(Call<Purchase_PaymentResponse> call, Response<Purchase_PaymentResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            tv_payment.setText(String.valueOf(response.body().getResult().size()));
                            tv_payment.setTextColor(getResources().getColor(R.color.red));
                            ex_payment.setVisibility(View.VISIBLE);
                            container_payment.setBackgroundColor(getResources().getColor(R.color.red9));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Purchase_PaymentResponse> call, Throwable t) {

            }
        });
    }

    private void initData() {
        api_getpayment();
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Purchase");
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
