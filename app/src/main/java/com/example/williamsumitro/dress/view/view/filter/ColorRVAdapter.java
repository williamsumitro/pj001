package com.example.williamsumitro.dress.view.view.filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class ColorRVAdapter extends RecyclerView.Adapter<ColorRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;
    private Boolean checked = false;
    public ColorRVAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridviewfilter1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String colorname = arrayList.get(position);
        holder.namawarna.setText(colorname);
        holder.checkBox.setTag(colorname);
        if (colorname.equals("black"))
            holder.image.setImageResource(R.color.black);
        else if (colorname.equals("blue"))
            holder.image.setImageResource(R.color.blue4);
        else if (colorname.equals("red"))
            holder.image.setImageResource(R.color.red4);
        else if (colorname.equals("white"))
            holder.image.setImageResource(R.color.white);
        else if (colorname.equals("yellow"))
            holder.image.setImageResource(R.color.yellow4);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemgridviewfilter1_checkbox)
        CheckBox checkBox;
        @BindView(R.id.itemgridviewfilter1_tvnamawarna)
        TextView namawarna;
        @BindView(R.id.itemgridviewfilter1_img)
        ImageView image;
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
