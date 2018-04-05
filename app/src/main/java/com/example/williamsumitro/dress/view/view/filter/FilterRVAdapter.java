package com.example.williamsumitro.dress.view.view.filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 03/04/2018.
 */

public class FilterRVAdapter extends RecyclerView.Adapter<FilterRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> arrayList;
    private Boolean checked = false;

    public FilterRVAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public FilterRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridviewfilter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterRVAdapter.ViewHolder holder, int position) {
        String cityname = arrayList.get(position);
        holder.namakota.setText(cityname);
        holder.checkBox.setTag(cityname);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemgridviewfilter_checkbox) CheckBox checkBox;
        @BindView(R.id.itemgridviewfilter_tvnamakota) TextView namakota;
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
