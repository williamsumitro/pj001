package com.example.williamsumitro.dress.view.view.mystore.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CourierDetails;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.CourierService;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.mystore.adapter.CourierServiceRVAdapter;
import com.example.williamsumitro.dress.view.view.mystore.adapter.SpinCourierAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;

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

public class CourierServiceActivity extends AppCompatActivity {
    @BindView(R.id.courierservice_btn_add) Button add;
    @BindView(R.id.courierservice_rv) RecyclerView recyclerView;
    @BindView(R.id.courierservice_spinner) Spinner spinner;
    @BindView(R.id.courierservice_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.courierservice_toolbar) Toolbar toolbar;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private final static String STORE_RESULT = "STORE_RESULT";
    private StoreDetails storeDetails;
    private ArrayList<model_CourierService> courierServices;
    private CourierServiceRVAdapter adapter;
    private String storeid;
    private SweetAlertDialog sweetAlertDialog;
    private SpinCourierAdapter spinCourierAdapter;
    private String id_courier, choosen_courier;
    private ArrayList<CourierDetails> courierServiceArrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_service);
        initView();
        setuptoolbar();
        api_getuserstore();
    }
    private void api_getuserstore() {
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.code()==200){
                    storeid = response.body().getStore().getStoreId().toString();
                    api_getstoredetail(storeid);
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }
    private void api_getstoredetail(String store_id){
        service.req_get_store_detail(store_id).enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                if (response.code()==200){
                    storeDetails = response.body().getResult();
                    initCourierSpinner();
                    initRefresh();
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            initRefresh();
                        }
                    });
                    progressDialog.dismiss();
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }
    private void initCourierSpinner(){
        service = apiUtils.getAPIService();
        service.req_get_courier_list().enqueue(new Callback<CourierResponse>() {
            @Override
            public void onResponse(Call<CourierResponse> call, Response<CourierResponse> response) {
                if(response.code() == 200){
                    courierServiceArrayList = response.body().getCourier();
                    spinCourierAdapter = new SpinCourierAdapter(context, R.layout.item_spinner, courierServiceArrayList);
                    spinner.setAdapter(spinCourierAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            CourierDetails courierdetails = spinCourierAdapter.getItem(position);
                            id_courier = courierdetails.getCourierId();
                            choosen_courier = courierdetails.getCourierName();
                            initClick();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CourierResponse> call, Throwable t) {
                initDialog(3);
            }
        });

    }

    private void initClick() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.req_insert_user_store_courier(token, storeid, id_courier).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                String message = jsonResults.getString("message");
                                if(jsonResults.getBoolean("status")){
                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                    initRefresh();
                                }else if (!jsonResults.getBoolean("status")){
                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        initDialog(3);
                    }
                });
            }
        });
    }

    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(STORE_RESULT)){
            storeDetails = (StoreDetails) getintent.getSerializableExtra(STORE_RESULT);
            storeid = storeDetails.getStoreId().toString();
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }
    public void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getCourierServices();
    }
    private void api_getCourierServices(){
        service.req_get_store_detail(storeid).enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                if (response.code()==200){
                    courierServices = response.body().getResult().getCourierService();
                    setupRV();
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                initDialog(3);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setupRV(){
        adapter = new CourierServiceRVAdapter(context, courierServices, storeid, CourierServiceActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
        courierServices = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Courier Services");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    private void initDialog(int stats){
        if (stats == 3){
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
