package com.example.williamsumitro.dress.view.view.purchase.payment.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.payment.adapter.PurchasePaymentRVOrder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class PP_InvoiceDetail extends AppCompatActivity {
    @BindView(R.id.purchasepaymentdetail_rv) RecyclerView recyclerView;
    @BindView(R.id.purchasepaymentdetail_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasepaymentdetail_tv_grandtotal) TextView grandtotal;
    @BindView(R.id.purchasepaymentdetail_tv_invoicenumber) TextView invoicenumber;


    private Context context;
    private apiService service;
    private String token, invoice_number, grand_total;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;


    private final static String ORDERSTORELIST = "ORDERSTORELIST";
    private final static String INVOICENUMBER = "INVOICENUMBER";
    private final static String GRANDTOTAL = "GRANDTOTAL";
    private ArrayList<OrderStore> orderStoreArrayList;
    private PurchasePaymentRVOrder adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pp_invoicedetail);
        initView();
        setuptoolbar();
        initGetIntent();
        initData();
        setupRV();
    }

    private void initData() {
        grandtotal.setText("Grand Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(grand_total))));
        invoicenumber.setText("Invoice Number : " + invoice_number);
    }

    private void setupRV() {
        adapter = new PurchasePaymentRVOrder(context, orderStoreArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderStoreArrayList = new ArrayList<>();
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
        getSupportActionBar().setTitle("Invoice Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(ORDERSTORELIST)){
            orderStoreArrayList = (ArrayList<OrderStore>) getintent.getSerializableExtra(ORDERSTORELIST);
            invoice_number = getintent.getStringExtra(INVOICENUMBER);
            grand_total = getintent.getStringExtra(GRANDTOTAL);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
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

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
