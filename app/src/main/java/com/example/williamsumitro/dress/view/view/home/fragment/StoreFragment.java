package com.example.williamsumitro.dress.view.view.home.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.AllStoreResponse;
import com.example.williamsumitro.dress.view.model.BestResponse;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.model.StoreInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.openstore.adapter.StoreRVAdapter;

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
public class StoreFragment extends Fragment {
    @BindView(R.id.store_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.store_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private ArrayList<StoreInfo> storeInfoArrayList;
    private StoreRVAdapter adapter;
    private apiService service;


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        setupRV();
        return view;
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getallstore();
    }

    private void api_getallstore() {
        service.req_get_all_store().enqueue(new Callback<AllStoreResponse>() {
            @Override
            public void onResponse(Call<AllStoreResponse> call, Response<AllStoreResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStoreInfo().size()>0){
                        storeInfoArrayList = response.body().getStoreInfo();
                        setupRV();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                else {
                    Toasty.error(getContext(), "Please swipe down to refresh on Outlet Tab", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<AllStoreResponse> call, Throwable t) {
                Toasty.error(getContext(), "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        service = apiUtils.getAPIService();
        storeInfoArrayList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new StoreRVAdapter(storeInfoArrayList, context);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(adapter);
    }

}
