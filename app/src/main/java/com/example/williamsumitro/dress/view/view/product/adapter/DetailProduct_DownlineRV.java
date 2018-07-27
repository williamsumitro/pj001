package com.example.williamsumitro.dress.view.view.product.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.DownlinePartner;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by William Sumitro on 7/12/2018.
 */

public class DetailProduct_DownlineRV extends RecyclerView.Adapter<DetailProduct_DownlineRV.ViewHolder> {
    private Context context;
    private ArrayList<DownlinePartner> downlinePartnerArrayList;
    private final static String PRODUCT_ID = "PRODUCT_ID";

    public DetailProduct_DownlineRV(Context context, ArrayList<DownlinePartner> downlinePartnerArrayList){
        this.context = context;
        this.downlinePartnerArrayList = downlinePartnerArrayList;
        PicassoTools.clearCache(Picasso.with(context));
    }
    @Override
    public DetailProduct_DownlineRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_downlinepartner, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailProduct_DownlineRV.ViewHolder holder, int position) {
        final DownlinePartner downlinePartner = downlinePartnerArrayList.get(position);
        holder.storename.setText(downlinePartner.getStoreNamePartner());
        Picasso.with(context)
                .load(downlinePartner.getStorePhotoPartner())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_photo)
                .into(holder.image);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, DetailProduct.class);
                    intent.putExtra(PRODUCT_ID, downlinePartner.getProductIdPartner().toString());
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return downlinePartnerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemdownlinepartnert_btn) Button button;
        @BindView(R.id.itemdownlinepartnert_img) CircleImageView image;
        @BindView(R.id.itemdownlinepartnert_tv) TextView storename;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
