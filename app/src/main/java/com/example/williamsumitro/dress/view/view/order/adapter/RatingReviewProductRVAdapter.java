package com.example.williamsumitro.dress.view.view.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ProductRating;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 01/04/2018.
 */

public class RatingReviewProductRVAdapter extends RecyclerView.Adapter<RatingReviewProductRVAdapter.ViewHolder> {
    private List<ProductRating> productRatingList;
    private Context context;
    private DecimalFormat formatter;
    public RatingReviewProductRVAdapter(List<ProductRating> productRatingList, Context context){
        this.productRatingList = productRatingList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviewproduct1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ProductRating productRating = productRatingList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(productRating.getPrice()))));
        holder.productname.setText(productRating.getName());
        holder.imageproduct.setImageResource(productRating.getImage());
        holder.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star);
            }
        });
        holder.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star0);
            }
        });
        holder.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
        });
        holder.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
        });
        holder.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star0);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productRatingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemreviewproduct1_etReview) EditText review;
        @BindView(R.id.itemreviewproduct1_tvProductName) TextView productname;
        @BindView(R.id.itemreviewproduct1_tvPrice) TextView price;
        @BindView(R.id.itemreviewproduct1_imgstar5) ImageView star5;
        @BindView(R.id.itemreviewproduct1_imgstar4) ImageView star4;
        @BindView(R.id.itemreviewproduct1_imgstar3) ImageView star3;
        @BindView(R.id.itemreviewproduct1_imgstar2) ImageView star2;
        @BindView(R.id.itemreviewproduct1_imgstar1) ImageView star1;
        @BindView(R.id.itemreviewproduct1_imageproduct) ImageView imageproduct;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
