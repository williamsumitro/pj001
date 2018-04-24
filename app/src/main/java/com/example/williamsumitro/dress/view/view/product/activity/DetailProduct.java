package com.example.williamsumitro.dress.view.view.product.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.example.williamsumitro.dress.view.model.PriceDetails;
import com.example.williamsumitro.dress.view.view.bag.activity.Buy;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.store.activity.DetailOutlet;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductSlideImagesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class DetailProduct extends AppCompatActivity {
    @BindView(R.id.detailproduct_rv_partnership) RecyclerView rv_partnership;
    @BindView(R.id.detailproduct_ln_partnership) LinearLayout partnership;
    @BindView(R.id.detailproduct_ln_detailpartnership) LinearLayout detailpartnership;
    @BindView(R.id.detailproduct_imgcaret) ImageView caret;
    @BindView(R.id.detailproduct_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailproduct_circleindicator) CircleIndicator circleIndicator;
    @BindView(R.id.detailproduct_collapstoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailproduct_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproduct_vp) ViewPager viewPager;
    @BindView(R.id.detailproduct_tvTitle) TextView title;
    @BindView(R.id.detailproduct_tvPrice) TextView price;
    @BindView(R.id.detailproduct_tvNamaToko) TextView namatoko;
    @BindView(R.id.detailproduct_tvLocation) TextView location;
    @BindView(R.id.detailproduct_tvLastOnline) TextView lastonline;
    @BindView(R.id.detailproduct_linearOutlet) LinearLayout outlet;
    @BindView(R.id.detailproduct_imgWish) ImageView wish;
    @BindView(R.id.detailproduct_imgFotoToko) ImageView fotoko;
    @BindView(R.id.detailproduct_btnSizeguides) Button sizeguides;
    @BindView(R.id.detailproduct_btnProductdetails) Button productdetails;
    @BindView(R.id.detailproduct_imgAddtocontact) ImageView addtocontract;
    @BindView(R.id.detailproduct_btnAddtobag) Button addtobag;
    @BindView(R.id.detailproduct_lncourier) LinearLayout courier;
    @BindView(R.id.detailproduct_lnreview) LinearLayout review;
    @BindView(R.id.detailproduct_lndiscussion) LinearLayout discussion;
    @BindView(R.id.detailproduct_rvpricedetails) RecyclerView pricedetails;

    private static final Integer[] XMEN= {R.drawable.fake,R.drawable.fake,R.drawable.fake,R.drawable.fake,R.drawable.fake};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private Context context;
    private static int currentPage = 0;

    private List<PriceDetails> priceDetailsList;
    private BuyRVAdapter pricedetailsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initObject();
        initCollapToolbar();
        setupToolbar();
        init();
        initClick();
        setupRV();
        initData();
    }
    private void initClick(){
        addtobag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Buy.class);
                initanim(intent);
            }
        });
        productdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_details.class);
                initanim(intent);
            }
        });
        sizeguides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_sizeguide.class);
                initanim(intent);
            }
        });
        outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailOutlet.class);
                initanim(intent);
            }
        });
        courier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_courier.class);
                initanim(intent);
            }
        });
        discussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_discussions.class);
                initanim(intent);
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_reviews.class);
                initanim(intent);
            }
        });
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        priceDetailsList = new ArrayList<>();
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle("Detail Product");
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);
        viewPager.setAdapter(new DetailProductSlideImagesAdapter(context,XMENArray));
        circleIndicator.setViewPager(viewPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }
    private void setupRV(){
        pricedetailsadapter = new BuyRVAdapter(null, null, priceDetailsList, context);
        RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        pricedetails.setLayoutManager(horizontallayout);
        pricedetails.setItemAnimator(new DefaultItemAnimator());
        pricedetails.setAdapter(pricedetailsadapter);
    }
    private void initData(){
        PriceDetails priceDetails = new PriceDetails(5, 5);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(15, 15);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(25, 25);
        priceDetailsList.add(priceDetails);
        priceDetails = new PriceDetails(35, 35);
        priceDetailsList.add(priceDetails);
    }
}
