package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ReviewRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WilliamSumitro on 21/03/2018.
 */

public class DetailProductReviewsRVADapter extends RecyclerView.Adapter<DetailProductReviewsRVADapter.ViewHolder> {
    private ArrayList<ReviewRating> reviewArrayList;
    private Context context;

    public DetailProductReviewsRVADapter(ArrayList<ReviewRating> reviewArrayList, Context context){
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }
    @Override
    public DetailProductReviewsRVADapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reviewproduct, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailProductReviewsRVADapter.ViewHolder holder, int position) {
        ReviewRating reviewRating = reviewArrayList.get(position);
        Picasso.with(context)
                .load(reviewRating.getAvatar())
                .placeholder(R.drawable.logo404)
                .into(holder.user);
        holder.username.setText(reviewRating.getFullName());
        holder.date.setText(reviewRating.getUpdatedAt());
        holder.comment.setText(reviewRating.getReview());
        if (reviewRating.getRating() == null || reviewRating.getRating() == 0){
            holder.one.setImageResource(R.drawable.star0);
            holder.two.setImageResource(R.drawable.star0);
            holder.three.setImageResource(R.drawable.star0);
            holder.four.setImageResource(R.drawable.star0);
            holder.five.setImageResource(R.drawable.star0);
        }
        else if(reviewRating.getRating()>0 && reviewRating.getRating()<1){
            holder.one.setImageResource(R.drawable.star1);
        }
        else if (reviewRating.getRating() == 1){
            holder.one.setImageResource(R.drawable.star);
        }
        else if(reviewRating.getRating()>1 && reviewRating.getRating()<2){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star1);
        }
        else if (reviewRating.getRating() == 2){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
        }
        else if(reviewRating.getRating()>2 && reviewRating.getRating()<3){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star1);
        }
        else if (reviewRating.getRating() == 3){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
        }
        else if(reviewRating.getRating()>3 && reviewRating.getRating()<4){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star1);
        }
        else if (reviewRating.getRating() == 4){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
        }
        else if(reviewRating.getRating()>4 && reviewRating.getRating()<5){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
            holder.five.setImageResource(R.drawable.star1);
        }
        else if (reviewRating.getRating() == 5){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
            holder.five.setImageResource(R.drawable.star);
        }
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemreviewproduct_circleimageview) CircleImageView user;
        @BindView(R.id.itemreviewproduct_tvusername) TextView username;
        @BindView(R.id.itemreviewproduct_tvdate) TextView date;
        @BindView(R.id.itemreviewproduct_tvcomment) TextView comment;
        @BindView(R.id.itemreviewproduct_imgstar5)ImageView five;
        @BindView(R.id.itemreviewproduct_imgstar4) ImageView four;
        @BindView(R.id.itemreviewproduct_imgstar3) ImageView three;
        @BindView(R.id.itemreviewproduct_imgstar2) ImageView two;
        @BindView(R.id.itemreviewproduct_imgstar1) ImageView one;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
