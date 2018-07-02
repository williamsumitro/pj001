package com.example.williamsumitro.dress.view.view.purchase.reviewrating.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.Purchase_ReviewRatingResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.adapter.SpinBankAdapter;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.activity.PurchaseReviewRatingDetail;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class PurchaseReviewRating_RV extends RecyclerView.Adapter<PurchaseReviewRating_RV.ViewHolder>{
    private Context context;
    private ArrayList<Purchase_ReviewRatingResult> result;
    private DecimalFormat formatter;
    private Dialog dialog;
    private SpinBankAdapter spinBankAdapter;
    private String company_bank_id;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private ArrayList<Product> productArrayList;

    private final static String PRODUCT = "PRODUCT";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";
    private final static String STORE_ID = "STORE_ID";
    private final static String STORE_PHOTO = "STORE_PHOTO";
    private final static String STORE_NAME = "STORE_NAME";

    public PurchaseReviewRating_RV(Context context, ArrayList<Purchase_ReviewRatingResult> result){
        this.context = context;
        this.result = result;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);

        productArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_reviewrating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Purchase_ReviewRatingResult reviewRatingResult = result.get(position);
        productArrayList = reviewRatingResult.getProduct();
        holder.accepted.setText("Accepted Product : " + reviewRatingResult.getAccepted());
        holder.rejected.setText("Rejected Product : " + reviewRatingResult.getRejected());
        holder.ordernumber.setText(reviewRatingResult.getOrderNumber());
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, PurchaseReviewRatingDetail.class);
                    intent.putExtra(TRANSACTION_ID, reviewRatingResult.getTransactionId().toString());
                    intent.putExtra(PRODUCT, productArrayList);
                    intent.putExtra(STORE_ID, reviewRatingResult.getStoreId().toString());
                    intent.putExtra(STORE_NAME, reviewRatingResult.getStoreName());
                    intent.putExtra(STORE_PHOTO, reviewRatingResult.getStorePhoto());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasereviewrating_tv_accept) TextView accepted;
        @BindView(R.id.item_purchasereviewrating_tv_reject) TextView rejected;
        @BindView(R.id.item_purchasereviewrating_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchasereviewrating_btn_rate) Button rate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
