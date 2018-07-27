package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.SizeInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 27/07/2018.
 */

public class ShoppingBagSizeRV extends RecyclerView.Adapter<ShoppingBagSizeRV.ViewHolder> {
    private Context context;
    private ArrayList<SizeInfo> sizeInfos;

    public ShoppingBagSizeRV(Context context, ArrayList<SizeInfo> sizeInfos){
        this.context = context;
        this.sizeInfos = sizeInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_size, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SizeInfo sizeInfo = sizeInfos.get(position);
        holder.name.setText(sizeInfo.getSizeName());
        holder.qty.setText(sizeInfo.getProductQty().toString());
    }

    @Override
    public int getItemCount() {
        return sizeInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemsize_tv_name) TextView name;
        @BindView(R.id.itemsize_tv_qty) TextView qty;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
