package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.bag.adapter.ShoppingBagRVAdapter;
import com.example.williamsumitro.dress.view.view.checkout.activity.Checkout;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingBag extends AppCompatActivity {
    @BindView(R.id.bag_btncheckout) Button checkout;
    @BindView(R.id.bag_btncontinue) Button continues;
    @BindView(R.id.bag_rvshoppingbag) RecyclerView recyclerView;
    @BindView(R.id.bag_toolbar) Toolbar toolbar;
    @BindView(R.id.bag_tvTotalPembayaran) TextView totalpembayaran;
    @BindView(R.id.bag_tvpoint) TextView point;
    private ShoppingBagRVAdapter adapter;
    private Context context;
    private List<Bag> bagList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);
        initView();
        setupRV();
        setuptoolbar();
        initData();
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Checkout.class);
                initanim(intent);
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        bagList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Bag");
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
        adapter = new ShoppingBagRVAdapter(bagList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Bag bag = new Bag("ABC Store", "Baju Renang", R.drawable.image, 60000, 10, 20, 30 ,10, 70, 35, 500000, 8000000, 7500000, 600000, 1200000, 1800000, 600000);
        bagList.add(bag);
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
}
