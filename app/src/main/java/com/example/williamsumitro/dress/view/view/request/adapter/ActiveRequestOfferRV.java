package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Offer;
import com.example.williamsumitro.dress.view.model.RFQ_ActiveResult;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class ActiveRequestOfferRV extends RecyclerView.Adapter<ActiveRequestOfferRV.ViewHolder> {
    private Context context;
    private ArrayList<Offer> offers;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    public ActiveRequestOfferRV(Context context, ArrayList<Offer> offers){
        this.context = context;
        this.offers = offers;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offerdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        Picasso.with(context)
                .load(offer.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getTotalPrice()))));
        holder.storename.setText(offer.getStoreName());
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getPriceUnit()))));
        holder.location.setText(offer.getCityName());
        holder.description.setText(offer.getDescription());
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_offerdetail_img) ImageView imageView;
        @BindView(R.id.item_offerdetail_btn_accept) Button accept;
        @BindView(R.id.item_offerdetail_tv_total) TextView total;
        @BindView(R.id.item_offerdetail_tv_storename) TextView storename;
        @BindView(R.id.item_offerdetail_tv_price) TextView price;
        @BindView(R.id.item_offerdetail_tv_location) TextView location;
        @BindView(R.id.item_offerdetail_tv_description) TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
