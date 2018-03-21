package com.example.williamsumitro.dress.view.view.product.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagePasswords extends AppCompatActivity {
    @BindView(R.id.managepassword_toolbar) Toolbar toolbar;
    @BindView(R.id.managepassword_layoutoldpassword) TextInputLayout layoutoldpasswords;
    @BindView(R.id.managepassword_layoutnewpassword) TextInputLayout layoutnewpasswords;
    @BindView(R.id.managepassword_layoutconfirmnewpassword) TextInputLayout layoutconfirmnnewpasswords;
    @BindView(R.id.managepassword_etoldpassword) TextInputEditText oldpasswords;
    @BindView(R.id.managepassword_etnewpassword) TextInputEditText newpasswords;
    @BindView(R.id.managepassword_etconfirmnewpassword) TextInputEditText confirmnnewpasswords;
    @BindView(R.id.managepassword_btnLogin) Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_passwords);
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
        getSupportActionBar().setTitle("Settings");
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
