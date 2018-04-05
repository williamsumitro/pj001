package com.example.williamsumitro.dress.view.view.wishlist.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.ViewHolder> {
    private List<Cloth> clothList;
    private Context context;

    public WishlistRVAdapter(List<Cloth> clothList, Context context){
        this.clothList = clothList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cloth cloth = clothList.get(position);
        holder.name.setText(cloth.getName());
        holder.price.setText(cloth.getPrice());
//        Picasso.with(context).load(cloth.getPicture()).into(holder.image);
        holder.imageView.setImageResource(cloth.getPicture());
    }

    @Override
    public int getItemCount() {
        return clothList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemwishlist_btnAddToBag) Button addtobag;
        @BindView(R.id.itemwishlist_price) TextView price;
        @BindView(R.id.itemwishlist_name) TextView name;
        @BindView(R.id.itemwishlist_image) ImageView imageView;
        @BindView(R.id.itemwishlist_btnDelete) ImageView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailProduct.class);
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
