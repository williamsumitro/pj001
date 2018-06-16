package com.example.williamsumitro.dress.view.view.openstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CourierDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 17/05/2018.
 */

public class OpenStore_CourierAdapter extends RecyclerView.Adapter<OpenStore_CourierAdapter.ViewHolder> {
    private List<CourierDetails> courierDetailsList;
    private Context context;

    public OpenStore_CourierAdapter(List<CourierDetails> courierDetailsList, Context context){
        this.context = context;
        this.courierDetailsList = courierDetailsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_courier2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CourierDetails courierDetails = courierDetailsList.get(position);
        //in some cases, it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkbox.setChecked(courierDetails.getSelected());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                courierDetailsList.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
        holder.name.setText(courierDetails.getCourierName());
    }

    @Override
    public int getItemCount() {
        return courierDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemcourier2_checkbox) CheckBox checkbox;
        @BindView(R.id.itemcourier2_name) TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
