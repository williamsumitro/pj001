package com.example.williamsumitro.dress.view.view.wishlist.adapter;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.WishlistResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.activity.Buy;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.ViewHolder> {
    private apiService service;
    private ProgressDialog progressDialog;
    private ArrayList<WishlistResult> wishlistArraylist;
    private Context context;
    private String token;
    private SessionManagement sessionManagement;
    private final static String PRODUCT_ID = "PRODUCT_ID";


    public WishlistRVAdapter(ArrayList<WishlistResult> wishlistArraylist, Context context){
        this.wishlistArraylist = wishlistArraylist;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WishlistResult wishlist = wishlistArraylist.get(position);
        holder.name.setText(wishlist.getProductName());
        if (wishlist.getRating() == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(wishlist.getRating()>0 && wishlist.getRating()<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (wishlist.getRating() == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(wishlist.getRating()>1 && wishlist.getRating()<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (wishlist.getRating() == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(wishlist.getRating()>2 && wishlist.getRating()<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (wishlist.getRating() == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(wishlist.getRating()>3 && wishlist.getRating()<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (wishlist.getRating() == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(wishlist.getRating()>4 && wishlist.getRating()<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (wishlist.getRating() == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
        holder.storename.setText(wishlist.getStoreName());
        Picasso.with(context).load(wishlist.getPhoto()).into(holder.imageView);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, wishlist.getProductId().toString());
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlistArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_wishlist_tv_name) TextView name;
        @BindView(R.id.item_wishlist_img) ImageView imageView;
        @BindView(R.id.item_wishlist_img_star1) ImageView star1;
        @BindView(R.id.item_wishlist_img_star2) ImageView star2;
        @BindView(R.id.item_wishlist_img_star3) ImageView star3;
        @BindView(R.id.item_wishlist_img_star4) ImageView star4;
        @BindView(R.id.item_wishlist_img_star5) ImageView star5;
        @BindView(R.id.item_wishlist_container) CardView container;
        @BindView(R.id.item_wishlist_tv_store) TextView storename;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {

                    }
                }
            });
        }
    }
}
