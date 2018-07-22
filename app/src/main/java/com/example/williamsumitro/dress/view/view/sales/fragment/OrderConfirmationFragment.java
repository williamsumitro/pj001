package com.example.williamsumitro.dress.view.view.sales.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
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
import com.example.williamsumitro.dress.view.view.sales.adapter.OC_RVAdapter;

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
public class OrderConfirmationFragment extends Fragment {
    @BindView(R.id.order_confirmation_rv) RecyclerView recyclerView;
    @BindView(R.id.order_confirmation_ln_bottom) LinearLayout bottom;
    @BindView(R.id.order_confirmation_ln_top) LinearLayout top;
    @BindView(R.id.order_confirmation_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ArrayList<Sales_OrderResult> orderResultArrayList;
    private OC_RVAdapter adapter;
    private SnapHelper snapHelper = new LinearSnapHelper();
    public OrderConfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
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
        api_getorderconfirmation();
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getContext();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        orderResultArrayList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new OC_RVAdapter(context, orderResultArrayList, OrderConfirmationFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }
    private void api_getorderconfirmation() {
        service = apiUtils.getAPIService();
        service.req_seller_get_order(token).enqueue(new Callback<Sales_OrderResponse>() {
            @Override
            public void onResponse(Call<Sales_OrderResponse> call, Response<Sales_OrderResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getSales_OrderResult().size()>0){
                            bottom.setVisibility(View.VISIBLE);
                            orderResultArrayList = response.body().getSales_OrderResult();
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            top.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                    else {
                        Toasty.error(context, "Error, please contact our administrator", Toast.LENGTH_SHORT, true).show();
                        swipeRefreshLayout.setRefreshing(false);
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


}
