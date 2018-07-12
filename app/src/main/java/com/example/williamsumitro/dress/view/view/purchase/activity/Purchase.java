package com.example.williamsumitro.dress.view.view.purchase.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResponse;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.model.Purchase_ReviewRatingResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.history.activity.PurchaseHistory;
import com.example.williamsumitro.dress.view.view.purchase.order.activity.PurchaseOrderStatus;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PurchasePayment;
import com.example.williamsumitro.dress.view.view.purchase.receipt.activity.PurchaseReceiptConfirmation;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.activity.PurchaseReviewRating;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Purchase extends AppCompatActivity {

    public static Purchase PURCHASE;

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
    @BindView(R.id.purchase_img_exclmationreviewandrating) ImageView ex_reviewandrating;
    @BindView(R.id.purchase_img_exclmationreceiptconfirmation) ImageView ex_receiptconfirmation;
    @BindView(R.id.purchase_img_exclmationpayment) ImageView ex_payment;
    @BindView(R.id.purchase_img_exclmationorderstatus) ImageView ex_orderstatus;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        initView();
        setuptoolbar();
        initRefresh();
        initonClick();
    }
    private void initRefresh(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        api_getpayment();
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
                        if (response.body().getPurchasePaymentResult().size()>0){
                            tv_payment.setText(String.valueOf(response.body().getPurchasePaymentResult().size()));
                            tv_payment.setTextColor(getResources().getColor(R.color.red));
                            ex_payment.setVisibility(View.VISIBLE);
                            container_payment.setBackgroundColor(getResources().getColor(R.color.red9));
                            api_getorder();
                        }
                        else {
                            api_getorder();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Purchase_PaymentResponse> call, Throwable t) {
                Toast.makeText(context, "Please refresh again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void api_getorder(){
        service = apiUtils.getAPIService();
        service.req_get_purchase_orderstatu(token).enqueue(new Callback<Purchase_OrderResponse>() {
            @Override
            public void onResponse(Call<Purchase_OrderResponse> call, Response<Purchase_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getPurchase_OrderResult().size()>0){
                            tv_orderstatus.setText(String.valueOf(response.body().getPurchase_OrderResult().size()));
                            tv_orderstatus.setTextColor(getResources().getColor(R.color.red));
                            ex_orderstatus.setVisibility(View.VISIBLE);
                            container_orderstatus.setBackgroundColor(getResources().getColor(R.color.red9));
                            api_getreceiptconfirmation();
                        }
                        else {
                            api_getreceiptconfirmation();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Purchase_OrderResponse> call, Throwable t) {
                Toast.makeText(context, "Please refresh again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void api_getreceiptconfirmation() {
        service = apiUtils.getAPIService();
        service.req_get_receipt_confirmation(token).enqueue(new Callback<Sales_OrderResponse>() {
            @Override
            public void onResponse(Call<Sales_OrderResponse> call, Response<Sales_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getSales_OrderResult().size()>0){
                            tv_receiptconfirmation.setText(String.valueOf(response.body().getSales_OrderResult().size()));
                            tv_receiptconfirmation.setTextColor(getResources().getColor(R.color.red));
                            ex_receiptconfirmation.setVisibility(View.VISIBLE);
                            container_receiptconfirmation.setBackgroundColor(getResources().getColor(R.color.red9));
                            api_getreviewrating();
                        }
                        else {
                            api_getreviewrating();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Sales_OrderResponse> call, Throwable t) {
                Toast.makeText(context, "Please refresh again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    private void api_getreviewrating() {
        service = apiUtils.getAPIService();
        service.req_get_review_rating(token).enqueue(new Callback<Purchase_ReviewRatingResponse>() {
            @Override
            public void onResponse(Call<Purchase_ReviewRatingResponse> call, Response<Purchase_ReviewRatingResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            tv_reviewandrating.setText(String.valueOf(response.body().getResult().size()));
                            tv_reviewandrating.setTextColor(getResources().getColor(R.color.red));
                            ex_reviewandrating.setVisibility(View.VISIBLE);
                            container_reviewandrating.setBackgroundColor(getResources().getColor(R.color.red9));
                            progressDialog.dismiss();
                        }
                        else {
                            progressDialog.dismiss();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<Purchase_ReviewRatingResponse> call, Throwable t) {
                Toast.makeText(context, "Please refresh again", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        PURCHASE = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbar_refresh) {
            initRefresh();
        }

        return super.onOptionsItemSelected(item);
    }
}
