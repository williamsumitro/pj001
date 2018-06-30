package com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.activity;

import android.app.Dialog;
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
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.adapter.OC_ProductRVAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private ArrayList<Product> productArrayList;
    private OC_ProductRVAdapter rvadapter;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    private Dialog dialog;

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
                approveOrderProductArrayList = rvadapter.retrivedata();
                api_approveproduct();
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
                            initDialog(message, 2);
                        }else{
                            String message = jsonResults.getString("message");
                            progressDialog.dismiss();
                            initDialog(message, 0);
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
        rvadapter = new OC_ProductRVAdapter(transactionid, productArrayList,  context);
        SnapHelper snapHelper = new LinearSnapHelper();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rvadapter);
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
            productArrayList = (ArrayList<Product>) getintent.getSerializableExtra(PRODUCT);
            transactionid = getintent.getStringExtra(TRANSACTION_ID);
            storeid = getintent.getStringExtra(STORE_ID);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDialog(final String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 0){
            status.setText("Oops!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.red6);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 2){
            status.setText("Confirmation");
            detail.setText("Are you sure to submit ? Once submit you cannot undo");
            bg.setBackgroundResource(R.color.orange7);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setText("I'm sure");
            buttoncancel.setVisibility(View.VISIBLE);
            buttoncancel.setBackgroundResource(R.drawable.button1_red);
            buttoncancel.setText("No");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    initDialog1(message, 1);
                }
            });
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 3){
            bg.setBackgroundResource(R.color.red7);
            status.setText("Uh Oh!");
            detail.setText("There is a problem with internet connection or the server");
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    private void initDialog1(final String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 1){
            status.setText("Success !");
            detail.setText(message);
            bg.setBackgroundResource(R.color.green7);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setText("Ok");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderConfirmation.ORDERCONFIRMATION.finish();
                    finish();
                    Intent intent = new Intent(context, OrderConfirmation.class);
                    initanim(intent);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
