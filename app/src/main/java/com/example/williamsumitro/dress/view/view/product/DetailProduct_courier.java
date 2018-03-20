package com.example.williamsumitro.dress.view.view.product;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Courier;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_courier extends AppCompatActivity {
    @BindView(R.id.detailproductcourier_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproductcourier_recyclerview) RecyclerView recyclerView;
    private List<Courier> courierList = new ArrayList<>();
    private DetailProductCourierRVAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_courier);
        initView();
        setuptoolbar();
        initdata();
        setuprecyclerview();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Courier");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setuprecyclerview(){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initdata(){
        adapter = new DetailProductCourierRVAdapter(courierList, context);
        Courier courier = new Courier(R.drawable.image, "JNE");
        courierList.add(courier);
        courier = new Courier(R.drawable.image, "INDAH");
        courierList.add(courier);
    }
}
