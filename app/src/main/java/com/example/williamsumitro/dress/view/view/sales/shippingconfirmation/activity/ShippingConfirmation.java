package com.example.williamsumitro.dress.view.view.sales.shippingconfirmation.activity;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.model.SizeInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sales.shippingconfirmation.adapter.SC_RVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingConfirmation extends AppCompatActivity {
    @BindView(R.id.shippingconfirmation_rv) RecyclerView recyclerView;
    @BindView(R.id.shippingconfirmation_ln_bottom) LinearLayout bottom;
    @BindView(R.id.shippingconfirmation_ln_top) LinearLayout top;
    @BindView(R.id.shippingconfirmation_toolbar) Toolbar toolbar;
    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ArrayList<Sales_OrderResult> orderResultArrayList;
    private List<SizeInfo> sizeInfoList;
    private ArrayList<Product> productArrayList;
    private SC_RVAdapter adapter;
    private String product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_confirmation);
        initView();
        setuptoolbar();
        api_getshippingconfirmation();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderResultArrayList = new ArrayList<>();
        sizeInfoList = new ArrayList<>();
        productArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Shipping Confirmation");
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
    private void setupRV(){
        Toast.makeText(context, String.valueOf(orderResultArrayList.size()), Toast.LENGTH_LONG).show();
        adapter = new SC_RVAdapter(context, orderResultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void api_getshippingconfirmation() {
        service = apiUtils.getAPIService();

        service.req_seller_get_shipping(token).enqueue(new Callback<Sales_OrderResponse>() {
            @Override
            public void onResponse(Call<Sales_OrderResponse> call, Response<Sales_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getSales_OrderResult().size()>0){
                            bottom.setVisibility(View.VISIBLE);
                            orderResultArrayList = response.body().getSales_OrderResult();
                            setupRV();
                        }
                        else {
                            top.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Sales_OrderResponse> call, Throwable t) {

            }
        });
    }
}
