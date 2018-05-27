package com.example.williamsumitro.dress.view.view.sellerpanel.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.adapter.SpinProvinceAdapter;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    @BindView(R.id.openstore_storeinformation_tvsince) TextView since;
    @BindView(R.id.openstore_storeinformation_spinner_province) Spinner province;
    @BindView(R.id.openstore_storeinformation_spinner_city) Spinner city;
    @BindView(R.id.openstore_storeinformation_since) LinearLayout lnsince;
    @BindView(R.id.openstore_storeinformation_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.openstore_storeinformation_name) TextInputEditText name;
    @BindView(R.id.openstore_storeinformation_layout_phonenumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.openstore_storeinformation_layout_name) TextInputLayout layout_name;
    @BindView(R.id.openstore_storeinformation_layout_jobtitle) TextInputLayout layout_jobtitle;
    @BindView(R.id.openstore_storeinformation_layout_detail) TextInputLayout layout_detail;
    @BindView(R.id.openstore_storeinformation_jobtitle) TextInputEditText jobtitle;
    @BindView(R.id.openstore_storeinformation_detail) TextInputEditText detail;

    private apiService service;
    private Context context;
    private List<ProvinceDetails> provinceDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SessionManagement sessionManagement;
    private String idprovince, idcity, choosen_province, choosen_city;
    public Openstore_storeinformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_openstore_storeinformation, container, false);
        initView(view);
        initOnClick();
        initProvinceSpinner();
        return view;
    }
    private void initOnClick(){
        lnsince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDate();
            }
        });
    }
    private void initDate(){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar cal = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                since.setText(dateFormat.format(calendar.getTime()));
            }
        }, yy, mm, dd);
        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();
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
    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(context);
    }
    @Nullable
    @Override
    public VerificationError verifyStep() {
        VerificationError verificationError = null;
        sessionManagement.keepStoreInformation(since.getText().toString(), idprovince, "0", name.getText().toString(),
                jobtitle.getText().toString(), phonenumber.getText().toString(), detail.getText().toString(), choosen_province, "medan");
        return verificationError;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }
}
