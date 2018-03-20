package com.example.williamsumitro.dress.view.view.product;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_details extends AppCompatActivity {
    @BindView(R.id.detailproductdetails_tvwaiseline) TextView waiseline;
    @BindView(R.id.detailproductdetails_tvstyle) TextView style;
    @BindView(R.id.detailproductdetails_tvsleeve) TextView sleeve;
    @BindView(R.id.detailproductdetails_tvsize) TextView size;
    @BindView(R.id.detailproductdetails_tvseason) TextView season;
    @BindView(R.id.detailproductdetails_tvneckline) TextView neckline;
    @BindView(R.id.detailproductdetails_tvfabrictype) TextView fabrictype;
    @BindView(R.id.detailproductdetails_tvdot) TextView dot;
    @BindView(R.id.detailproductdetails_tvdecoration) TextView decoration;
    @BindView(R.id.detailproductdetails_tvcolor) TextView color;
    @BindView(R.id.detailproductdetails_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproductdetails_tvmaterial) TextView material;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_details);
        initView();
        setuptoolbar();
    }
    private void initView(){
        ButterKnife.bind(this);
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Product Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
}
