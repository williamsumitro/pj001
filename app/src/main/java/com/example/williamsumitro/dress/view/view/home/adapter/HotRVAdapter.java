package com.example.williamsumitro.dress.view.view.home.adapter;

import android.app.ActivityOptions;
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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 02/03/2018.
 */

public class HotRVAdapter extends RecyclerView.Adapter<HotRVAdapter.ViewHolder>{
    private List<ProductInfo> productInfoList;
    private int favoriteclick = 0;
    private Context context;
    private List<Price> priceList = new ArrayList<>();
    public HotRVAdapter (List<ProductInfo> productInfoList, Context context){
        this.productInfoList = productInfoList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newbrands, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProductInfo productInfo = productInfoList.get(position);
        holder.name.setText(productInfo.getProductName());
        priceList = productInfo.getPrice();
        holder.price.setText(String.valueOf(priceList.get(0).getPrice()));
        Picasso.with(context)
                .load(productInfo.getPhoto())
                .placeholder(R.drawable.logo)
                .into(holder.image);
        holder.storename.setText(productInfo.getStoreName());
//        Picasso.with(context).load(cloth.getPicture()).into(holder.image);
//        holder.image.setImageResource(cloth.getPicture());
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoriteclick == 0){
                    holder.favorite.setImageResource(R.drawable.like);
                    favoriteclick = 1;
                }
                else {
                    holder.favorite.setImageResource(R.drawable.unlike);
                    favoriteclick = 0;
                }
            }
        });
        if (productInfo.getRating() == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(productInfo.getRating()>0 && productInfo.getRating()<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (productInfo.getRating() == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(productInfo.getRating()>1 && productInfo.getRating()<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (productInfo.getRating() == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(productInfo.getRating()>2 && productInfo.getRating()<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (productInfo.getRating() == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(productInfo.getRating()>3 && productInfo.getRating()<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (productInfo.getRating() == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(productInfo.getRating()>4 && productInfo.getRating()<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (productInfo.getRating() == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productInfoList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_newbrands_image) ImageView image;
        @BindView(R.id.item_newbrands_name) TextView name;
        @BindView(R.id.item_newbrands_price) TextView price;
        @BindView(R.id.item_newbrands_favorite) ImageView favorite;
        @BindView(R.id.item_newbrands_container) CardView container;
        @BindView(R.id.item_newbrands_storename) TextView storename;
        @BindView(R.id.item_newbrands_star1) ImageView star1;
        @BindView(R.id.item_newbrands_star2) ImageView star2;
        @BindView(R.id.item_newbrands_star3) ImageView star3;
        @BindView(R.id.item_newbrands_star4) ImageView star4;
        @BindView(R.id.item_newbrands_star5) ImageView star5;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
