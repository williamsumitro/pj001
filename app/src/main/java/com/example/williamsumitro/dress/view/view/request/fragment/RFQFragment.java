package com.example.williamsumitro.dress.view.view.request.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.partnership.activity.MyDownlinePartnership;
import com.example.williamsumitro.dress.view.view.purchase.history.fragment.P_historyFragment;
import com.example.williamsumitro.dress.view.view.purchase.order.fragment.P_orderFragment;
import com.example.williamsumitro.dress.view.view.purchase.payment.fragment.P_paymentFragment;
import com.example.williamsumitro.dress.view.view.purchase.receipt.fragment.P_receiptFragment;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.fragment.P_reviewratingFragment;
import com.example.williamsumitro.dress.view.view.request.activity.AddNewRequest;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RFQFragment extends Fragment {
    @BindView(R.id.rfq_smarttablayout) SmartTabLayout smartTabLayout;
    @BindView(R.id.rfq_viewpager) ViewPager viewPager;
    @BindView(R.id.rfq_fab) FloatingActionButton floatingActionButton;

    private Context context;

    public RFQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rfq, container, false);
        initView(view);
        viewPager.setOffscreenPageLimit(2);
        setupVP(viewPager);
        smartTabLayout.setViewPager(viewPager);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, AddNewRequest.class);
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveRequestFragment(), "Active Request");
        adapter.addFragment(new MyrequestHistoryFragment(), "My Request History");
        viewPager.setAdapter(adapter);
    }
}
