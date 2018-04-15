package com.example.williamsumitro.dress.view.view.wishlist.fragment;


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
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.view.wishlist.adapter.WishlistRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {
    @BindView(R.id.wishlist_rv) RecyclerView recyclerView;
    private Context context;
    private WishlistRVAdapter adapter;
    private List<Cloth> newList = new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initView(view);
        initData();
        setuprv();
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this,view);
        context = getActivity();
    }
    private void initData(){
        adapter = new WishlistRVAdapter(newList, context);
        Cloth cloth = new Cloth("Sequin Tank Sheath Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Striped Wrap Midi Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Long Sleeve Pocket Shirt Dress", R.drawable.image, "10.000.000");
        newList.add(cloth);
        cloth = new Cloth("Off The Shoulder Lace Sheath Dress", R.drawable.image, "9.000.000");
        newList.add(cloth);
    }
    private void setuprv() {
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
