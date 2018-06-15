package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.model.BagResponse;
import com.example.williamsumitro.dress.view.model.Bagz;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.ShoppingBagRVAdapter;
import com.example.williamsumitro.dress.view.view.checkout.activity.Checkout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingBag extends AppCompatActivity {
    @BindView(R.id.bag_btncheckout) Button checkout;
//    @BindView(R.id.bag_btncontinue) Button continues;
    @BindView(R.id.bag_rvshoppingbag) RecyclerView recyclerView;
    @BindView(R.id.bag_toolbar) Toolbar toolbar;
    @BindView(R.id.bag_tvTotalPembayaran) TextView totalpembayaran;
    @BindView(R.id.bag_tvTotal) TextView total;
    @BindView(R.id.bag_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.bag_ln_top) LinearLayout top;
    @BindView(R.id.bag_ln_buttom) LinearLayout bottom;
    @BindView(R.id.bag_btn_continue) Button btn_continue;

//    @BindView(R.id.bag_tvpoint) TextView point;
    private ShoppingBagRVAdapter adapter;
    private Context context;
    private List<Bagz> bagList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<Bag> bagArrayList;
    private String total_qty, total_price;
    private DecimalFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        initView();
        api_viewshoppingbag();
        setuptoolbar();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Checkout.class);
                initanim(intent);
            }
        });
        initonClick();
    }

    private void initonClick() {
//        continues.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
//                finish();
//            }
//        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    private void api_viewshoppingbag() {
        service = apiUtils.getAPIService();
        service.req_view_shopping_bag(token).enqueue(new Callback<BagResponse>() {
            @Override
            public void onResponse(Call<BagResponse> call, Response<BagResponse> response) {
                if (response.code()==200){
                    bagArrayList = response.body().getBagDetail();
                    if (bagArrayList.size() == 0){
                        btn_continue.setVisibility(View.VISIBLE);
                        top.setVisibility(View.VISIBLE);
                    }
                    else {
                        bottom.setVisibility(View.VISIBLE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                    }
                    total_price = response.body().getTotalPrice();
                    total_qty = response.body().getTotalQty();
                    initData();
                    setupRV();
                }
            }
            @Override
            public void onFailure(Call<BagResponse> call, Throwable t) {

            }
        });
    }
    private void initStore(){

    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        bagList = new ArrayList<>();
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
    private void setupRV(){
        adapter = new ShoppingBagRVAdapter(bagArrayList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        totalpembayaran.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(total_price))));
        total.setText("Total ("+formatter.format(Double.parseDouble(total_qty))+" pcs)");
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
}
