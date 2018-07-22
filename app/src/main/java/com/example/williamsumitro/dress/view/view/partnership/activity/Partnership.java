package com.example.williamsumitro.dress.view.view.partnership.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.partnership.fragment.DownlinePartnershipFragment;
import com.example.williamsumitro.dress.view.view.partnership.fragment.UplinePartnershipFragment;
import com.example.williamsumitro.dress.view.view.sales.fragment.OrderConfirmationFragment;
import com.example.williamsumitro.dress.view.view.sales.fragment.ShippingConfirmationFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Partnership extends AppCompatActivity {

    @BindView(R.id.partnership_smarttablayout) SmartTabLayout smartTabLayout;
    @BindView(R.id.partnership_toolbar) Toolbar toolbar;
    @BindView(R.id.partnership_viewpager) ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership);
        initView();
        setuptoolbar();
        viewPager.setOffscreenPageLimit(2);
        setupVP(viewPager);
        smartTabLayout.setViewPager(viewPager);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Partnership");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initView() {
        ButterKnife.bind(this);
    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getSupportFragmentManager());
        adapter.addFragment(new UplinePartnershipFragment(), "Upline Partner");
        adapter.addFragment(new DownlinePartnershipFragment(), "Downline Partner");
        viewPager.setAdapter(adapter);
    }
}
