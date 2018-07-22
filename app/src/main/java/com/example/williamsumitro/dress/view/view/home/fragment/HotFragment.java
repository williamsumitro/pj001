package com.example.williamsumitro.dress.view.view.home.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.BestResponse;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

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
public class HotFragment extends Fragment {
    @BindView(R.id.hot_rv_bestseller) RecyclerView rv_bestseller;
    @BindView(R.id.hot_rv_newproduct) RecyclerView rv_newproduct;
    @BindView(R.id.hot_ln_best) LinearLayout container_best;
    @BindView(R.id.hot_ln_new) LinearLayout container_new;
    @BindView(R.id.hot_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private List<ProductInfo> newList = new ArrayList<>();
    private List<ProductInfo> bestList = new ArrayList<>();
    private HotRVAdapter bestAdapter, newAdapter;
    private apiService service;
    private ProductInfo productInfo;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private SnapHelper snapHelper = new LinearSnapHelper();
    public HotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
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
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getbestsellerproduct();
        api_getnewproduct();
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getContext());
    }
    private void api_getbestsellerproduct(){
        service = apiUtils.getAPIService();
        service.req_get_best_seller().enqueue(new Callback<BestResponse>() {
            @Override
            public void onResponse(Call<BestResponse> call, Response<BestResponse> response) {
                if (response.code() == 200){
                    if (response.body().getProductInfo().size()>0){
                        container_best.setVisibility(View.VISIBLE);
                        bestList = response.body().getProductInfo();
                        bestAdapter = new HotRVAdapter(bestList, getActivity());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        rv_bestseller.setLayoutManager(layoutManager);
                        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(bestAdapter);
                        alphaAdapter.setDuration(1000);
                        alphaAdapter.setInterpolator(new OvershootInterpolator());
                        rv_bestseller.setAdapter(alphaAdapter);
                        snapHelper.attachToRecyclerView(rv_bestseller);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        container_best.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                else {
                    Toasty.error(getContext(), "Something error", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<BestResponse> call, Throwable t) {
                Toasty.error(getContext(), "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void api_getnewproduct(){
        service = apiUtils.getAPIService();
        service.req_get_new_product().enqueue(new Callback<BestResponse>() {
            @Override
            public void onResponse(Call<BestResponse> call, Response<BestResponse> response) {
                if (response.code() == 200){
                    if (response.body().getProductInfo().size()>0){
                        container_new.setVisibility(View.VISIBLE);
                        newList = response.body().getProductInfo();
                        newAdapter = new HotRVAdapter(newList, getActivity());
                        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(getActivity(), 2);
                        rv_newproduct.setLayoutManager(grid_layoutmanager);
                        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(newAdapter);
                        alphaAdapter.setDuration(1000);
                        alphaAdapter.setInterpolator(new OvershootInterpolator());
                        rv_newproduct.setAdapter(alphaAdapter);
                        snapHelper.attachToRecyclerView(rv_newproduct);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        container_new.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                else {
                    Toasty.error(getContext(), "Something error", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<BestResponse> call, Throwable t) {
                Toasty.error(getContext(), "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
