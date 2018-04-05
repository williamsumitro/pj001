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

    @BindView(R.id.category_lncategory1) LinearLayout category1;
    @BindView(R.id.category_lncategory2) LinearLayout category2;
    @BindView(R.id.category_lncategory3) LinearLayout category3;
    @BindView(R.id.category_lncategory4) LinearLayout category4;
    @BindView(R.id.category_lncategory5) LinearLayout category5;
    @BindView(R.id.category_lncategory6) LinearLayout category6;
    @BindView(R.id.category_lncategory7) LinearLayout category7;
    @BindView(R.id.category_lncategory8) LinearLayout category8;
    @BindView(R.id.category_lncategory9) LinearLayout category9;
    @BindView(R.id.category_lncategory10) LinearLayout category10;
    @BindView(R.id.category_lncategory11) LinearLayout category11;
    @BindView(R.id.category_lncategory12) LinearLayout category12;
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
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
        category12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryDetail.class);
                context.startActivity(intent);
            }
        });
    }

}
