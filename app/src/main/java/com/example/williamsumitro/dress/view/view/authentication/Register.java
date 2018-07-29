package com.example.williamsumitro.dress.view.view.authentication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
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

public class Register extends AppCompatActivity {
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
    @BindView(R.id.register_btnskip) Button skip;
    private RadioButton sexbutton;
    private Context context;
    private apiService service;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
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
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                initanim(intent);
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context= this;
        progressDialog = new ProgressDialog(this);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
            layoutpassword.setError("Password must minimum 8 characters");
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
        service.req_register(email.getText().toString(), password.getText().toString(), name.getText().toString(), sex, phonenumber.getText().toString()).
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
                            Intent intent = new Intent(context, Login.class);
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
}
