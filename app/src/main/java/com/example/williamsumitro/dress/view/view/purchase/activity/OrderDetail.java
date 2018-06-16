package com.example.williamsumitro.dress.view.view.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;
import com.example.williamsumitro.dress.view.view.purchase.adapter.OrderDetailRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetail extends AppCompatActivity {

    private final static String PENDING = "PENDING";
    private final static String ACCEPT = "ACCEPT";
    private final static String REJECT = "REJECT";
    private final static String STATUS = "STATUS";

    @BindView(R.id.orderdetail_imagestore) ImageView imagestore;
    @BindView(R.id.orderdetail_tvTotal) TextView totalprice;
    @BindView(R.id.orderdetail_tvSubtotal) TextView subtotalprice;
    @BindView(R.id.orderdetail_tvStorename) TextView storename;
    @BindView(R.id.orderdetail_tvShippingMethod) TextView shippingmethod;
    @BindView(R.id.orderdetail_tvShippingFee) TextView shippingfee;
    @BindView(R.id.orderdetail_tvOrdernumber) TextView ordernumber;
    @BindView(R.id.orderdetail_tvOrderDate) TextView orderdate;
    @BindView(R.id.orderdetail_tvAddress) TextView address;
    @BindView(R.id.orderdetail_toolbar) Toolbar toolbar;
    @BindView(R.id.orderdetail_rvApprove) RecyclerView rvapprove;
    @BindView(R.id.orderdetail_rvPending) RecyclerView rvpending;
    @BindView(R.id.orderdetail_rvReject) RecyclerView rvreject;
    @BindView(R.id.orderdetail_lnApprove) LinearLayout lnapprove;
    @BindView(R.id.orderdetail_lnPending) LinearLayout lnpending;
    @BindView(R.id.orderdetail_lnReject) LinearLayout lnreject;
    @BindView(R.id.orderdetail_received) Button received;
    @BindView(R.id.orderdetail_ratingandreview) Button ratingandreview;
    @BindView(R.id.orderdetail_payment) Button payment;
    @BindView(R.id.orderdetail_lncontact) LinearLayout lncontact;
    @BindView(R.id.orderdetail_tvarrivaldate) TextView arrivaldate;
    @BindView(R.id.orderdetail_tvnamacourier) TextView namacourier;
    @BindView(R.id.orderdetail_tvnomorcourier) TextView nomorcourier;


    private Context context;
    private String extrapending, extrareject, extraaccept, extrastatus;
    private OrderDetailRVAdapter adapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initView();
        setuptoolbar();
        setupRV();
        initGetIntent();
        initData();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        lnapprove.setVisibility(View.GONE);
        lnpending.setVisibility(View.GONE);
        lnreject.setVisibility(View.GONE);
        lncontact.setVisibility(View.GONE);
        orderList = new ArrayList<>();
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order Detail");
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
        adapter = new OrderDetailRVAdapter(orderList, context);
        RecyclerView.LayoutManager layoutapprove = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutpending = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutreject = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvapprove.setLayoutManager(layoutapprove);
        rvapprove.setItemAnimator(new DefaultItemAnimator());
        rvapprove.setAdapter(adapter);
        rvpending.setLayoutManager(layoutpending);
        rvpending.setItemAnimator(new DefaultItemAnimator());
        rvpending.setAdapter(adapter);
        rvreject.setLayoutManager(layoutreject);
        rvreject.setItemAnimator(new DefaultItemAnimator());
        rvreject.setAdapter(adapter);
    }
    private void initGetIntent(){
        Intent getIntent = getIntent();
        if (getIntent.hasExtra(STATUS)){
            extrastatus = getIntent.getExtras().getString(STATUS);
            if(getIntent.hasExtra(PENDING)){
                extrapending = getIntent.getExtras().getString(PENDING);
                if(extrapending.toLowerCase().equals("true")){
                    lnpending.setVisibility(View.VISIBLE);
                }
            }
            else if(getIntent.hasExtra(ACCEPT)){
                extraaccept = getIntent.getExtras().getString(ACCEPT);
                if(extraaccept.toLowerCase().equals("true")){
                    lnapprove.setVisibility(View.VISIBLE);
                    lncontact.setVisibility(View.VISIBLE);
                }
            }
            else if(getIntent.hasExtra(REJECT)){
                extrareject = getIntent.getExtras().getString(REJECT);
                if(extrareject.toLowerCase().equals("true")){
                    lnreject.setVisibility(View.VISIBLE);
                }
            }
            if(extrastatus.toLowerCase().equals("payment")){
                payment.setVisibility(View.VISIBLE);
            }
            else if(extrastatus.toLowerCase().equals("confirmation")){
                received.setVisibility(View.VISIBLE);
            }
            else if(extrastatus.toLowerCase().equals("reviewandrating")){
                ratingandreview.setVisibility(View.VISIBLE);
            }
        }
    }
    private void initData(){
        Order order = new Order(R.drawable.image, "Blazzer", 10, 4, 3, 2, 200000,800000,60000,400000, 2000000, 20000, "");
        orderList.add(order);
    }
}
