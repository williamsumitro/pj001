package com.example.williamsumitro.dress.view.view.sellerpanel.sales.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OCFragment extends Fragment {
    @BindView(R.id.orderconfirmation_rv) RecyclerView recyclerView;

    public OCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_confirmation, container, false);
        initView(view);
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this,view);
    }

}
