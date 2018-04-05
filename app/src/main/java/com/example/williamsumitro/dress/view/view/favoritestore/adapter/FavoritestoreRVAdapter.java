package com.example.williamsumitro.dress.view.view.favoritestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Store;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class FavoritestoreRVAdapter extends RecyclerView.Adapter<FavoritestoreRVAdapter.ViewHolder> {

    private Context context;
    private List<Store> storeList;
    public FavoritestoreRVAdapter(List<Store> storeList, Context context){
        this.context = context;
        this.storeList = storeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoritestore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.storeimage.setImageResource(store.getImage());
        holder.storename.setText(store.getName());
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
        @BindView(R.id.itemfavoritestore_imgdelete) ImageView delete;
        @BindView(R.id.itemfavoritestore_imgstar1) ImageView star1;
        @BindView(R.id.itemfavoritestore_imgstar2) ImageView star2;
        @BindView(R.id.itemfavoritestore_imgstar3) ImageView star3;
        @BindView(R.id.itemfavoritestore_imgstar4) ImageView star4;
        @BindView(R.id.itemfavoritestore_imgstar5) ImageView star5;
        @BindView(R.id.itemfavoritestore_tvname) TextView storename;
        @BindView(R.id.itemfavoritestore_imgStore) ImageView storeimage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
