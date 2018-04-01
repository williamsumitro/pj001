package com.example.williamsumitro.dress.view.view.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 30/03/2018.
 */

public class BankRVAdapter extends RecyclerView.Adapter<BankRVAdapter.ViewHolder> {
    private List<Bank> bankList;
    private Context context;

    public BankRVAdapter(List<Bank> bankList, Context context){
        this.bankList = bankList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.bankname.setText(bank.getBankname());
        holder.accountholder.setText(bank.getAccountholder());
        holder.accountnumber.setText(bank.getAccountnumber());
        holder.imageView.setImageResource(bank.getImage());
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itembank1_image) ImageView imageView;
        @BindView(R.id.itembank1_bankname) TextView bankname;
        @BindView(R.id.itembank1_accountnumber) TextView accountnumber;
        @BindView(R.id.itembank1_accountholder) TextView accountholder;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
