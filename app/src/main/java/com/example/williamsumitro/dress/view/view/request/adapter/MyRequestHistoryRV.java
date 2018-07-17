package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.williamsumitro.dress.view.model.Offer;
import com.example.williamsumitro.dress.view.model.RFQ_HistoryResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.home.fragment.HotFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/16/2018.
 */

public class MyRequestHistoryRV extends RecyclerView.Adapter<MyRequestHistoryRV.ViewHolder> {
    private Context context;
    private ArrayList<RFQ_HistoryResult> results;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private SnapHelper snapHelper;
    private String token;
    private SessionManagement sessionManagement;
    private MyRequestHistoryOfferRV rvadapter;
    private apiService service;

    public MyRequestHistoryRV(Context context, ArrayList<RFQ_HistoryResult> results){
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
        final RFQ_HistoryResult result = results.get(position);
        holder.qty.setText(formatter.format(Double.parseDouble(String.valueOf(result.getQty()))));
        holder.description.setText(result.getDescription());
        holder.dateexpired.setText(result.getRequestExpired());
        holder.budget.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMin()))) + " - IDR" + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMax()))));
        Picasso.with(context)
                .load(result.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.image_product);
        holder.viewoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(result.getOffer());
            }
        });
        holder.accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog1(result.getAcceptedOffer());
            }
        });
        holder.status.setText(result.getRfqStatus());
        if (result.getRfqStatus().equals("Request Expired")) {
            holder.container.setCardBackgroundColor(context.getResources().getColor(R.color.orange9));
            holder.viewoffer.setVisibility(View.GONE);
            holder.accepted.setVisibility(View.GONE);
        }
        else if (result.getRfqStatus().equals("Closed")) {
            holder.container.setCardBackgroundColor(context.getResources().getColor(R.color.red9));
            holder.viewoffer.setVisibility(View.GONE);
            holder.accepted.setVisibility(View.GONE);
        }
        else if (result.getRfqStatus().equals("Accepted")) {
            holder.container.setCardBackgroundColor(context.getResources().getColor(R.color.green9));
        }
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
    private void initDialog(final ArrayList<Offer> offers){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_viewoffer);
        final LinearLayout container_nooffer = (LinearLayout) dialog.findViewById(R.id.dialog_viewoffer_ln_nooffer);
        final LinearLayout container_offer = (LinearLayout) dialog.findViewById(R.id.dialog_viewoffer_ln_offer);
        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.dialog_viewoffer_rv);
        final Button buttoncancel = (Button) dialog.findViewById(R.id.dialog_viewoffer_btn_close);

        if (offers.size()>0){
            container_offer.setVisibility(View.VISIBLE);
            rvadapter = new MyRequestHistoryOfferRV(context, offers);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setAdapter(rvadapter);
            snapHelper.attachToRecyclerView(rv);

        }else {
            container_nooffer.setVisibility(View.VISIBLE);
        }

        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void initDialog1(final Offer offer){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_offerdetail);
        final ImageView image = (ImageView) dialog.findViewById(R.id.dialog_offerdetail_img);
        final TextView total = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_total);
        final TextView storename = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_storename);
        final TextView price = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_price);
        final TextView location = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_location);
        final TextView description = (TextView) dialog.findViewById(R.id.dialog_offerdetail_tv_description);
        final Button close = (Button) dialog.findViewById(R.id.dialog_offerdetail_btn_close);

        Picasso.with(context)
                .load(offer.getPhoto().getFilePath())
                .into(image);
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getTotalPrice()))));
        storename.setText(offer.getStoreName());
        price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getPriceUnit()))));
        location.setText(offer.getCityName());
        description.setText(offer.getDescription());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
