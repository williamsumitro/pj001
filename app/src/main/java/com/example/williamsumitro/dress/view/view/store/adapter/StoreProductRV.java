package com.example.williamsumitro.dress.view.view.store.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/13/2018.
 */

public class StoreProductRV extends RecyclerView.Adapter<StoreProductRV.ViewHolder> implements Filterable {
    private ArrayList<ProductInfo> productArrayList;
    private ArrayList<ProductInfo> productArrayListFiltered;
    private int favoriteclick = 0;
    private Context context;
    private DecimalFormat formatter;
    private SessionManagement sessionManagement;
    private List<Price> priceList = new ArrayList<>();
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private Boolean detailclick = false, wishliststatus= false;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    public StoreProductRV (ArrayList<ProductInfo> productArrayList, Context context){
        this.productArrayList = productArrayList;
        this.productArrayListFiltered = productArrayList;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newbrands, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(StoreProductRV.ViewHolder holder, int position) {
        final ProductInfo product = productArrayListFiltered.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.name.setText(product.getProductName());
        priceList = product.getPrice();
        holder.price.setVisibility(View.GONE);
        Picasso.with(context)
                .load(product.getPhoto())
                .placeholder(R.drawable.default_product)
                .into(holder.image);
        holder.storename.setText(product.getStoreName());
        get_rating(product, holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, product.getProductId().toString());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayListFiltered.size();
    }

    private void get_rating(ProductInfo product, ViewHolder holder){
        if (Double.parseDouble(product.getAverageRating()) == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(product.getAverageRating())>0 && Double.parseDouble(product.getAverageRating())<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(product.getAverageRating()) == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(product.getAverageRating())>1 && Double.parseDouble(product.getAverageRating())<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(product.getAverageRating()) == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(product.getAverageRating())>2 && Double.parseDouble(product.getAverageRating())<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(product.getAverageRating()) == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(product.getAverageRating())>3 && Double.parseDouble(product.getAverageRating())<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(product.getAverageRating()) == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(product.getAverageRating())>4 && Double.parseDouble(product.getAverageRating())<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(product.getAverageRating()) == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productArrayListFiltered = productArrayList;
                } else {
                    ArrayList<ProductInfo> filteredList = new ArrayList<>();
                    for (ProductInfo row : productArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getProductName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    productArrayListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productArrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productArrayListFiltered = (ArrayList<ProductInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_newbrands_image)
        ImageView image;
        @BindView(R.id.item_newbrands_name)
        TextView name;
        @BindView(R.id.item_newbrands_price) TextView price;
        @BindView(R.id.item_newbrands_container)
        CardView container;
        @BindView(R.id.item_newbrands_storename) TextView storename;
        @BindView(R.id.item_newbrands_star1) ImageView star1;
        @BindView(R.id.item_newbrands_star2) ImageView star2;
        @BindView(R.id.item_newbrands_star3) ImageView star3;
        @BindView(R.id.item_newbrands_star4) ImageView star4;
        @BindView(R.id.item_newbrands_star5) ImageView star5;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
