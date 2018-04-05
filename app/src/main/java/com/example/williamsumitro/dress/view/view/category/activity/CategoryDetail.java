package com.example.williamsumitro.dress.view.view.category.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HotRVAdapter;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.search.activity.Search;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryDetail extends AppCompatActivity {
    @BindView(R.id.categorydetail_lnfilter) LinearLayout filter;
    @BindView(R.id.categorydetail_lnsort) LinearLayout sort;
    @BindView(R.id.categorydetail_rv) RecyclerView recyclerView;
    @BindView(R.id.categorydetail_toolbar) Toolbar toolbar;
    @BindView(R.id.categorydetail_searchview) SearchView searchView;
    private Context context;
    private List<Cloth> newList = new ArrayList<>();
    private HotRVAdapter adapternew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);initView();
        setuptoolbar();
        initData();
        initSearch();
        setuprv();
    }

    private void setuprv() {
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapternew);
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
        getSupportActionBar().setTitle("Category Detail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        adapternew = new HotRVAdapter(newList, 1, context);
        Cloth cloth = new Cloth("Sequin Tank Sheath Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Striped Wrap Midi Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Long Sleeve Pocket Shirt Dress", R.drawable.image, "10.000.000");
        newList.add(cloth);
        cloth = new Cloth("Off The Shoulder Lace Sheath Dress", R.drawable.image, "9.000.000");
        newList.add(cloth);
        cloth = new Cloth("Sweetheart Neckline Sheath Dress", R.drawable.image, "8.300.000");
        newList.add(cloth);
        cloth = new Cloth("SStrappy Back Lace Inset Sheath Dress", R.drawable.image, "14.000.000");
        newList.add(cloth);
        cloth = new Cloth("STie-Sleeve Shift Dress", R.drawable.image, "2.000.000");
        newList.add(cloth);
    }

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
    private void initSearch(){
        searchView.setQueryHint("Search your product here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapternew.getFilter().filter(newText);
                return false;
            }
        });

    }
}
