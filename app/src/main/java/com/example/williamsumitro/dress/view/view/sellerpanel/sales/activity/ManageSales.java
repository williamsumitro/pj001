package com.example.williamsumitro.dress.view.view.sellerpanel.sales.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.fragment.OrderorderFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.OrderstatusFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.PaymentstatusFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.ReviewandratingFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.ShippingconfirmationFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.TransactionhistoryFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.adapter.TabManageSalesAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.activity.OrderConfirmation;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.shippingconfirmation.activity.ShippingConfirmation;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageSales extends AppCompatActivity {
    @BindView(R.id.managesales_toolbar) Toolbar toolbar;
    @BindView(R.id.managesales_cv_orderconfirmation) CardView container_order;
    @BindView(R.id.managesales_cv_shippingconfirmation) CardView container_shipping;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sales);
        initView();
        setuptoolbar();
        initOnClick();
    }

    private void initOnClick() {
        container_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderConfirmation.class);
                initanim(intent);
            }
        });
        container_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShippingConfirmation.class);
                initanim(intent);
            }
        });
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Sales");
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
