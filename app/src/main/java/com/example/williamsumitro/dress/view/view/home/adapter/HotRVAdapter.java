package com.example.williamsumitro.dress.view.view.home.adapter;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 02/03/2018.
 */

public class HotRVAdapter extends RecyclerView.Adapter<HotRVAdapter.ViewHolder>{
    private List<ProductInfo> productInfoList;
    private int favoriteclick = 0;
    private Context context;
    private DecimalFormat formatter;
    private SessionManagement sessionManagement;
    private List<Price> priceList = new ArrayList<>();
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private Boolean detailclick = false, wishliststatus= false;
    private ProgressDialog progressDialog;
    private Dialog dialog;


    public HotRVAdapter (List<ProductInfo> productInfoList, Context context){
        this.productInfoList = productInfoList;
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_newbrands, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductInfo productInfo = productInfoList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.name.setText(productInfo.getProductName());
        priceList = productInfo.getPrice();
//        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(priceList.get(0).getPrice()))));
        holder.price.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load(productInfo.getPhoto())
                .placeholder(R.drawable.logo404)
                .into(holder.image);
        holder.storename.setText(productInfo.getStoreName());
        get_rating(productInfo, holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, productInfo.getProductId().toString());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productInfoList.size();
    }
    private void get_rating(ProductInfo productInfo, ViewHolder holder){
        if (Double.parseDouble(productInfo.getAverageRating()) == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(productInfo.getAverageRating())>0 && Double.parseDouble(productInfo.getAverageRating())<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getAverageRating()) == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getAverageRating())>1 && Double.parseDouble(productInfo.getAverageRating())<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getAverageRating()) == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getAverageRating())>2 && Double.parseDouble(productInfo.getAverageRating())<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getAverageRating()) == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getAverageRating())>3 && Double.parseDouble(productInfo.getAverageRating())<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getAverageRating()) == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(productInfo.getAverageRating())>4 && Double.parseDouble(productInfo.getAverageRating())<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(productInfo.getAverageRating()) == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_newbrands_image) ImageView image;
        @BindView(R.id.item_newbrands_name) TextView name;
        @BindView(R.id.item_newbrands_price) TextView price;
        @BindView(R.id.item_newbrands_container) CardView container;
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
