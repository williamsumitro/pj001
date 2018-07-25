package com.example.williamsumitro.dress.view.view.checkout.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CheckoutInfo;
import com.example.williamsumitro.dress.view.model.CityDetails;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.activity.Checkout;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_AddressFragment extends Fragment implements Step {
    @BindView(R.id.checkout_address_spinner_province) Spinner spinner_province;
    @BindView(R.id.checkout_address_spinner_city) Spinner spinner_city;
    @BindView(R.id.checkout_address_postalcode) TextInputEditText postalcode;
    @BindView(R.id.checkout_address_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.checkout_address_name) TextInputEditText name;
    @BindView(R.id.checkout_address_layout_postalcode) TextInputLayout layout_postalcode;
    @BindView(R.id.checkout_address_layout_phonenumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.checkout_address_layout_name) TextInputLayout layout_name;
    @BindView(R.id.checkout_address_layout_address) TextInputLayout layout_address;
    @BindView(R.id.checkout_address_container) ScrollView container;
    @BindView(R.id.checkout_address_btn_check) Button check;
    @BindView(R.id.checkout_address_address) TextInputEditText address;

    private apiService service;
    private Context context;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private SessionManagement sessionManagement;
    private String idprovince, idcity, choosen_province, choosen_city;
    private Boolean checked = false;
    private ProgressDialog progressDialog;
    private String token;
    private ArrayList<CheckoutInfo> checkoutInfoArrayList;
    private String total_qty;
    private String total_price;
    private String available_points;
    @Nullable
    private OnNavigationBarListener onNavigationBarListener;
    public Checkout_AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout__address, container, false);
        initView(view);
        initOnClick();
        initProvinceSpinner();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
        progressDialog = new ProgressDialog(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    private void initCitySpinner(){
        service = apiUtils.getAPIService();
        service.req_get_city(idprovince).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.code() == 200){
                    cityDetailsList = response.body().getCityDetails();
                    spinCityAdapter = new SpinCityAdapter(context, android.R.layout.simple_spinner_item, cityDetailsList);
                    spinner_city.setAdapter(spinCityAdapter);
                    spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            checked  = false;
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
                    spinner_province.setAdapter(spinProvinceAdapter);
                    spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            checked  = false;
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
        postalcode.addTextChangedListener(new TextWatcher() {
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
        address.addTextChangedListener(new TextWatcher() {
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
                layout_address.setErrorEnabled(false);
                layout_postalcode.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_phonenumber.setErrorEnabled(false);
                if (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Name of receiver is required");
                    checked = false;
                }if (TextUtils.isEmpty(address.getText().toString())){
                    layout_address.setErrorEnabled(true);
                    layout_address.setError("Address is required");
                    checked = false;
                }if (TextUtils.isEmpty(phonenumber.getText().toString())){
                    layout_phonenumber.setErrorEnabled(true);
                    layout_phonenumber.setError("Phone number is required");
                    checked = false;
                }if (TextUtils.isEmpty(postalcode.getText().toString())){
                    layout_postalcode.setErrorEnabled(true);
                    layout_postalcode.setError("Postal Code is required");
                    checked = false;
                }
                if (checked) {
                    ((Checkout)getActivity()).ChangeColor(1);
                    Toasty.info(context, "Please click the next button to continue", Toast.LENGTH_SHORT, true).show();
                }
                else {
                    Toasty.error(context, "Please check the information again, something is error", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
        if (isInformationValid()) {
//            Toasty.info(context, "Name " + name.getText().toString() + "\n" +
//                    "Address " + address.getText().toString()+ "\n" +
//                    "Phone Number " + phonenumber.getText().toString()+ "\n" +
//                    "Id Province " + idprovince+ "\n" +
//                    "Id City " + idcity+ "\n" +
//                    "Postal Code " + postalcode.getText().toString(), Toast.LENGTH_LONG, true).show();
            sessionManagement.keepCheckoutAddress(name.getText().toString(), address.getText().toString(),
                    phonenumber.getText().toString(), idprovince, idcity, postalcode.getText().toString());
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
}
