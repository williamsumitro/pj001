package com.example.williamsumitro.dress.view.view.purchase.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.purchase.history.fragment.P_TransactionHistory;
import com.example.williamsumitro.dress.view.view.purchase.history.fragment.P_historyFragment;
import com.example.williamsumitro.dress.view.view.purchase.order.fragment.P_orderFragment;
import com.example.williamsumitro.dress.view.view.purchase.payment.fragment.P_paymentFragment;
import com.example.williamsumitro.dress.view.view.purchase.receipt.fragment.P_receiptFragment;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.fragment.P_reviewratingFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseFragment extends Fragment {
    @BindView(R.id.purchase_smarttablayout) SmartTabLayout smartTabLayout;
    @BindView(R.id.purchase_viewpager) ViewPager viewPager;

    private final static String NOTIFICATION1 = "NOTIFICATION1";
    private final static String REJECT = "REJECT";
    public PurchaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        initView(view);
        viewPager.setOffscreenPageLimit(2);
        setupVP(viewPager);
        smartTabLayout.setViewPager(viewPager);
        if (getArguments()!=null){
            if (getArguments().getString(NOTIFICATION1)!=null){
                smartTabLayout.getTabAt(1);
                viewPager.setCurrentItem(1);
            }
            else if (getArguments().getString(REJECT)!=null){
                smartTabLayout.getTabAt(4);
                viewPager.setCurrentItem(4);
            }
        }
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);

    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getChildFragmentManager());
        adapter.addFragment(new P_paymentFragment(), "Payment");
        adapter.addFragment(new P_orderFragment(), "Order Status");
        adapter.addFragment(new P_receiptFragment(), "Receipt Confirmation");
        adapter.addFragment(new P_reviewratingFragment(), "Review Rating");
        adapter.addFragment(new P_TransactionHistory(), "Transaction History");
        viewPager.setAdapter(adapter);
    }

}
