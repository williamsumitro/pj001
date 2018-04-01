package com.example.williamsumitro.dress.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 02/03/2018.
 */

public class HotRVAdapter extends RecyclerView.Adapter<HotRVAdapter.ViewHolder> {
    private List<Cloth> clothList;
    private int choice, favoriteclick = 0;
    private Context context;
    public HotRVAdapter (List<Cloth> clothList, int choice, Context context){
        this.clothList = clothList;
        this.choice = choice;
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
        Cloth cloth = clothList.get(position);
        holder.name.setText(cloth.getName());
        holder.price.setText(cloth.getPrice());
//        Picasso.with(context).load(cloth.getPicture()).into(holder.image);
        holder.image.setImageResource(cloth.getPicture());
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
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.slideleft).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_newbrands_image) ImageView image;
        @BindView(R.id.item_newbrands_name) TextView name;
        @BindView(R.id.item_newbrands_price) TextView price;
        @BindView(R.id.item_newbrands_favorite) ImageView favorite;
        @BindView(R.id.item_newbrands_container) LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
