package com.example.williamsumitro.dress.view.view.purchase.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.purchase.adapter.TabOrderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    @BindView(R.id.order_tabs) TabLayout tabLayout;
    @BindView(R.id.order_viewpager) ViewPager viewPager;
    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        setupVP(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);

    }
    private void setupVP(ViewPager viewPager){
        TabOrderAdapter adapter = new TabOrderAdapter(getChildFragmentManager());
        adapter.addFragment(new OrderorderFragment(), "Order");
        adapter.addFragment(new PaymentstatusFragment(), "Payment Status");
        adapter.addFragment(new OrderstatusFragment(), "Order Status");
        adapter.addFragment(new ShippingconfirmationFragment(), "Shipping Confirmation");
        adapter.addFragment(new ReviewandratingFragment(), "Review and Rating");
        adapter.addFragment(new TransactionhistoryFragment(), "Transaction History");
        viewPager.setAdapter(adapter);
    }

}
