package com.example.williamsumitro.dress.view.view.wishlist.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.model.WishlistResponse;
import com.example.williamsumitro.dress.view.model.WishlistResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.wishlist.adapter.WishlistRVAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {
    @BindView(R.id.wishlist_rv) RecyclerView recyclerView;
    @BindView(R.id.wishlist_ln_bottom) LinearLayout bottom;
    @BindView(R.id.wishlist_ln_top) LinearLayout top;
    private Context context;
    private WishlistRVAdapter adapter;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ArrayList<WishlistResult> wishlistResultArrayList;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initView(view);
        api_getwishlist();
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);
        context = getActivity();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        wishlistResultArrayList = new ArrayList<>();
    }
    private void api_getwishlist(){
        service = apiUtils.getAPIService();
        service.req_get_my_wishlist(token).enqueue(new Callback<WishlistResponse>() {
            @Override
            public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                if (response.code() == 200){
                    if (response.body().getWishlistResult().size() >0) {
                        bottom.setVisibility(View.VISIBLE);
                        wishlistResultArrayList = response.body().getWishlistResult();
                        setuprv();
                    }
                }
            }

            @Override
            public void onFailure(Call<WishlistResponse> call, Throwable t) {

            }
        });
    }
    private void setuprv() {
        adapter = new WishlistRVAdapter(wishlistResultArrayList, context);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
