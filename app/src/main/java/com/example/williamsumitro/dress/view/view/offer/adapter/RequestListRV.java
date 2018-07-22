package com.example.williamsumitro.dress.view.view.offer.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import com.example.williamsumitro.dress.view.model.RFQResult;
import com.example.williamsumitro.dress.view.view.offer.activity.AddMyOffer;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class RequestListRV extends RecyclerView.Adapter<RequestListRV.ViewHolder> {
    private Context context;
    private ArrayList<RFQResult> results;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private Drawable picture;

    private final static String RFQID = "RFQID";

    public RequestListRV(Context context, ArrayList<RFQResult> results){
        this.context = context;
        this.results = results;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        picture = context.getResources().getDrawable(R.drawable.picture);
    }

    @Override
    public RequestListRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requestlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestListRV.ViewHolder holder, int position) {
        final RFQResult result = results.get(position);
        holder.username.setText(result.getFullName());
        holder.description.setText(result.getDescription());
        holder.qty.setText(formatter.format(Double.parseDouble(String.valueOf(result.getQty()))));
        holder.itemname.setText(result.getItemName());
        holder.dateexpired.setText(result.getRequestExpired());
        holder.budget.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMin()))) + " - IDR" + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMax()))));
        Picasso.with(context)
                .load(result.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, AddMyOffer.class);
                    intent.putExtra(RFQID, result.getRfqRequestId().toString());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_requestlist_tv_username) TextView username;
        @BindView(R.id.item_requestlist_tv_description) TextView description;
        @BindView(R.id.item_requestlist_tv_qty) TextView qty;
        @BindView(R.id.item_requestlist_tv_itemname) TextView itemname;
        @BindView(R.id.item_requestlist_tv_dateexpired) TextView dateexpired;
        @BindView(R.id.item_requestlist_tv_budget) TextView budget;
        @BindView(R.id.item_requestlist_image) ImageView imageView;
        @BindView(R.id.item_requestlist_container) CardView container;
        @BindView(R.id.item_requestlist_btn_offer) Button offer;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
