package com.example.williamsumitro.dress.view.view.authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity{
    @BindView(R.id.login_btnLogin) Button login;
    @BindView(R.id.login_etemail) TextInputEditText email;
    @BindView(R.id.login_lnRegister) LinearLayout register;
    @BindView(R.id.login_toolbar) Toolbar toolbar;
    @BindView(R.id.login_layoutpassword) TextInputLayout layoutpassword;
    @BindView(R.id.login_layoutemail) TextInputLayout layoutemail;
    @BindView(R.id.login_etpassword) TextInputEditText password;
    @BindView(R.id.login_container) RelativeLayout container;
    private Context context;
    private apiService service;
    private ProgressDialog progressDialog;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setuptoolbar();
        initOnClick();
    }

    private void initOnClick() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToAPI();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Register.class);
                initanim(intent);
            }
        });
    }
    private void postDataToAPI(){
        layoutemail.setErrorEnabled(false);
        layoutpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        }else if (TextUtils.isEmpty(password.getText())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is required");
            return;
        }
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        api_login();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        progressDialog = new ProgressDialog(this);
    }
    private void api_login(){
        service = apiUtils.getAPIService();
        service.req_login(email.getText().toString(), password.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code() == 200){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("status").toLowerCase().equals("true")){
                                    token = jsonResults.getString("jwt");
                                    context.startActivity(new Intent(context, MainActivity.class));
                                    finish();
                                }else if (jsonResults.getString("status").toLowerCase().equals("false")){
                                    String message = jsonResults.getString("message");
                                    Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
                                }else {
                                    Snackbar.make(container, "FUCKING ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            Snackbar.make(container, "Invalid Email or Password", Snackbar.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Login");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
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
