package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Courier;

import java.util.List;

/**
 * Created by WilliamSumitro on 10/06/2018.
 */

public class CourierRVAdapter extends RecyclerView.Adapter<CourierRVAdapter.ViewHolder> {
    private List<Courier> courierList;
    private Context context;

    public CourierRVAdapter(List<Courier> courierList, Context context){
        this.context = context;
        this.courierList = courierList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_courier1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Courier courier = courierList.get(position);
        holder.imagecourier.setImageResource(courier.getImage());
    }

    @Override
    public int getItemCount() {
        return courierList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagecourier;

        public ViewHolder(View itemView) {
            super(itemView);
            imagecourier = (ImageView) itemView.findViewById(R.id.itemcourier1_image);
        }
    }
}
