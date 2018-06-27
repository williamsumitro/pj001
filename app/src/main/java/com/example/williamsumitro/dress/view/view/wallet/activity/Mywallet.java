package com.example.williamsumitro.dress.view.view.wallet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.TransactionDetails;
import com.example.williamsumitro.dress.view.model.UserDetails;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.authentication.Unauthorized;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.purchase.adapter.SpinBankAdapter;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PurchasePayment;
import com.example.williamsumitro.dress.view.view.wallet.adapter.Wallet_Transaction_RV_Adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mywallet extends AppCompatActivity {
    @BindView(R.id.mywallet_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.mywallet_collapstoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.mywallet_toolbar) Toolbar toolbar;
    @BindView(R.id.mywallet_spinner) Spinner spinner;
    @BindView(R.id.mywallet_rv) RecyclerView recyclerView;
    @BindView(R.id.mywallet_btn_showhistory) Button showhistory;
    @BindView(R.id.mywallet_btn_withdraw) Button withdraw;
    @BindView(R.id.mywallet_tv_balance) TextView balance;

    private Context context;
    private ArrayAdapter<CharSequence> monthadapter;
    private String choosen_spinner;
    private Wallet_Transaction_RV_Adapter adapter;
    private ProgressDialog progressDialog;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private List<TransactionDetails> transactionDetailsList = new ArrayList<>();
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);
        initObject();
        setupToolbar();
        initCollapToolbar();
        initspinner();
        setuprv();
        api_getauthuser();
    }

    private void initOnClick(final String balances) {
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(balances);
            }
        });
    }

    private void api_getauthuser(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        service = apiUtils.getAPIService();
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, final Response<UserResponse> response) {
                if(response.code()==200){
                    progressDialog.dismiss();
                    balance.setText(formatter.format(Double.parseDouble(String.valueOf(response.body().getUserDetails().getBalance()))));
                    initOnClick(String.valueOf(response.body().getUserDetails().getBalance()));
                }
                else if (response.code()==500){
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, Unauthorized.class);
                    initanim(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                initDialog(3);
            }
        });
    }
    private void setuprv() {
        TransactionDetails transactionDetails = new TransactionDetails("20 May 2018", "Jual Beli Barang", "Jual beli barang di tokopedia, laaada, tah apa", 200000, 200000, 2000000);
        transactionDetailsList.add(transactionDetails);
        adapter = new Wallet_Transaction_RV_Adapter(context, transactionDetailsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle("My Wallet");
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void initspinner(){
        monthadapter = ArrayAdapter.createFromResource(context, R.array.month, android.R.layout.simple_spinner_item);
        monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosen_spinner = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }

    private void initDialog(final String grand_total){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.withdraw_dialog);
        TextView tv_balance = (TextView) dialog.findViewById(R.id.withdrawdialog_tv_balance);
        final TextInputLayout layout_amount = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_amount);
        final TextInputLayout layout_bankname = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_bankname);
        final TextInputLayout layout_bankaccount = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_banknumber);
        final TextInputLayout layout_name = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_nameholder);
        final TextInputLayout layout_bankbranch = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_bankbranch);
        final TextInputEditText amount = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_amount);
        final TextInputEditText bankname = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_bankname);
        final TextInputEditText bankaccount = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_banknumber);
        final TextInputEditText name = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_nameholder);
        final TextInputEditText bankbranch = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_bankbranch);
        final TextInputLayout layout_password = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_password);
        final TextInputEditText password = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_password);
        final TextInputLayout layout_retypepassword = (TextInputLayout) dialog.findViewById(R.id.withdrawdialog_layout_retypepassword);
        final TextInputEditText retypepassword = (TextInputEditText) dialog.findViewById(R.id.withdrawdialog_retypepassword);
        final Button buttonok = (Button) dialog.findViewById(R.id.withdrawdialog_btnok);
        final Button buttoncancel = (Button) dialog.findViewById(R.id.withdrawdialog_btncancel);

        amount.addTextChangedListener(new FinancialTextWatcher(amount));

        tv_balance.setText("Balance : IDR " + formatter.format(Double.parseDouble(grand_total)));

        buttonok.setText("Submit");
        buttoncancel.setVisibility(View.VISIBLE);
        buttoncancel.setText("Cancel");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttoncancel.setBackgroundResource(R.drawable.button1_1);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_amount.setErrorEnabled(false);
                layout_bankname.setErrorEnabled(false);
                layout_bankbranch.setErrorEnabled(false);
                layout_bankaccount.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_password.setErrorEnabled(false);
                layout_retypepassword.setErrorEnabled(false);

                if (TextUtils.isEmpty(amount.getText().toString())){
                    layout_amount.setErrorEnabled(true);
                    layout_amount.setError("Amount is required");
                    return;
                }else if (TextUtils.isEmpty(bankname.getText().toString())){
                    layout_bankname.setErrorEnabled(true);
                    layout_bankname.setError("Bank Name is required");
                    return;
                }else if(TextUtils.isEmpty(bankbranch.getText().toString())){
                    layout_bankbranch.setErrorEnabled(true);
                    layout_bankbranch.setError("Bank Branch is required");
                    return;
                }else if(TextUtils.isEmpty(bankaccount.getText().toString())){
                    layout_bankaccount.setErrorEnabled(true);
                    layout_bankaccount.setError("Account Number is required");
                    return;
                }else if  (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Holder Name is required");
                    return;
                }else if  (TextUtils.isEmpty(password.getText().toString())){
                    layout_password.setErrorEnabled(true);
                    layout_password.setError("Password is required");
                    return;
                }else if  (TextUtils.isEmpty(retypepassword.getText().toString())){
                    layout_retypepassword.setErrorEnabled(true);
                    layout_retypepassword.setError("Retype Password is required");
                    return;
                }else if (!password.getText().toString().equals(retypepassword.getText().toString())) {
                    layout_retypepassword.setErrorEnabled(true);
                    layout_retypepassword.setError("Password not match");
                    return;
                }

                service = apiUtils.getAPIService();
                String amounts = FinancialTextWatcher.trimCommaOfString(amount.getText().toString());
                service.req_withdraw(token, amounts, bankname.getText().toString(), bankbranch.getText().toString(), bankaccount.getText().toString(), name.getText().toString(), password.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getBoolean("status")){
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            dialog.dismiss();
                                        }
                                        else {
                                            String message = jsonObject.getString("message");
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
            }
        });
        dialog.show();
    }
    private void initDialog(int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
        Button buttonok = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if (stats == 1){
            status.setText("Uh Oh!");
            detail.setText("You need to login first !");
            bg.setBackgroundResource(R.color.red7);
            buttonok.setBackgroundResource(R.drawable.button1_green);
            buttoncancel.setBackgroundResource(R.drawable.button1_1);
            buttonok.setText("Login");
            buttoncancel.setText("Cancel");
            buttoncancel.setVisibility(View.VISIBLE);
            buttonok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, Login.class);
                    initanim(intent);
                }
            });
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if(!((Activity) context).isFinishing())
            {
                dialog.show();
            }
        }
        if (stats == 3){
            status.setText("Uh Oh!");
            detail.setText("There is a problem with internet connection or the server");
            bg.setBackgroundResource(R.color.red7);
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
            if(!((Activity) context).isFinishing())
            {
                dialog.show();
            }
        }
    }
}
