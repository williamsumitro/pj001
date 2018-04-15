package com.example.williamsumitro.dress.view.view.store.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.store.adapter.DetailOutletVPAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailOutlet extends AppCompatActivity {
    @BindView(R.id.detailoutlet_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailoutlet_viewpager) ViewPager viewPager;
    @BindView(R.id.detailoutlet_tvNamaToko) TextView namatoko;
    @BindView(R.id.detailoutlet_Toolbar) Toolbar toolbar;
    @BindView(R.id.detailoutlet_tablayout) TabLayout tabLayout;
    @BindView(R.id.detailoutlet_imgBanner) ImageView banner;
    @BindView(R.id.detailoutlet_btnFollow) Button follow;
    @BindView(R.id.detailoutlet_imgAveragerating) ImageView averagerating;
    @BindView(R.id.detailoutlet_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailoutlet_circleLogo) CircleImageView circleImageView;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_detail);
        initObject();
        initCollapToolbar();
        setupToolbar();
        setupViewPager();
    }
    private void initObject() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle("Nama Toko");
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupViewPager(){
        viewPager.setAdapter(new DetailOutletVPAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
}
