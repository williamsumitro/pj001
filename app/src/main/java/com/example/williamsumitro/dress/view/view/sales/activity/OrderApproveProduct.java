package com.example.williamsumitro.dress.view.view.sales.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sales.adapter.OC_ProductRVAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderApproveProduct extends AppCompatActivity {
    @BindView(R.id.orderapproveproduct_rv) RecyclerView recyclerView;
    @BindView(R.id.orderapproveproduct_toolbar) Toolbar toolbar;
    @BindView(R.id.orderapproveproduct_btn_submit) Button submit;
    private Context context;
    private apiService service;
    private String token, transactionid, storeid;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ProgressDialog progressDialog;
    private ArrayList<ProductInfo> productArrayList;
    private OC_ProductRVAdapter rvadapter;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    private SweetAlertDialog sweetAlertDialog;
    private SnapHelper snapHelper = new LinearSnapHelper();

    private final static String PRODUCT = "PRODUCT";
    private final static String STORE_ID = "STORE_ID";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_approve_product);
        initView();
        setuptoolbar();
        initGetIntent();
        setupRV();
        initOnClick();
    }

    private void initOnClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog("",2);
            }
        });
    }
    private void api_approveproduct(){
        service = apiUtils.getAPIService();
        ApproveOrder approveOrder = new ApproveOrder(token, transactionid, storeid, approveOrderProductArrayList);
        service.req_approve_order_product(approveOrder).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            progressDialog.dismiss();
                            initDialog1(message, 1);
                        }else{
                            String message = jsonResults.getString("message");
                            progressDialog.dismiss();
                            initDialog1(message, 0);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initDialog("",3);
            }
        });
    }

    private void setupRV(){
        rvadapter = new OC_ProductRVAdapter(productArrayList,  context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rvadapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(this);
        productArrayList = new ArrayList<>();
        approveOrderProductArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Approve Product");
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
            if (getintent.hasExtra(TRANSACTION_ID)){
            productArrayList = (ArrayList<ProductInfo>) getintent.getSerializableExtra(PRODUCT);
            transactionid = getintent.getStringExtra(TRANSACTION_ID);
            storeid = getintent.getStringExtra(STORE_ID);
        }
        else{
                Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initDialog(final String message, int stats){
        if (stats == 2){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Confirm")
                    .setContentText("Are you sure submit ?")
                    .setContentText("Once submit you cannot undo")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            approveOrderProductArrayList = rvadapter.retrivedata();
                            api_approveproduct();
                        }
                    })
                    .show();
        }
    }
    private void initDialog1(final String message, int stats){

        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Success!")
                    .setContentText(message)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
}
