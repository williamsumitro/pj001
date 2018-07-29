package com.example.williamsumitro.dress.view.view.checkout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.*;
import com.example.williamsumitro.dress.view.model.Checkout;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
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
    @BindView(R.id.payment_ln_usepoint) LinearLayout ln_usepoint;

    private Context context;
    private apiService service;
    private String token, subtotal, point, receivername, alamat, province, city, phonenumber, postalcode;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private Checkout_CourierArrayList ccal;
    private PaymentResponse paymentResponse;
    private double usepoint = 0, x=0, y = 0, totprice;
    private SweetAlertDialog sweetAlertDialog;

    private final static String PAYMENTRESPONSE = "PAYMENTRESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        setuptoolbar();
        initData();
        initEt();
        initOnClick();
    }

    private void initEt() {
        et_point.addTextChangedListener(new FinancialTextWatcher(et_point));
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
                String nocomma = FinancialTextWatcher.trimCommaOfString(s.toString());
                if (!nocomma.equals("")){
                    if ((Double.parseDouble(nocomma) > Double.parseDouble(point))){
                        et_point.setError("You can't insert above your point");
                        submit.setEnabled(false);
                    }
                    else if ((Double.parseDouble(nocomma) > Double.parseDouble(String.valueOf(totprice)))){
                        et_point.setError("Your point must below or equal to total price");
                        submit.setEnabled(false);
                    }
                    else{
                        ln_usepoint.setVisibility(View.VISIBLE);
                        tv_usepoint.setText("IDR " + formatter.format(Double.parseDouble(nocomma)));
                        submit.setEnabled(true);
                        changePrice();
                    }
                }
                else {
                    tv_usepoint.setText("IDR " + formatter.format(Double.parseDouble("0")));
                    changePrice();
                }
                if (!et_point.getText().toString().equals("")){
                    String nocommas = FinancialTextWatcher.trimCommaOfString(et_point.getText().toString());
                    if ((Double.parseDouble(nocommas) > Double.parseDouble(point))){
                        et_point.setError("You can't insert above your point");
                        submit.setEnabled(false);
                    }
                    else if ((Double.parseDouble(nocommas) > Double.parseDouble(String.valueOf(totprice)))){
                        et_point.setError("Your point must below or equal to subtotal price");
                        submit.setEnabled(false);
                    }
                    else{
                        ln_usepoint.setVisibility(View.VISIBLE);
                        tv_usepoint.setText("IDR " + formatter.format(Double.parseDouble(nocommas)));
                        submit.setEnabled(true);
                        changePrice();
                    }
                }
                else {
                    tv_usepoint.setText("IDR " + formatter.format(Double.parseDouble("0")));
                    changePrice();
                }
            }
        });
    }
    private void initOnClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog1(1);
            }
        });

    }
    private void api_submit(){
        String pointz;
        if (et_point.getText().toString().equals(""))
            pointz = "0";
        else
            pointz = FinancialTextWatcher.trimCommaOfString(et_point.getText().toString());
        com.example.williamsumitro.dress.view.model.Checkout checkout = new Checkout(token, receivername, alamat, province, city, phonenumber, postalcode, pointz, ccal.getCheckout_courierArrayList());
        service.req_checkout(checkout).enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()){
                        api_delete();
                        paymentResponse = response.body();
                        Gson gson = new Gson();
                        String json = gson.toJson(paymentResponse);

                        Intent intent = new Intent(context, CheckoutSuccess.class);
                        intent.putExtra(PAYMENTRESPONSE, json);
                        initanim(intent);
                        AltCheckout.ALTCHECKOUT.finish();
                        finish();
                    }
                    else {
                        initDialog(response.body().getMessage(), 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                initDialog("",3);
            }
        });
    }
    private void api_delete(){
        service.req_delete_all_product_from_bag(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){

                }
                else {
                    Toasty.error(context, "error in delete all product", Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initDialog("",3);
            }
        });
    }
    private void initDialog1(int stats){
        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Submit")
                    .setContentText("Are you sure to submit ?")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.setTitleText("Success")
                                    .setContentText("")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            api_submit();
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        }
    }
    private void initDialog(String message, int stats){
        if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .show();
        }
        else if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .show();
        }
    }
    private void initData() {
        for (int i = 0;i<ccal.getCheckout_courierArrayList().size();i++){
            x+= Double.parseDouble(ccal.getCheckout_courierArrayList().get(i).getFee());
        }
        tv_point.setText("IDR " +formatter.format(Double.parseDouble(point)));
        tv_subtotalprice.setText("IDR " +formatter.format(Double.parseDouble(subtotal)));
        tv_shippingprice.setText("IDR " +formatter.format(Double.parseDouble(String.valueOf(x))));
        totprice = x + Double.parseDouble(subtotal);
        totalprice.setText("Total Price : IDR " + formatter.format(Double.parseDouble(String.valueOf(totprice))));

    }
    private void changePrice(){
        if (!et_point.getText().toString().equals("")){
            String nocomma = FinancialTextWatcher.trimCommaOfString(et_point.getText().toString());
            usepoint = Double.parseDouble(nocomma);
        }
        else {
            usepoint = 0.0;
        }
        y = x + Double.parseDouble(subtotal) - usepoint;
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
        Toasty.info(context, "Receiver name = " + receivername +
                "\nAlamat = " + alamat +
                "\nProvince = " + province +
                "\nCity = " + city +
                "\nPhonenumber = " + phonenumber +
                "\nPostalCode = " + postalcode, Toast.LENGTH_LONG, true).show();
        for (int i = 0; i < ccal.getCheckout_courierArrayList().size(); i++){
            Toasty.info(context, "Courier ID = " + ccal.getCheckout_courierArrayList().get(i).getCourier_id() +
                    "\nCourier Service = " + ccal.getCheckout_courierArrayList().get(i).getCourier_service() +
                    "\nNote = " + ccal.getCheckout_courierArrayList().get(i).getNote() +
                    "\nFee = " + ccal.getCheckout_courierArrayList().get(i).getFee(), Toast.LENGTH_LONG, true).show();
        }
        Toasty.info(context, "Receiver name = " + subtotal +
                "\nPoint = " + point, Toast.LENGTH_LONG, true).show();
        service = apiUtils.getAPIService();

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
