package com.example.williamsumitro.dress.view.view.favoritestore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        if (position%10==0){
            holder.container.setBackgroundResource(R.color.brown7);
        }
        else if (position%10==1){
            holder.container.setBackgroundResource(R.color.red7);
        }
        else if (position%10==2){
            holder.container.setBackgroundResource(R.color.blue7);
        }
        else if (position%10==3){
            holder.container.setBackgroundResource(R.color.orange7);
        }
        else if (position%10==4){
            holder.container.setBackgroundResource(R.color.green7);
        }
        else if (position%10==5){
            holder.container.setBackgroundResource(R.color.indigo7);
        }
        else if (position%10==6){
            holder.container.setBackgroundResource(R.color.pink7);
        }
        else if (position%10==7){
            holder.container.setBackgroundResource(R.color.lightblue7);
        }
        else if (position%10==8){
            holder.container.setBackgroundResource(R.color.yellow7);
        }
        else if (position%10==9){
            holder.container.setBackgroundResource(R.color.purple7);
        }
        holder.storeimage.setImageResource(store.getImage());
        holder.storename.setText(store.getName());

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemfavoritestore_img_delete) ImageView delete;
        @BindView(R.id.itemfavoritestore_rl_background) RelativeLayout background;
        @BindView(R.id.itemfavoritestore_ln_container) LinearLayout container;
        @BindView(R.id.itemfavoritestore_rl_container) RelativeLayout container1;
        @BindView(R.id.itemfavoritestore_ln_follower) LinearLayout container_follower;
        @BindView(R.id.itemfavoritestore_ln_partnership) LinearLayout container_partnership;
        @BindView(R.id.itemfavoritestore_ln_product) LinearLayout container_product;
        @BindView(R.id.itemfavoritestore_ln_rating) LinearLayout container_rating;
        @BindView(R.id.itemfavoritestore_tv_follower) TextView follower;
        @BindView(R.id.itemfavoritestore_tv_partnership) TextView partnership;
        @BindView(R.id.itemfavoritestore_tv_product) TextView product;
        @BindView(R.id.itemfavoritestore_tv_rating) TextView rating;
        @BindView(R.id.itemfavoritestore_tvname) TextView storename;
        @BindView(R.id.itemfavoritestore_imgStore) ImageView storeimage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void removeItem(int position) {
        storeList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Store item, int position) {
        storeList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
