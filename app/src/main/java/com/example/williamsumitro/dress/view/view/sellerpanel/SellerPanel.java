package com.example.williamsumitro.dress.view.view.sellerpanel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.sellerpanel.product.activity.AddProduct;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.activity.ManageSales;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.activity.OpenStore;

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
    private StoreDetails storeDetails;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkstatus();
        setContentView(R.layout.activity_seller_panel);
        initView();
        setupToolbar();
        initOnClick();
    }

    private void checkstatus() {
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if(response.code()==200){
                    storeDetails = response.body().getStore();
                    switch (storeDetails.getStoreActiveStatus()) {
                        case "0":
                            top.setVisibility(View.VISIBLE);
                            status.setText("Waiting for admin approval");
                            top_bottom.setVisibility(View.GONE);
                            bottom.setVisibility(View.GONE);
                            break;
                        case "1":
                            top.setVisibility(View.GONE);
                            top_bottom.setVisibility(View.GONE);
                            bottom.setVisibility(View.VISIBLE);
                            break;
                        default:
                            top.setVisibility(View.VISIBLE);
                            status.setText("Waiting for admin approval");
                            top_bottom.setVisibility(View.VISIBLE);
                            bottom.setVisibility(View.GONE);
                            comment.setText(extra_comment);
                            break;
                    }
                }
            }
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                initDialog(3);
            }
        });
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;

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
    private void initDialog(int stats) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button buttonok = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if (stats == 0) {
            status.setText("Sorry");
            detail.setText("You need to login first");
            bg.setBackgroundResource(R.color.red7);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttoncancel.setBackgroundResource(R.drawable.button1_1);
            buttonok.setText("Login");
            buttoncancel.setVisibility(View.VISIBLE);
            buttoncancel.setText("Cancel");
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Login.class);
                    initanim(intent);
                }
            });
            dialog.show();
        } else if (stats == 4) {
            status.setText("Oops!");
            detail.setText("");
            bg.setBackgroundResource(R.color.red7);
//            imageView.setImageResource(R.drawable.emoji_oops);
            buttonok.setBackgroundResource(R.drawable.button1_red);
            buttonok.setText("Try Again");
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else if (stats == 1) {
            status.setText("Registered Success!");
            detail.setText("");
            bg.setBackgroundResource(R.color.green7);
//            imageView.setImageResource(R.drawable.emoji_success);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(context, Login.class));
                    finish();
                }
            });
            dialog.show();
        } else if (stats == 3) {
            status.setText("Uh Oh!");
            detail.setText("There is a problem with internet connection or the server");
            bg.setBackgroundResource(R.color.red7);
//            imageView.setImageResource(R.drawable.emoji_cry);
            buttonok.setBackgroundResource(R.drawable.button1_red);
            buttonok.setText("Try Again");
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent restart = new Intent(context, MainActivity.class);
                    initanim(restart);
                }
            });
            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
        }
    }
}
