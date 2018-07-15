package com.example.williamsumitro.dress.view.view.openstore.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CityDetails;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierDetails;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;
import com.example.williamsumitro.dress.view.view.openstore.adapter.OpenStore_CourierAdapter;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_storeinformationFragment extends Fragment implements Step{
    @BindView(R.id.openstore_storeinformation_spinner_province) Spinner province;
    @BindView(R.id.openstore_storeinformation_spinner_city) Spinner city;
    @BindView(R.id.openstore_storeinformation_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.openstore_storeinformation_name) TextInputEditText name;
    @BindView(R.id.openstore_storeinformation_layout_phonenumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.openstore_storeinformation_layout_name) TextInputLayout layout_name;
    @BindView(R.id.openstore_storeinformation_layout_jobtitle) TextInputLayout layout_jobtitle;
    @BindView(R.id.openstore_storeinformation_layout_detail) TextInputLayout layout_detail;
    @BindView(R.id.openstore_storeinformation_jobtitle) TextInputEditText jobtitle;
    @BindView(R.id.openstore_storeinformation_detail) TextInputEditText detail;
    @BindView(R.id.openstore_storeinformation_btn_check) Button check;
    @BindView(R.id.openstore_storeinformation_tvStatus) TextView status;
    @BindView(R.id.openstore_storeinformation_container) ScrollView container;
    @BindView(R.id.openstore_storeinformation_spinner_businesstype) Spinner spinner_businesstype;
    @BindView(R.id.openstore_storeinformation_layout_yearestbalished) TextInputLayout layout_since;
    @BindView(R.id.openstore_storeinformation_yearestbalished) TextInputEditText since;
    @BindView(R.id.openstore_storeinformation_rv_courier) RecyclerView rv_courier;
    private apiService service;
    private Dialog dialog;
    private Context context;
    private ProgressDialog progressDialog;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private SessionManagement sessionManagement;
    private String idprovince, idcity, choosen_province, choosen_city, chosen_businesstype;
    private Boolean checked = false;
    private List<String> bisnis = new ArrayList<>();
    private List<CourierDetails> courierDetailsList;
    private OpenStore_CourierAdapter adapter;

    @Nullable
    private OnNavigationBarListener onNavigationBarListener;
    public Openstore_storeinformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_storeinformation, container, false);
        initView(view);
        api_getcourier();
        initOnClick();
        initProvinceSpinner();
        initBisnisSpinner();
        return view;
    }

    private void initBisnisSpinner() {
        bisnis.add("Manufacture / Factory");
        bisnis.add("Trading Company");
        bisnis.add("Distributor / Wholesaler");
        bisnis.add("Retailer");
        bisnis.add("Buying Office");
        bisnis.add("Online Shop / Store");
        bisnis.add("Individual");
        bisnis.add("Other");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, bisnis);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_businesstype.setAdapter(dataAdapter);
        spinner_businesstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosen_businesstype = dataAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initOnClick(){
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        jobtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        since.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                layout_detail.setErrorEnabled(false);
                layout_jobtitle.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_phonenumber.setErrorEnabled(false);
                layout_since.setErrorEnabled(false);
                if (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Name of contact person is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(jobtitle.getText().toString())){
                    layout_jobtitle.setErrorEnabled(true);
                    layout_jobtitle.setError("Job title is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(phonenumber.getText().toString())){
                    layout_phonenumber.setErrorEnabled(true);
                    layout_phonenumber.setError("Phone number is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(since.getText().toString())){
                    layout_since.setErrorEnabled(true);
                    layout_since.setError("Year Established is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(detail.getText().toString())){
                    layout_detail.setErrorEnabled(true);
                    layout_detail.setError("Description is required");
                    checked = false;
                    return;
                }if (!ischecked()){
                    Snackbar.make(container, "Please pick any courier", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                    return;
                }
                if (checked) {
                    status.setText("Successful");
                    Toast.makeText(context, "Please click the complete button to continue", Toast.LENGTH_LONG).show();
                    status.setTextColor(getResources().getColor(R.color.green));
                }
                else {
                    status.setText("Please check the information again, something is error");
                    status.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }
    public void api_getcourier(){
        service = apiUtils.getAPIService();
        service.req_get_courier_list().enqueue(new Callback<CourierResponse>() {
            @Override
            public void onResponse(Call<CourierResponse> call, Response<CourierResponse> response) {
                if(response.code()==200){
                    courierDetailsList = response.body().getCourier();
                    adapter = new OpenStore_CourierAdapter(courierDetailsList, context);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    rv_courier.setLayoutManager(layoutManager);
                    rv_courier.setItemAnimator(new DefaultItemAnimator());
                    rv_courier.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<CourierResponse> call, Throwable t) {

            }
        });
    }
    private void initCitySpinner(){
        service = apiUtils.getAPIService();
        service.req_get_city(idprovince).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.code() == 200){
                    cityDetailsList = response.body().getCityDetails();
                    spinCityAdapter = new SpinCityAdapter(context, android.R.layout.simple_spinner_item, cityDetailsList);
                    city.setAdapter(spinCityAdapter);
                    city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            CityDetails citydetails = spinCityAdapter.getItem(position);
                            idcity = citydetails.getCityId();
                            choosen_city = citydetails.getCityName();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }
    private void initProvinceSpinner(){
        service = apiUtils.getAPIService();
        service.req_get_province_list().enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if(response.code() == 200){
                    provinceDetailsList = response.body().getProvinceDetails();
                    spinProvinceAdapter = new SpinProvinceAdapter(context, android.R.layout.simple_spinner_item, provinceDetailsList);
                    province.setAdapter(spinProvinceAdapter);
                    province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            ProvinceDetails provinceDetails = spinProvinceAdapter.getItem(position);
                            idprovince = provinceDetails.getProvinceId();
                            choosen_province = provinceDetails.getProvinceName();
                            initCitySpinner();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
                initDialog(3);
            }
        });

    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        progressDialog = new ProgressDialog(context);
    }
    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
        if (isInformationValid()) {
            sessionManagement.keepStoreInformation(since.getText().toString(), idprovince, idcity, name.getText().toString(),
                    jobtitle.getText().toString(), phonenumber.getText().toString(), detail.getText().toString(), choosen_province, choosen_city, chosen_businesstype);
        }
        else {
            verificationError = new VerificationError("Please press the button check");
        }
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if(!checked)
            check.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
    }
    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(isInformationValid());
        }
    }
    private boolean isInformationValid(){
        return checked;
    }
    private boolean ischecked(){
        StringBuilder stringBuilder = new StringBuilder();
        for (CourierDetails courierDetails : courierDetailsList) {
            if (courierDetails.getSelected()) {
                if (stringBuilder.length() > 0)
                    stringBuilder.append(",");
                stringBuilder.append(courierDetails.getCourierId());
            }
        }
        if (stringBuilder.length()>0){
            String baru = stringBuilder.toString();
            sessionManagement.keepStoreCourier(baru);
            return true;
        }
        else return false;
    }
    private void initDialog(int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button ok = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button cancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);

        if (stats == 3){
            status.setText("Uh Oh!");
            bg.setBackgroundResource(R.color.red7);
            detail.setText("There is a problem with internet connection or the server");
//            imageView.setImageResource(R.drawable.emoji_cry);
            ok.setBackgroundResource(R.drawable.button1_red);
            ok.setText("Try Again");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
