package com.example.williamsumitro.dress.view.view.profile.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.UserDetails;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.mystore.activity.EditStore;
import com.example.williamsumitro.dress.view.view.mystore.activity.SupportingDocument;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    @BindView(R.id.profile_circleimageview) CircleImageView user_image;
    @BindView(R.id.profile_tv_name) TextView user_name;
    @BindView(R.id.profile_img_gender) ImageView gender;
    @BindView(R.id.profile_tv_phonenumber) TextView phonenumber;
    @BindView(R.id.profile_tv_email) TextView email;
    @BindView(R.id.profile_toolbar) Toolbar toolbar;
    @BindView(R.id.profile_boom) BoomMenuButton bmb;
    private Context context;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private apiService service;
    private String storeid, token;
    private UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        setupToolbar();
        api_getuser();
        initBoom();
    }

    private void api_getuser() {
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    userDetails = response.body().getUserDetails();
                    initData();
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void initData() {
        Picasso.with(context)
                .load(userDetails.getAvatar())
                .placeholder(R.drawable.logo404)
                .into(user_image);
        user_name.setText(userDetails.getFullName());
        phonenumber.setText(userDetails.getPhoneNumber());
        email.setText(userDetails.getEmail());
        if (userDetails.getGender().toLowerCase().equals("m")){
            gender.setImageResource(R.drawable.male);
        }
        else{
            gender.setImageResource(R.drawable.female);
        }
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initBoom(){
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            if (i == 0){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalText("Edit Profile")
                        .subNormalText("Edit Profile Information")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.green9)
                        .normalColorRes(R.color.green5)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, EditProfile.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
            else if (i == 1){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalText("Change Image")
                        .subNormalText("Edit Profile Image")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.brown9)
                        .normalColorRes(R.color.brown6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, ChangeProfilePicture.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
            else if (i == 2){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalText("Change password")
                        .subNormalText("Edit Password with a new one")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.orange9)
                        .normalColorRes(R.color.orange6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, ChangePassword.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
        }
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
