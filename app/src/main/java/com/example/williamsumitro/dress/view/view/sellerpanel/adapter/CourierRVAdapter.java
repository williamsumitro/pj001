package com.example.williamsumitro.dress.view.view.sellerpanel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Courier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 14/04/2018.
 */

public class CourierRVAdapter extends RecyclerView.Adapter<CourierRVAdapter.ViewHolder> {
    private List<Courier> courierList;
    private Context context;

    public CourierRVAdapter(List<Courier> courierList, Context context){
        this.courierList = courierList;
        this.context = context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courier1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Courier courier = courierList.get(position);
        if (courier.getName().toLowerCase().equals("tiki")){
            holder.courierimage.setImageResource(R.drawable.logo_tiki);
        }
        else if (courier.getName().toLowerCase().equals("j&t")){
            holder.courierimage.setImageResource(R.drawable.logo_jt);
        }
        else if (courier.getName().toLowerCase().equals("sicepat")){
            holder.courierimage.setImageResource(R.drawable.logo_sicepat);
        }
        else if (courier.getName().toLowerCase().equals("pos")){
            holder.courierimage.setImageResource(R.drawable.logo_pos);
        }
        else if (courier.getName().toLowerCase().equals("indah")){
            holder.courierimage.setImageResource(R.drawable.logo_indah);
        }
        else if (courier.getName().toLowerCase().equals("jne")){
            holder.courierimage.setImageResource(R.drawable.logo_jne);
        }
    }

    @Override
    public int getItemCount() {
        return courierList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemcourier1_image) ImageView courierimage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
