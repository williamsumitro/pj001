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
import android.util.Log;
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
import com.example.williamsumitro.dress.view.view.openstore.activity.OpenStore;
import com.example.williamsumitro.dress.view.view.sellerpanel.OnNavigationBarListener;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;
import com.example.williamsumitro.dress.view.view.openstore.adapter.OpenStore_CourierAdapter;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Openstore_storeinformationFragment extends Fragment implements BlockingStep{
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
    @BindView(R.id.openstore_storeinformation_container) ScrollView container;
    @BindView(R.id.openstore_storeinformation_spinner_businesstype) Spinner spinner_businesstype;
    @BindView(R.id.openstore_storeinformation_rv_courier) RecyclerView rv_courier;
    @BindView(R.id.openstore_storeinformation_ln_year) LinearLayout container_year;
    @BindView(R.id.openstore_storeinformation_tv_year) TextView tv_year;

    private apiService service;
    private SweetAlertDialog sweetAlertDialog;
    private Context context;
    private ProgressDialog progressDialog;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private SessionManagement sessionManagement;
    private String idprovince, idcity, choosen_province, choosen_city, chosen_businesstype, year="";
    private List<String> bisnis = new ArrayList<>();
    private List<CourierDetails> courierDetailsList;
    private OpenStore_CourierAdapter adapter;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";



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
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, bisnis);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
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
        container_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeFragment = (SwitchDateTimeDialogFragment) getFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
                if(dateTimeFragment == null) {
                    dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                            getString(R.string.label_datetime_dialog),
                            getString(android.R.string.ok),
                            getString(android.R.string.cancel)
                    );
                }
                dateTimeFragment.setTimeZone(TimeZone.getDefault());

                final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy", java.util.Locale.getDefault());
                dateTimeFragment.setMinimumDateTime(new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime());
                dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2018, Calendar.DECEMBER, 31).getTime());

                try {
                    dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
                } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                    Log.e("TAG", e.getMessage());
                }

                dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
                    @Override
                    public void onPositiveButtonClick(Date date) {
                        year = myDateFormat.format(date);
                        tv_year.setText("Year Established : " + year);
                    }

                    @Override
                    public void onNegativeButtonClick(Date date) {
                    }

                    @Override
                    public void onNeutralButtonClick(Date date) {
                    }
                });
                dateTimeFragment.startAtYearView();
                if (year.equals("")){
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2000, Calendar.MARCH, 4, 15, 20).getTime());
                }else {
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(Integer.parseInt(year), Calendar.MARCH, 4, 15, 20).getTime());
                }
                dateTimeFragment.show(getFragmentManager(), TAG_DATETIME_FRAGMENT);
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
                    spinCityAdapter = new SpinCityAdapter(context, R.layout.item_spinner, cityDetailsList);
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
                    spinProvinceAdapter = new SpinProvinceAdapter(context, R.layout.item_spinner, provinceDetailsList);
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
        return verificationError;
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }
    private void updateNavigationBar() {

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
        if (stats == 3){
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

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        layout_name.setErrorEnabled(false);
        layout_jobtitle.setErrorEnabled(false);
        layout_phonenumber.setErrorEnabled(false);
        layout_detail.setErrorEnabled(false);
        if (TextUtils.isEmpty(name.getText().toString())){
            layout_name.setErrorEnabled(true);
            layout_name.setError("Name of contact person is required");
            Snackbar.make(container,"Name of contact person is required", Snackbar.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(jobtitle.getText().toString())){
            layout_jobtitle.setErrorEnabled(true);
            layout_jobtitle.setError("Job title is required");
            Snackbar.make(container,"Job title is required", Snackbar.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(phonenumber.getText().toString())){
            layout_phonenumber.setErrorEnabled(true);
            layout_phonenumber.setError("Phone number is required");
            Snackbar.make(container, "Phone number is required", Snackbar.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(year)){
            Snackbar.make(container, "Please choose your year established", Snackbar.LENGTH_SHORT).show();
            return;
        }if (TextUtils.isEmpty(detail.getText().toString())){
            layout_detail.setErrorEnabled(true);
            layout_detail.setError("Description is required");
            Snackbar.make(container,"Description is required", Snackbar.LENGTH_SHORT).show();
            return;
        }if (!ischecked()){
            Snackbar.make(container, "Please pick any courier", Snackbar.LENGTH_SHORT).show();
            return;
        }
        else {
            sessionManagement.keepStoreInformation(year, idprovince, idcity, name.getText().toString(),
                    jobtitle.getText().toString(), phonenumber.getText().toString(), detail.getText().toString(), choosen_province, choosen_city, chosen_businesstype);
            callback.complete();
        }
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

    }

}
