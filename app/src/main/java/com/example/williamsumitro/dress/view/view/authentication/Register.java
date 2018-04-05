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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.register_btnRegister)Button register;
    @BindView(R.id.register_lnLogin) LinearLayout login;
    @BindView(R.id.register_radiogroup) RadioGroup radioGroup;
    @BindView(R.id.register_container) RelativeLayout container;
    private RadioButton sexbutton;
    private Context context;
    private ProgressDialog progressDialog;
    private apiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setuptoolbar();
        initOnClick();
    }
    private void initOnClick() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataToAPI();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Login.class);
                initanim(intent);
            }
        });

    }
    private void initView(){
        ButterKnife.bind(this);
        context= this;
        progressDialog = new ProgressDialog(this);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Register");
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
    private void postDataToAPI(){
        layoutname.setErrorEnabled(false);
        layoutemail.setErrorEnabled(false);
        layoutpassword.setErrorEnabled(false);
        layoutphonenumber.setErrorEnabled(false);

        if (TextUtils.isEmpty(name.getText())) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name is required");
            return;
        } else if (name.getText().length() < 3) {
            layoutname.setErrorEnabled(true);
            layoutname.setError("Name minimal 3");
            return;
        } else if (TextUtils.isEmpty(email.getText())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is required");
            return;
        } else if (!AuthActivity.isemailvalid(email.getText().toString())) {
            layoutemail.setErrorEnabled(true);
            layoutemail.setError("Email is not valid");
            return;
        } else if (TextUtils.isEmpty(password.getText())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(password.getText().toString())) {
            layoutpassword.setErrorEnabled(true);
            layoutpassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (TextUtils.isEmpty(phonenumber.getText())) {
            layoutphonenumber.setErrorEnabled(true);
            layoutphonenumber.setError("Phone number is required");
            return;
        } else if(radioGroup.getCheckedRadioButtonId() == -1){
            Snackbar.make(container, "Please choose your sex", Snackbar.LENGTH_LONG).show();
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
        progressDialog.setMessage("Wait a sec ...");
        progressDialog.show();


        service = apiUtils.getAPIService();
        service.req_register(email.getText().toString(), password.getText().toString(), name.getText().toString(), sex, phonenumber.getText().toString()).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").equals("User created successfully")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(context, Login.class));
                                    finish();
                                }else{
                                    String message = jsonResults.getString("message");
                                    Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(context, "There is a problem with internet connection or the server", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });


    }
}
