package com.example.williamsumitro.dress.view.view.favoritestore.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.FavoriteResponse;
import com.example.williamsumitro.dress.view.model.FavoriteResult;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.model.WishlistResponse;
import com.example.williamsumitro.dress.view.model.WishlistResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.favoritestore.adapter.FavoriteStoreRVTouch;
import com.example.williamsumitro.dress.view.view.favoritestore.adapter.FavoritestoreRVAdapter;
import com.example.williamsumitro.dress.view.view.wishlist.adapter.WishlistRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteStoreFragment extends Fragment implements FavoriteStoreRVTouch.FavoriteStoreTouchListener {
    @BindView(R.id.favorite_rv) RecyclerView recyclerView;
    @BindView(R.id.favorite_ln_top) LinearLayout top;
    @BindView(R.id.favorite_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.favorite_container) LinearLayout container;

    private Context context;
    private FavoritestoreRVAdapter adapter;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<FavoriteResult> favoriteResults;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;


    public FavoriteStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_store, container, false);
        initView(view);
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
        return view;
    }
    private void initRefresh(){
        swipeRefreshLayout.setRefreshing(true);
        api_getfavoritestore();
    }
    private void api_getfavoritestore(){
        service = apiUtils.getAPIService();
        service.req_my_favorite(token).enqueue(new Callback<FavoriteResponse>() {
            @Override
            public void onResponse(Call<FavoriteResponse> call, Response<FavoriteResponse> response) {
                if (response.code() == 200){
                    if (response.body().getResult().size() >0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        favoriteResults = response.body().getResult();
                        setupRV();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    else {
                        top.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<FavoriteResponse> call, Throwable t) {
                initDialog("",3);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void initView(View view) {
        ButterKnife.bind(this,view);
        context = getActivity();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        favoriteResults = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
    }
    private void setupRV(){
        adapter = new FavoritestoreRVAdapter(favoriteResults, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(alphaAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new FavoriteStoreRVTouch(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoritestoreRVAdapter.ViewHolder) {
            final int index = viewHolder.getAdapterPosition();
            final String name = favoriteResults.get(index).getName();
            final FavoriteResult favoriteResult = favoriteResults.get(index);

            adapter.removeItem(index);
            Snackbar snackbar = Snackbar
                    .make(container, name + " removed from wishlist!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    private void initDialog(final String message, int stats){
        if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
}
