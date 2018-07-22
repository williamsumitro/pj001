package com.example.williamsumitro.dress.view.view.offer.fragment;


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
import com.example.williamsumitro.dress.view.model.RFQResponse;
import com.example.williamsumitro.dress.view.model.RFQResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.offer.adapter.RequestListRV;

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
public class RequestListFragment extends Fragment {
    @BindView(R.id.requestlist_rv) RecyclerView recyclerView;
    @BindView(R.id.requestlist_ln_bottom) LinearLayout bottom;
    @BindView(R.id.requestlist_ln_top) LinearLayout top;
    @BindView(R.id.requestlist_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ArrayList<RFQResult> resultArrayList;
    private RequestListRV adapter;
    private SnapHelper snapHelper = new LinearSnapHelper();

    public RequestListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);
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
        api_getrequestlist();
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getContext();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        resultArrayList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new RequestListRV(context, resultArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }
    private void api_getrequestlist() {
        service = apiUtils.getAPIService();
        service.req_get_rfq_request(token).enqueue(new Callback<RFQResponse>() {
            @Override
            public void onResponse(Call<RFQResponse> call, Response<RFQResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            bottom.setVisibility(View.VISIBLE);
                            resultArrayList = response.body().getResult();
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
            public void onFailure(Call<RFQResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
