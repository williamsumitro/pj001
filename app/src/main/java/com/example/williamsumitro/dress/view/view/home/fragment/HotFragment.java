package com.example.williamsumitro.dress.view.view.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bid;
import com.example.williamsumitro.dress.view.view.home.adapter.HotOpenBidRVAdapter;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.model.Cloth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {

    @BindView(R.id.hot_rvList) RecyclerView rvList;
    @BindView(R.id.hot_rv_OpenBid) RecyclerView rvOpenBid;
    @BindView(R.id.hot_btn_openbid) Button btnOpenBid;
    private HotOpenBidRVAdapter openbid_adapter;
    private List<Cloth> ListList = new ArrayList<>();
    private List<Bid> bidList = new ArrayList<>();
    private HotRVAdapter adapterList;

    public HotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(view);
        adapterList = new HotRVAdapter(ListList, 1, getContext());
        openbid_adapter = new HotOpenBidRVAdapter(bidList, getContext());
        RecyclerView.LayoutManager horizontal_layoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager grid_layoutmanager = new GridLayoutManager(getContext(), 2);
        rvOpenBid.setLayoutManager(horizontal_layoutmanager);
        rvOpenBid.setItemAnimator(new DefaultItemAnimator());
        rvOpenBid.setAdapter(openbid_adapter);

        rvList.setLayoutManager(grid_layoutmanager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapterList);
        initData();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
    }
    private void initData(){
        Cloth cloth = new Cloth("Express One Eleven Love And Wi-Fi Muscle Tank", R.drawable.image, "50.000");
        ListList.add(cloth);
        cloth = new Cloth("Eyelet Full Cotton Midi Skirt", R.drawable.image, "10.000");
        ListList.add(cloth);
        cloth = new Cloth("Side Tie Flared Skirt", R.drawable.image, "62.000");
        ListList.add(cloth);
        cloth = new Cloth("Stripe Ruched A-Line Dress", R.drawable.image, "40.000");
        ListList.add(cloth);
        cloth = new Cloth("High Waisted Windowpane A-Line Skirt", R.drawable.image, "32.000");
        ListList.add(cloth);
        cloth = new Cloth("High Waisted Striped Clean Pencil Skirt", R.drawable.image, "12.000");
        ListList.add(cloth);
        cloth = new Cloth("Ruched Sleeve Cutaway Blazer", R.drawable.image, "54.000");
        ListList.add(cloth);
        cloth = new Cloth("Express One Eleven Lace Inset Tee", R.drawable.image, "78.000");
        ListList.add(cloth);

        Bid bid = new Bid("Dress sexy", "Sexy Store", R.drawable.fake_dress1, 4,4,1,"2 days",true);
        bidList.add(bid);
        bid = new Bid("Party Dress", "Tutup Lapak", R.drawable.fake_dress2, 0,10,0,"4 hours left",false);
        bidList.add(bid);
        bid = new Bid("Dress seperti gambar dibawah ini geng", "KPOP Store", R.drawable.fake_dress6, 0,4,0,"1 days",true);
        bidList.add(bid);
        bid = new Bid("Terserah yang penting mirip kek gini", "Bunda Cinta", R.drawable.fake_dress3, 1,0,0,"5 days",false);
        bidList.add(bid);
        bid = new Bid("Dress Lingerie", "Sukses Sejati", R.drawable.fake_dress4, 2,5,1,"3 hours left",false);
        bidList.add(bid);
        bid = new Bid("Need Fast", "Terang Bulan", R.drawable.fake_dress5, 0,0,0,"1 days",true);
        bidList.add(bid);
    }
}
