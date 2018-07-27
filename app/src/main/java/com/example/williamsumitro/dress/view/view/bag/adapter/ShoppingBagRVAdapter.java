package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class ShoppingBagRVAdapter extends RecyclerView.Adapter<ShoppingBagRVAdapter.ViewHolder> {
    private ArrayList<Bag> bagList;
    private Context context;
    private DecimalFormat formatter;
    private boolean open = false;
    private ShoppingBagProductRVAdapter adapter;
    private SnapHelper snapHelper = new LinearSnapHelper();
    private ShoppingBag shoppingBag;

    public ShoppingBagRVAdapter(ArrayList<Bag> bagList, Context context, ShoppingBag shoppingBag){
        this.context = context;
        this.bagList = bagList;
        this.shoppingBag = shoppingBag;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppingbag, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position%5==0){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.brown9));
        }
        else if (position%5==1){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.blue9));
        }
        else if (position%5==2){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.red9));
        }
        else if (position%5==3){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.orange9));
        }
        else if (position%5==4){
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.purple9));
        }
        Bag bag = bagList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.storename.setText(bag.getStoreName());
        adapter = new ShoppingBagProductRVAdapter(bagList.get(position).getProduct(), context, shoppingBag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.rv.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        holder.rv.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(holder.rv);
    }

    @Override
    public int getItemCount() {
        return bagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemshoppingbag_cardview) CardView cardView;
        @BindView(R.id.itemshoppingbag_rv) RecyclerView rv;
        @BindView(R.id.itemshoppingbag_tv_storename) TextView storename;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
