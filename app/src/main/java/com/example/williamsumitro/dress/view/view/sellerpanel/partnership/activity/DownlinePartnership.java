package com.example.williamsumitro.dress.view.view.sellerpanel.partnership.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipItem;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipResponse;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipResult;
import com.example.williamsumitro.dress.view.model.UplinePartnershipItem;
import com.example.williamsumitro.dress.view.model.UplinePartnershipResponse;
import com.example.williamsumitro.dress.view.model.UplinePartnershipResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.partnership.adapter.DownlinePartnership_RV;
import com.example.williamsumitro.dress.view.view.sellerpanel.partnership.adapter.UplinePartnership_RV;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownlinePartnership extends AppCompatActivity {
    @BindView(R.id.downline_partnership_rv) RecyclerView recyclerView;
    @BindView(R.id.downline_partnership_ln_bottom) LinearLayout bottom;
    @BindView(R.id.downline_partnership_ln_top) LinearLayout top;
    @BindView(R.id.downline_partnership_toolbar) Toolbar toolbar;
    @BindView(R.id.downline_partnership_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.downline_partnership_btn_mydownlinepartners) Button mydownlinepartnership;

    public static DownlinePartnership DOWNLINEPARTNERSHIP;
    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private DownlinePartnership_RV adapter;
    private ArrayList<DownlinePartnershipItem> itemArrayList;
    private ArrayList<DownlinePartnershipResult> resultArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downline_partnership);
        initView();
        setuptoolbar();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        initClick();
    }
    private void initClick() {
        mydownlinepartnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyDownlinePartnership.class);
                initanim(intent);
            }
        });
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getorderconfirmation();
    }
    private void initView(){
        DOWNLINEPARTNERSHIP = this;
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        itemArrayList = new ArrayList<>();
        resultArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Downline Partner");
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
    private void setupRV(){
        adapter = new DownlinePartnership_RV(context, itemArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void api_getorderconfirmation() {
        service = apiUtils.getAPIService();
        service.req_upline_get_request_partnership(token).enqueue(new Callback<DownlinePartnershipResponse>() {
            @Override
            public void onResponse(Call<DownlinePartnershipResponse> call, Response<DownlinePartnershipResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            bottom.setVisibility(View.VISIBLE);
                            resultArrayList = response.body().getResult();
                            itemArrayList = new ArrayList<>();
                            for (int i = 0; i<resultArrayList.size(); i++){
                                for (int j = 0; j<resultArrayList.get(i).getProduct().size();j++){
                                    DownlinePartnershipItem item = new DownlinePartnershipItem(resultArrayList.get(i).getStoreNamePartner(),
                                            resultArrayList.get(i).getProduct().get(j));
                                    itemArrayList.add(item);
                                }
                            }
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            top.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DownlinePartnershipResponse> call, Throwable t) {
                Toast.makeText(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
