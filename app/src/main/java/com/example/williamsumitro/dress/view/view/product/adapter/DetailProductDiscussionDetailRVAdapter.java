package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Discussion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WilliamSumitro on 21/03/2018.
 */

public class DetailProductDiscussionDetailRVAdapter extends RecyclerView.Adapter<DetailProductDiscussionDetailRVAdapter.ViewHolder> {
    private List<Discussion> discussionList;
    private Context context;
    public DetailProductDiscussionDetailRVAdapter(List<Discussion> discussionList, Context context){
        this.discussionList = discussionList;
        this.context = context;
    }
    @Override
    public DetailProductDiscussionDetailRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productdiscussion, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailProductDiscussionDetailRVAdapter.ViewHolder holder, int position) {
        final Discussion discussion = discussionList.get(position);
        holder.username.setText(discussion.getName());
        holder.userstatus.setText(discussion.getStatus());
        if(discussion.getStatus() == "Seller"){
            holder.userstatus.setBackgroundResource(R.color.gold);
        }
        else{
            holder.userstatus.setBackgroundResource(R.color.green);
        }
        holder.comment.setText(discussion.getComment());
        holder.date.setText(discussion.getDate());
        holder.commentcount.setText(String.valueOf(discussion.getCountcomment()));
        holder.image.setImageResource(discussion.getImage());
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemproductdiscussion_container)
        CardView container;
        @BindView(R.id.itemproductdiscussion_tvuserstatus)
        TextView userstatus;
        @BindView(R.id.itemproductdiscussion_tvusername) TextView username;
        @BindView(R.id.itemproductdiscussion_tvdate) TextView date;
        @BindView(R.id.itemproductdiscussion_tvcommentcount) TextView commentcount;
        @BindView(R.id.itemproductdiscussion_tvcomment) TextView comment;
        @BindView(R.id.itemproductdiscussion_circleimageview)
        CircleImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
