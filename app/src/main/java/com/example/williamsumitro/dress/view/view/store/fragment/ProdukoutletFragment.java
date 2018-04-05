package com.example.williamsumitro.dress.view.view.store.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Cloth;
import com.example.williamsumitro.dress.view.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.filter.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdukoutletFragment extends Fragment {
    int clickchoice;
    @BindView(R.id.produkoutlet_nestedscrollview) NestedScrollView nestedScrollView;
    @BindView(R.id.produkoutlet_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.produkoutlet_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.produkoutlet_searchview) SearchView searchView;
    @BindView(R.id.produkoutlet_lnfilter) LinearLayout filter;
    @BindView(R.id.produkoutlet_lnsort) LinearLayout sort;
    private List<Cloth> newList = new ArrayList<>();
    private HotRVAdapter adapternew;
    private Context context;

    public ProdukoutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produkoutlet, container, false);
        initObject(view);
        initData();
        initRefresh();
        initSearch();
        initonClick();
        return view;
    }

    private void initonClick() {
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemview) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                builderSingle.setTitle("Sort by");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
                arrayAdapter.add("Recommended");
                arrayAdapter.add("Most Popular");
                arrayAdapter.add("New Arrival");
                arrayAdapter.add("Price Low to High");
                arrayAdapter.add("Price High to Low");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                    }
                });
                builderSingle.show();
            }
        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Filter.class);
                context.startActivity(intent);
            }
        });
    }

    private void initObject(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
    }
    private void initRefresh(){
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapternew);
        adapternew.notifyDataSetChanged();
    }
    private void initSearch(){
        searchView.setQueryHint("Search your product here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapternew.getFilter().filter(newText);
                return false;
            }
        });

    }
    private void initData(){
        adapternew = new HotRVAdapter(newList, 1, getContext());
        Cloth cloth = new Cloth("Sequin Tank Sheath Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Striped Wrap Midi Dress", R.drawable.image, "12.000.000");
        newList.add(cloth);
        cloth = new Cloth("Long Sleeve Pocket Shirt Dress", R.drawable.image, "10.000.000");
        newList.add(cloth);
        cloth = new Cloth("Off The Shoulder Lace Sheath Dress", R.drawable.image, "9.000.000");
        newList.add(cloth);
        cloth = new Cloth("Sweetheart Neckline Sheath Dress", R.drawable.image, "8.300.000");
        newList.add(cloth);
        cloth = new Cloth("SStrappy Back Lace Inset Sheath Dress", R.drawable.image, "14.000.000");
        newList.add(cloth);
        cloth = new Cloth("STie-Sleeve Shift Dress", R.drawable.image, "2.000.000");
        newList.add(cloth);
    }
}
