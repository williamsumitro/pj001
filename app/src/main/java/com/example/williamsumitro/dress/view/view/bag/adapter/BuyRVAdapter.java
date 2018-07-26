package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 24/03/2018.
 */

public class BuyRVAdapter extends RecyclerView.Adapter<BuyRVAdapter.ViewHolder> {
    private List<Price> priceList;
    private Context context;
    public BuyRVAdapter(List<Price> priceList, Context context){
        this.context = context;
        this.priceList = priceList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pricedetails, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (priceList.size()>0){
            Price price = priceList.get(position);
            if (price.getQtyMax().toLowerCase().equals("max")){
                holder.qty_min.setText(formatter.format(Double.parseDouble(String.valueOf(price.getQtyMin()))));
                holder.semicolon.setText("Qty >= ");
            }
            else {
                holder.qty_min.setText(formatter.format(Double.parseDouble(String.valueOf(price.getQtyMin()))));
                holder.qty_max.setText(formatter.format(Double.parseDouble(String.valueOf(price.getQtyMax()))));
                holder.dash.setText(" - ");
                holder.semicolon.setText("Qty : ");
            }
            if (price.getPrice().matches("\\d+(?:\\.\\d+)?"))
                holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(price.getPrice()))));
            else
                holder.price.setText(price.getPrice());
        }

    }

    @Override
    public int getItemCount() {
       return priceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itempricedetails_qtymin) TextView qty_min;
        @BindView(R.id.itempricedetails_qtymax) TextView qty_max;
        @BindView(R.id.itempricedetails_price) TextView price;
        @BindView(R.id.itempricedetail_dash) TextView dash;
        @BindView(R.id.itempricedetail_semicolon) TextView semicolon;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
