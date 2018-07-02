package com.example.williamsumitro.dress.view.view.purchase.activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ProductRating;
import com.example.williamsumitro.dress.view.view.purchase.adapter.RatingReviewProductRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewandrateDetail extends AppCompatActivity {
    @BindView(R.id.reviewandratedetail_tvStorename) TextView storename;
    @BindView(R.id.reviewandratedetail_tvRejected) TextView rejected;
    @BindView(R.id.reviewandratedetail_tvReason) TextView reason;
    @BindView(R.id.reviewandratedetail_tvOrderReceivedDate) TextView receivedate;
    @BindView(R.id.reviewandratedetail_tvOrdernumber) TextView ordernumber;
    @BindView(R.id.reviewandratedetail_tvOrderDate) TextView orderdate;
    @BindView(R.id.reviewandratedetail_tvAccepted) TextView accepted;
    @BindView(R.id.reviewandratedetail_toolbar) Toolbar toolbar;
    @BindView(R.id.reviewandratedetail_rv) RecyclerView recyclerView;
    @BindView(R.id.reviewandratedetail_imagestore) ImageView imagestore;
    @BindView(R.id.reviewandratedetail_finish) Button finish;
    @BindView(R.id.reviewandratedetail_imgstar1) ImageView star1;
    @BindView(R.id.reviewandratedetail_imgstar2) ImageView star2;
    @BindView(R.id.reviewandratedetail_imgstar3) ImageView star3;
    @BindView(R.id.reviewandratedetail_imgstar4) ImageView star4;
    @BindView(R.id.reviewandratedetail_imgstar5) ImageView star5;

    private Context context;
    private RatingReviewProductRVAdapter adapter;
    private List<ProductRating> productRatingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewandrate_detail);
        initView();
        setuptoolbar();
        initOnClick();
    }
    private void initOnClick(){
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star);
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star);
                star5.setImageResource(R.drawable.star0);
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star);
                star4.setImageResource(R.drawable.star0);
                star5.setImageResource(R.drawable.star0);
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star);
                star3.setImageResource(R.drawable.star0);
                star4.setImageResource(R.drawable.star0);
                star5.setImageResource(R.drawable.star0);
            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.star);
                star2.setImageResource(R.drawable.star0);
                star3.setImageResource(R.drawable.star0);
                star4.setImageResource(R.drawable.star0);
                star5.setImageResource(R.drawable.star0);
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        context = this;
        productRatingList = new ArrayList<>();
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
}
