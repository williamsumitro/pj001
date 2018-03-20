package com.example.williamsumitro.dress.view;


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
import com.example.williamsumitro.dress.view.model.Cloth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {

    @BindView(R.id.hot_rvNew) RecyclerView rvnew;
    @BindView(R.id.hot_rvTop) RecyclerView rvtop;
    @BindView(R.id.hot_rvList) RecyclerView rvList;
    @BindView(R.id.hot_btnSeeNew) Button seenew;

    private List<Cloth> newList = new ArrayList<>();
    private List<Cloth> topList = new ArrayList<>();
    private List<Cloth> ListList = new ArrayList<>();
    private HotRVAdapter adapternew;
    private HotRVAdapter adaptertop;
    private HotRVAdapter adapterList;

    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(view);
        adapternew = new HotRVAdapter(newList, 1, getContext());
        adaptertop = new HotRVAdapter(topList, 1, getContext());
        adapterList = new HotRVAdapter(ListList, 1, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        rvnew.setLayoutManager(layoutManager);
        rvnew.setItemAnimator(new DefaultItemAnimator());
        rvnew.setAdapter(adapternew);
        rvtop.setLayoutManager(layoutManager1);
        rvtop.setItemAnimator(new DefaultItemAnimator());
        rvtop.setAdapter(adaptertop);
        rvList.setLayoutManager(layoutManager2);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setAdapter(adapterList);
        initData();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
    }
    private void initData(){
        Cloth cloth = new Cloth("Sequin Tank Sheath Dress", R.drawable.fake, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Striped Wrap Midi Dress", R.drawable.fake, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Long Sleeve Pocket Shirt Dress", R.drawable.fake, "10.000.000");
        newList.add(cloth);
        cloth = new Cloth("Off The Shoulder Lace Sheath Dress", R.drawable.fake, "9.000.000");
        newList.add(cloth);
        cloth = new Cloth("Sweetheart Neckline Sheath Dress", R.drawable.fake, "8.300.000");
        newList.add(cloth);
        cloth = new Cloth("SStrappy Back Lace Inset Sheath Dress", R.drawable.fake, "14.000.000");
        newList.add(cloth);
        cloth = new Cloth("STie-Sleeve Shift Dress", R.drawable.fake, "2.000.000");
        newList.add(cloth);

        cloth = new Cloth("Ruffle Cold Shoulder Maxi Dress", R.drawable.fake, "120.000");
        topList.add(cloth);
        cloth = new Cloth("Belted Surplice Jumpsuit", R.drawable.fake, "110.000");
        topList.add(cloth);
        cloth = new Cloth("Smocked Off The Shoulder Dress", R.drawable.fake, "1.000.000");
        topList.add(cloth);
        cloth = new Cloth("Off The Shoulder Lace Sheath Dress", R.drawable.fake, "9.000.000");
        topList.add(cloth);
        cloth = new Cloth("Sweetheart Neckline Sheath Dress", R.drawable.fake, "8.300.000");
        topList.add(cloth);

        cloth = new Cloth("Express One Eleven Love And Wi-Fi Muscle Tank", R.drawable.fake, "50.000");
        ListList.add(cloth);
        cloth = new Cloth("Eyelet Full Cotton Midi Skirt", R.drawable.fake, "10.000");
        ListList.add(cloth);
        cloth = new Cloth("Side Tie Flared Skirt", R.drawable.fake, "62.000");
        ListList.add(cloth);
        cloth = new Cloth("Stripe Ruched A-Line Dress", R.drawable.fake, "40.000");
        ListList.add(cloth);
        cloth = new Cloth("High Waisted Windowpane A-Line Skirt", R.drawable.fake, "32.000");
        ListList.add(cloth);
        cloth = new Cloth("High Waisted Striped Clean Pencil Skirt", R.drawable.fake, "12.000");
        ListList.add(cloth);
        cloth = new Cloth("Ruched Sleeve Cutaway Blazer", R.drawable.fake, "54.000");
        ListList.add(cloth);
        cloth = new Cloth("Express One Eleven Lace Inset Tee", R.drawable.fake, "78.000");
        ListList.add(cloth);
    }
}
