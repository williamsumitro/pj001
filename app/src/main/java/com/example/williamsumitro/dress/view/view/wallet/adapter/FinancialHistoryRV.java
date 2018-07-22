package com.example.williamsumitro.dress.view.view.wallet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.FinancialHistoryResult;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 26/05/2018.
 */

public class FinancialHistoryRV extends RecyclerView.Adapter<FinancialHistoryRV.ViewHolder> {
    private ArrayList<FinancialHistoryResult> results;
    private Context context;
    private DecimalFormat formatter;

    public FinancialHistoryRV(Context context, ArrayList<FinancialHistoryResult> results){
        this.context = context;
        this.results = results;
        formatter = new DecimalFormat("#,###,###");
    }
    @Override
    public FinancialHistoryRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FinancialHistoryRV.ViewHolder holder, int position) {
        FinancialHistoryResult result = results.get(position);
        holder.transaction_name.setText(result.getTransaction());
        if (result.getNote().equals("") || result.getNote() == null){
            holder.note.setVisibility(View.GONE);
        }
        holder.note.setText(result.getNote());
        if (result.getTransaction().equals("BEGINNING BALANCE")){
            holder.nominal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getDebit()))));
            holder.status.setText("");
        }
        else {
            if (result.getCredit().equals("0")){
                holder.nominal.setText("IDR +" + formatter.format(Double.parseDouble(String.valueOf(result.getDebit()))));
                holder.nominal.setTextColor(context.getResources().getColor(R.color.green1));
                holder.status.setText("Debit");
                holder.status.setTextColor(context.getResources().getColor(R.color.green1));
                holder.balance.setTextColor(context.getResources().getColor(R.color.green1));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.green9));
            }
            else if (result.getDebit() == 0){
                holder.nominal.setText("IDR -" + formatter.format(Double.parseDouble(String.valueOf(result.getCredit()))));
                holder.status.setText("Credit");
                holder.nominal.setTextColor(context.getResources().getColor(R.color.red));
                holder.status.setTextColor(context.getResources().getColor(R.color.red));
                holder.balance.setTextColor(context.getResources().getColor(R.color.red));
                holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red9));
            }
        }
        holder.date.setText(result.getDate());
        holder.balance.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBalance()))));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_transaction_tv_transactionname) TextView transaction_name;
        @BindView(R.id.item_transaction_tv_note) TextView note;
        @BindView(R.id.item_transaction_tv_nominal) TextView nominal;
        @BindView(R.id.item_transaction_tv_date) TextView date;
        @BindView(R.id.item_transaction_tv_balance) TextView balance;
        @BindView(R.id.item_transaction_tv_status) TextView status;
        @BindView(R.id.item_transaction_cv) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
