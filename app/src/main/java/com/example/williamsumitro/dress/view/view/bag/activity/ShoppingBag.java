package com.example.williamsumitro.dress.view.view.bag.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.model.BagResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.ShoppingBagRVAdapter;
import com.example.williamsumitro.dress.view.view.checkout.activity.AltCheckout;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingBag extends AppCompatActivity {
    public static ShoppingBag SHOPPINGBAG;
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
    @BindView(R.id.bag_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

//    @BindView(R.id.bag_tvpoint) TextView point;
    private ShoppingBagRVAdapter adapter;
    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<Bag> bagArrayList;
    private String total_qty, total_price;
    private DecimalFormat formatter;
    private ProgressDialog progressDialog;
    private SnapHelper snapHelper = new LinearSnapHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        initView();
        setuptoolbar();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                initRefresh();
            }
        });

        initonClick();
    }
    public void initRefresh(){
        api_viewshoppingbag();
    }
    private void initonClick() {
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AltCheckout.class);
                initanim(intent);
            }
        });
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
        swipeRefreshLayout.setRefreshing(false);
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        service = apiUtils.getAPIService();
        service.req_view_shopping_bag(token).enqueue(new Callback<BagResponse>() {
            @Override
            public void onResponse(Call<BagResponse> call, Response<BagResponse> response) {
                if (response.code()==200){
                    bagArrayList = response.body().getBagDetail();
                    if (bagArrayList.size() == 0){
                        btn_continue.setVisibility(View.VISIBLE);
                        top.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        btn_continue.setVisibility(View.GONE);
                        top.setVisibility(View.GONE);
                        bottom.setVisibility(View.VISIBLE);
                        nestedScrollView.setVisibility(View.VISIBLE);
                    }
                    total_price = response.body().getTotalPrice();
                    total_qty = response.body().getTotalQty();
                    initData();
                    setupRV();
                    swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<BagResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        SHOPPINGBAG = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);

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
    private void setupRV(){
        adapter = new ShoppingBagRVAdapter(bagArrayList, context, ShoppingBag.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
