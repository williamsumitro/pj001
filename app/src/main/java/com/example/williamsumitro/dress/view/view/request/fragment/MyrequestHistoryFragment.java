package com.example.williamsumitro.dress.view.view.request.fragment;


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
import com.example.williamsumitro.dress.view.model.RFQ_HistoryResponse;
import com.example.williamsumitro.dress.view.model.RFQ_HistoryResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.request.adapter.MyRequestHistoryRV;

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
public class MyrequestHistoryFragment extends Fragment {
    @BindView(R.id.myrequesthistory_rv) RecyclerView recyclerView;
    @BindView(R.id.myrequesthistory_ln_bottom) LinearLayout bottom;
    @BindView(R.id.myrequesthistory_ln_top) LinearLayout top;
    @BindView(R.id.myrequesthistory_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ArrayList<RFQ_HistoryResult> resultArrayList;
    private MyRequestHistoryRV adapter;
    private SnapHelper snapHelper = new LinearSnapHelper();

    public MyrequestHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myrequest_history, container, false);
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
        api_getRFQhistory();
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getContext();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        resultArrayList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new MyRequestHistoryRV(context, resultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }
    private void api_getRFQhistory() {
        service = apiUtils.getAPIService();
        service.req_rfq_history(token).enqueue(new Callback<RFQ_HistoryResponse>() {
            @Override
            public void onResponse(Call<RFQ_HistoryResponse> call, Response<RFQ_HistoryResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            top.setVisibility(View.GONE);
                            bottom.setVisibility(View.VISIBLE);
                            resultArrayList = response.body().getResult();
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            top.setVisibility(View.VISIBLE);
                            bottom.setVisibility(View.GONE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RFQ_HistoryResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
