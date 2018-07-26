package com.example.williamsumitro.dress.view.view.store.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.SortByIdResult;
import com.example.williamsumitro.dress.view.model.SortByIdStoreResult;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.filter.Filter;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.openstore.adapter.StoreRVAdapter;
import com.example.williamsumitro.dress.view.view.search.activity.Search;
import com.example.williamsumitro.dress.view.view.search.adapter.CourierAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;
import com.example.williamsumitro.dress.view.view.store.adapter.StoreProductRV;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import me.himanshusoni.quantityview.QuantityView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukoutletFragment extends Fragment {
    int clickchoice;
    @BindView(R.id.produkoutlet_rv) RecyclerView recyclerView;
    @BindView(R.id.produkoutlet_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.produkoutlet_lnfilter) LinearLayout filter;
    @BindView(R.id.produkoutlet_lnsort) LinearLayout sort;
    @BindView(R.id.produkoutlet_ln_bottom) LinearLayout bottom;
    @BindView(R.id.produkoutlet_ln_top) LinearLayout top;


    private StoreProductRV adapterProduct;
    private Context context;
    private apiService service;
    private String token;
    private String productname = "";
    private SessionManagement sessionManagement;
    private ArrayList<ProductInfo> searchList;
    private Dialog dialog;
    private DecimalFormat formatter;
    private int qtyORDER = 0;

    private StoreDetails storeDetails;
    private final static String STORE_RESULT = "STORE_RESULT";
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private List<CourierDetails> courierDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private CourierAdapter courierAdapter;
    private String idprovince, idcity, choosen_province, choosen_city, idcourier, choosen_courier;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;

    public ProdukoutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produkoutlet, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        initonClick();
        return view;
    }

    private void initonClick() {
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
                        progressDialog.setMessage("Getting all the data, please wait");
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
                        service.req_get_sort_by_id_store(storeDetails.getStoreId().toString(), token, String.valueOf(id)).enqueue(new Callback<SortByIdStoreResult>() {
                            @Override
                            public void onResponse(Call<SortByIdStoreResult> call, Response<SortByIdStoreResult> response) {
                                if (response.code()==200){
                                    searchList = response.body().getProductInfo();
                                    if (searchList.size()>0){
                                        setupRV();
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                    else {
                                        setupRV();
                                        progressDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<SortByIdStoreResult> call, Throwable t) {
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

    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        Bundle args = getArguments();
        storeDetails = (StoreDetails) args.getSerializable(STORE_RESULT);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
        searchList = new ArrayList<>();
        searchList = storeDetails.getProduct();
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        progressDialog.setMessage("Getting all the data, please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        setupRV();
        swipeRefreshLayout.setRefreshing(false);
    }
    private void setupRV(){
        bottom.setVisibility(View.VISIBLE);
        adapterProduct = new StoreProductRV(searchList, context);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
        progressDialog.dismiss();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_qty, null, false);
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
                progressDialog.setMessage("Wait a sec..");
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
