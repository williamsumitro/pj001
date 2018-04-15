package com.example.williamsumitro.dress.view.view.favoritestore.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.view.favoritestore.adapter.FavoriteStoreRVTouch;
import com.example.williamsumitro.dress.view.view.favoritestore.adapter.FavoritestoreRVAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteStoreFragment extends Fragment implements FavoriteStoreRVTouch.FavoriteStoreTouchListener {

    @BindView(R.id.favoritestore_rv) RecyclerView recyclerView;
    @BindView(R.id.favoritestore_container) LinearLayout container;
    private Context context;
    private ArrayList<Store> storeList;
    private FavoritestoreRVAdapter adapter;
    public FavoriteStoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_store, container, false);
        initView(view);
        initData();
        setupRV();
        return view;
    }
    private void initView(View view){
        ButterKnife.bind(this, view);
        context = getActivity();
        storeList = new ArrayList<>();
    }
    private void setupRV(){
        adapter = new FavoritestoreRVAdapter(storeList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new FavoriteStoreRVTouch(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
    private void initData(){
        Store store = new Store("ABC Store", R.drawable.image, 4.2);
        storeList.add(store);
        store =  new Store("Tek Store", R.drawable.image, 3);
        storeList.add(store);
        store =  new Store("Pikach", R.drawable.image, 2.4);
        storeList.add(store);
        store =  new Store("Blunt", R.drawable.image, 2);
        storeList.add(store);
        store =  new Store("Jamsf", R.drawable.image, 5);
        storeList.add(store);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavoritestoreRVAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = storeList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Store deletedStore = storeList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(container, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedStore, deletedIndex);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(R.color.gold));
            snackbar.show();
        }
    }
}
