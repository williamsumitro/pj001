package com.example.williamsumitro.dress.view.view.product.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Discussion;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductDiscussionRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_discussions extends AppCompatActivity {
    @BindView(R.id.detailproductdiscussion_add) FloatingActionButton add;
    @BindView(R.id.detailproductdiscussion_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.detailproductdiscussion_toolbar) Toolbar toolbar;
    private Context context;
    private List<Discussion> discussionList;
    private DetailProductDiscussionRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_discussions);
        initView();
        setuptoolbar();
        initdata();
        setuprecyclerview();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct_discussionsnew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.fadeout);
                context.startActivity(intent);
            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        discussionList = new ArrayList<>();
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Discussion");
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
        adapter = new DetailProductDiscussionRVAdapter(discussionList, context);
        Discussion discussion = new Discussion(R.drawable.image, "Anna", "UserResponse", "20 March 2018", "Do you have black color ?", 4);
        discussionList.add(discussion);
        discussion = new Discussion(R.drawable.image, "Nina", "UserResponse", "20 March 2018", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?", 2);
        discussionList.add(discussion);
        discussion = new Discussion(R.drawable.image, "Dudi", "Seller", "20 March 2018", "Go buy it now", 0);
        discussionList.add(discussion);
    }
}
