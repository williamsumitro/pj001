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
import com.example.williamsumitro.dress.view.view.order.adapter.ConfirmationRVAdapter;
import com.example.williamsumitro.dress.view.view.order.adapter.TransactionhistoryRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionhistoryFragment extends Fragment {
    @BindView(R.id.transactionhistory_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.transactionhistory_tv_total) TextView total;
    private Context context;
    private List<Order> orderList;
    private TransactionhistoryRVAdapter adapter;
    private int temp = 0;
    private DecimalFormat formatter;



    public TransactionhistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactionhistory, container, false);
        initView(view);
        initData();
        setupRV();
        for(int i = 0; i<orderList.size();i++){
            temp += Integer.valueOf(orderList.get(i).getTotal());
        }
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(temp))));
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        orderList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
    }
    private void setupRV(){
        adapter = new TransactionhistoryRVAdapter(orderList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Order order = new Order(R.drawable.fake_store,"ABC Store", "ODR/2018/02/001/XX", "20 Februari 2018", "RCP/2210/223/122/XX/1");
        orderList.add(order);
        order = new Order(R.drawable.fake_store1,"TEK Store", "ODR/2018/01/001/XX", "18 Januari 2018", "RCP/2210/223/122/XX/2");
        orderList.add(order);
    }

}
