package com.example.williamsumitro.dress.view.view.category.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.category.activity.CategoryDetail;
import com.example.williamsumitro.dress.view.view.filter.Filter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private Context context;
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initObject(view);
        initOnClick();
        return view;
    }

    private void initObject(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
    }
    private void initOnClick(){
    }

}
