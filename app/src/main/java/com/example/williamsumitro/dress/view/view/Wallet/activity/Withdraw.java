package com.example.williamsumitro.dress.view.view.Wallet.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Withdraw extends AppCompatActivity {
    @BindView(R.id.withdraw_tv_balance) TextView balance;
    @BindView(R.id.withdraw_toolbar) Toolbar toolbar;
    @BindView(R.id.withdraw_layout_yourpassword) TextInputLayout layout_yourpassword;
    @BindView(R.id.withdraw_layout_withdrawammount) TextInputLayout layout_withdrawamount;
    @BindView(R.id.withdraw_layout_nameinbankaccount) TextInputLayout layout_nameinbankaccount;
    @BindView(R.id.withdraw_layout_branch) TextInputLayout layout_branch;
    @BindView(R.id.withdraw_layout_bankname) TextInputLayout layout_bankname;
    @BindView(R.id.withdraw_layout_bankaccountnumber) TextInputLayout layout_bankaccountnumber;
    @BindView(R.id.withdraw_et_yourpassword) TextInputEditText yourpassword;
    @BindView(R.id.withdraw_et_withdrawammount) TextInputEditText withdrawamount;
    @BindView(R.id.withdraw_et_nameinbankaccount) TextInputEditText nameinbankaccount;
    @BindView(R.id.withdraw_et_branch) TextInputEditText branch;
    @BindView(R.id.withdraw_et_bankname) TextInputEditText bankname;
    @BindView(R.id.withdraw_et_bankaccountnumber) TextInputEditText bankaccountnumber;
    @BindView(R.id.withdraw_btn_submit) Button submit;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initView();
        setupToolbar();
    }
    private void initView() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Withdraw");
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
