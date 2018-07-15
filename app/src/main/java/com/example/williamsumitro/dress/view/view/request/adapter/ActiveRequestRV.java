package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.RFQResult;
import com.example.williamsumitro.dress.view.model.RFQ_ActiveResult;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class ActiveRequestRV extends RecyclerView.Adapter<ActiveRequestRV.ViewHolder> {
    private Context context;
    private ArrayList<RFQ_ActiveResult> results;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private Boolean click = false;
    private ActiveRequestOfferRV rvadapter;
    private SnapHelper snapHelper;

    public ActiveRequestRV(Context context, ArrayList<RFQ_ActiveResult> results){
        this.context = context;
        this.results = results;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        snapHelper = new LinearSnapHelper();
    }
    @Override
    public ActiveRequestRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activerequest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActiveRequestRV.ViewHolder holder, int position) {
        final RFQ_ActiveResult result = results.get(position);
        holder.qty.setText(formatter.format(Double.parseDouble(String.valueOf(result.getQty()))));
        holder.description.setText(result.getDescription());
        holder.dateexpired.setText(result.getRequestExpired());
        holder.budget.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMin()))) + " - IDR" + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMax()))));
        Picasso.with(context)
                .load(result.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.image_product);

        holder.offer_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click){
                    holder.offer_detail.setVisibility(View.VISIBLE);
                    click = true;
                    holder.caret.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.offer_detail.setVisibility(View.GONE);
                    click = false;
                    holder.caret.setImageResource(R.drawable.caret);
                }
            }
        });
        rvadapter = new ActiveRequestOfferRV(context, result.getOffer());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(rvadapter);
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_activerequest_tv_qty) TextView qty;
        @BindView(R.id.item_activerequest_tv_description) TextView description;
        @BindView(R.id.item_activerequest_tv_dateexpired) TextView dateexpired;
        @BindView(R.id.item_activerequest_tv_budget) TextView budget;
        @BindView(R.id.item_activerequest_rv) RecyclerView recyclerView;
        @BindView(R.id.item_activerequest_ln_offerproductdetail) LinearLayout offer_detail;
        @BindView(R.id.item_activerequest_ln_offer) LinearLayout offer;
        @BindView(R.id.item_activerequest_ln_offerproduct) LinearLayout offer_product;
        @BindView(R.id.item_activerequest_img_product) ImageView image_product;
        @BindView(R.id.item_activerequest_img_offercaret) ImageView caret;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
