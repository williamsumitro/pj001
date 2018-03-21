package com.example.williamsumitro.dress.view.view.product.adapter;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 20/03/2018.
 */

public class DetailProductCourierRVAdapter extends RecyclerView.Adapter<DetailProductCourierRVAdapter.ViewHolder> {
    private List<Courier> courierList;
    private Context context;
    public DetailProductCourierRVAdapter(List<Courier> courierList, Context context){
        this.courierList = courierList;
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
        Courier courier = courierList.get(position);
        holder.name.setText(courier.getName());
        holder.image.setImageResource(courier.getImage());
    }

    @Override
    public int getItemCount() {
        return courierList.size();
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
