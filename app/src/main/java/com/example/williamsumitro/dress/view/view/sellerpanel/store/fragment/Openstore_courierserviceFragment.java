package com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CourierDetails;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.adapter.OpenStore_CourierAdapter;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

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
public class Openstore_courierserviceFragment extends Fragment implements Step {
    @BindView(R.id.openstore_courierservice_recyclerview) RecyclerView recyclerView;

    private apiService service;
    private Context context;
    private SessionManagement sessionManagement;
    private List<CourierDetails> courierDetailsList;
    private OpenStore_CourierAdapter adapter;
    private ArrayList<String> courier_name;
    private ArrayList<String> courier_id;

    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    public Openstore_courierserviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_courierservice, container, false);
        initView(view);
        api_getcourier();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
    }
    private boolean ischecked(){
        StringBuilder stringBuilder = new StringBuilder();
        for (CourierDetails courierDetails : courierDetailsList) {
            if (courierDetails.getSelected()) {
                if (stringBuilder.length() > 0)
                    stringBuilder.append(",");
                stringBuilder.append(courierDetails.getCourierId());
            }
        }
        if (stringBuilder.length()>0){
            String baru = stringBuilder.toString();
            sessionManagement.keepStoreCourier(baru);
            return true;
        }
        else return false;
    }
    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
        if (!ischecked())
            verificationError = new VerificationError("Please pick any courier");
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {


    }
    public void api_getcourier(){
        service = apiUtils.getAPIService();
        service.req_get_courier_list().enqueue(new Callback<CourierResponse>() {
            @Override
            public void onResponse(Call<CourierResponse> call, Response<CourierResponse> response) {
                if(response.code()==200){
                    courierDetailsList = response.body().getCourier();
                    adapter = new OpenStore_CourierAdapter(courierDetailsList, context);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CourierResponse> call, Throwable t) {

            }
        });
    }
    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(ischecked());
        }
    }
}
