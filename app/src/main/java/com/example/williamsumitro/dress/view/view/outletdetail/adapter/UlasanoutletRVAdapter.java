package com.example.williamsumitro.dress.view.view.outletdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.HotRVAdapter;
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
        holder.gambarproduk.setImageResource(review.getGambarproduk());
        holder.review.setText(review.getReview());
        holder.namauser.setText(review.getNamauser());
        holder.jangkareview.setText(review.getJangkareview());
        holder.namaproduk.setText(review.getNamaproduk());
        holder.rating.setImageResource(review.getRating());
        holder.gambaruser.setImageResource(review.getGambaruser());
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
        @BindView(R.id.itemulasan_imgRating) ImageView rating;
        @BindView(R.id.itemulasan_imgGambarUser) ImageView gambaruser;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
