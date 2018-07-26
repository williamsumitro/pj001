package com.example.williamsumitro.dress.view.view.offer.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.OfferHistoryResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.request.adapter.MyRequestHistoryOfferRV;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class OfferHistoryRV extends RecyclerView.Adapter<OfferHistoryRV.ViewHolder> {
    private Context context;
    private ArrayList<OfferHistoryResult> results;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private SnapHelper snapHelper;
    private String token;
    private SessionManagement sessionManagement;
    private MyRequestHistoryOfferRV rvadapter;
    private apiService service;

    public OfferHistoryRV(Context context, ArrayList<OfferHistoryResult> results){
        this.context = context;
        this.results = results;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        snapHelper = new LinearSnapHelper();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myrequesthistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OfferHistoryResult result = results.get(position);
        holder.qty.setText(formatter.format(Double.parseDouble(String.valueOf(result.getQty()))));
        holder.description.setText(result.getRequestDescription());
        holder.dateexpired.setText(result.getRequestExpired());
        holder.budget.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMin()))) + " - IDR" + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMax()))));
        Picasso.with(context)
                .load(result.getRequestPhoto().getFilePath())
                .placeholder(R.drawable.default_product)
                .into(holder.image_product);
        holder.accepted.setVisibility(View.GONE);
        holder.viewoffer.setText("View Your Offer");
        holder.viewoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog1(result.getDescription(), result.getPriceUnit().toString(), result.getWeightUnit().toString(), result.getOfferPhoto().getFilePath());
            }
        });
        holder.status.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_myrequesthistory_tv_qty) TextView qty;
        @BindView(R.id.item_myrequesthistory_tv_description) TextView description;
        @BindView(R.id.item_myrequesthistory_tv_dateexpired) TextView dateexpired;
        @BindView(R.id.item_myrequesthistory_tv_budget) TextView budget;
        @BindView(R.id.item_myrequesthistory_btn_accepted) Button accepted;
        @BindView(R.id.item_myrequesthistory_btn_viewoffer) Button viewoffer;
        @BindView(R.id.item_myrequesthistory_img_product) ImageView image_product;
        @BindView(R.id.item_myrequesthistory_cv) CardView container;
        @BindView(R.id.item_myrequesthistory_tv_status) TextView status;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog1(final String des, String price_unit, String weight_unit, String offer_photo){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_offerdetail);
        final ImageView image = (ImageView) dialog.findViewById(R.id.dialog_offerdetail_img);
        final TextView total = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_total);
        final TextView storename = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_storename);
        final TextView price = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_price);
        final TextView location = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_location);
        final TextView description = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_description);
        final TextView weight = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_weight);
        final Button close = (Button) dialog.findViewById(R.id.dialog_offerdetail_btn_close);
        final LinearLayout container_total = (LinearLayout) dialog.findViewById(R.id.dialog_offerdetail_ln_total);

        Picasso.with(context)
                .load(offer_photo)
                .into(image);
        price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(price_unit))));
        weight.setText(formatter.format(Double.parseDouble(String.valueOf(weight_unit))) + " gr");
        description.setText(des);
        storename.setVisibility(View.GONE);
        container_total.setVisibility(View.GONE);
        location.setVisibility(View.GONE);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
