package com.example.williamsumitro.dress.view.view.sellerpanel.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_choosestorenameFragment extends Fragment implements Step {
    @BindView(R.id.openstore_choosestorename_btnCheckStore) Button button_checkstore;
    @BindView(R.id.openstore_choosestorename_etStorename) EditText edittext_storename;
    @BindView(R.id.openstore_choosestorename_tvStatus) TextView tv_status;
    private apiService service;
    private SessionManagement sessionManagement;
    private boolean check = false, success = false;
    private String nama_toko, token = "";
    private Dialog dialog;
    private Context context;
    private ProgressDialog progressDialog;
    private boolean trigger = false;
    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    public Openstore_choosestorenameFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_choosestorename, container, false);
        initView(view);
        initSession();
        button_checkstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api_checkstore();
            }
        });
        edittext_storename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                trigger = true;
                check = false;
                tv_status.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        progressDialog = new ProgressDialog(context);
    }
    private void initSession(){
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    private boolean api_checkstore(){
        nama_toko = edittext_storename.getText().toString();
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        if (nama_toko.equals("")){
            edittext_storename.setError("Please fill the store name");
            check = false;
            progressDialog.dismiss();
        }
        else {
            service = apiUtils.getAPIService();
            service.req_check_store(nama_toko)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code() == 200){
                                try {
                                    JSONObject jsonResults = new JSONObject(response.body().string());
                                    if(jsonResults.getString("status").toLowerCase().equals("true")){
                                        String message = jsonResults.getString("message");
                                        check = true;
                                        tv_status.setText(message);
                                        tv_status.setTextColor(getResources().getColor(R.color.green));
                                        progressDialog.dismiss();
                                    }
                                    else {
                                        String message = jsonResults.getString("message");
                                        check = false;
                                        tv_status.setText(message);
                                        tv_status.setTextColor(getResources().getColor(R.color.red));
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                progressDialog.dismiss();
                                check = false;
                                initDialog("", 3);
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            progressDialog.dismiss();
                            initDialog(t.getMessage(), 3);
                            check = false;
                        }
                    });
        }
        return check;
    }

    private boolean isStoreValid(){
        if (trigger)
            if (check)
                if (api_registerstorename())
                    return true;
                else
                    return false;
            else
                return false;
        else
            return false;
    }
    public boolean api_registerstorename(){
        nama_toko = edittext_storename.getText().toString();
        progressDialog.setMessage("Wait a sec..");
        progressDialog.show();
        service = apiUtils.getAPIService();
        service.req_register_store_name(token, nama_toko)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200){
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                String status = jsonResults.getString("status").toLowerCase();
                                if(status.equals("true")){
                                    String message = jsonResults.getString("message");
                                    initDialog(message,1);
                                    progressDialog.dismiss();
                                    success = true;
                                }
                                else if(status.equals("false")) {
                                    String message = jsonResults.getString("message");
                                    initDialog(message,0);
                                    progressDialog.dismiss();
                                    success = false;
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
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        progressDialog.dismiss();
                        initDialog(t.getMessage(), 3);
                        success = false;
                    }
                });
        return false;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNavigationBarListener) {
            onNavigationBarListener = (OnNavigationBarListener) context;
        }
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
//        if(!isStoreValid()){
//           verificationError = new VerificationError("Please fill your store name or press check store");
//        }
        sessionManagement.keepStoreName(edittext_storename.getText().toString());
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        button_checkstore.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
    }
    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(isStoreValid());
        }
    }
    private void initDialog(String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        if(stats == 1){
            status.setText("Registered Success!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.green7);
            imageView.setImageResource(R.drawable.emoji_success);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    startActivity(new Intent(context, Login.class));
                    getActivity().finish();
                }
            });
            dialog.show();
        }
        else if(stats == 0){
            status.setText("Oops!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.red7);
            imageView.setImageResource(R.drawable.emoji_oops);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 3){
            status.setText("Uh Oh!");
            bg.setBackgroundResource(R.color.red7);
            detail.setText("There is a problem with internet connection or the server");
            imageView.setImageResource(R.drawable.emoji_cry);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

}
