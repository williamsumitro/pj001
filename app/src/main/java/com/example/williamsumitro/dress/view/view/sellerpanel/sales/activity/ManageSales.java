package com.example.williamsumitro.dress.view.view.sellerpanel.sales.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.purchase.fragment.OrderorderFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.OrderstatusFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.PaymentstatusFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.ReviewandratingFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.ShippingconfirmationFragment;
import com.example.williamsumitro.dress.view.view.purchase.fragment.TransactionhistoryFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.adapter.TabManageSalesAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageSales extends AppCompatActivity {
    @BindView(R.id.managesales_toolbar) Toolbar toolbar;
    @BindView(R.id.managesales_viewpager) ViewPager viewpager;
    @BindView(R.id.managesales_tabs) TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sales);
        initView();
        setuptoolbar();
        setupVP(viewpager);
        tabLayout.setupWithViewPager(viewpager);
    }
    private void initView(){
        ButterKnife.bind(this);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Sales");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupVP(ViewPager viewPager){
        TabManageSalesAdapter adapter = new TabManageSalesAdapter(getSupportFragmentManager());
        adapter.addFragment(new OrderorderFragment(), "Order");
        adapter.addFragment(new PaymentstatusFragment(), "Payment Status");
        adapter.addFragment(new OrderstatusFragment(), "Order Status");
        adapter.addFragment(new ShippingconfirmationFragment(), "Shipping Confirmation");
        adapter.addFragment(new ReviewandratingFragment(), "Review and Rating");
        adapter.addFragment(new TransactionhistoryFragment(), "Transaction History");
        viewPager.setAdapter(adapter);
    }
}
