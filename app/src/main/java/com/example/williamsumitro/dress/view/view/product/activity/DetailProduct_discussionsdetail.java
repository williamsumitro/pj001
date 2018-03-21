package com.example.williamsumitro.dress.view.view.product.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Discussion;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductDiscussionDetailRVAdapter;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductDiscussionRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProduct_discussionsdetail extends AppCompatActivity {
    @BindView(R.id.detailproductdiscussiondetails_tvproductname) TextView productname;
    @BindView(R.id.detailproductdiscussiondetails_toolbar) Toolbar toolbar;
    @BindView(R.id.detailproductdiscussiondetails_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.detailproductdiscussiondetails_linearlayout) LinearLayout linearLayout;
    @BindView(R.id.detailproductdiscussiondetails_imgsend) ImageView send;
    @BindView(R.id.detailproductdiscussiondetails_imgproduct) ImageView product;
    @BindView(R.id.detailproductdiscussiondetails_etcomment) EditText comment;
    @BindView(R.id.detailproductdiscussiondetails_layoutcontainer) LinearLayout container;

    private Context context;
    private List<Discussion> discussionList;
    private DetailProductDiscussionDetailRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_discussionsdetail);
        initView();
        setuptoolbar();
        initdata();
        setuprecyclerview();
    }
    private void initView(){
        ButterKnife.bind(this);
        discussionList = new ArrayList<>();
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Discussion Detail");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
        for(int i = 0; i <container.getChildCount(); i++){
            View view = container.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Authentication")
                            .setMessage("You must login first")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            });
        }


    }
    private void setuprecyclerview(){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void initdata(){
        adapter = new DetailProductDiscussionDetailRVAdapter(discussionList, context);
        Discussion discussion = new Discussion(R.drawable.image, "Anna", "Customer", "20 March 2018", "Do you have black color ?", 4);
        discussionList.add(discussion);
        discussion = new Discussion(R.drawable.image, "Nina", "Seller", "20 March 2018", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?", 2);
        discussionList.add(discussion);
        discussion = new Discussion(R.drawable.image, "Dudi", "Seller", "20 March 2018", "Go buy it now", 0);
        discussionList.add(discussion);
    }
}
