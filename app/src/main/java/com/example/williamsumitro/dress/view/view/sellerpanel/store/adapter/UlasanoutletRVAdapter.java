package com.example.williamsumitro.dress.view.view.sellerpanel.store.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 15/03/2018.
 */

public class UlasanoutletRVAdapter extends RecyclerView.Adapter<UlasanoutletRVAdapter.ViewHolder>{
    private List<Review> reviewList;
    private Context context;

    public UlasanoutletRVAdapter(List<Review> reviewList, Context context){
        this.reviewList = reviewList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ulasan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.gambarproduk.setImageResource(review.getGambarpakaian());
        holder.review.setText(review.getReview());
        holder.namauser.setText(review.getNamauser());
        holder.jangkareview.setText(review.getTanggal());
        holder.namaproduk.setText(review.getNamapakaian());
        holder.gambaruser.setImageResource(review.getGambaruser());
        if (review.getRating() == 0){
            holder.one.setImageResource(R.drawable.star0);
            holder.two.setImageResource(R.drawable.star0);
            holder.three.setImageResource(R.drawable.star0);
            holder.four.setImageResource(R.drawable.star0);
            holder.five.setImageResource(R.drawable.star0);
        }
        else if(review.getRating()>0 && review.getRating()<1){
            holder.one.setImageResource(R.drawable.star1);
        }
        else if (review.getRating() == 1){
            holder.one.setImageResource(R.drawable.star);
        }
        else if(review.getRating()>1 && review.getRating()<2){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star1);
        }
        else if (review.getRating() == 2){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
        }
        else if(review.getRating()>2 && review.getRating()<3){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star1);
        }
        else if (review.getRating() == 3){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
        }
        else if(review.getRating()>3 && review.getRating()<4){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star1);
        }
        else if (review.getRating() == 4){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
        }
        else if(review.getRating()>4 && review.getRating()<5){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
            holder.five.setImageResource(R.drawable.star1);
        }
        else if (review.getRating() == 5){
            holder.one.setImageResource(R.drawable.star);
            holder.two.setImageResource(R.drawable.star);
            holder.three.setImageResource(R.drawable.star);
            holder.four.setImageResource(R.drawable.star);
            holder.five.setImageResource(R.drawable.star);
        }
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemulasan_gambarproduk) ImageView gambarproduk;
        @BindView(R.id.itemulasan_tvReview) TextView review;
        @BindView(R.id.itemulasan_tvNamaUser) TextView namauser;
        @BindView(R.id.itemulasan_tvJangkaBalasan) TextView jangkareview;
        @BindView(R.id.itemulasan_namaproduk) TextView namaproduk;
        @BindView(R.id.itemulasan_imgGambarUser) ImageView gambaruser;
        @BindView(R.id.iitemulasan_imgstar1) ImageView one;
        @BindView(R.id.iitemulasan_imgstar2) ImageView two;
        @BindView(R.id.iitemulasan_imgstar3) ImageView three;
        @BindView(R.id.iitemulasan_imgstar4) ImageView four;
        @BindView(R.id.iitemulasan_imgstar5) ImageView five;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
