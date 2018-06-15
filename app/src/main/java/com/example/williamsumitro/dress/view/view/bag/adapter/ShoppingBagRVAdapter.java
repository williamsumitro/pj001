package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.model.Bagz;
import com.example.williamsumitro.dress.view.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class ShoppingBagRVAdapter extends RecyclerView.Adapter<ShoppingBagRVAdapter.ViewHolder> {
    private ArrayList<Bag> bagList;
    private Context context;
    private DecimalFormat formatter;
    private boolean open = false;
    private ShoppingBagProductRVAdapter adapter;

    public ShoppingBagRVAdapter(ArrayList<Bag> bagList, Context context){
        this.context = context;
        this.bagList = bagList;
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
        adapter = new ShoppingBagProductRVAdapter(bagList.get(position).getProduct(), context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.rv.setLayoutManager(layoutManager);
        holder.rv.setItemAnimator(new DefaultItemAnimator());
        holder.rv.setAdapter(adapter);
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
