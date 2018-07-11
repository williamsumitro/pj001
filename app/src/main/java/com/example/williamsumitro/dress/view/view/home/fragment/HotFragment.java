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

//        Cloth cloth = new Cloth(productInfo.getProductName(), R.drawable.fake_dress1, "50.000");
//        ListList.add(cloth);
//        cloth = new Cloth("Eyelet Full Cotton Midi Skirt", R.drawable.fake_dress2, "10.000");
//        ListList.add(cloth);
//        cloth = new Cloth("Side Tie Flared Skirt", R.drawable.fake_dress3, "62.000");
//        ListList.add(cloth);
//        cloth = new Cloth("Stripe Ruched A-Line Dress", R.drawable.fake_dress4, "40.000");
//        ListList.add(cloth);
//        cloth = new Cloth("High Waisted Windowpane A-Line Skirt", R.drawable.fake_dress5, "32.000");
//        ListList.add(cloth);
//        cloth = new Cloth("High Waisted Striped Clean Pencil Skirt", R.drawable.fake_dress6, "12.000");
//        ListList.add(cloth);
//        cloth = new Cloth("Ruched Sleeve Cutaway Blazer", R.drawable.fake_dress2, "54.000");
//        ListList.add(cloth);
//        cloth = new Cloth("Express One Eleven Lace Inset Tee", R.drawable.fake_dress1, "78.000");
//        ListList.add(cloth);

//        Bid bid = new Bid("Dress sexy", "Sexy Store", R.drawable.fake_dress1, 4,4,1,"2 days",true);
//        bidList.add(bid);
//        bid = new Bid("Party Dress", "Tutup Lapak", R.drawable.fake_dress2, 0,10,0,"4 hours left",false);
//        bidList.add(bid);
//        bid = new Bid("Dress seperti gambar dibawah ini geng", "KPOP Store", R.drawable.fake_dress6, 0,4,0,"1 days",true);
//        bidList.add(bid);
//        bid = new Bid("Terserah yang penting mirip kek gini", "Bunda Cinta", R.drawable.fake_dress3, 1,0,0,"5 days",false);
//        bidList.add(bid);
//        bid = new Bid("Dress Lingerie", "Sukses Sejati", R.drawable.fake_dress4, 2,5,1,"3 hours left",false);
//        bidList.add(bid);
//        bid = new Bid("Need Fast", "Terang Bulan", R.drawable.fake_dress5, 0,0,0,"1 days",true);
//        bidList.add(bid);
    }
}
