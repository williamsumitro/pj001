package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.AddToBag;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Courier;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.PriceDetails;
import com.example.williamsumitro.dress.view.model.Product_Size_Qty;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.bag.adapter.CourierRVAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Buy extends AppCompatActivity {
//    @BindView(R.id.buy_tvtotalxl) TextView totalXL;
//    @BindView(R.id.buy_tvtotals) TextView totalS;
//    @BindView(R.id.buy_tvtotalm) TextView totalM;
//    @BindView(R.id.buy_tvtotall) TextView totalL;
//    @BindView(R.id.buy_tvsubtotal) TextView subtotal;
//    @BindView(R.id.buy_tvpercentage) TextView percentage;
    @BindView(R.id.buy_tvnameproduct) TextView nameproduct;
//    @BindView(R.id.buy_tvdiscount) TextView discount;
    @BindView(R.id.buy_tvpriceproduct) TextView priceproduct;
    @BindView(R.id.buy_tvtotal) TextView total;
    @BindView(R.id.buy_toolbar) Toolbar toolbar;
//    @BindView(R.id.buy_rvpricedetails) RecyclerView pricedetails;
//    @BindView(R.id.buy_rvcourier) RecyclerView courier;
    @BindView(R.id.buy_imgproduct) ImageView imageproduct;
    @BindView(R.id.buy_etxl) EditText etXL;
    @BindView(R.id.buy_ets) EditText etS;
    @BindView(R.id.buy_etm) EditText etM;
    @BindView(R.id.buy_etl) EditText etL;
    @BindView(R.id.buy_btnminxl) Button minXL;
    @BindView(R.id.buy_btnmins) Button minS;
    @BindView(R.id.buy_btnminm) Button minM;
    @BindView(R.id.buy_btnminl) Button minL;
    @BindView(R.id.buy_btnmaxxl) Button maxXL;
    @BindView(R.id.buy_btnmaxs) Button maxS;
    @BindView(R.id.buy_btnmaxm) Button maxM;
    @BindView(R.id.buy_btnmaxl) Button maxL;
    @BindView(R.id.buy_tvMinOrder) TextView minorder;
    @BindView(R.id.buy_btncontinue) Button continues;
    @BindView(R.id.buy_lnfree) LinearLayout container_free;
    @BindView(R.id.buy_lnl) LinearLayout container_l;
    @BindView(R.id.buy_lnm) LinearLayout container_m;
    @BindView(R.id.buy_lnxl) LinearLayout container_xl;
    @BindView(R.id.buy_lns) LinearLayout container_s;
    @BindView(R.id.buy_etfree) EditText etFree;
    @BindView(R.id.buy_btnminfree) Button minFree;
    @BindView(R.id.buy_btnmaxfree) Button maxFree;

    private List<Courier> courierList;
    private List<Price> priceDetailsList;
    private Context context;
    private BuyRVAdapter pricedetailsadapter;
    private CourierRVAdapter courieradapter;
    private int qtyS = 0, qtyM = 0, qtyL = 0, qtyXL = 0, qtyFree = 0, subtot = 0, tot = 0, disc = 0, percen = 0, price = 100000;
    private DecimalFormat formatter;
    private apiService service;
    private SessionManagement sessionManagement;
    private ArrayList<Product_Size_Qty> product_size_qtyArrayList;

    private final static String NAMAPRODUCT = "NAMAPRODUCT";
    private final static String QTYMINORDER = "QTYMINORDER";
    private final static String GAMBARPRODUCT = "GAMBARPRODUCT";
    private final static String MINORDER = "MINORDER";
    private final static String PRICELIST = "PRICELIST";
    private final static String SIZELIST = "SIZELIST";
    private final static String PRODUCT_ID = "PRODUCT_ID";

    private String extra_namaproduct, extra_gambarproduct, extra_minorder, token, extra_productid;
    private ArrayList<String> extra_priceminlist, extra_qtyminorder, extra_sizelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initView();
        setuptoolbar();
//        setupRV();
        getactivityIntent();
        initData();
        initClick();
        initEditText();
    }

    private void initEditText() {
        etS.addTextChangedListener(new FinancialTextWatcher(etS));
        etM.addTextChangedListener(new FinancialTextWatcher(etM));
        etL.addTextChangedListener(new FinancialTextWatcher(etL));
        etXL.addTextChangedListener(new FinancialTextWatcher(etXL));
        etFree.addTextChangedListener(new FinancialTextWatcher(etFree));
        etS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String hasil = FinancialTextWatcher.trimCommaOfString(etS.getText().toString());
                    etS.setText(hasil.replaceFirst("^0+(?!$)", ""));
                    if (hasil.equals("")) {
                        qtyS = 0;
                    }
                    else
                        qtyS = Integer.parseInt(hasil);
                    changeprice();
                }
            }
        });
        etM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String hasil = FinancialTextWatcher.trimCommaOfString(etM.getText().toString());
                    etM.setText(hasil.replaceFirst("^0+(?!$)", ""));
                    if (hasil.equals("")) {
                        qtyM = 0;
                    }
                    else
                        qtyM = Integer.parseInt(hasil);
                    changeprice();
                }
            }
        });
        etL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String hasil = FinancialTextWatcher.trimCommaOfString(etL.getText().toString());
                    etL.setText(hasil.replaceFirst("^0+(?!$)", ""));
                    if (hasil.equals("")) {
                        qtyL = 0;
                    }
                    else
                        qtyL = Integer.parseInt(hasil);
                    changeprice();
                }
            }
        });
        etXL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String hasil = FinancialTextWatcher.trimCommaOfString(etXL.getText().toString());
                    etXL.setText(hasil.replaceFirst("^0+(?!$)", ""));
                    if (hasil.equals("")) {
                        qtyXL = 0;
                    }
                    else
                        qtyXL = Integer.parseInt(hasil);
                    changeprice();
                }
            }
        });
        etFree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String hasil = FinancialTextWatcher.trimCommaOfString(etFree.getText().toString());
                    etFree.setText(hasil.replaceFirst("^0+(?!$)", ""));
                    if (hasil.equals("")) {
                        qtyFree = 0;
                    }
                    else
                        qtyFree = Integer.parseInt(hasil);
                    changeprice();
                }
            }
        });
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        courierList = new ArrayList<>();
        priceDetailsList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        etS.setText(String.valueOf(qtyS));
        etM.setText(String.valueOf(qtyM));
        etL.setText(String.valueOf(qtyL));
        etXL.setText(String.valueOf(qtyXL));
        etFree.setText(String.valueOf(qtyFree));
