package com.example.williamsumitro.dress.view.view.checkout.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CheckoutInfo;
import com.example.williamsumitro.dress.view.model.CheckoutResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.activity.Checkout;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutRVAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_CourierFragment extends Fragment implements Step {
    @BindView(R.id.checkout_courier_rvshoppingbag) RecyclerView recyclerView;
    @BindView(R.id.checkout_courier_btn_refresh) Button refresh;
    @BindView(R.id.checkout_courier_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.checkout_courier_btn_check) Button check;
    @BindView(R.id.checkout_courier_tvStatus) TextView tv_status;

    private apiService service;
    private Context context;
    private SessionManagement sessionManagement;
    private String token, id_city;
    private ProgressDialog progressDialog;
    private ArrayList<CheckoutInfo> checkoutInfoArrayList;
    private String total_qty;
    private String total_price;
    private String available_points;
    private CheckoutRVAdapter adapter;
    private SweetAlertDialog sweetAlertDialog;

    private Boolean checked = false, check_refresh = false;


    @Nullable
    private OnNavigationBarListener onNavigationBarListener;


    public Checkout_CourierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout__courier, container, false);
        initView(view);
        initButton();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_getcheckoutinfo();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(0);
            }
        });
        return view;
    }
    private void initButton(){
        if (!check_refresh)
            check.setVisibility(View.GONE);
        else
            check.setVisibility(View.VISIBLE);
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        checkoutInfoArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
        if (!isRefresh())
            verificationError = new VerificationError("Please press the button confirm");
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();

    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if(!checked)
            check.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));

    }
    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(true);
        }
    }
    private boolean isRefresh(){
        return checked;
    }

    private void initDialog(final int stats){
        if(stats == 0){
            final TextView text = new TextView(context);
            text.setText("Have you check all the information ?");
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Confirm")
                    .setCustomView(text)
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            checked = true;
                            ((Checkout)getActivity()).ChangeColor(2);
                            tv_status.setText("Please click complete to proceed");
                            tv_status.setTextColor(getResources().getColor(R.color.green));
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void api_getcheckoutinfo() {
        HashMap<String, String> idcity = sessionManagement.getCheckoutIdCity();
        id_city = idcity.get(SessionManagement.CHECKOUT_IDCITY);
//        Toasty.warning(context, id_city, Toast.LENGTH_LONG, true).show();
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        service = apiUtils.getAPIService();

        service = apiUtils.getAPIService();
        service.req_get_checkout_info(token, id_city).enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()){
                        checkoutInfoArrayList = response.body().getCheckoutInfo();
                        total_price = response.body().getTotalPrice();
                        total_qty = response.body().getTotalQty();
                        available_points = String.valueOf(response.body().getAvailablePoints());
                        CheckoutRVAdapter adapter = new CheckoutRVAdapter(checkoutInfoArrayList, context, Checkout_CourierFragment.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.checkout_courier_rvshoppingbag);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        progressDialog.dismiss();
                        sessionManagement.keepCheckoutCourier(total_price, total_qty, available_points);
                        check_refresh = true;
                        initButton();
                    }
                    else {
                        String message = response.body().getMessage();
                        Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                Toasty.error(context, "Please press button refresh again", Toast.LENGTH_LONG, true).show();
                progressDialog.dismiss();
            }
        });
    }
    public void setCheck(Boolean checkz){
        checked = checkz;
    }
}
