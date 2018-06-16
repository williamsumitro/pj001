package com.example.williamsumitro.dress.view.view.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Order;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 01/04/2018.
 */

public class OrderDetailRVAdapter extends RecyclerView.Adapter<OrderDetailRVAdapter.ViewHolder> {
    private List<Order> OrderList;
    private Context context;
    private DecimalFormat formatter;

    public OrderDetailRVAdapter(List<Order> OrderList, Context context){
        this.OrderList = OrderList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = OrderList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.reason.setText(order.getReason());
        holder.qtyXL.setText(String.valueOf(order.getQtyXL()));
        holder.qtyS.setText(String.valueOf(order.getQtyS()));
        holder.qtyM.setText(String.valueOf(order.getQtyM()));
        holder.qtyL.setText(String.valueOf(order.getQtyL()));
        holder.priceXL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getPriceXL()))));
        holder.subtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getSubtotal()))));
        holder.priceM.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getPriceM()))));
        holder.priceL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(order.getPriceL()))));
        holder.namapakaian.setText(order.getNamapakaian());
        holder.pakaian.setImageResource(order.getImage());
        if (order.getReason().equals("") || order.getReason().equals(null)){
            holder.containerReason.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemdetailorder_tvReason) TextView reason;
        @BindView(R.id.itemdetailorder_tvqtyXL) TextView qtyXL;
        @BindView(R.id.itemdetailorder_tvqtyS) TextView qtyS;
        @BindView(R.id.itemdetailorder_tvqtyM) TextView qtyM;
        @BindView(R.id.itemdetailorder_tvqtyL) TextView qtyL;
        @BindView(R.id.itemdetailorder_tvpriceXL) TextView priceXL;
        @BindView(R.id.itemdetailorder_tvpriceSubtotal) TextView subtotal;
        @BindView(R.id.itemdetailorder_tvpriceM) TextView priceM;
        @BindView(R.id.itemdetailorder_tvpriceL) TextView priceL;
        @BindView(R.id.itemdetailorder_tvnamapakaian) TextView namapakaian;
        @BindView(R.id.itemdetailorder_lnXL) LinearLayout containerXL;
        @BindView(R.id.itemdetailorder_lnS) LinearLayout containerS;
        @BindView(R.id.itemdetailorder_lnReason) LinearLayout containerReason;
        @BindView(R.id.itemdetailorder_lnM) LinearLayout containerM;
        @BindView(R.id.itemdetailorder_lnL) LinearLayout containerL;
        @BindView(R.id.itemdetailorder_imgpakaian) ImageView pakaian;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
