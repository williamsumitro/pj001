package com.example.williamsumitro.dress.view.view.home.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.home.adapter.TabHomeAdapter;
import com.example.williamsumitro.dress.view.view.category.fragment.CategoryFragment;
import com.example.williamsumitro.dress.view.view.openstore.fragment.StoreFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.home_tabs) TabLayout tabLayout;
    @BindView(R.id.home_viewpager) ViewPager viewPager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        setupVP(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);

    }
    private void setupVP(ViewPager viewPager){
        TabHomeAdapter adapter = new TabHomeAdapter(getChildFragmentManager());
        adapter.addFragment(new HotFragment(), "Hot");
        adapter.addFragment(new CategoryFragment(), "Category");
        adapter.addFragment(new StoreFragment(), "Outlet");
        viewPager.setAdapter(adapter);
    }


}
