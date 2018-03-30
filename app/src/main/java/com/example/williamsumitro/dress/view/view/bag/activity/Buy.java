package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Courier;
import com.example.williamsumitro.dress.view.model.PriceDetails;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Buy extends AppCompatActivity {
    @BindView(R.id.buy_tvtotalxl) TextView totalXL;
    @BindView(R.id.buy_tvtotals) TextView totalS;
    @BindView(R.id.buy_tvtotalm) TextView totalM;
    @BindView(R.id.buy_tvtotall) TextView totalL;
    @BindView(R.id.buy_tvsubtotal) TextView subtotal;
    @BindView(R.id.buy_tvpercentage) TextView percentage;
    @BindView(R.id.buy_tvnameproduct) TextView nameproduct;
    @BindView(R.id.buy_tvdiscount) TextView discount;
    @BindView(R.id.buy_tvpriceproduct) TextView priceproduct;
    @BindView(R.id.buy_tvtotal) TextView total;
    @BindView(R.id.buy_toolbar) Toolbar toolbar;
    @BindView(R.id.buy_rvpricedetails) RecyclerView pricedetails;
    @BindView(R.id.buy_rvcourier) RecyclerView courier;
    @BindView(R.id.buy_rvbank) RecyclerView bank;
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
    @BindView(R.id.buy_btncontinue) Button continues;

    private List<Bank> bankList;
    private List<Courier> courierList;
    private List<PriceDetails> priceDetailsList;
    private Context context;
    private BuyRVAdapter bankadapter, courieradapter, pricedetailsadapter;
    private int qtyS = 0, qtyM = 0, qtyL = 0, qtyXL = 0, subtot = 0, tot = 0, disc = 0, percen = 0, price = 100000;
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initView();
        setuptoolbar();
        setupRV();
        initData();
        initClick();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        bankList = new ArrayList<>();
        courierList = new ArrayList<>();
        priceDetailsList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        etS.setText(String.valueOf(qtyS));
        etM.setText(String.valueOf(qtyM));
        etL.setText(String.valueOf(qtyL));
        etXL.setText(String.valueOf(qtyXL));
        totalS.setText("0" );
        totalM.setText("0" );
        totalL.setText("0" );
        totalXL.setText("0" );
        subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(subtot))));
        percentage.setText(String.valueOf(percen) + "%");
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
        discount.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(disc))));
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(price))));
        continues.setEnabled(false);
        continues.setBackgroundColor(getResources().getColor(R.color.gray1));
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
    private void setupRV(){
        bankadapter = new BuyRVAdapter(null, bankList, null, context);
        courieradapter = new BuyRVAdapter(courierList, null, null, context);
        pricedetailsadapter = new BuyRVAdapter(null, null, priceDetailsList, context);
        RecyclerView.LayoutManager gridlayout = new GridLayoutManager(context, 3);
        RecyclerView.LayoutManager gridlayout1 = new GridLayoutManager(context, 4);
        RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        bank.setLayoutManager(gridlayout);
        bank.setItemAnimator(new DefaultItemAnimator());
        bank.setAdapter(bankadapter);
        courier.setLayoutManager(gridlayout1);
        courier.setItemAnimator(new DefaultItemAnimator());
        courier.setAdapter(courieradapter);
        pricedetails.setLayoutManager(horizontallayout);
        pricedetails.setItemAnimator(new DefaultItemAnimator());
        pricedetails.setAdapter(pricedetailsadapter);
    }
    private void initData(){
        Bank bank = new Bank(R.drawable.bank);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank1);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank2);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank3);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank4);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank5);
        bankList.add(bank);
        bank = new Bank(R.drawable.bank6);
        bankList.add(bank);

        Courier courier = new Courier(R.drawable.courier1, "JNE");
        courierList.add(courier);
        courier = new Courier(R.drawable.courier2, "Rabbit");
        courierList.add(courier);
        courier = new Courier(R.drawable.courier3, "POS");
        courierList.add(courier);
        courier = new Courier(R.drawable.courier4, "Whatever");
        courierList.add(courier);

        PriceDetails priceDetails = new PriceDetails(5, 5);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(15, 15);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(25, 25);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(35, 35);
        priceDetailsList.add(priceDetails);
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
    }
    private void changeprice(){
        int sum = qtyS + qtyM + qtyL + qtyXL, tots = price * qtyS, totm= price * qtyM, totl = price * qtyL, totxl = price * qtyXL;
        if(sum>5 && sum<=15)
            percen = 5;
        else if(sum>15 && sum<=25)
            percen = 15;
        else if(sum>25 && sum<=35)
            percen = 25;
        else if(sum>35)
            percen = 35;

        if(sum == 0){
            continues.setEnabled(false);
            continues.setBackgroundColor(getResources().getColor(R.color.gray1));
        }
        else {
            continues.setEnabled(true);
            continues.setBackgroundColor(getResources().getColor(R.color.green1));
        }
        subtot = tots+totm+ totl+ totxl;
        totalS.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tots))));
        totalM.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totm))));
        totalL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totl))));
        totalXL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(totxl))));
        subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(subtot))));
        percentage.setText(String.valueOf(percen) + "%");
        disc = percen * subtot / 100;
        discount.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(disc))));
        tot = subtot - disc;
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
    }
}
