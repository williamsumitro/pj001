package com.example.williamsumitro.dress.view.view.purchase.order.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResponse;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.activity.Purchase;
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
    @BindView(R.id.purchaseorderstatus_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

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
            public void onFailure(Call<Purchase_OrderResponse> call, Throwable t) {
                Toast.makeText(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, Purchase.class);
        initanim(intent);
//        super.onBackPressed();
    }

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            finish();
            Purchase.PURCHASE.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
