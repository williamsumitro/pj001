package com.example.williamsumitro.dress.view.view.sellerpanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.product.activity.AddProduct;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.activity.ManageSales;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerPanel extends AppCompatActivity {
    @BindView(R.id.sellerpanel_tv_dashboardstatus) TextView dashboardstatus;
    @BindView(R.id.sellerpanel_tv_dashboarddate) TextView dashboarddate;
    @BindView(R.id.sellerpanel_tv_balance) TextView balance;
    @BindView(R.id.sellerpanel_toolbar) Toolbar toolbar;
    @BindView(R.id.sellerpanel_spinner) Spinner spinner;
    @BindView(R.id.sellerpanel_cv_partnership) CardView layout_partnership;
    @BindView(R.id.sellerpanel_cv_openrequest) CardView layout_openrequest;
    @BindView(R.id.sellerpanel_cv_managesales) CardView layout_managesales;
    @BindView(R.id.sellerpanel_ing_viewstore) ImageView viewstore;
    @BindView(R.id.sellerpanel_img_viewproduct) ImageView viewproduct;
    @BindView(R.id.sellerpanel_img_legaldoc) ImageView legaldoc;
    @BindView(R.id.sellerpanel_img_editprofilephoto) ImageView editprofilephoto;
    @BindView(R.id.sellerpanel_img_addproduct) ImageView addproduct;
    @BindView(R.id.sellerpanel_tv_status) TextView status;
    @BindView(R.id.sellerpanel_tv_comment) TextView comment;
    @BindView(R.id.sellerpanel_ln_top_bottom) LinearLayout top_bottom;
    @BindView(R.id.sellerpanel_ln_bottom) LinearLayout bottom;
    @BindView(R.id.sellerpanel_ln_top) LinearLayout top;

    private final static String STATUS = "STATUS";
    private final static String COMMENT = "COMMENT";

    private Context context;
    private SessionManagement sessionManagement;
    private String token ="";
    private apiService service;
    private String extra_status, extra_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_panel);
        initView();
        setupToolbar();
        initGetIntent();
        initOnClick();
    }

    private void checkstatus() {
        if (extra_status.equals("0")){
            top.setVisibility(View.VISIBLE);
            status.setText("Waiting for admin approval");
            top_bottom.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
        }else if (extra_status.equals("1")){
            top.setVisibility(View.GONE);
            top_bottom.setVisibility(View.GONE);
            bottom.setVisibility(View.VISIBLE);
        }else if (extra_status.equals("2")){
            top.setVisibility(View.VISIBLE);
            status.setText("Waiting for admin approval");
            top_bottom.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.GONE);
            comment.setText(extra_comment);
        }
    }

    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(STATUS)){
            extra_status = getintent.getExtras().getString(STATUS);
            extra_comment = getintent.getExtras().getString(COMMENT);
            checkstatus();
        }else {
            Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
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
    private void initOnClick(){
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProduct.class);
                initanim(intent);
            }
        });
        layout_managesales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageSales.class);
                initanim(intent);
            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
