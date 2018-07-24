package com.example.williamsumitro.dress.view.view.product.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 24/07/2018.
 */

public class MyProductRV extends RecyclerView.Adapter<MyProductRV.ViewHolder> {
    private ArrayList<Product> products;
    private int favoriteclick = 0;
    private Context context;
    private DecimalFormat formatter;
    private SessionManagement sessionManagement;
    private ArrayList<Price> priceList = new ArrayList<>();
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private Boolean detailclick = false, wishliststatus= false;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    public MyProductRV(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }
    @Override
    public MyProductRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myproduct, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyProductRV.ViewHolder holder, int position) {
        final Product product = products.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.name.setText(product.getProductName());
        priceList = product.getPrice();
        Picasso.with(context)
                .load(product.getPhoto())
                .resize(180, 200)
                .placeholder(R.drawable.logo404)
                .into(holder.image);
        holder.storename.setText(product.getStoreName());
        get_rating(product, holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, product.getProductId().toString());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    private void get_rating(Product product, ViewHolder holder){
        if (product.getAverageRating() != null){
            if (Double.parseDouble(product.getAverageRating()) == 0){
                holder.star1.setImageResource(R.drawable.star0);
                holder.star2.setImageResource(R.drawable.star0);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
            else if(Double.parseDouble(product.getAverageRating())>0 && Double.parseDouble(product.getAverageRating())<1){
                holder.star1.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 1){
                holder.star1.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>1 && Double.parseDouble(product.getAverageRating())<2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>2 && Double.parseDouble(product.getAverageRating())<3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>3 && Double.parseDouble(product.getAverageRating())<4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>4 && Double.parseDouble(product.getAverageRating())<5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star);
            }
        }
        if (product.getAverageRating()!=null){
            if (Double.parseDouble(product.getAverageRating()) == 0){
                holder.star1.setImageResource(R.drawable.star0);
                holder.star2.setImageResource(R.drawable.star0);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
            else if(Double.parseDouble(product.getAverageRating())>0 && Double.parseDouble(product.getAverageRating())<1){
                holder.star1.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 1){
                holder.star1.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>1 && Double.parseDouble(product.getAverageRating())<2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>2 && Double.parseDouble(product.getAverageRating())<3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>3 && Double.parseDouble(product.getAverageRating())<4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>4 && Double.parseDouble(product.getAverageRating())<5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star);
            }
        }

    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_myproduct_image)
        ImageView image;
        @BindView(R.id.item_myproduct_name)
        TextView name;
        @BindView(R.id.item_myproduct_container)
        CardView container;
        @BindView(R.id.item_myproduct_storename) TextView storename;
        @BindView(R.id.item_myproduct_star1) ImageView star1;
        @BindView(R.id.item_myproduct_star2) ImageView star2;
        @BindView(R.id.item_myproduct_star3) ImageView star3;
        @BindView(R.id.item_myproduct_star4) ImageView star4;
        @BindView(R.id.item_myproduct_star5) ImageView star5;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
