package com.example.williamsumitro.dress.view.view.profile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.AuthActivity;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.mystore.activity.MyStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    @BindView(R.id.changepassword_toolbar) Toolbar toolbar;
    @BindView(R.id.changepassword_layoutpassword) TextInputLayout layout_password;
    @BindView(R.id.changepassword_etpassword) TextInputEditText password;
    @BindView(R.id.changepassword_layout_newpassword) TextInputLayout layout_newpassword;
    @BindView(R.id.changepassword_et_newpassword) TextInputEditText newpassword;
    @BindView(R.id.changepassword_layout_confirmnewpassword) TextInputLayout layout_confirmnewpassword;
    @BindView(R.id.changepassword_et_confirmnewpassword) TextInputEditText confirmnewpassword;
    @BindView(R.id.changepassword_btn_savechanges) Button savechanges;

    private Context context;
    private apiService service;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private String token;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        setupToolbar();
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_updatepassword();
            }
        });
    }

    private void api_updatepassword() {
        layout_password.setErrorEnabled(false);
        layout_newpassword.setErrorEnabled(false);
        layout_confirmnewpassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(password.getText())) {
            layout_password.setErrorEnabled(true);
            layout_password.setError("Old password is required");
            return;
        } else if (TextUtils.isEmpty(newpassword.getText())) {
            layout_newpassword.setErrorEnabled(true);
            layout_newpassword.setError("Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(newpassword.getText().toString())) {
            layout_newpassword.setErrorEnabled(true);
            layout_newpassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (TextUtils.isEmpty(confirmnewpassword.getText())) {
            layout_confirmnewpassword.setErrorEnabled(true);
            layout_confirmnewpassword.setError("Re-Password is required");
            return;
        } else if (!AuthActivity.ispasswordvalid(confirmnewpassword.getText().toString())) {
            layout_confirmnewpassword.setErrorEnabled(true);
            layout_confirmnewpassword.setError("Password is not valid. Password minimum 8 characters");
            return;
        } else if (!newpassword.getText().toString().equals(confirmnewpassword.getText().toString())) {
            layout_confirmnewpassword.setErrorEnabled(true);
            layout_confirmnewpassword.setError("Password not match");
            return;
        }
        service.req_update_user_password(token, password.getText().toString(), newpassword.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        String message = jsonResults.getString("message");
                        if(jsonResults.getBoolean("status")){
                            initDialog(message, 1);
                        }else{
                            initDialog(message, 0);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
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
        getSupportActionBar().setTitle("Edit Password");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            Profile.PROFILE.finish();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initDialog(String message, int stats){
        if(stats == 1) {
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Updated Success!")
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
