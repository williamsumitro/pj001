package com.example.williamsumitro.dress.view.view.search.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Filter_Model;
import com.example.williamsumitro.dress.view.view.search.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Search extends AppCompatActivity {
    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_rv) RecyclerView rv;
    private SearchAdapter adapter;
    private ArrayList<Filter_Model> filter_modelList;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setuptoolbar();
        initData();
    }
    private void initView(){
        ButterKnife.bind(this);
        filter_modelList = new ArrayList<>();
        context = this;
    }

    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setTitle("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.onActionViewExpanded();
        item.expandActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Type the product you want to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void initData(){
        adapter = new SearchAdapter(filter_modelList, context);
        Filter_Model filter_model = new Filter_Model("Sexy");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Lingerie");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Paris");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Proud");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Nice");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Calvin Klein");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Victoria Secret");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Shein");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Asos");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Another");
        filter_modelList.add(filter_model);
        filter_model = new Filter_Model("Day");
        filter_modelList.add(filter_model);
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL,false ));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id==R.id.search_search){
//
//        }
//    }
}
