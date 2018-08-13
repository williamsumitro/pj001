package com.example.williamsumitro.dress.view.view.purchase.history.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.category.fragment.CategoryFragment;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.home.fragment.HotFragment;
import com.example.williamsumitro.dress.view.view.home.fragment.StoreFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class P_TransactionHistory extends Fragment {
    @BindView(R.id.transactionhistory_smarttablayout) SmartTabLayout tabLayout;
    @BindView(R.id.transactionhistory_viewpager) ViewPager viewPager;


    public P_TransactionHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_p__transaction_history, container, false);
        initView(view);
        viewPager.setOffscreenPageLimit(2);
        setupVP(viewPager);
        tabLayout.setViewPager(viewPager);
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);

    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getChildFragmentManager());
        adapter.addFragment(new P_historyFragment(), "Success Transaction");
        adapter.addFragment(new P_RejectedPayment(), "Reject Payment");
        viewPager.setAdapter(adapter);
    }

}
