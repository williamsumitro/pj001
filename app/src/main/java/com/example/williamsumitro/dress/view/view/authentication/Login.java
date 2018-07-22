package com.example.williamsumitro.dress.view.view.authentication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

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

public class Login extends AppCompatActivity{
    @BindView(R.id.login_btnLogin) Button login;
    @BindView(R.id.login_etemail) TextInputEditText email;
    @BindView(R.id.login_lnRegister) LinearLayout register;
    @BindView(R.id.login_layoutpassword) TextInputLayout layoutpassword;
    @BindView(R.id.login_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.login_etpassword) TextInputEditText password;
    @BindView(R.id.login_container) RelativeLayout container;
    @BindView(R.id.login_btnskip) Button skip;
    private Context context;
    private apiService service;
    private String token;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;
    private SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initOnClick();
    }

    private void initOnClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToAPI(view);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Register.class);
                initanim(intent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                initanim(intent);
            }
        });
    }
    private void postDataToAPI(View view){
        layoutemail.setErrorEnabled(false);
        layoutpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        }else if (!AuthActivity.isemailvalid(email.getText().toString())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is not valid");
            return;
        }else if (TextUtils.isEmpty(password.getText())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is required");
            return;
        }
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        api_login(view);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        progressDialog = new ProgressDialog(this);
        sessionManagement = new SessionManagement(getApplicationContext());
    }
    private void api_login(final View view){
        service = apiUtils.getAPIService();
        service.req_login(email.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getBoolean("status")){
                                    token = jsonResults.getString("jwt");
                                    progressDialog.dismiss();
                                    initDialog(token, 1);
                                }else if (!jsonResults.getBoolean("status")){
                                    String message = jsonResults.getString("message");
                                    progressDialog.dismiss();
                                    initDialog(message, 0);
                                }else {
                                    Snackbar.make(container, "WHAT ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        progressDialog.dismiss();
                        initDialog(t.getMessage(), 3);
                    }
                });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    private void initDialog(final String message, int stats){
        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Welcome back!")
                    .setContentText("Hello there, check out our new dresses")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sessionManagement.createLoginSession(message);
                            MainActivity.mainactivity.finish();
                            finish();
                            Intent intent = new Intent(context, MainActivity.class);
                            initanim(intent);
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
}
