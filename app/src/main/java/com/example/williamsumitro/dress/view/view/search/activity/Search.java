package com.example.williamsumitro.dress.view.view.search.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.AdvancedSearchResult;
import com.example.williamsumitro.dress.view.model.BestResponse;
import com.example.williamsumitro.dress.view.model.CityDetails;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierDetails;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.Filter_Model;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.SortByIdResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.search.adapter.CourierAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import me.himanshusoni.quantityview.QuantityView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_rv) RecyclerView rv;
    @BindView(R.id.search_ln_bottom) LinearLayout bottom;
    @BindView(R.id.search_ln_top) LinearLayout top;
    @BindView(R.id.search_lnfilter) LinearLayout filter;
    @BindView(R.id.search_lnsort) LinearLayout sort;
    @BindView(R.id.search_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Filter_Model> filter_modelList;
    private Context context;
    private apiService service;
    private ArrayList<ProductInfo> searchList;
    private HotRVAdapter adapter;
    private String productname = "";
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private int qtyORDER = 0;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private List<CourierDetails> courierDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private CourierAdapter courierAdapter;
    private String idprovince, idcity, choosen_province, choosen_city, idcourier, choosen_courier;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initClick();
        setuptoolbar();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }

    private void initClick() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_filter();
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemview) {
                final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
                builderSingle.setTitle("Sort by");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Recommended");
                arrayAdapter.add("Newest");
                arrayAdapter.add("Latest");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        progressDialog.setMessage("Searching... please wait");
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        String strName = arrayAdapter.getItem(which);
                        int id = 0;
                        if (strName.equals("Recommended")){
                            id = 1;
                        } else if (strName.equals("Newest")) {
                            id = 2;
                        }else if (strName.equals("Latest")) {
                            id = 3;
                        }
                        service.req_get_sort_by_id(String.valueOf(id)).enqueue(new Callback<SortByIdResult>() {
                            @Override
                            public void onResponse(Call<SortByIdResult> call, Response<SortByIdResult> response) {
                                if (response.code()==200){
                                    searchList = response.body().getProductInfo();
                                    if (searchList.size()>0){
                                        bottom.setVisibility(View.VISIBLE);
                                        top.setVisibility(View.GONE);
                                        setupRV();
                                    }
                                    else {
                                        top.setVisibility(View.VISIBLE);
                                        bottom.setVisibility(View.GONE);
                                        setupRV();
                                    }
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<SortByIdResult> call, Throwable t) {
                                progressDialog.dismiss();
                                Toasty.error(context, "Please try again", Toast.LENGTH_SHORT, true).show();
                            }
                        });
                    }
                });
                builderSingle.show();
            }
        });
    }

    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        if (productname.equals("")){
            swipeRefreshLayout.setRefreshing(false);
        }
        else {
            api_getsearch(productname);
        }
    }
    private void initView(){
        ButterKnife.bind(this);
        filter_modelList = new ArrayList<>();
        context = this;
        progressDialog = new ProgressDialog(this);
        formatter = new DecimalFormat("#,###,###");
        provinceDetailsList = new ArrayList<>();
        cityDetailsList = new ArrayList<>();
        courierDetailsList = new ArrayList<>();
        service = apiUtils.getAPIService();
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.onActionViewExpanded();
        item.expandActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Type the product you want to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                productname = s;
                progressDialog.setMessage("Wait a sec..");
                progressDialog.show();
                progressDialog.setCancelable(false);
                api_getsearch(productname);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void api_getsearch(String prodcut_name){
        service.req_search(prodcut_name).enqueue(new Callback<BestResponse>() {
            @Override
            public void onResponse(Call<BestResponse> call, Response<BestResponse> response) {
                if (response.code() == 200){
                    searchList = response.body().getProductInfo();
                    if (searchList.size()>0){
                        bottom.setVisibility(View.VISIBLE);
                        top.setVisibility(View.GONE);
                        setupRV();
                        swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                    }
                    else {
                        top.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BestResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void setupRV(){
        adapter = new HotRVAdapter(searchList, context);
        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(grid_layoutmanager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.dismiss();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 3000);
    }
    private void dialog_filter(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_filter);

        final QuantityView qv_minorder = (QuantityView) dialog.findViewById(R.id.dialog_filter_qv_minorder);
        final TextView minpricerangevalue = (TextView) dialog.findViewById(R.id.dialog_filter_tvminpricerangevalue);
        final TextView maxpricerangevalue = (TextView) dialog.findViewById(R.id.dialog_filter_tvmaxpricerangevalue);
        final TextView maxprice = (TextView) dialog.findViewById(R.id.dialog_filter_tvmaxpricerange);
        final TextView minprice = (TextView) dialog.findViewById(R.id.dialog_filter_tvminpricerange);
        final TextView min_rating = (TextView) dialog.findViewById(R.id.dialog_filter_tv_ratingmin);
        final TextView max_rating = (TextView) dialog.findViewById(R.id.dialog_filter_tv_ratingmax);
        final TextView minrate = (TextView) dialog.findViewById(R.id.dialog_filter_tv_minrate);
        final TextView maxrate = (TextView) dialog.findViewById(R.id.dialog_filter_tv_maxrate);
        final Spinner courier = (Spinner) dialog.findViewById(R.id.dialog_filter_spinner_shipping);
        final Spinner province = (Spinner) dialog.findViewById(R.id.dialog_filter_spinner_province);
        final Spinner city = (Spinner) dialog.findViewById(R.id.dialog_filter_spinner_city);
        final CrystalRangeSeekbar price = (CrystalRangeSeekbar) dialog.findViewById(R.id.dialog_filter_rangeseekbar_price);
        final CrystalRangeSeekbar rate = (CrystalRangeSeekbar) dialog.findViewById(R.id.dialog_filter_rangeseekbar_rate);
        final Button set = (Button) dialog.findViewById(R.id.dialog_filter_btnSet);

        min_rating.setText("0");
        max_rating.setText("5");

        rate.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min_rating.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                max_rating.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                minrate.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxrate.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
            }
        });

        minprice.setText(formatter.format(Double.parseDouble("100000")));
        maxprice.setText(formatter.format(Double.parseDouble("100000000")));
        minpricerangevalue.setText(formatter.format(Double.parseDouble("100000")));
        maxpricerangevalue.setText(formatter.format(Double.parseDouble("100000000")));

        price.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minprice.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxprice.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
                minpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(minValue))));
                maxpricerangevalue.setText(formatter.format(Double.parseDouble(String.valueOf(maxValue))));
            }
        });
        qv_minorder.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Search.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(Search.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);

                et.setText(String.valueOf(qv_minorder.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyORDER = Integer.parseInt(newQuantity);
                        if (qtyORDER>50){
                            Toasty.error(context, "Maximum quantity you can set only 50", Toast.LENGTH_LONG, true).show();
                        }
                        else {
                            qv_minorder.setQuantity(qtyORDER);
                        }
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
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
                            choosen_province = provinceDetails.getProvinceName();service = apiUtils.getAPIService();
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
                                    initDialog(3);
                                }
                            });
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
        service.req_get_courier_list().enqueue(new Callback<CourierResponse>() {
            @Override
            public void onResponse(Call<CourierResponse> call, Response<CourierResponse> response) {
                if(response.code() == 200){
                    courierDetailsList = response.body().getCourier();
                    courierAdapter = new CourierAdapter(context, R.layout.item_spinner, courierDetailsList);
                    courier.setAdapter(courierAdapter);
                    courier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            CourierDetails courierdetails = courierAdapter.getItem(position);
                            idcourier = courierdetails.getCourierId();
                            choosen_courier = courierdetails.getCourierName();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CourierResponse> call, Throwable t) {
                initDialog(3);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Searching... please wait");
                progressDialog.show();
                progressDialog.setCancelable(false);
                service.req_advance_search(String.valueOf(qv_minorder.getQuantity()), min_rating.getText().toString(), max_rating.getText().toString(), idprovince, idcity, idcourier,
                        FinancialTextWatcher.trimCommaOfString(minprice.getText().toString()), FinancialTextWatcher.trimCommaOfString(maxprice.getText().toString())).enqueue(new Callback<AdvancedSearchResult>() {
                    @Override
                    public void onResponse(Call<AdvancedSearchResult> call, Response<AdvancedSearchResult> response) {
                        if (response.code()==200){
                            searchList = response.body().getProductInfo();
                            if (searchList.size()>0){
                                bottom.setVisibility(View.VISIBLE);
                                top.setVisibility(View.GONE);
                                setupRV();
                                progressDialog.dismiss();
                                dialog.dismiss();
                            }
                            else {
                                top.setVisibility(View.VISIBLE);
                                bottom.setVisibility(View.GONE);
                                setupRV();
                                progressDialog.dismiss();
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AdvancedSearchResult> call, Throwable t) {
                        progressDialog.dismiss();
                        Toasty.error(context, "Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                });
            }
        });
        dialog.show();
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
}
