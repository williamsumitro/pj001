package com.example.williamsumitro.dress.view.view.sellerpanel.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SellerPanel extends AppCompatActivity {
    @BindView(R.id.sellerpanel_tv_dashboardstatus) TextView dashboardstatus;
    @BindView(R.id.sellerpanel_tv_dashboarddate) TextView dashboarddate;
    @BindView(R.id.sellerpanel_tv_balance) TextView balance;
    @BindView(R.id.sellerpanel_toolbar) Toolbar toolbar;
    @BindView(R.id.sellerpanel_spinner) Spinner spinner;
    @BindView(R.id.sellerpanel_cv_storesettings) CardView layout_storesettings;
    @BindView(R.id.sellerpanel_cv_productsettings) CardView layout_productsettings;
    @BindView(R.id.sellerpanel_cv_partnership) CardView layout_partnership;
    @BindView(R.id.sellerpanel_cv_openrequest) CardView layout_openrequest;
    @BindView(R.id.sellerpanel_cv_managesales) CardView layout_managesales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_panel);
        initView();
        setupToolbar();

    }
    private void initView(){
        ButterKnife.bind(this);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Seller Panel");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
}
