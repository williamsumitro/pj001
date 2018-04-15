package com.example.williamsumitro.dress.view.view.home.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bid;
import com.example.williamsumitro.dress.view.view.bid.activity.BidDetail;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 11/04/2018.
 */

public class HotOpenBidRVAdapter extends RecyclerView.Adapter<HotOpenBidRVAdapter.ViewHolder> {
    private List<Bid> bid_list;
    private Context context;

    public HotOpenBidRVAdapter(List<Bid> bid_list, Context context){
        this.bid_list = bid_list;
        this.context = context;
    }
    @Override
    public HotOpenBidRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_openbid, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotOpenBidRVAdapter.ViewHolder holder, int position) {
        Bid bid = bid_list.get(position);
        if(bid.isBid_status()){
            holder.status.setText("Open");
            holder.status.setBackgroundResource(R.color.green);
        }
        else {
            holder.status.setText("Closed");
            holder.status.setBackgroundResource(R.color.red);
        }
        holder.rejected.setText(String.valueOf(bid.getBid_rejected()));
        holder.accepted.setText(String.valueOf(bid.getBid_accepted()));
        holder.pending.setText(String.valueOf(bid.getBid_pending()));
        holder.namatoko.setText(bid.getBid_storename());
        holder.remaining.setText(bid.getExpired());
        holder.judul.setText(bid.getBid_title());
        holder.image.setImageResource(bid.getBid_image());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BidDetail.class);
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bid_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_openbid_btnStatus) Button status;
        @BindView(R.id.item_openbid_tvRemaining) TextView remaining;
        @BindView(R.id.item_openbid_tvRejected) TextView rejected;
        @BindView(R.id.item_openbid_tvAccepted) TextView accepted;
        @BindView(R.id.item_openbid_tv_Requested) TextView pending;
        @BindView(R.id.item_openbid_namatoko) TextView namatoko;
        @BindView(R.id.item_openbid_jdlBiding) TextView judul;
        @BindView(R.id.item_openbid_image) ImageView image;
        @BindView(R.id.item_openbid_container) CardView container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
