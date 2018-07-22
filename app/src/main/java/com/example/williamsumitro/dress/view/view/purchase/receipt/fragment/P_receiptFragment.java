package com.example.williamsumitro.dress.view.view.purchase.receipt.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.receipt.adapter.PurchaseReceiptRV;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class P_receiptFragment extends Fragment {
    @BindView(R.id.p_receipt_ln_top) LinearLayout container_top;
    @BindView(R.id.p_receipt_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.p_receipt_rv) RecyclerView recyclerView;
    @BindView(R.id.p_receipt_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<Sales_OrderResult> orderResultArrayList;
    private DecimalFormat formatter;
    private PurchaseReceiptRV adapter;


    public P_receiptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p_receipt, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        return view;
    }
    public void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getreceiptconfirmation();
    }
    private void api_getreceiptconfirmation() {
        service = apiUtils.getAPIService();
        service.req_get_receipt_confirmation(token).enqueue(new Callback<Sales_OrderResponse>() {
            @Override
            public void onResponse(Call<Sales_OrderResponse> call, Response<Sales_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getSales_OrderResult().size()>0){
                            container_bottom.setVisibility(View.VISIBLE);
                            container_top.setVisibility(View.GONE);
                            orderResultArrayList = response.body().getSales_OrderResult();
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            container_bottom.setVisibility(View.GONE);
                            container_top.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Sales_OrderResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setupRV(){
        adapter = new PurchaseReceiptRV(context, orderResultArrayList, P_receiptFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderResultArrayList = new ArrayList<>();
    }
    private void initanim(Intent intent){
        Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
        context.startActivity(intent, bundle);
    }

}
