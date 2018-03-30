package com.example.williamsumitro.dress.view.view.checkout.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Shipping;
import com.example.williamsumitro.dress.view.view.bag.adapter.ShoppingBagRVAdapter;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Checkout extends AppCompatActivity {
    @BindView(R.id.checkout_tvTotalPembayaran) TextView totalpembayaran;
    @BindView(R.id.checkout_tvSubtotal) TextView subtotal;
    @BindView(R.id.checkout_tvShippingprice) TextView shippingprice;
    @BindView(R.id.checkout_tvpoint) TextView yourpoint;
    @BindView(R.id.checkout_tvDiscPoint) TextView discpoint;
    @BindView(R.id.checkout_tvaddressname) TextView addressname;
    @BindView(R.id.checkout_tvaddressdetail) TextView addressdetail;
    @BindView(R.id.checkout_toolbar) Toolbar toolbar;
    @BindView(R.id.checkout_rvshoppingcheckout) RecyclerView recyclerView;
    @BindView(R.id.checkout_etyourpoint) EditText yourtextpoint;
    @BindView(R.id.checkout_btnusepoint) Button usepoint;
    @BindView(R.id.checkout_btnFinish) Button finish;
    @BindView(R.id.checkout_btnchooseanotheraddress) Button chooseanotherdress;
    private Context context;
    private CheckoutRVAdapter adapter;
    private List<Bag> bagList;
    private List<Shipping> shippingList;
    private List<Bank> bankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initView();
        initData();
        setupRV();
        setuptoolbar();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        bagList = new ArrayList<>();
        shippingList = new ArrayList<>();
        bankList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Checkout");
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
        adapter = new CheckoutRVAdapter(bagList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Bank bank = new Bank("BCA", "Tek Kin Lung", "127541116365");
        bankList.add(bank);
        bank = new Bank("Mandiri", "Tek Kin Lung", "11005566884");
        bankList.add(bank);
        Shipping shipping = new Shipping("JNE", 5000);
        shippingList.add(shipping);
        shipping = new Shipping("TIKI", 2000);
        shippingList.add(shipping);
        shipping = new Shipping("Lite", 8000);
        shippingList.add(shipping);
        shipping = new Shipping("THH", 10000);
        shippingList.add(shipping);
        Bag bag = new Bag("ABC Store", "Baju Renang", R.drawable.image, 60000, 10, 20, 30 ,10, 70, 35, 500000, 8000000, 7500000, 600000, 1200000, 1800000, 600000, shippingList, bankList);
        bagList.add(bag);
    }
}
