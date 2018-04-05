package com.example.williamsumitro.dress.view.view.store.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.example.williamsumitro.dress.view.view.store.activity.DetailOutlet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 01/04/2018.
 */

public class StoreRVAdapter extends RecyclerView.Adapter<StoreRVAdapter.ViewHolder> {
    private Context context;
    private List<Store> storeList;

    public StoreRVAdapter(List<Store> storeList, Context context) {
        this.storeList = storeList;
        this.context = context;
    }

    @Override
    public StoreRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(size);
            }
        }catch (NoSuchMethodError error){
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(width/3.5), LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lpimage = new LinearLayout.LayoutParams((int)(width/3.5) - 5, (int)(width/3.5) - 5);
        lp.setMargins(5,5,5,5);

        holder.container.setLayoutParams(lp);
        holder.image.setLayoutParams(lpimage);
        holder.container.setForegroundGravity(Gravity.CENTER);
        Store store = storeList.get(position);
        holder.image.setImageResource(store.getImage());
        holder.name.setText(store.getName());
        if (store.getRate() == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(store.getRate()>0 && store.getRate()<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (store.getRate() == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(store.getRate()>1 && store.getRate()<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (store.getRate() == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(store.getRate()>2 && store.getRate()<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (store.getRate() == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(store.getRate()>3 && store.getRate()<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (store.getRate() == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(store.getRate()>4 && store.getRate()<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (store.getRate() == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemstore_container) CardView container;
        @BindView(R.id.itemstore_image) ImageView image;
        @BindView(R.id.itemstore_name) TextView name;
        @BindView(R.id.itemstore_imgstar1) ImageView star1;
        @BindView(R.id.itemstore_imgstar2) ImageView star2;
        @BindView(R.id.itemstore_imgstar3) ImageView star3;
        @BindView(R.id.itemstore_imgstar4) ImageView star4;
        @BindView(R.id.itemstore_imgstar5) ImageView star5;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailOutlet.class);
                    Bundle bundle = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                        context.startActivity(intent, bundle);
                    }
                }
            });
        }
    }
}
