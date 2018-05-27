package com.example.williamsumitro.dress.view.view.sellerpanel.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoreEditInformation extends AppCompatActivity {
    @BindView(R.id.store_editinformation_banner) ImageView banner;
    @BindView(R.id.store_editinformation_toolbar) Toolbar toolbar;
    @BindView(R.id.store_editinformation_WA) TextInputEditText WA;
    @BindView(R.id.store_editinformation_tvsince) TextView since;
    @BindView(R.id.store_editinformation_StoreName) TextInputEditText store_name;
    @BindView(R.id.store_editinformation_SPprovince) Spinner spinner_province;
    @BindView(R.id.store_editinformation_SPcity) Spinner spinner_city;
    @BindView(R.id.store_editinformation_since)LinearLayout container_since;
    @BindView(R.id.store_editinformation_PhoneNumber) TextInputEditText phonenumber;
    @BindView(R.id.store_editinformation_logo) CircleImageView logo;
    @BindView(R.id.store_editinformation_Line) TextInputEditText line;
    @BindView(R.id.store_editinformation_layoutwebsite) TextInputLayout layout_website;
    @BindView(R.id.store_editinformation_layoutWA) TextInputLayout layout_WA;
    @BindView(R.id.store_editinformation_layoutStoreName) TextInputLayout layout_storename;
    @BindView(R.id.store_editinformation_layoutPhoneNumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.store_editinformation_layoutLine) TextInputLayout layout_line;
    @BindView(R.id.store_editinformation_layoutIG) TextInputLayout layout_IG;
    @BindView(R.id.store_editinformation_layoutFB) TextInputLayout layout_FB;
    @BindView(R.id.store_editinformation_layoutemail) TextInputLayout layout_email;
    @BindView(R.id.store_editinformation_layoutdetail) TextInputLayout layout_detail;
    @BindView(R.id.store_editinformation_layoutaddress) TextInputLayout layout_address;
    @BindView(R.id.store_editinformation_IG) TextInputEditText instagram;
    @BindView(R.id.store_editinformation_FB) TextInputEditText facebook;
    @BindView(R.id.store_editinformation_email) TextInputEditText email;
    @BindView(R.id.store_editinformation_detail) TextInputEditText detail;
    @BindView(R.id.store_editinformation_address) TextInputEditText address;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_edit_information);
        initObject();
        setupToolbar();
    }
    private void initObject() {
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
        getSupportActionBar().setTitle("Edit Store Information");
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
