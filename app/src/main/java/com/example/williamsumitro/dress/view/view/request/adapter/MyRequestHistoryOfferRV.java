package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.FullScreenImage;
import com.example.williamsumitro.dress.view.model.Offer;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class MyRequestHistoryOfferRV extends RecyclerView.Adapter<MyRequestHistoryOfferRV.ViewHolder> {
    private Context context;
    private ArrayList<Offer> offers;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private final static String IMAGE = "IMAGE";
    private String token;
    private SessionManagement sessionManagement;
    private apiService service;

    public MyRequestHistoryOfferRV(Context context, ArrayList<Offer> offers){
        this.context = context;
        this.offers = offers;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public MyRequestHistoryOfferRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offerdetail1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRequestHistoryOfferRV.ViewHolder holder, int position) {
        final Offer offer = offers.get(position);
        Picasso.with(context)
                .load(offer.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getTotalPrice()))));
        holder.storename.setText(offer.getStoreName());
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getPriceUnit()))));
        holder.location.setText(offer.getCityName());
        holder.description.setText(offer.getDescription());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, FullScreenImage.class);
                    intent.putExtra(IMAGE, offer.getPhoto().getFilePath());
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_offerdetail1_img) ImageView imageView;
        @BindView(R.id.item_offerdetail1_tv_total) TextView total;
        @BindView(R.id.item_offerdetail1_tv_storename) TextView storename;
        @BindView(R.id.item_offerdetail1_tv_price) TextView price;
        @BindView(R.id.item_offerdetail1_tv_location) TextView location;
        @BindView(R.id.item_offerdetail1_tv_description) TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
