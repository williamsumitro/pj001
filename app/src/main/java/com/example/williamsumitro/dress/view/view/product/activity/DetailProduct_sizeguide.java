package com.example.williamsumitro.dress.view.view.product.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_sizeguide extends AppCompatActivity {
    @BindView(R.id.detailproductsize_tvsleevelengthxl) TextView sleevelengthXL;
    @BindView(R.id.detailproductsize_tvsleevelengths) TextView  sleevelengthS;
    @BindView(R.id.detailproductsize_tvsleevelengthm) TextView sleevelengthM;
    @BindView(R.id.detailproductsize_tvsleevelengthl) TextView sleevelengthL;
    @BindView(R.id.detailproductsize_tvshoulderxl) TextView shoulderXL;
    @BindView(R.id.detailproductsize_tvshoulders) TextView shoulderS;
    @BindView(R.id.detailproductsize_tvshoulderm) TextView shoulderM;
    @BindView(R.id.detailproductsize_tvshoulderl) TextView shoulderL;
    @BindView(R.id.detailproductsize_tvlengthxl) TextView lengthXL;
    @BindView(R.id.detailproductsize_tvlengths) TextView lengthS;
    @BindView(R.id.detailproductsize_tvlengthm) TextView lengthM;
    @BindView(R.id.detailproductsize_tvlengthl) TextView lengthL;
    @BindView(R.id.detailproductsize_tvcuffxl) TextView cuffXL;
    @BindView(R.id.detailproductsize_tvcuffs) TextView cuffS;
    @BindView(R.id.detailproductsize_tvcuffm) TextView cuffM;
    @BindView(R.id.detailproductsize_tvcuffl) TextView cuffL;
    @BindView(R.id.detailproductsize_tvbustxl) TextView bustXL;
    @BindView(R.id.detailproductsize_tvbusts) TextView bustS;
    @BindView(R.id.detailproductsize_tvbustm) TextView bustM;
    @BindView(R.id.detailproductsize_tvbustl) TextView bustL;
    @BindView(R.id.detailproductsize_tvbiceptlengthxl) TextView biceptlengthXL;
    @BindView(R.id.detailproductsize_tvbiceptlengths) TextView biceptlengthS;
    @BindView(R.id.detailproductsize_tvbiceptlengthm) TextView biceptlengthM;
    @BindView(R.id.detailproductsize_tvbiceptlengthl) TextView biceptlengthL;
    @BindView(R.id.detailproductsize_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproductsize_btninch) Button inch;
    @BindView(R.id.detailproductsize_btncm) Button cm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_sizeguide);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Size Guide");
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
