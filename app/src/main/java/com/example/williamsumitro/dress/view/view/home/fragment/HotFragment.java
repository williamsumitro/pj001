package com.example.williamsumitro.dress.view.view.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bid;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.home.adapter.HotOpenBidRVAdapter;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.model.Cloth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {

    @BindView(R.id.hot_rvList) RecyclerView rvList;
//    @BindView(R.id.hot_rv_OpenBid) RecyclerView rvOpenBid;
//    @BindView(R.id.hot_btn_openbid) Button btnOpenBid;
    private HotOpenBidRVAdapter openbid_adapter;
    private List<ProductInfo> productInfoList = new ArrayList<>();
//    private List<Cloth> ListList = new ArrayList<>();
//    private List<Bid> bidList = new ArrayList<>();
    private HotRVAdapter adapterList;
    private apiService service;
    private ProductInfo productInfo;

    public HotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(view);
        initData();
//        openbid_adapter = new HotOpenBidRVAdapter(bidList, getContext());
//        RecyclerView.LayoutManager horizontal_layoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rvOpenBid.setLayoutManager(horizontal_layoutmanager);
//        rvOpenBid.setItemAnimator(new DefaultItemAnimator());
//        rvOpenBid.setAdapter(openbid_adapter);

        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
    }
    private void addProductInfo(ProductInfo productInfo){
        productInfoList.add(productInfo);
    }
    private void setupRV(){
        adapterList = new HotRVAdapter(productInfoList, getActivity());
        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(getActivity(), 2);
        rvList.setLayoutManager(grid_layoutmanager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapterList);
    }
    private void initData(){
        service = apiUtils.getAPIService();
        service.req_get_product_detail("8").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("9").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("10").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("11").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("12").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("13").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("14").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("15").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("16").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        service.req_get_product_detail("17").enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    productInfo = response.body().getProductInfo();
                    addProductInfo(productInfo);
                    setupRV();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
    }
}
