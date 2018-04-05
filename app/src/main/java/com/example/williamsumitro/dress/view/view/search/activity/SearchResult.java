package com.example.williamsumitro.dress.view.view.search.activity;

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

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HotRVAdapter;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResult extends AppCompatActivity {
    private final static String FILTER = "FILTER";
    @BindView(R.id.searchresult_lnfilter) LinearLayout filter;
    @BindView(R.id.searchresult_lnsort) LinearLayout sort;
    @BindView(R.id.searchresult_rv) RecyclerView recyclerView;
    @BindView(R.id.searchresult_toolbar) Toolbar toolbar;
    private Context context;
    private String extrafilter;
    private List<Cloth> newList = new ArrayList<>();
    private HotRVAdapter adapternew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
        initGetIntent();
        setuptoolbar();
        initData();
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
        getSupportActionBar().setTitle(extrafilter);
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
    private void initGetIntent(){
        Intent getIntent = getIntent();
        if (getIntent.hasExtra(FILTER)) {
            extrafilter = getIntent.getExtras().getString(FILTER);
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailsearch, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button1, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.detailsearch_search){
            Intent intent = new Intent(this, Search.class);
            initanim(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
        context.startActivity(intent);
    }
}
