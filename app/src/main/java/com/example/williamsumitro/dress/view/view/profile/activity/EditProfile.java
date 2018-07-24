package com.example.williamsumitro.dress.view.view.profile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.authentication.AuthActivity;
import com.example.williamsumitro.dress.view.view.authentication.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    @BindView(R.id.editprofile_toolbar) Toolbar toolbar;
    @BindView(R.id.editprofile_rbmale) RadioButton male;
    @BindView(R.id.editprofile_rbfemale) RadioButton female;
    @BindView(R.id.editprofile_radiogroup) RadioGroup radioGroup;
    @BindView(R.id.editprofile_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.editprofile_layoutfullname) TextInputLayout layoutfullname;
    @BindView(R.id.editprofile_etphonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editprofile_etfullname) TextInputEditText fullname;
    @BindView(R.id.editprofile_emailaddress) TextView email;
    @BindView(R.id.editprofile_btn_save)Button save;
    @BindView(R.id.editprofile_container) LinearLayout container;

    private RadioButton sexbutton;
    private Context context;
    private apiService service;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        setupToolbar();
    }
    private void initView(){
        ButterKnife.bind(this);
        context= this;
        progressDialog = new ProgressDialog(this);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    private void postDataToAPI(){
        layoutfullname.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);

        if (TextUtils.isEmpty(fullname.getText())) {
            layoutfullname.setErrorEnabled(true);
            layoutfullname.setError("Name is required");
            return;
        } else if (fullname.getText().length() < 3) {
            layoutfullname.setErrorEnabled(true);
            layoutfullname.setError("Name minimal 3");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        } else if(radioGroup.getCheckedRadioButtonId() == -1){
            Snackbar.make(container, "Please choose your gender", Snackbar.LENGTH_LONG).show();
            return;
        }
        int selectedID = radioGroup.getCheckedRadioButtonId();
        sexbutton = (RadioButton) findViewById(selectedID);
        String sex = "";
        if (sexbutton.getText().toString().toLowerCase().equals("female")){
            sex = "F";
        }else if(sexbutton.getText().toString().toLowerCase().equals("male")){
            sex = "M";
        }

        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        service = apiUtils.getAPIService();
        service.req_update_user_profile(fullname.getText().toString(), sex, phonenumber.getText().toString()).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getBoolean("status")){
                                    String message = jsonResults.getString("message");
                                    progressDialog.dismiss();
                                    initDialog(message, 1);
                                }else{
                                    String message = jsonResults.getString("message");
                                    progressDialog.dismiss();
                                    initDialog(message, 0);
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            progressDialog.dismiss();
                            initDialog(response.message(), 0);
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        progressDialog.dismiss();
                        initDialog(t.getMessage(), 3);
                    }
                });
    }
    private void initDialog(String message, int stats){
        if(stats == 1) {
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Registered Success!")
                    .setContentText(message)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(context, Profile.class);
                            initanim(intent);
                            finish();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
