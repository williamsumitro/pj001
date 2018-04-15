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
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;
import com.example.williamsumitro.dress.view.view.order.adapter.PaymentRVAdapter;
import com.example.williamsumitro.dress.view.view.order.adapter.StatusRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderstatusFragment extends Fragment {
    @BindView(R.id.orderstatus_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.orderstatus_tv_pending) TextView pending;
    private Context context;
    private List<Order> orderList;
    private StatusRVAdapter adapter;
    private int temp;

    public OrderstatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderstatus, container, false);
        initView(view);
        initData();
        setupRV();
        for(int i = 0;i<orderList.size();i++){
            if(orderList.get(i).getStatus().toLowerCase().equals("waiting seller response"))
                temp += 1;
        }
        pending.setText(String.valueOf(temp));
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        orderList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new StatusRVAdapter(orderList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Order order = new Order(R.drawable.fake_store, "ABC Store", "ODR/2018/02/001/XX", "20 Februari 2018", 2000000, "Waiting Seller Response");
        orderList.add(order);
        order = new Order(R.drawable.fake_store1, "TEK Store", "ODR/2018/01/001/XX", "18 Januari 2018", 2000000, "Order Approved");
        orderList.add(order);
    }

}
