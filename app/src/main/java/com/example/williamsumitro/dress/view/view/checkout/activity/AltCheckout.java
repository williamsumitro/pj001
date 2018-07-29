package com.example.williamsumitro.dress.view.view.checkout.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CheckoutInfo;
import com.example.williamsumitro.dress.view.model.CheckoutResponse;
import com.example.williamsumitro.dress.view.model.Checkout_CourierArrayList;
import com.example.williamsumitro.dress.view.model.CityDetails;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltCheckout extends AppCompatActivity {
    public static AltCheckout ALTCHECKOUT;
    @BindView(R.id.altcheckout_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.altcheckout_toolbar) Toolbar toolbar;
    @BindView(R.id.altcheckout_spinner_province) Spinner spinner_province;
    @BindView(R.id.altcheckout_spinner_city) Spinner spinner_city;
    @BindView(R.id.altcheckout_postalcode) TextInputEditText postalcode;
    @BindView(R.id.altcheckout_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.altcheckout_name) TextInputEditText name;
    @BindView(R.id.altcheckout_layout_postalcode) TextInputLayout layout_postalcode;
    @BindView(R.id.altcheckout_layout_phonenumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.altcheckout_layout_name) TextInputLayout layout_name;
    @BindView(R.id.altcheckout_layout_address) TextInputLayout layout_address;
    @BindView(R.id.altcheckout_address) TextInputEditText address;
    @BindView(R.id.altcheckout_rvshoppingbag) RecyclerView recyclerView;
    @BindView(R.id.altcheckout_btn_submit) Button submit;
    @BindView(R.id.altcheckout_ln) LinearLayout container;

    private CheckoutRVAdapter adapter;
    private SweetAlertDialog sweetAlertDialog;
    private apiService service;
    private Context context;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private SessionManagement sessionManagement;
    private String idprovince, idcity, choosen_province, choosen_city;
    private Boolean checked = false, trigger_receivername = false,
            trigger_address = false,
            trigger_phonenumber = false,
            trigger_postalcode = false;
    private ProgressDialog progressDialog;
    private String token;
    private ArrayList<CheckoutInfo> checkoutInfoArrayList;
    private String total_qty;
    private String total_price;
    private String available_points;
    private String temp_subtotal, temp_point, temp_receivername, temp_alamat, temp_province, temp_city, temp_phonenumber, temp_postalcode;
    private Checkout_CourierArrayList ccal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alt_checkout);
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
        initProvinceSpinner();
        swipeRefreshLayout.setRefreshing(false);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
        progressDialog = new ProgressDialog(context);
        sessionManagement.clearCheckoutData();
        ALTCHECKOUT = this;

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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    private void initProvinceSpinner(){
        service.req_get_province_list().enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if(response.code() == 200){
                    provinceDetailsList = response.body().getProvinceDetails();
                    spinProvinceAdapter = new SpinProvinceAdapter(context, R.layout.item_spinner, provinceDetailsList);
                    spinner_province.setAdapter(spinProvinceAdapter);
                    spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            checked  = false;
                            ProvinceDetails provinceDetails = spinProvinceAdapter.getItem(position);
                            idprovince = provinceDetails.getProvinceId();
                            choosen_province = provinceDetails.getProvinceName();
                            initCitySpinner();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void initCitySpinner(){
        service = apiUtils.getAPIService();
        service.req_get_city(idprovince).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.code() == 200){
                    cityDetailsList = response.body().getCityDetails();
                    spinCityAdapter = new SpinCityAdapter(context, R.layout.item_spinner, cityDetailsList);
                    spinner_city.setAdapter(spinCityAdapter);
                    spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            checked  = false;
                            CityDetails citydetails = spinCityAdapter.getItem(position);
                            idcity = citydetails.getCityId();
                            choosen_city = citydetails.getCityName();
                            api_getcheckoutinfo();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void api_getcheckoutinfo() {
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        service.req_get_checkout_info(token, idcity).enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()){
                        checkoutInfoArrayList = response.body().getCheckoutInfo();
                        total_price = response.body().getTotalPrice();
                        total_qty = response.body().getTotalQty();
                        available_points = String.valueOf(response.body().getAvailablePoints());
                        CheckoutRVAdapter adapter = new CheckoutRVAdapter(checkoutInfoArrayList, context);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                        initClick();
                    }
                    else {
                        String message = response.body().getMessage();
                        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_LONG, true).show();
                progressDialog.dismiss();
            }
        });
    }
    private void initClick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_address.setErrorEnabled(false);
                layout_postalcode.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_phonenumber.setErrorEnabled(false);
                if (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Name of receiver is required");
                    Snackbar.make(container, "Name of receiver is required", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                }if (TextUtils.isEmpty(address.getText().toString())){
                    layout_address.setErrorEnabled(true);
                    layout_address.setError("Address is required");
                    Snackbar.make(container, "Address is required", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                }if (TextUtils.isEmpty(phonenumber.getText().toString())){
                    layout_phonenumber.setErrorEnabled(true);
                    layout_phonenumber.setError("Phone number is required");
                    Snackbar.make(container, "Phone number is required", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                }if (TextUtils.isEmpty(postalcode.getText().toString())){
                    layout_postalcode.setErrorEnabled(true);
                    layout_postalcode.setError("Postal Code is required");
                    Snackbar.make(container, "Postal Code is required", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                }
                else {
                    sessionManagement.keepCheckoutAddress(name.getText().toString(), address.getText().toString(),
                            phonenumber.getText().toString(), idprovince, idcity, postalcode.getText().toString());
                    sessionManagement.keepCheckoutCourier(total_price, total_qty, available_points);
                    Intent intent = new Intent(context, Payment.class);
                    initanim(intent);
                }

                HashMap<String, String> courier = sessionManagement.getcheckoutcourier();
                temp_subtotal = courier.get(SessionManagement.CHECKOUT_TOTAL_PRICE);
                temp_point = courier.get(SessionManagement.CHECKOUT_POINT);
                ccal = sessionManagement.getcheckoutcourierService();
                HashMap<String, String> address = sessionManagement.getCheckoutAddress();
                temp_receivername = address.get(SessionManagement.CHECKOUT_RECEIVER_NAME);
                temp_alamat = address.get(SessionManagement.CHECKOUT_ADDRESS);
                temp_province = address.get(SessionManagement.CHECKOUT_IDPROVINCE);
                temp_city = address.get(SessionManagement.CHECKOUT_IDCITY);
                temp_phonenumber = address.get(SessionManagement.CHECKOUT_PHONE_NUMBER);
                temp_postalcode = address.get(SessionManagement.CHECKOUT_POSTAL_CODE);
//                Toasty.info(context, "Receiver name = " + temp_receivername +
//                "\nAlamat = " + temp_alamat +
//                        "\nProvince = " + temp_province +
//                        "\nCity = " + temp_city +
//                        "\nPhonenumber = " + temp_phonenumber +
//                        "\nPostalCode = " + temp_postalcode, Toast.LENGTH_LONG, true).show();
//                for (int i = 0; i < ccal.getCheckout_courierArrayList().size(); i++){
//                    Toasty.info(context, "Courier ID = " + ccal.getCheckout_courierArrayList().get(i).getCourier_id() +
//                            "\nCourier Service = " + ccal.getCheckout_courierArrayList().get(i).getCourier_service() +
//                            "\nNote = " + ccal.getCheckout_courierArrayList().get(i).getNote() +
//                            "\nFee = " + ccal.getCheckout_courierArrayList().get(i).getFee(), Toast.LENGTH_LONG, true).show();
//                }
//                Toasty.info(context, "Receiver name = " + temp_subtotal +
//                        "\nPoint = " + temp_point, Toast.LENGTH_LONG, true).show();
            }
        });
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
}
