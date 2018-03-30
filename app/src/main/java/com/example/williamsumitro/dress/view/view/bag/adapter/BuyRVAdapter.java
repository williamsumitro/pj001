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
    private List<Courier> courierList;
    private List<Bank> bankList;
    private List<PriceDetails> priceDetailsList;
    private Context context;
    public BuyRVAdapter(List<Courier> courierList, List<Bank> bankList, List<PriceDetails> priceDetailsList, Context context){
        this.context = context;
        this.courierList = courierList;
        this.bankList = bankList;
        this.priceDetailsList = priceDetailsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(courierList != null){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_courier1, parent, false);
        }
        else if(bankList != null){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_bank, parent, false);
        }
        else if(priceDetailsList != null){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_pricedetails, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(courierList != null){
            Courier courier = courierList.get(position);
            holder.courier.setText(courier.getName());
            holder.imagecourier.setImageResource(courier.getImage());
        }
        else if(bankList != null){
            Bank bank = bankList.get(position);
            holder.imagebank.setImageResource(bank.getImage());
        }
        else if(priceDetailsList != null){
            PriceDetails priceDetails = priceDetailsList.get(position);
            Log.d("Fuck the tag", String.valueOf(priceDetails.getDiscount()));
            holder.discount.setText(String.valueOf(priceDetails.getDiscount()));
            holder.qty.setText(String.valueOf(priceDetails.getQty()));
        }
    }

    @Override
    public int getItemCount() {
        if(courierList != null){
            return courierList.size();
        }
        else if(bankList != null){
            return bankList.size();
        }
        else if(priceDetailsList != null){
            return priceDetailsList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bank;
        ImageView imagebank;
        TextView courier;
        ImageView imagecourier;
        TextView discount;
        TextView qty;
        public ViewHolder(View itemView) {
            super(itemView);
            if(courierList != null){
                courier = (TextView) itemView.findViewById(R.id.itemcourier1_courier);
                imagecourier = (ImageView) itemView.findViewById(R.id.itemcourier1_image);
            }
            else if(bankList!= null){
                imagebank = (ImageView) itemView.findViewById(R.id.itembank_image);
            }
            else if(priceDetailsList != null){
                discount = (TextView) itemView.findViewById(R.id.itempricedetails_discount);
                qty = (TextView) itemView.findViewById(R.id.itempricedetails_qty);
            }
        }
    }
}
