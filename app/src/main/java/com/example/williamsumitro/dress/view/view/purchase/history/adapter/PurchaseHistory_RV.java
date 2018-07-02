package com.example.williamsumitro.dress.view.view.purchase.history.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_TransactionHistoryResult;
import com.example.williamsumitro.dress.view.view.purchase.history.activity.PurchaseHistoryDetail;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.activity.PurchaseReviewRatingDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class PurchaseHistory_RV extends RecyclerView.Adapter<PurchaseHistory_RV.ViewHolder> {
    private Context context;
    private ArrayList<Purchase_TransactionHistoryResult> resultArrayList;
    private DecimalFormat formatter;

    private final static String RESULT = "RESULT";

    public PurchaseHistory_RV(Context context, ArrayList<Purchase_TransactionHistoryResult> resultArrayList){
        this.context = context;
        this.resultArrayList = resultArrayList;
        formatter = new DecimalFormat("#,###,###");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transactionhistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Purchase_TransactionHistoryResult result = resultArrayList.get(position);
        holder.date.setText(result.getInvoiceDate());
        holder.ordernumber.setText(result.getOrderNumber());
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getTotalPrice()))));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, PurchaseHistoryDetail.class);
                    intent.putExtra(RESULT, result);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemtransactionhistory_container) CardView container;
        @BindView(R.id.itemtransactionhistory_tv_date) TextView date;
        @BindView(R.id.itemtransactionhistory_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.itemtransactionhistory_tv_price) TextView price;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
