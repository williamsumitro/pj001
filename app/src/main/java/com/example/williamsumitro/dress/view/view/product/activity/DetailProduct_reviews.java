package com.example.williamsumitro.dress.view.view.product.activity;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Review;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductReviewsRVADapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_reviews extends AppCompatActivity {
    @BindView(R.id.detailproductreview_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.detailproductreview_tvscore) TextView score;
    @BindView(R.id.detailproductreview_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproductreview_imgstar5) ImageView five;
    @BindView(R.id.detailproductreview_imgstar4) ImageView four;
    @BindView(R.id.detailproductreview_imgstar3) ImageView three;
    @BindView(R.id.detailproductreview_imgstar2) ImageView two;
    @BindView(R.id.detailproductreview_imgstar1) ImageView one;
    private DetailProductReviewsRVADapter adapter;
    private List<Review> reviewList;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_reviews);
        initView();
        setuptoolbar();
        initdata();
        setuprecyclerview();
    }
    private void initView(){
        ButterKnife.bind(this);
        reviewList = new ArrayList<>();
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Reviews");
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
        adapter = new DetailProductReviewsRVADapter(reviewList, context);
        Review review = new Review("Kelvin", "This shirt is freakhing hot", "20/3/2018", R.drawable.image, 4.4);
        reviewList.add(review);
        review = new Review("Asfa", "This shirt is freakhing hot", "21/3/2018", R.drawable.image, 1.2);
        reviewList.add(review);
        review = new Review("Kfdsaelvin", "This shirt is freakhing hot", "11/3/2018", R.drawable.image, 5);
        reviewList.add(review);
        review = new Review("Kekek", "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.", "10/3/2018", R.drawable.image, 4);
        reviewList.add(review);
        review = new Review("Nigga", "This shirt is freakhing hot", "2/3/2018", R.drawable.image, 3.4);
        reviewList.add(review);
        review = new Review("Kelvin", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.", "20/3/2018", R.drawable.image, 4.4);
        reviewList.add(review);
    }

}
