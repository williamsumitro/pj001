package com.example.williamsumitro.dress.view.view.partnership.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/9/2018.
 */

public class MyPartnershipDetail_RV extends RecyclerView.Adapter<MyPartnershipDetail_RV.ViewHolder> {
    private Context context;
    private ArrayList<ProductInfo> productArrayList;
    private int index;
    private Dialog dialog;
    private BuyRVAdapter pricedetailsadapter;

    public MyPartnershipDetail_RV(Context context, ArrayList<ProductInfo> productArrayList){
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @Override
    public MyPartnershipDetail_RV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mypartnership_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPartnershipDetail_RV.ViewHolder holder, int position) {
        final ProductInfo product = productArrayList.get(position);
        holder.status.setText("Status : " + product.getStatus());
        Picasso.with(context)
                .load(product.getPhoto())
                .placeholder(R.drawable.default_product)
                .into(holder.image);
        holder.product_name.setText(product.getProductName());
        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(product.getStoreName(), product.getStoreNamePartner(), product.getPrice(), product.getRequestPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_mypartnershipdetail_btn_viewdetails) Button viewdetails;
        @BindView(R.id.item_mypartnershipdetail_tv_status) TextView status;
        @BindView(R.id.item_mypartnershipdetail_tv_productname) TextView product_name;
        @BindView(R.id.item_mypartnershipdetail_img_product) ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(final String store_name, final String storerequest_name, final ArrayList<Price> priceList, final ArrayList<Price> requestPriceArrayList){
        index = 0;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_pprovepartnership);

        final TextView storerequestname = (TextView) dialog.findViewById(R.id.approvepartnershipdialog_tv_storerequestname);
        final TextView storename = (TextView) dialog.findViewById(R.id.approvepartnershipdialog_tv_storename);
        final RecyclerView rvrequest = (RecyclerView) dialog.findViewById(R.id.approvepartnershipdialog_rv_request);
        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.approvepartnershipdialog_rv);
        final Button close = (Button) dialog.findViewById(R.id.approvepartnershipdialog_btn_close);
        storename.setText(store_name);
        storerequestname.setText(storerequest_name);
        pricedetailsadapter = new BuyRVAdapter(priceList, context);
        RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(horizontallayout);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(pricedetailsadapter);

        pricedetailsadapter = new BuyRVAdapter(requestPriceArrayList, context);
        RecyclerView.LayoutManager horizontallayout1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvrequest.setLayoutManager(horizontallayout1);
        rvrequest.setItemAnimator(new DefaultItemAnimator());
        rvrequest.setAdapter(pricedetailsadapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
