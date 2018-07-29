package com.example.williamsumitro.dress.view.view.product.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.product.adapter.MyProductRV;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProduct extends AppCompatActivity {
    @BindView(R.id.myproduct_fab) FloatingActionButton floatingActionButton;
    @BindView(R.id.myproduct_toolbar) Toolbar toolbar;
    @BindView(R.id.myproduct_rv) RecyclerView recyclerView;
    @BindView(R.id.myproduct_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.myproduct_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.myproduct_ln_top) LinearLayout container_top;

    private Context context;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private apiService service;
    private String storeid, token;
    private MyProductRV adapter;
    private ArrayList<ProductInfo> productArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);
        initView();
        setuptoolbar();
        initGetData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddProduct.class);
                initanim(intent);
            }
        });
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }

    private void initGetData() {
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.code()==200){
                    storeid = response.body().getStore().getStoreId().toString();
                    get_detailstore();
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void initRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        initGetData();
    }

    private void get_detailstore() {
        service.req_get_user_store_detail(token, storeid).enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                if (response.code()==200){
                    productArrayList = response.body().getResult().getProduct();
                    if (productArrayList.size()>0){
                        container_bottom.setVisibility(View.VISIBLE);
                        container_top.setVisibility(View.GONE);
                        setupRV();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        container_bottom.setVisibility(View.GONE);
                        container_top.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void setupRV(){
        adapter = new MyProductRV(productArrayList, context);
        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(grid_layoutmanager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(this);
        service = apiUtils.getAPIService();
        productArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Settings");
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
