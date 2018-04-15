package com.example.williamsumitro.dress.view.view.order.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;
import com.example.williamsumitro.dress.view.view.order.activity.OrderDetail;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by William Sumitro on 01/04/2018.
 */

public class StatusRVAdapter extends RecyclerView.Adapter<StatusRVAdapter.ViewHolder> {
    private final static String PENDING = "PENDING";
    private final static String ACCEPT = "ACCEPT";
    private final static String REJECT = "REJECT";
    private List<Order> orderList;
    private Context context;
    private DecimalFormat formatter;

    public StatusRVAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = orderList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.ordernumber.setText(order.getOrdernumber());
        holder.orderdate.setText(order.getOrderdate());
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getTotal()))));
        holder.storename.setText(order.getStorename());
        holder.imagestore.setImageResource(order.getImage());
        if(!order.getStatus().isEmpty()){
            holder.status.setText(order.getStatus());
            if(order.getStatus().toLowerCase().equals("waiting seller response")){
                holder.imgstatus.setImageResource(R.drawable.clock);
                holder.status.setTextColor(context.getResources().getColor(R.color.orange3));
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, OrderDetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(PENDING, "true");
                        Bundle bundle = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.slideleft).toBundle();
                            context.startActivity(intent, bundle);
                        }
                        else {
                            context.startActivity(intent);
                        }
                    }
                });
            }
            else if(order.getStatus().toLowerCase().equals("order approved")){
                holder.imgstatus.setImageResource(R.drawable.approved);
                holder.status.setTextColor(context.getResources().getColor(R.color.green2));
                holder.container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, OrderDetail.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(ACCEPT, "true");
                        intent.putExtra(REJECT, "true");
                        Bundle bundle = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.slideleft).toBundle();
                            context.startActivity(intent, bundle);
                        }
                        else {
                            context.startActivity(intent);
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemstatus_storeimage) CircleImageView imagestore;
        @BindView(R.id.itemstatus_container) CardView container;
        @BindView(R.id.itemstatus_tvtotal) TextView total;
        @BindView(R.id.itemstatus_tvstorename) TextView storename;
        @BindView(R.id.itemstatus_tvstatus) TextView status;
        @BindView(R.id.itemstatus_tvordernumber) TextView ordernumber;
        @BindView(R.id.itemstatus_tvoderdate) TextView orderdate;
        @BindView(R.id.itemstatus_imgstatus) ImageView imgstatus;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this ,itemView);
        }
    }
}
