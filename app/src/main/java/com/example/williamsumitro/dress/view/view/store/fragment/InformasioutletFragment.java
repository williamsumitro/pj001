package com.example.williamsumitro.dress.view.view.store.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Courier;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.view.purchase.adapter.BankRVAdapter;
import com.example.williamsumitro.dress.view.view.openstore.adapter.CourierRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformasioutletFragment extends Fragment {
    @BindView(R.id.informasioutlet_tv_province) TextView province;
    @BindView(R.id.informasioutlet_tv_establishedyear) TextView establishedyear;
    @BindView(R.id.informasioutlet_tv_description) TextView description;
    @BindView(R.id.informasioutlet_tv_contactpersonphonenumber) TextView contactpersonnumber;
    @BindView(R.id.informasioutlet_tv_contactpersonname) TextView contactpersonname;
    @BindView(R.id.informasioutlet_tv_contactpersonjobtitle) TextView contactpersonjobtitle;
    @BindView(R.id.informasioutlet_tv_city) TextView city;
    @BindView(R.id.informasioutlet_tv_businesstype) TextView businesstype;

    private StoreDetails storeDetails;
    private final static String STORE_RESULT = "STORE_RESULT";

    public InformasioutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasioutlet, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        establishedyear.setText(storeDetails.getEstablishedYear());
        province.setText(storeDetails.getProvinceName());
        city.setText(storeDetails.getCityName());
        businesstype.setText(storeDetails.getBusinessType());
        description.setText(storeDetails.getDescription());
        contactpersonname.setText(storeDetails.getContactPersonName());
        contactpersonjobtitle.setText(storeDetails.getContactPersonJobTitle());
        contactpersonnumber.setText(storeDetails.getContactPersonPhoneNumber());
    }

    private void initView(View view){
        ButterKnife.bind(this, view);
        Bundle args = getArguments();
        storeDetails = (StoreDetails) args.getSerializable(STORE_RESULT);
    }

}
