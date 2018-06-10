package com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.activity.PreviewStore;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.activity.StoreEditInformation;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyStoreFragment extends Fragment {
    @BindView(R.id.mystore_cvPreviewStore) CardView container_previewstore;
    @BindView(R.id.mystore_cvEditBankAccount) CardView container_editbankaccount;
    @BindView(R.id.mystore_cvEditCourier) CardView container_editcourier;
    @BindView(R.id.mystore_cvEditInformation) CardView container_editinformation;
    @BindView(R.id.mystore_cvSales) CardView container_sales;
    private Context context;

    public MyStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_store, container, false);
        initView(view);
        container_editbankaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        container_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        container_previewstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PreviewStore.class);
                initanim(intent);
            }
        });
        container_editinformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoreEditInformation.class);
                initanim(intent);
            }
        });
        container_editcourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
    }
    private void initanim(Intent intent){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
            context.startActivity(intent, bundle);
        }
    }
}
