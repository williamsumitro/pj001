package com.example.williamsumitro.dress.view.view.purchase.order.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResponse;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.order.adapter.PurchaseOrderRV;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseOrderStatus extends AppCompatActivity {
    @BindView(R.id.purchaseorderstatus_toolbar) Toolbar toolbar;
    @BindView(R.id.purchaseorderstatus_rv) RecyclerView recyclerView;
    @BindView(R.id.purchaseorderstatus_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.purchaseorderstatus_ln_top) LinearLayout container_top;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private PurchaseOrderRV adapter;
    private ArrayList<Purchase_OrderResult> orderResultArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_status);
        initView();
        setuptoolbar();
        api_getorder();
    }
    private void api_getorder(){
        service = apiUtils.getAPIService();
        service.req_get_purchase_orderstatu(token).enqueue(new Callback<Purchase_OrderResponse>() {
            @Override
            public void onResponse(Call<Purchase_OrderResponse> call, Response<Purchase_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getPurchase_OrderResult().size()>0){
                            container_bottom.setVisibility(View.VISIBLE);
                            orderResultArrayList = response.body().getPurchase_OrderResult();
                            setupRV();
                        }
                    }
                    else {
                        container_top.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Purchase_OrderResponse> call, Throwable t) {

            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderResultArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Purchase Order Status");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupRV(){
        adapter = new PurchaseOrderRV(context, orderResultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
