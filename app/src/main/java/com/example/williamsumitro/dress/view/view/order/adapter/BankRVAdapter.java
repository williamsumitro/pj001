package com.example.williamsumitro.dress.view.view.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        holder.accountholder.setText(bank.getAccountholder());
        holder.accountnumber.setText(bank.getAccountnumber());
        if (bank.getBankname().toLowerCase().equals("bca")){
            holder.imageView.setImageResource(R.drawable.logo_bca);
            holder.container.setBackgroundResource(R.color.grey6);
        }
        else if (bank.getBankname().toLowerCase().equals("mandiri")){
            holder.imageView.setImageResource(R.drawable.logo_mandiri);
            holder.container.setBackgroundResource(R.color.blue4);
        }
        else if (bank.getBankname().toLowerCase().equals("maybank")){
            holder.imageView.setImageResource(R.drawable.logo_maybank);
            holder.container.setBackgroundResource(R.color.orange01);
        }
        else if (bank.getBankname().toLowerCase().equals("bni")){
            holder.imageView.setImageResource(R.drawable.logo_bni);
            holder.container.setBackgroundResource(R.color.brown6);
        }
        else if (bank.getBankname().toLowerCase().equals("bri")){
            holder.imageView.setImageResource(R.drawable.logo_bri);
            holder.container.setBackgroundResource(R.color.blue6);
        }
        else if (bank.getBankname().toLowerCase().equals("ocbc")){
            holder.imageView.setImageResource(R.drawable.logo_ocbc);
            holder.container.setBackgroundResource(R.color.ocbc);
        }
        else if (bank.getBankname().toLowerCase().equals("permata")){
            holder.imageView.setImageResource(R.drawable.logo_permata);
            holder.container.setBackgroundResource(R.color.green);
        }
        else if (bank.getBankname().toLowerCase().equals("sinarmas")){
            holder.imageView.setImageResource(R.drawable.logo_sinarmas);
            holder.container.setBackgroundResource(R.color.red6);
        }
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itembank1_image) ImageView imageView;
        @BindView(R.id.itembank1_accountnumber) TextView accountnumber;
        @BindView(R.id.itembank1_accountholder) TextView accountholder;
        @BindView(R.id.itembank1_container) LinearLayout container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
