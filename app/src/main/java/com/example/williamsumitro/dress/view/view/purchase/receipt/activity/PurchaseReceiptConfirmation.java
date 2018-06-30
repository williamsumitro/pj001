package com.example.williamsumitro.dress.view.view.purchase.receipt.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.payment.adapter.PurchasePaymentRVInvoice;
import com.example.williamsumitro.dress.view.view.purchase.receipt.adapter.PurchaseReceiptRV;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.adapter.OC_RVAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.shippingconfirmation.adapter.SC_RVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseReceiptConfirmation extends AppCompatActivity {
    @BindView(R.id.purchasereceiptconfirmation_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasereceiptconfirmation_ln_top) LinearLayout container_top;
    @BindView(R.id.purchasereceiptconfirmation_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.purchasereceiptconfirmation_rv) RecyclerView recyclerView;
    @BindView(R.id.purchasereceiptconfirmation_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<Sales_OrderResult> orderResultArrayList;
    private DecimalFormat formatter;
    private PurchaseReceiptRV adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_receipt_confirmation);
        initView();
        setuptoolbar();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getreceiptconfirmation();
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
        getSupportActionBar().setTitle("Purchase Receipt Confirmation");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
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
                            container_bottom.setVisibility(View.VISIBLE);
                            orderResultArrayList = response.body().getSales_OrderResult();
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            container_top.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Sales_OrderResponse> call, Throwable t) {
                Toast.makeText(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setupRV(){
        adapter = new PurchaseReceiptRV(context, orderResultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
