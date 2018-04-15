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
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;
import com.example.williamsumitro.dress.view.view.order.activity.OrderDetail;
import com.example.williamsumitro.dress.view.view.order.activity.PaymentDetail;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by William Sumitro on 30/03/2018.
 */

public class PaymentRVAdapter extends RecyclerView.Adapter<PaymentRVAdapter.ViewHolder> {

    private final static String PENDING = "PENDING";
    private final static String ACCEPT = "ACCEPT";
    private final static String REJECT = "REJECT";
    private final static String STATUS = "STATUS";
    private List<Order> orderList;
    private Context context;
    private DecimalFormat formatter;
    public PaymentRVAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public PaymentRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentRVAdapter.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.ordernumber.setText(order.getOrdernumber());
        holder.orderdate.setText(order.getOrderdate());
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getTotal()))));
        holder.storename.setText(order.getStorename());
        holder.storeimage.setImageResource(order.getImage());
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(PENDING, "true");
                intent.putExtra(STATUS, "payment");
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

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itempayment_btncancel) Button cancel;
        @BindView(R.id.itempayment_btnconfirm) Button confirm;
        @BindView(R.id.itempayment_tvtotal) TextView total;
        @BindView(R.id.itempayment_tvstorename) TextView storename;
        @BindView(R.id.itempayment_tvordernumber) TextView ordernumber;
        @BindView(R.id.itempayment_tvoderdate) TextView orderdate;
        @BindView(R.id.itempayment_container) CardView container;
        @BindView(R.id.itempayment_storeimage) CircleImageView storeimage;
            public ViewHolder(View itemView) {
            super(itemView);
                ButterKnife.bind(this,itemView);
        }
    }
}
