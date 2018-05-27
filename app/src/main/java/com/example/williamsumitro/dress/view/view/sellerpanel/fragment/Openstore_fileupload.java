package com.example.williamsumitro.dress.view.view.sellerpanel.fragment;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_fileupload extends Fragment implements Step {
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;

    @BindView(R.id.openstore_fileupload_tdp) ImageView tdp;
    @BindView(R.id.openstore_fileupload_skdp) ImageView skdp;
    @BindView(R.id.openstore_fileupload_siup) ImageView siup;
    @BindView(R.id.openstore_fileupload_npwp) ImageView npwp;
    @BindView(R.id.openstore_fileupload_logo) CircleImageView logo;
    @BindView(R.id.openstore_fileupload_ktp) ImageView ktp;
    @BindView(R.id.openstore_fileupload_banner) ImageView banner;
    @BindView(R.id.openstore_fileupload_addtdp) ImageView add_tdp;
    @BindView(R.id.openstore_fileupload_addskdp) ImageView add_skdp;
    @BindView(R.id.openstore_fileupload_addsiup) ImageView add_siup;
    @BindView(R.id.openstore_fileupload_addnpwp) ImageView add_npwp;
    @BindView(R.id.openstore_fileupload_addktp) ImageView add_ktp;

    private apiService service;
    private Context context;
    private SessionManagement sessionManagement;

    public Openstore_fileupload() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_fileupload, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
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
