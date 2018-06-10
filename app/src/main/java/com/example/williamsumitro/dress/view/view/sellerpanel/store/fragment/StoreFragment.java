package com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.adapter.StoreRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    @BindView(R.id.store_recyclerview) RecyclerView recyclerView;
    private Context context;
    private List<Store> storeList;
    private StoreRVAdapter adapter;


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
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
        adapter = new StoreRVAdapter(storeList, context);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
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
        store =  new Store("HP tiam", R.drawable.image, 5);
        storeList.add(store);
        store =  new Store("Kaskas", R.drawable.image, 4.1);
        storeList.add(store);
        store =  new Store("Dummy1", R.drawable.image, 2.4);
        storeList.add(store);
        store =  new Store("Loks", R.drawable.image, 2);
        storeList.add(store);
        store =  new Store("God must have spent a little more on you", R.drawable.image, 3);
        storeList.add(store);
        store =  new Store("Fake Store", R.drawable.image, 1);
        storeList.add(store);
    }

}
