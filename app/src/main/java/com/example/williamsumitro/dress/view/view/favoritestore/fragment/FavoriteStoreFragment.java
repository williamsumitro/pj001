package com.example.williamsumitro.dress.view.view.favoritestore.fragment;


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

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.view.favoritestore.adapter.FavoritestoreRVAdapter;
import com.example.williamsumitro.dress.view.view.store.adapter.StoreRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteStoreFragment extends Fragment {

    @BindView(R.id.favoritestore_rv) RecyclerView recyclerView;
    private Context context;
    private ArrayList<Store> storeList;
    private FavoritestoreRVAdapter adapter;
    public FavoriteStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_store, container, false);
        initView(view);
        initData();
        setupRV();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        storeList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new FavoritestoreRVAdapter(storeList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void initData(){
        Store store = new Store("ABC Store", R.drawable.image, 4.2);
        storeList.add(store);
        store =  new Store("Tek Store", R.drawable.image, 3);
        storeList.add(store);
        store =  new Store("Pikach", R.drawable.image, 2.4);
        storeList.add(store);
        store =  new Store("Blunt", R.drawable.image, 2);
        storeList.add(store);
        store =  new Store("Jamsf", R.drawable.image, 5);
        storeList.add(store);
    }

}
