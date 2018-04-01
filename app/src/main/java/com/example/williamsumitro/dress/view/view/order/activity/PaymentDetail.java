package com.example.williamsumitro.dress.view.view.order.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutRVAdapter;
import com.example.williamsumitro.dress.view.view.order.adapter.BankRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentDetail extends AppCompatActivity {
    @BindView(R.id.paymentdetail_tvTotal) TextView total;
    @BindView(R.id.paymentdetail_tvStorename) TextView storename;
    @BindView(R.id.paymentdetail_tvOrdernumber) TextView ordernumber;
    @BindView(R.id.paymentdetail_tvOrderdeadline) TextView orderdeadline;
    @BindView(R.id.paymentdetail_tvOrderdate) TextView orderdate;
    @BindView(R.id.paymentdetail_toolbar) Toolbar toolbar;
    @BindView(R.id.paymentdetail_spinner) Spinner spinner;
    @BindView(R.id.paymentdetail_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.paymentdetail_imgStore)ImageView storeimage;
    @BindView(R.id.paymentdetail_etTotalPayment) TextInputEditText totalpayment;
    @BindView(R.id.paymentdetail_etNote) EditText note;
    @BindView(R.id.paymentdetail_etlTotalPayment) TextInputLayout layouttotalpayment;
    @BindView(R.id.paymentdetail_etlAccountNumber) TextInputLayout layoutaccountnumber;
    @BindView(R.id.paymentdetail_etlAccountBank) TextInputLayout layoutaccountbank;
    @BindView(R.id.paymentdetail_etAccountNumber) TextInputEditText accountnumber;
    @BindView(R.id.paymentdetail_etAccountBank) TextInputEditText accoutbank;
    @BindView(R.id.paymentdetail_btnUpload) Button upload;
    @BindView(R.id.paymentdetail_btnSend) Button send;
    private Context context;
    private BankRVAdapter adapter;
    private List<Bank> bankList;
    private List<String> namabank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        initView();
        setuptoolbar();
        setupRV();
        initData();
        int size = bankList.size();
        for(int i = 0; i<size; i++){
            namabank.add(bankList.get(i).getBankname());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, namabank);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = this;
        bankList = new ArrayList<>();
        namabank = new ArrayList<>();
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment Detail");
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
        adapter = new BankRVAdapter(bankList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Bank bank = new Bank(R.drawable.bank2, "Union Pay", "Tek Kin Lung", "1231232321311");
        bankList.add(bank);
        bank = new Bank(R.drawable.bank3, "Pay Pay", "Tek Kin Lung", "1231232321311");
        bankList.add(bank);
        bank = new Bank(R.drawable.bank1, "HSBC", "Tek Kin Lung", "1231232321311");
        bankList.add(bank);
    }
}
