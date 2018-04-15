package com.example.williamsumitro.dress.view.view.store.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Courier;
import com.example.williamsumitro.dress.view.view.order.adapter.BankRVAdapter;
import com.example.williamsumitro.dress.view.view.store.adapter.CourierRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformasioutletFragment extends Fragment {
    @BindView(R.id.informasioutlet_tvWA) TextView WA;
    @BindView(R.id.informasioutlet_tvLine) TextView line;
    @BindView(R.id.informasioutlet_tvIG) TextView IG;
    @BindView(R.id.informasioutlet_tvFB) TextView FB;
    @BindView(R.id.informasioutlet_lnContainer) LinearLayout container;
    @BindView(R.id.informasioutlet_rv_bank) RecyclerView rv_bank;
    @BindView(R.id.informasioutlet_rv_shipment) RecyclerView rv_shipment;
    private BankRVAdapter bankRVAdapter;
    private List<Bank> bankList;
    private List<Courier> courierList;
    private CourierRVAdapter courierRVAdapter;

    public InformasioutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasioutlet, container, false);
        initObject(view);
        initData();
        setupRV();
        return view;
    }
    private void initObject(View view){
        ButterKnife.bind(this, view);
        bankList = new ArrayList<>();
        courierList = new ArrayList<>();
    }
    private void setupRV(){
        bankRVAdapter = new BankRVAdapter(bankList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_bank.setLayoutManager(layoutManager);
        rv_bank.setItemAnimator(new DefaultItemAnimator());
        rv_bank.setAdapter(bankRVAdapter);

        courierRVAdapter = new CourierRVAdapter(courierList, getContext());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_shipment.setLayoutManager(layoutManager1);
        rv_shipment.setItemAnimator(new DefaultItemAnimator());
        rv_shipment.setAdapter(courierRVAdapter);
    }
    private void initData(){
        Bank bank = new Bank("bca", "Tek Kin Lung", "1563016616");
        bankList.add(bank);
        bank = new Bank("mandiri", "Tek Kin Lung", "1300000109473");
        bankList.add(bank);
        bank = new Bank("ocbc", "Tek Kin Lung", "084800005252");
        bankList.add(bank);

        Courier courier = new Courier("tiki");
        courierList.add(courier);
        courier = new Courier("jne");
        courierList.add(courier);
        courier = new Courier("sicepat");
        courierList.add(courier);
        courier = new Courier("pos");
        courierList.add(courier);
    }

}
