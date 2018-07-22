package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 20/03/2018.
 */

public class DetailProductCourierRVAdapter extends RecyclerView.Adapter<DetailProductCourierRVAdapter.ViewHolder> {
    private ArrayList<model_CourierService> courierArrayList;
    private Context context;
    public DetailProductCourierRVAdapter(ArrayList<model_CourierService> courierArrayList, Context context){
        this.courierArrayList = courierArrayList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_courier, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        model_CourierService courier = courierArrayList.get(position);
        holder.name.setText(courier.getCourierName());
        Picasso.with(context)
                .load(courier.getLogo())
                .placeholder(R.drawable.logo404)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return courierArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemcourier_image) ImageView image;
        @BindView(R.id.itemcourier_name) TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
