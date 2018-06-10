package com.example.williamsumitro.dress.view.view.filter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.presenter.helper.InputFilterMinMax;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Filter extends AppCompatActivity {
    @BindView(R.id.filter_toolbar) Toolbar toolbar;
    @BindView(R.id.filter_crystalseekbar) CrystalSeekbar seekbar;
    @BindView(R.id.filter_tvminordervalue) TextView minordervalue;
    @BindView(R.id.filter_tvmaxordervalue) TextView maxordervalue;
    @BindView(R.id.filter_etminorder) EditText minorder;
    @BindView(R.id.filter_tvminpricerangevalue) TextView minpricerangevalue;
    @BindView(R.id.filter_tvmaxpricerangevalue) TextView maxpricerangevalue;
    @BindView(R.id.filter_rangeseekbar) CrystalRangeSeekbar rangeseekbar;
    @BindView(R.id.filter_etminpricerange) EditText minpricerange;
    @BindView(R.id.filter_etmaxpricerange) EditText maxpricerange;
    @BindView(R.id.filter_tvmaxpricerange) TextView maxprice;
    @BindView(R.id.filter_tvminpricerange) TextView minprice;
    @BindView(R.id.filter_spinner_shipping) Spinner shipping;
    @BindView(R.id.filter_spinner_province) Spinner province;
    @BindView(R.id.filter_spinner_city) Spinner city;
    @BindView(R.id.filter_btnSet) Button set;

    private DecimalFormat formatter;
    private apiService service;
    private Context context;
    private List<ProvinceDetails> provinceDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private String idprovince, idcity, choosen_province, choosen_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initView();
        setuptoolbar();
        initSeekbar();
        initRangeSeekbar();
        initProvinceSpinner();
    }


    private void initSeekbar(){
        minorder.setText("1");
        minorder.setFilters(new InputFilter[]{new InputFilterMinMax("1","50")});
        minordervalue.setText("1");
        maxordervalue.setText("50");
        minorder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>50){
                        minorder.setText("50");
                        minordervalue.setText("50");
                    }
                    else
                        minordervalue.setText(minorder.getText());
                }
                else if(String.valueOf(charSequence).equals(""))
                    minordervalue.setText("1");
                else
                    minordervalue.setText(minorder.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        seekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                minorder.setText(String.valueOf(value));
                minordervalue.setText(String.valueOf(value));
            }
        });
    }
    private void initRangeSeekbar(){
        formatter = new DecimalFormat("#,###,###");
        minpricerange.setFilters(new InputFilter[]{new InputFilterMinMax("0","100000000")});
        maxpricerange.setFilters(new InputFilter[]{new InputFilterMinMax("0","100000000")});
        minprice.setText(formatter.format(Double.parseDouble("100000")));
        maxprice.setText(formatter.format(Double.parseDouble("100000000")));
        minpricerangevalue.setText(formatter.format(Double.parseDouble("100000")));
        maxpricerangevalue.setText(formatter.format(Double.parseDouble("100000000")));
        minpricerange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>100000000){
                        minpricerange.setText("100000000");
                        minprice.setText(formatter.format(Double.parseDouble("100000000")));
                    }
                    else
                        minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                }
                else if(String.valueOf(charSequence).equals(""))
                    minprice.setText(formatter.format(Double.parseDouble("100000")));
                else
                    minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        minpricerange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()){
                    if(!String.valueOf(minpricerange.getText()).equals("")){
                        if(Integer.parseInt(String.valueOf(minpricerange.getText()))<100000){
                            minpricerange.setText("100000");
                            minprice.setText(formatter.format(Double.parseDouble("100000")));
                        }
                        else
                            minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                    }
                    else if(String.valueOf(minpricerange.getText()).equals(""))
                        minprice.setText(formatter.format(Double.parseDouble("100000")));
                    else
                        minprice.setText(formatter.format(Double.parseDouble(minpricerange.getText().toString())));
                }
            }
        });
        maxpricerange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!String.valueOf(charSequence).equals("")){
                    if (Integer.parseInt(String.valueOf(charSequence))>100000000){
                        maxpricerange.setText("100000000");
                        maxprice.setText(formatter.format(Double.parseDouble("100000000")));
                    }
                    else
                        maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                }
                else if(String.valueOf(charSequence).equals(""))
                    maxprice.setText(formatter.format(Double.parseDouble("100000")));
                else
                    maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        maxpricerange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(hasWindowFocus()){
                    if(!String.valueOf(maxpricerange.getText()).equals("")){
                        if(Integer.parseInt(String.valueOf(maxpricerange.getText()))<100000){
                            maxpricerange.setText("100000");
                            maxprice.setText(formatter.format(Double.parseDouble("100000")));
                        }
                        else
                            maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                    }
                    else if(String.valueOf(maxpricerange.getText()).equals(""))
                        maxprice.setText(formatter.format(Double.parseDouble("100000")));
                    else
                        maxprice.setText(formatter.format(Double.parseDouble(maxpricerange.getText().toString())));
                }
            }
        });
        rangeseekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minprice.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxprice.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                minpricerange.setText(String.valueOf(minValue));
                minpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                maxpricerange.setText(String.valueOf(maxValue));
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
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

}
