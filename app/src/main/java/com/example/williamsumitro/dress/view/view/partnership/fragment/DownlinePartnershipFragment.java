package com.example.williamsumitro.dress.view.view.partnership.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipItem;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipResponse;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.partnership.activity.MyDownlinePartnership;
import com.example.williamsumitro.dress.view.view.partnership.adapter.DownlinePartnership_RV;

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
public class DownlinePartnershipFragment extends Fragment {
    @BindView(R.id.downlinepartnership_rv) RecyclerView recyclerView;
    @BindView(R.id.downlinepartnership_ln_bottom) LinearLayout bottom;
    @BindView(R.id.downlinepartnership_ln_top) LinearLayout top;
    @BindView(R.id.downlinepartnership_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.downlinepartnership_btn_mydownlinepartners) Button mydownlinepartnership;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private DownlinePartnership_RV adapter;
    private ArrayList<DownlinePartnershipItem> itemArrayList;
    private ArrayList<DownlinePartnershipResult> resultArrayList;
    private SnapHelper snapHelper = new LinearSnapHelper();

    public DownlinePartnershipFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downline_partnership, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        initClick();
        return view;
    }
    private void initClick() {
        mydownlinepartnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, MyDownlinePartnership.class);
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
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
        itemArrayList = new ArrayList<>();
        resultArrayList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new DownlinePartnership_RV(context, itemArrayList, DownlinePartnershipFragment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(recyclerView);
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
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
