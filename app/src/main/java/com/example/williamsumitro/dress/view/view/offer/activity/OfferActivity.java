package com.example.williamsumitro.dress.view.view.offer.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.offer.fragment.OfferHistoryFragment;
import com.example.williamsumitro.dress.view.view.offer.fragment.RequestListFragment;
import com.example.williamsumitro.dress.view.view.request.activity.AddNewRequest;
import com.example.williamsumitro.dress.view.view.request.fragment.ActiveRequestFragment;
import com.example.williamsumitro.dress.view.view.request.fragment.MyrequestHistoryFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferActivity extends AppCompatActivity {
    @BindView(R.id.offer_smarttablayout) SmartTabLayout smartTabLayout;
    @BindView(R.id.offer_viewpager) ViewPager viewPager;
    @BindView(R.id.offer_toolbar) Toolbar toolbar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        initView();
        setupToolbar();
        viewPager.setOffscreenPageLimit(2);
        setupVP(viewPager);
        smartTabLayout.setViewPager(viewPager);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Request for Quotation");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getSupportFragmentManager());
        adapter.addFragment(new RequestListFragment(), "Request List");
        adapter.addFragment(new OfferHistoryFragment(), "Offer History");
        viewPager.setAdapter(adapter);
    }
}
