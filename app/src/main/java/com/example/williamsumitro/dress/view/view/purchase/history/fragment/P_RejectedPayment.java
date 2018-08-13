package com.example.williamsumitro.dress.view.view.purchase.history.fragment;


import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResult;
import com.example.williamsumitro.dress.view.model.Purchase_RejectResponse;
import com.example.williamsumitro.dress.view.model.Purchase_RejectResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.history.adapter.PurchaseRejectAdapter;
import com.example.williamsumitro.dress.view.view.purchase.payment.fragment.P_paymentFragment;

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
public class P_RejectedPayment extends Fragment {

    @BindView(R.id.p_reject_ln_top) LinearLayout container_top;
    @BindView(R.id.p_reject_ln_bottom) LinearLayout container_bottom;
    @BindView(R.id.p_reject_rv) RecyclerView recyclerView;
    @BindView(R.id.p_reject_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private PurchaseRejectAdapter adapter;
    private ArrayList<Purchase_RejectResult> purchasePaymentResultArrayList;
    private ArrayList<Bank> bankArrayList;
    private ArrayList<OrderStore> orderStoreArrayList;
    private ProgressDialog progressDialog;


    public P_RejectedPayment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p__rejected_payment, container, false);
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
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        purchasePaymentResultArrayList = new ArrayList<>();
        bankArrayList = new ArrayList<>();
        orderStoreArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
    }
    private void initanim(Intent intent){
        Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
        context.startActivity(intent, bundle);
    }
    public void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getrejectpayment();
    }
    private void api_getrejectpayment() {
        service = apiUtils.getAPIService();
        service.req_reject(token).enqueue(new Callback<Purchase_RejectResponse>() {
            @Override
            public void onResponse(Call<Purchase_RejectResponse> call, Response<Purchase_RejectResponse> response) {
                if (response.code() == 200){
                    if (response.body().getStatus()){
                        if (response.body().getResult().size()>0){
                            container_bottom.setVisibility(View.VISIBLE);
                            container_top.setVisibility(View.GONE);
                            purchasePaymentResultArrayList = response.body().getResult();
                            setupRV();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        else {
                            container_bottom.setVisibility(View.GONE);
                            container_top.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                    else {
                        Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                else {
                    Toasty.error(context, "There is some error here", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Purchase_RejectResponse> call, Throwable t) {
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setupRV(){
        adapter = new PurchaseRejectAdapter(context, purchasePaymentResultArrayList, bankArrayList ,P_RejectedPayment.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
    }
}
