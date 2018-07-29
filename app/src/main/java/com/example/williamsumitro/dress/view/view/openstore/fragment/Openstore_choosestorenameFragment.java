package com.example.williamsumitro.dress.view.view.openstore.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.openstore.activity.OpenStore;
import com.example.williamsumitro.dress.view.view.openstore.adapter.OpenStore_Button;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_choosestorenameFragment extends Fragment implements BlockingStep {
//    @BindView(R.id.openstore_choosestorename_btnCheckStore) Button button_checkstore;
    @BindView(R.id.openstore_choosestorename_etStorename) EditText edittext_storename;
//    @BindView(R.id.openstore_choosestorename_tvStatus) TextView tv_status;
//    @BindView(R.id.openstore_choosestorename_btnRegistereStore) Button button_registerstore;
    private apiService service;
    private SessionManagement sessionManagement;
    private boolean check = false, success = false, trigger = false, registered = false;
    private String nama_toko, token = "";
    private SweetAlertDialog sweetAlertDialog;
    private Context context;
    private ProgressDialog progressDialog;
    private OpenStore_Button listener;
    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    public Openstore_choosestorenameFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_choosestorename, container, false);
        initView(view);
        initSession();
        edittext_storename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals(""))
                    trigger =false;
                else {
                    trigger = true;
                    check = false;
                }
            }
        });
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
    }
    private void initSession(){
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }

    private boolean api_checkstore(final StepperLayout.OnNextClickedCallback callback){
        nama_toko = edittext_storename.getText().toString();
        progressDialog.setMessage("Checking store name ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        service.req_check_store(nama_toko).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            check = true;
                            progressDialog.dismiss();
                            dialog_storename(callback);
                        }
                        else {
                            Toasty.error(context, jsonResults.getString("message"), Toast.LENGTH_SHORT, true).show();
                            check = false;
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
                progressDialog.dismiss();
                initDialog(t.getMessage(), 3);
                check = false;
            }
        });
        return check;
    }

    private boolean isStoreValid(){
        if (trigger && check && success) {
            return true;
        }
        else return false;
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
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if (!trigger)
            edittext_storename.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));

    }
    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(isStoreValid());
        }
    }
    private void initDialog(String message, int stats){
        if(stats == 0){
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
    private void dialog_storename(final StepperLayout.OnNextClickedCallback callback){
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Store name Available")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sessionManagement.keepStoreName(edittext_storename.getText().toString());
                        callback.goToNextStep();
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        if (edittext_storename.isEnabled()){
            if (!trigger)
                Toasty.error(context, "Please fill your store name", Toast.LENGTH_SHORT, true).show();
            else {
                api_checkstore(callback);
            }
        }
        else {
            callback.goToNextStep();
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }
}
