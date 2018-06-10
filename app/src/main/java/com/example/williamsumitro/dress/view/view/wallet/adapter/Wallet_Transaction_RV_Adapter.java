package com.example.williamsumitro.dress.view.view.wallet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.TransactionDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 26/05/2018.
 */

public class Wallet_Transaction_RV_Adapter extends RecyclerView.Adapter<Wallet_Transaction_RV_Adapter.ViewHolder> {
    private List<TransactionDetails> transactionDetailsList;
    private Context context;

    public Wallet_Transaction_RV_Adapter(Context context, List<TransactionDetails> transactionDetailsList){
        this.context = context;
        this.transactionDetailsList = transactionDetailsList;
    }
    @Override
    public Wallet_Transaction_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Wallet_Transaction_RV_Adapter.ViewHolder holder, int position) {
        TransactionDetails transactionDetails = transactionDetailsList.get(position);
        holder.transaction_name.setText(transactionDetails.getTransaction());
        holder.note.setText(transactionDetails.getNote());
        holder.nominal.setText(String.valueOf(transactionDetails.getCredit()));
        holder.date.setText(transactionDetails.getDate());
        holder.balance.setText(String.valueOf(transactionDetails.getBalance()));
    }

    @Override
    public int getItemCount() {
        return transactionDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_transaction_tv_transactionname) TextView transaction_name;
        @BindView(R.id.item_transaction_tv_note) TextView note;
        @BindView(R.id.item_transaction_tv_nominal) TextView nominal;
        @BindView(R.id.item_transaction_tv_IDR) TextView IDR;
        @BindView(R.id.item_transaction_tv_date) TextView date;
        @BindView(R.id.item_transaction_tv_balance) TextView balance;
        @BindView(R.id.item_transaction_img_status) ImageView status;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
