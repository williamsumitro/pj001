package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Courier;
import com.example.williamsumitro.dress.view.model.PriceDetails;

import java.util.List;

/**
 * Created by WilliamSumitro on 24/03/2018.
 */

public class BuyRVAdapter extends RecyclerView.Adapter<BuyRVAdapter.ViewHolder> {
    private List<PriceDetails> priceDetailsList;
    private Context context;
    public BuyRVAdapter(List<PriceDetails> priceDetailsList, Context context){
        this.context = context;
        this.priceDetailsList = priceDetailsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pricedetails, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            PriceDetails priceDetails = priceDetailsList.get(position);
            Log.d("Fuck the tag", String.valueOf(priceDetails.getDiscount()));
            holder.discount.setText(String.valueOf(priceDetails.getDiscount()));
            holder.qty.setText(String.valueOf(priceDetails.getQty()));
    }

    @Override
    public int getItemCount() {
       return priceDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bank;
        ImageView imagebank;
        TextView discount;
        TextView qty;
        public ViewHolder(View itemView) {
            super(itemView);
            discount = (TextView) itemView.findViewById(R.id.itempricedetails_discount);
            qty = (TextView) itemView.findViewById(R.id.itempricedetails_qty);
        }
    }
}
