package com.example.williamsumitro.dress.view.view.Wallet.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.TransactionDetails;
import com.example.williamsumitro.dress.view.view.Wallet.adapter.Wallet_Transaction_RV_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mywallet extends AppCompatActivity {
    @BindView(R.id.mywallet_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.mywallet_collapstoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.mywallet_toolbar) Toolbar toolbar;
    @BindView(R.id.mywallet_spinner) Spinner spinner;
    @BindView(R.id.mywallet_rv) RecyclerView recyclerView;
    @BindView(R.id.mywallet_btn_showhistory) Button showhistory;
    @BindView(R.id.mywallet_btn_withdraw) Button withdraw;

    private Context context;
    private ArrayAdapter<CharSequence> monthadapter;
    private String choosen_spinner;
    private Wallet_Transaction_RV_Adapter adapter;
    private List<TransactionDetails> transactionDetailsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initObject();
        setupToolbar();
        initCollapToolbar();
        initspinner();
        setuprv();
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Withdraw.class);
                initanim(intent);
            }
        });
    }

    private void setuprv() {
        TransactionDetails transactionDetails = new TransactionDetails("20 May 2018", "Jual Beli Barang", "Jual beli barang di tokopedia, laaada, tah apa", 200000, 200000, 2000000);
        transactionDetailsList.add(transactionDetails);
        adapter = new Wallet_Transaction_RV_Adapter(context, transactionDetailsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle("My Wallet");
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void initspinner(){
        monthadapter = ArrayAdapter.createFromResource(context, R.array.month, android.R.layout.simple_spinner_item);
        monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosen_spinner = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