//        totalS.setText("0");
//        totalM.setText("0");
//        totalL.setText("0");
//        totalXL.setText("0");
//        subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(subtot))));
//        percentage.setText(String.valueOf(percen) + "%");
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
//        discount.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(disc))));
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(price))));
        continues.setEnabled(false);
        continues.setBackgroundColor(getResources().getColor(R.color.gray1));
        sessionManagement = new SessionManagement(getApplicationContext());
    }
    private void getactivityIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(NAMAPRODUCT)){
            extra_productid = getintent.getExtras().getString(PRODUCT_ID);
            extra_namaproduct = getintent.getExtras().getString(NAMAPRODUCT);
            extra_gambarproduct = getintent.getExtras().getString(GAMBARPRODUCT);
            extra_minorder = getintent.getExtras().getString(MINORDER);
            extra_priceminlist = getintent.getStringArrayListExtra(PRICELIST);
            extra_qtyminorder = getintent.getStringArrayListExtra(QTYMINORDER);
            extra_sizelist = getintent.getStringArrayListExtra(SIZELIST);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Buy Product");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    private void initData(){
        nameproduct.setText(extra_namaproduct);
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(extra_priceminlist.get(0)))));
        Picasso.with(context)
                .load(extra_gambarproduct)
                .placeholder(R.drawable.logo404)
                .into(imageproduct);
        minorder.setText(extra_minorder);
        for (int i = 0; i<extra_sizelist.size(); i++){
            if (extra_sizelist.get(i).toLowerCase().equals("1"))
                container_s.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("2"))
                container_m.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("3"))
                container_l.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("4"))
                container_xl.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("5"))
                container_free.setVisibility(View.VISIBLE);
        }
    }
    private void addInt(String type){
        if (type.toLowerCase().equals("s")){
            qtyS += 1;
            etS.setText(String.valueOf(qtyS));
        }
        else if(type.toLowerCase().equals("m")){
            qtyM += 1;
            etM.setText(String.valueOf(qtyM));
        }
        else if(type.toLowerCase().equals("l")){
            qtyL += 1;
            etL.setText(String.valueOf(qtyL));
        }
        else if(type.toLowerCase().equals("xl")){
            qtyXL += 1;
            etXL.setText(String.valueOf(qtyXL));
        }
        else if (type.toLowerCase().equals("free")){
            qtyFree += 1;
            etFree.setText(String.valueOf(qtyFree));
        }
    }
    private void substractInt(String type){
        if (type.toLowerCase().equals("s")){
            if(qtyS>0)
                qtyS -= 1;
            etS.setText(String.valueOf(qtyS));
        }
        else if(type.toLowerCase().equals("m")){
            if(qtyM>0)
                qtyM -= 1;
            etM.setText(String.valueOf(qtyM));
        }
        else if(type.toLowerCase().equals("l")){
            if(qtyL>0)
                qtyL -= 1;
            etL.setText(String.valueOf(qtyL));
        }
        else if(type.toLowerCase().equals("xl")){
            if(qtyXL>0)
                qtyXL -= 1;
            etXL.setText(String.valueOf(qtyXL));
        }
        else if (type.toLowerCase().equals("free")){
            qtyFree -= 1;
            etFree.setText(String.valueOf(qtyFree));
        }
    }
    private void initClick(){
        maxS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInt("s");
                changeprice();
            }
        });
        maxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInt("m");
                changeprice();
            }
        });
        maxL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInt("l");
                changeprice();
            }
        });
        maxXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInt("xl");
                changeprice();
            }
        });
        maxFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInt("free");
                changeprice();
            }
        });
        minS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                substractInt("s");
                changeprice();
            }
        });
        minM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                substractInt("m");
                changeprice();
            }
        });
        minL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                substractInt("l");
                changeprice();
            }
        });
        minXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                substractInt("xl");
                changeprice();
            }
        });
        minFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                substractInt("free");
                changeprice();
            }
        });
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_addtobag();
            }
        });
    }

    private void api_addtobag() {
        product_size_qtyArrayList = new ArrayList<>();
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        for (int i = 0; i<extra_sizelist.size(); i++){
            Product_Size_Qty product_size_qty = null;
            if (extra_sizelist.get(i).toLowerCase().equals("1"))
                product_size_qty = new Product_Size_Qty(1, qtyS);
            else if (extra_sizelist.get(i).toLowerCase().equals("2"))
                product_size_qty = new Product_Size_Qty(2, qtyM);
            else if (extra_sizelist.get(i).toLowerCase().equals("3"))
                product_size_qty = new Product_Size_Qty(3, qtyL);
            else if (extra_sizelist.get(i).toLowerCase().equals("4"))
                product_size_qty = new Product_Size_Qty(4, qtyXL);
            else if (extra_sizelist.get(i).toLowerCase().equals("5"))
                product_size_qty = new Product_Size_Qty(5, qtyFree);
            product_size_qtyArrayList.add(product_size_qty);
        }
        service = apiUtils.getAPIService();
        AddToBag addToBag = new AddToBag(token, extra_productid, product_size_qtyArrayList);
        service.req_add_to_bag(addToBag).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ShoppingBag.class);
                            initanim(intent);
                            finish();
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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }

    private void changeprice(){
        int min, max;
        int sum = qtyS + qtyM + qtyL + qtyXL +qtyFree, tots = price * qtyS, totm= price * qtyM, totl = price * qtyL, totxl = price * qtyXL;
        for (int i = 0; i<extra_qtyminorder.size(); i ++){
            if (i!=extra_qtyminorder.size()-1){
                min = Integer.parseInt(extra_qtyminorder.get(i));
                max = Integer.parseInt(extra_qtyminorder.get(i+1));
                if(sum>=min && sum<=max) {
                    percen = Integer.parseInt(extra_priceminlist.get(i));
                }
            }
            else {
                min = Integer.parseInt(extra_qtyminorder.get(i));
                if(sum>=min) {
                    percen = Integer.parseInt(extra_priceminlist.get(i));
                }
            }
        }

        if(sum < Integer.parseInt(extra_minorder)){
            continues.setEnabled(false);
            continues.setBackgroundColor(getResources().getColor(R.color.gray1));
        }
        else {
            continues.setEnabled(true);
            continues.setBackgroundColor(getResources().getColor(R.color.green1));
        }
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(percen))));
        tot = sum * percen;
//        subtot = tots+totm+ totl+ totxl;
//        totalS.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tots))));
//        totalM.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totm))));
//        totalL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totl))));
//        totalXL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totxl))));
//        subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(subtot))));
//        percentage.setText(String.valueOf(percen) + "%");
//        disc = percen * subtot / 100;
//        discount.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(disc))));
//        tot = subtot - disc;
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
    }
}
