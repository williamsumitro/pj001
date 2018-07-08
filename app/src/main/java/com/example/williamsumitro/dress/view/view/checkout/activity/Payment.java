package com.example.williamsumitro.dress.view.view.checkout.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.*;
import com.example.williamsumitro.dress.view.model.Checkout;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.activity.Purchase;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment extends AppCompatActivity {
    public static Payment PAYMENT;
    @BindView(R.id.payment_btn_submit) Button submit;
    @BindView(R.id.payment_et_point) EditText et_point;
    @BindView(R.id.payment_toolbar) Toolbar toolbar;
    @BindView(R.id.payment_tv_point) TextView tv_point;
    @BindView(R.id.payment_tv_totalprice) TextView totalprice;
    @BindView(R.id.payment_tv_shippingprice) TextView tv_shippingprice;
    @BindView(R.id.payment_tv_subtotalprice) TextView tv_subtotalprice;
    @BindView(R.id.payment_tv_usepoint) TextView tv_usepoint;

    private Context context;
    private apiService service;
    private String token, subtotal, point, receivername, alamat, province, city, phonenumber, postalcode;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private Checkout_CourierArrayList ccal;
    private PaymentResponse paymentResponse;

    private final static String PAYMENTRESPONSE = "PAYMENTRESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        setuptoolbar();
        initEt();
        initData();
        initOnClick();
    }

    private void initEt() {
        if (Double.parseDouble(point)<0){
            et_point.setEnabled(false);
        }
        else if (Double.parseDouble(point)==0){
            et_point.setEnabled(false);
        }
        et_point.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!et_point.getText().toString().equals("")){
                    if ((Double.parseDouble(et_point.getText().toString()) > Double.parseDouble(point))){
                        et_point.setError("You can't insert above your point");
                        submit.setEnabled(false);
                    }
                    else if ((Double.parseDouble(et_point.getText().toString()) > Double.parseDouble(totalprice.getText().toString()))){
                        et_point.setError("Your point must below or equal to total price");
                        submit.setEnabled(false);
                    }
                    else{
                        tv_usepoint.setVisibility(View.VISIBLE);
                        tv_usepoint.setText("Use Point : IDR " + formatter.format(Double.parseDouble(et_point.getText().toString())));
                        submit.setEnabled(true);
                        initData();
                    }
                }
                else {
                    tv_usepoint.setText("Use Point : IDR " + formatter.format(Double.parseDouble("0")));
                    initData();
                }
            }
        });
    }

    private void initOnClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pointz;
                service = apiUtils.getAPIService();
                if (et_point.getText().toString().equals(""))
                    pointz = "0";
                else
                    pointz = et_point.getText().toString();
                com.example.williamsumitro.dress.view.model.Checkout checkout = new Checkout(token, receivername, alamat, province, city, phonenumber, postalcode, pointz,ccal.getCheckout_courierArrayList());
                service.req_checkout(checkout).enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                        if (response.code()==200){
                            if (response.body().getStatus()){
                                paymentResponse = response.body();
                                Gson gson = new Gson();
                                String json = gson.toJson(paymentResponse);

                                Intent intent = new Intent(context, CheckoutSuccess.class);
                                intent.putExtra(PAYMENTRESPONSE, json);
                                initanim(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void initData() {
        double usepoint, x=0,y;
        for (int i = 0;i<ccal.getCheckout_courierArrayList().size();i++){
            x+= Double.parseDouble(ccal.getCheckout_courierArrayList().get(i).getFee());
        }
        if (!et_point.getText().toString().equals("")){
            usepoint = Double.parseDouble(et_point.getText().toString());
        }
        else {
            usepoint = 0.0;
        }
        y = x + Double.parseDouble(subtotal) - usepoint;
        tv_point.setText("Your Points : IDR " +formatter.format(Double.parseDouble(point)));
        tv_subtotalprice.setText("Subtotal Price : IDR " +formatter.format(Double.parseDouble(subtotal)));

        tv_shippingprice.setText("Shipping Price : IDR " +formatter.format(Double.parseDouble(String.valueOf(x))));

        totalprice.setText("Total Price : IDR " + formatter.format(Double.parseDouble(String.valueOf(y))));


    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        PAYMENT = this;
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        HashMap<String, String> courier = sessionManagement.getcheckoutcourier();
        subtotal = courier.get(SessionManagement.CHECKOUT_TOTAL_PRICE);
        point = courier.get(SessionManagement.CHECKOUT_POINT);
        ccal = sessionManagement.getcheckoutcourierService();
        HashMap<String, String> address = sessionManagement.getCheckoutAddress();
        receivername = address.get(SessionManagement.CHECKOUT_RECEIVER_NAME);
        alamat = address.get(SessionManagement.CHECKOUT_ADDRESS);
        province = address.get(SessionManagement.CHECKOUT_IDPROVINCE);
        city = address.get(SessionManagement.CHECKOUT_IDCITY);
        phonenumber = address.get(SessionManagement.CHECKOUT_PHONE_NUMBER);
        postalcode = address.get(SessionManagement.CHECKOUT_POSTAL_CODE);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment");
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
