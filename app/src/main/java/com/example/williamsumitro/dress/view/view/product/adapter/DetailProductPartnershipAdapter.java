package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Partnership;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by William Sumitro on 18/04/2018.
 */

public class DetailProductPartnershipAdapter extends RecyclerView.Adapter<DetailProductPartnershipAdapter.ViewHolder> {
    private Context context;
    private List<Partnership> partnershipList;

    public DetailProductPartnershipAdapter(List<Partnership> partnershipList, Context context){
        this.context = context;
        this.partnershipList = partnershipList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_partnership, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Partnership partnership = partnershipList.get(position);

    }

    @Override
    public int getItemCount() {
        return partnershipList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itempartnership_tv_location) TextView location;
        @BindView(R.id.itempartnership_tv_minorder) TextView minorder;
        @BindView(R.id.itempartnership_tv_storename) TextView storename;
        @BindView(R.id.itempartnership_tv_price) TextView price;
        @BindView(R.id.itempartnership_circleimage)CircleImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
