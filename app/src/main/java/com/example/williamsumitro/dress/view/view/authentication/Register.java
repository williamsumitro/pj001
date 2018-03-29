package com.example.williamsumitro.dress.view.view.authentication;

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

public class Register extends AppCompatActivity {
    @BindView(R.id.register_toolbar) Toolbar toolbar;
    @BindView(R.id.register_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.register_layoutpassword) TextInputLayout layoutpassword;
    @BindView(R.id.register_layoutfullname) TextInputLayout layoutname;
    @BindView(R.id.register_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.register_etphonenumber) TextInputEditText phonenumber;
    @BindView(R.id.register_etpassword) TextInputEditText password;
    @BindView(R.id.register_etfullname) TextInputEditText name;
    @BindView(R.id.register_etemail) TextInputEditText email;
    @BindView(R.id.register_btnregister)Button register;
    @BindView(R.id.register_btnlogin) Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setuptoolbar();
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
        getSupportActionBar().setTitle("Register");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
}