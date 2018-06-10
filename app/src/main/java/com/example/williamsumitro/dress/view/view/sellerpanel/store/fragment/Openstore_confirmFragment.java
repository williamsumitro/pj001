package com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_confirmFragment extends Fragment implements Step {
    @BindView(R.id.openstore_confirm_storename) TextView storename;
    @BindView(R.id.openstore_confirm_province) TextView province;
    @BindView(R.id.openstore_confirm_phonenumber) TextView phonenumber;
    @BindView(R.id.openstore_confirm_name) TextView name;
    @BindView(R.id.openstore_confirm_jobtitle) TextView jobtitle;
    @BindView(R.id.openstore_confirm_establishedyear) TextView establishedyear;
    @BindView(R.id.openstore_confirm_description) TextView description;
    @BindView(R.id.openstore_confirm_delivery) TextView delivery;
    @BindView(R.id.openstore_confirm_city) TextView city;

    private ProgressDialog progressDialog;
    private apiService service;
    private SessionManagement sessionManagement;
    private Context context;
    public Openstore_confirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_confirm, container, false);
        initView(view);
        HashMap<String, String> store = sessionManagement.getStoreInformation();
        storename.setText(store.get(SessionManagement.STORE_NAME));
        province.setText(store.get(SessionManagement.STORE_PROVINCE));
        phonenumber.setText(store.get(SessionManagement.STORE_CONTACT_PERSON_PHONE_NUMBER));
        name.setText(store.get(SessionManagement.STORE_CONTACT_PERSON_NAME));
        jobtitle.setText(store.get(SessionManagement.STORE_CONTACT_PERSON_JOB_TITLE));
        establishedyear.setText(store.get(SessionManagement.STORE_ESTABLISHED_YEAR));
        description.setText(store.get(SessionManagement.STORE_DESCRIPTION));
        Toast.makeText(context, store.get(SessionManagement.STORE_COURIER), Toast.LENGTH_SHORT);
        delivery.setText(store.get(SessionManagement.STORE_COURIER));
        city.setText(store.get(SessionManagement.STORE_CITY));
        return view;
    }

    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        progressDialog = new ProgressDialog(context);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
