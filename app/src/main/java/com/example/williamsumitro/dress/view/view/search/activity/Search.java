package com.example.williamsumitro.dress.view.view.search.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.BestResponse;
import com.example.williamsumitro.dress.view.model.Filter_Model;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.purchase.history.adapter.PurchaseHistory_RV;
import com.example.williamsumitro.dress.view.view.search.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
    @BindView(R.id.search_toolbar) Toolbar toolbar;
    @BindView(R.id.search_rv) RecyclerView rv;
    @BindView(R.id.search_ln_bottom) LinearLayout bottom;
    @BindView(R.id.search_ln_top) LinearLayout top;
    @BindView(R.id.search_lnfilter) LinearLayout filter;
    @BindView(R.id.search_lnsort) LinearLayout sort;
    @BindView(R.id.search_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Filter_Model> filter_modelList;
    private Context context;
    private apiService service;
    private ArrayList<ProductInfo> searchList;
    private HotRVAdapter adapter;
    private String productname = "";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setuptoolbar();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        if (productname.equals("")){
            swipeRefreshLayout.setRefreshing(false);
        }
        else {
            api_getsearch(productname);
        }
    }
    private void initView(){
        ButterKnife.bind(this);
        filter_modelList = new ArrayList<>();
        context = this;
        progressDialog = new ProgressDialog(this);
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
                productname = s;
                api_getsearch(productname);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void api_getsearch(String prodcut_name){
        service = apiUtils.getAPIService();
        service.req_search(prodcut_name).enqueue(new Callback<BestResponse>() {
            @Override
            public void onResponse(Call<BestResponse> call, Response<BestResponse> response) {
                if (response.code() == 200){
                    searchList = response.body().getProductInfo();
                    if (searchList.size()>0){
                        bottom.setVisibility(View.VISIBLE);
                        top.setVisibility(View.GONE);
                        setupRV();
                        swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                    }
                    else {
                        top.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BestResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void setupRV(){
        adapter = new HotRVAdapter(searchList, context);
        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(grid_layoutmanager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }
//    private void initData(){
//        adapter = new SearchAdapter(filter_modelList, context);
//        Filter_Model filter_model = new Filter_Model("Sexy");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Lingerie");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Paris");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Proud");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Nice");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Calvin Klein");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Victoria Secret");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Shein");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Asos");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Another");
//        filter_modelList.add(filter_model);
//        filter_model = new Filter_Model("Day");
//        filter_modelList.add(filter_model);
//        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL,false ));
//        rv.setItemAnimator(new DefaultItemAnimator());
//        rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id==R.id.search_search){
//
//        }
//    }
}
