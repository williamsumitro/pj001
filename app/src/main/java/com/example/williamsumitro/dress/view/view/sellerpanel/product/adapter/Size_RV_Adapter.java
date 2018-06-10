package com.example.williamsumitro.dress.view.view.sellerpanel.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Size_RV_Adapter extends RecyclerView.Adapter<Size_RV_Adapter.ViewHolder>{
    private Context context;
    private List<Size> sizeList;
    private Boolean checked = false;

    public Size_RV_Adapter(Context context, List<Size> sizeList){
        this.context = context;
        this.sizeList = sizeList;
    }
    @Override
    public Size_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridviewfilter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Size_RV_Adapter.ViewHolder holder, int position) {
        Size size = sizeList.get(position);
        holder.namakota.setText(size.getSizeName());
        holder.checkBox.setTag(size.getSizeId());
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemgridviewfilter_checkbox)
        CheckBox checkBox;
        @BindView(R.id.itemgridviewfilter_tvnamakota)
        TextView namakota;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        checkBox.setChecked(true);
                        checked = true;
                    }
                    else {
                        checkBox.setChecked(false);
                        checked = false;
                    }
                }
            });
        }
    }
}
