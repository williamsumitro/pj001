package com.example.williamsumitro.dress.view.view.order.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;
import com.example.williamsumitro.dress.view.view.order.adapter.ConfirmationRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShippingconfirmationFragment extends Fragment {
    @BindView(R.id.shippingconfirmation_recyclerview) RecyclerView recyclerView;
    private Context context;
    private List<Order> orderList;
    private ConfirmationRVAdapter adapter;

    public ShippingconfirmationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shippingconfirmation, container, false);
        initView(view);
        initData();
        setupRV();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        orderList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new ConfirmationRVAdapter(orderList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Order order = new Order("ABC Store", "ODR/2018/02/001/XX", "20 Februari 2018", "RCP/2210/223/122/XX/1");
        orderList.add(order);
        order = new Order("TEK Store", "ODR/2018/01/001/XX", "18 Januari 2018", "RCP/2210/223/122/XX/2");
        orderList.add(order);
    }

}
