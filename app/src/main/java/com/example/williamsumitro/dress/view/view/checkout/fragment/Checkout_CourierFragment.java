package com.example.williamsumitro.dress.view.view.checkout.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CheckoutInfo;
import com.example.williamsumitro.dress.view.model.CheckoutResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutRVAdapter;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_CourierFragment extends Fragment implements Step {
    @BindView(R.id.checkout_courier_rvshoppingbag) RecyclerView recyclerView;
//    @BindView(R.id.checkout_courier_button) Button button;
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
    private Dialog dialog;

    private Boolean checked = false;

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
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                api_getcheckoutinfo();
//                button.setVisibility(View.GONE);
//                nestedScrollView.setVisibility(View.VISIBLE);
//            }
//        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(0);
            }
        });
        return view;
    }


    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        checkoutInfoArrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        HashMap<String, String> idcity = sessionManagement.getCheckoutIdCity();
        id_city = idcity.get(SessionManagement.CHECKOUT_IDCITY);
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
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        final TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button buttonok = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 0){
            status.setText("Confirm");
            detail.setText("You have checked all the information");
            bg.setBackgroundResource(R.color.green7);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttoncancel.setBackgroundResource(R.drawable.button1_1);
            buttonok.setText("Yes");
            buttoncancel.setVisibility(View.VISIBLE);
            buttoncancel.setText("No");
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checked = true;
                    tv_status.setText("Please click complete to proceed");
                    tv_status.setTextColor(getResources().getColor(R.color.green));
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
