package com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment;


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
import com.example.williamsumitro.dress.view.model.Review;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.adapter.UlasanoutletRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UlasanoutletFragment extends Fragment {
    @BindView(R.id.ulasanoutlet_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.ulasanoutlet_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.ulasanoutlet_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    private List<Review> reviewList = new ArrayList<>();
    private UlasanoutletRVAdapter adapter;

    public UlasanoutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ulasanoutlet, container, false);
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
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initData(){
        adapter = new UlasanoutletRVAdapter(reviewList, getContext());
        Review review = new Review("Krenny", "Stephen Hawking", "Mantap", "1 year ago", R.drawable.image, R.drawable.image, 5);
        reviewList.add(review);
        review = new Review("Krenny", "Bill Gates", "Barangnya sesuai dengan yang digambar", "8 months ago", R.drawable.image, R.drawable.image, 2.9);
        reviewList.add(review);
        review = new Review("Krenny", "Warren Buffet", "Ukuran Pas", "4 months ago", R.drawable.image, R.drawable.image, 3.5);
        reviewList.add(review);
        review = new Review("Krenny", "Jack Ma", "Boleh lah", "3 months ago", R.drawable.image, R.drawable.image, 4.7);
        reviewList.add(review);
        review = new Review("Krenny", "Elon Musk", "Nice", "2 months ago", R.drawable.image, R.drawable.image, 4);
        reviewList.add(review);
    }
}
