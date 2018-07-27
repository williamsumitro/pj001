package com.example.williamsumitro.dress.view.view.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 30/03/2018.
 */

public class BankRVAdapter extends RecyclerView.Adapter<BankRVAdapter.ViewHolder> {
    private ArrayList<Bank> bankList;
    private Context context;

    public BankRVAdapter(ArrayList<Bank> bankList, Context context){
        this.bankList = bankList;
        this.context = context;
        PicassoTools.clearCache(Picasso.with(context));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.accountholder.setText(bank.getNameInAccount());
        holder.accountnumber.setText(bank.getAccountNumber());

        Picasso.with(context)
                .load(bank.getLogo())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itembank1_image) ImageView imageView;
        @BindView(R.id.itembank1_accountnumber) TextView accountnumber;
        @BindView(R.id.itembank1_accountholder) TextView accountholder;
        @BindView(R.id.itembank1_container) CardView container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
