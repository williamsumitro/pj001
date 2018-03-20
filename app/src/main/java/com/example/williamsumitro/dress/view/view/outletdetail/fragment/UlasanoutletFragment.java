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
import com.example.williamsumitro.dress.view.model.Review;
import com.example.williamsumitro.dress.view.view.outletdetail.adapter.UlasanoutletRVAdapter;

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
        Review review = new Review("Krenny", "Stephen Hawking", "Asyikkk masih perawan hehehe", "1 year ago", R.drawable.fake, R.drawable.fakeuser1, R.drawable.star5);
        reviewList.add(review);
        review = new Review("Krenny", "Bill Gates", "Bah gak perawan lagi, kecewa kali njirrr", "8 months ago", R.drawable.fake, R.drawable.fakeuser2, R.drawable.star1_2);
        reviewList.add(review);
        review = new Review("Krenny", "Warren Buffet", "Pffttt aku gak sanggup narik nya coeg", "4 months ago", R.drawable.fake, R.drawable.fakeuser3, R.drawable.star3);
        reviewList.add(review);
        review = new Review("Krenny", "Jack Ma", "Boleh lah, lebih bagus dibanding yang cina", "3 months ago", R.drawable.fake, R.drawable.fakeuser4, R.drawable.star4);
        reviewList.add(review);
        review = new Review("Krenny", "Elon Musk", "Nice", "2 months ago", R.drawable.fake, R.drawable.fakeuser5, R.drawable.star5);
        reviewList.add(review);
    }
}
