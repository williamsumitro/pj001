package com.example.williamsumitro.dress.view.view.sellerpanel.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.openstore.activity.OpenStore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoStoreFragment extends Fragment {
    @BindView(R.id.no_store_btn_openstore) Button openstore;
    private Context context;

    public NoStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_store, container, false);
        initView(view);
        initOnClick();
        return view;
    }

    private void initOnClick() {
        openstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpenStore.class);
                initanim(intent);
            }
        });
    }

    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
    }

    private void initanim(Intent intent){
        Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
        context.startActivity(intent, bundle);
    }
}
