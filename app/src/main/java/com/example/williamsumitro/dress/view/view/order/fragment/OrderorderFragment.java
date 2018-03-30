package com.example.williamsumitro.dress.view.view.order.fragment;


import android.app.TabActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderorderFragment extends Fragment {

    @BindView(R.id.orderorder_lnOrderstatus) LinearLayout container_orderstatus;
    @BindView(R.id.orderorder_lnPayment) LinearLayout container_payment;
    @BindView(R.id.orderorder_lnReceiptconfirmation) LinearLayout container_receiptconfirmation;
    @BindView(R.id.orderorder_lnReviewandrating) LinearLayout container_reviewandrating;
    @BindView(R.id.orderorder_lnTransactionHistory) LinearLayout container_transactionhistory;
    @BindView(R.id.orderorder_tvTransactionHistory) TextView transactionshistory;
    @BindView(R.id.orderorder_tvReviewandrating) TextView reviewandrating;
    @BindView(R.id.orderorder_tvReceiptConfirmation) TextView receiptconfirmation;
    @BindView(R.id.orderorder_tvPayment) TextView payment;
    @BindView(R.id.orderorder_tvOrderStatus) TextView orderstatus;

    public OrderorderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderorder, container, false);
        initView(view);
        initClick();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
    }
    private void initClick(){
        container_orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.order_viewpager);
                viewPager.setCurrentItem(2);
            }
        });
        container_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.order_viewpager);
                viewPager.setCurrentItem(1);
            }
        });
        container_receiptconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.order_viewpager);
                viewPager.setCurrentItem(3);
            }
        });
        container_reviewandrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.order_viewpager);
                viewPager.setCurrentItem(4);
            }
        });
        container_transactionhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.order_viewpager);
                viewPager.setCurrentItem(5);
            }
        });
    }

}
