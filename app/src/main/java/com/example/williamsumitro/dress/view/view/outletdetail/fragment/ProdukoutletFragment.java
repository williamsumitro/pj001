package com.example.williamsumitro.dress.view.view.outletdetail.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.HotRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukoutletFragment extends Fragment {
    @BindView(R.id.produkoutlet_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.produkoutlet_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.produkoutlet_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private List<Cloth> newList = new ArrayList<>();
    private HotRVAdapter adapternew;


    public ProdukoutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produkoutlet, container, false);
        initObject(view);
        initData();
        initRefresh();
        return view;
    }
    private void initObject(View view){

        ButterKnife.bind(this, view);

    }
    private void initRefresh(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapternew);
        adapternew.notifyDataSetChanged();
    }
    private void initData(){
        adapternew = new HotRVAdapter(newList, 1, getContext());
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
    }
}
