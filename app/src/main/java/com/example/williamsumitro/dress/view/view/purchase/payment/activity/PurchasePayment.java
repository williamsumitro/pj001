package com.example.williamsumitro.dress.view.view.purchase.payment.activity;

import android.app.ProgressDialog;
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
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.activity.Purchase;
import com.example.williamsumitro.dress.view.view.purchase.payment.adapter.PurchasePaymentRVInvoice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasePayment extends AppCompatActivity {
    @BindView(R.id.purchasepayment_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasepayment_ln_top) LinearLayout container_top;
    @BindView(R.id.purchasepayment_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.purchasepayment_rv) RecyclerView recyclerView;
    @BindView(R.id.purchasepayment_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private PurchasePaymentRVInvoice adapter;
    private ArrayList<Purchase_PaymentResult> purchasePaymentResultArrayList;
    private ArrayList<Bank> bankArrayList;
    private ArrayList<OrderStore> orderStoreArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_payment);
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
        api_getpayment();
    }
    private void api_getpayment() {
        service = apiUtils.getAPIService();
        service.req_get_purchase_payment(token).enqueue(new Callback<Purchase_PaymentResponse>() {
            @Override
            public void onResponse(Call<Purchase_PaymentResponse> call, Response<Purchase_PaymentResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getPurchasePaymentResult().size()>0){
                            container_bottom.setVisibility(View.VISIBLE);
                            purchasePaymentResultArrayList = response.body().getPurchasePaymentResult();
                            bankArrayList = response.body().getBank();
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
            public void onFailure(Call<Purchase_PaymentResponse> call, Throwable t) {
                Toast.makeText(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setupRV(){
        adapter = new PurchasePaymentRVInvoice(context, purchasePaymentResultArrayList, bankArrayList);
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
        purchasePaymentResultArrayList = new ArrayList<>();
        bankArrayList = new ArrayList<>();
        orderStoreArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Purchase Payment");
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
    public void onBackPressed() {
        Intent intent = new Intent(context, Purchase.class);
        initanim(intent);
//        super.onBackPressed();
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
            Purchase.PURCHASE.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
