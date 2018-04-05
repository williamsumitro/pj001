package com.example.williamsumitro.dress.view.view.search.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.model.Filter_Model;
import com.example.williamsumitro.dress.view.view.search.activity.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements Filterable {
    private final static String FILTER = "FILTER";
    private ArrayList<Filter_Model> stringList;
    private ArrayList<Filter_Model> stringListFiltered;
    private Context context;

    public SearchAdapter(ArrayList<Filter_Model> stringList, Context context){
        this.context =context;
        this.stringList = stringList;
        this.stringListFiltered = stringList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searchhint, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Filter_Model filter_model = stringListFiltered.get(position);
        holder.textView.setText(filter_model.getName());
    }

    @Override
    public int getItemCount() {
        return stringListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                stringListFiltered = stringList;
            } else {
                ArrayList<Filter_Model> filteredList = new ArrayList<>();
                for (Filter_Model row : stringList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                    }
                }

                stringListFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = stringListFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            stringListFiltered = (ArrayList<Filter_Model>) filterResults.values;
            notifyDataSetChanged();
        }
    };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemsearchhint_tv) TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SearchResult.class);
                    intent.putExtra(FILTER, textView.getText().toString());
                    Bundle bundle = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                        context.startActivity(intent, bundle);
                    }
                }
            });
        }
    }
}
