package com.example.williamsumitro.dress.view.view.favoritestore.adapter;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.FavoriteResult;
import com.example.williamsumitro.dress.view.model.Store;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.example.williamsumitro.dress.view.view.store.activity.DetailStore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class FavoritestoreRVAdapter extends RecyclerView.Adapter<FavoritestoreRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FavoriteResult> results;
    private apiService service;
    private ProgressDialog progressDialog;
    private String token;
    private SessionManagement sessionManagement;
    private DecimalFormat df;


    private final static String STORE_ID = "STORE_ID";
    public FavoritestoreRVAdapter(ArrayList<FavoriteResult> results, Context context){
        this.context = context;
        this.results = results;
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        df = new DecimalFormat("###.#");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favoritestore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FavoriteResult store = results.get(position);
        if (position%10==0){
            holder.container.setBackgroundResource(R.color.brown7);
        }
        else if (position%10==1){
            holder.container.setBackgroundResource(R.color.red7);
        }
        else if (position%10==2){
            holder.container.setBackgroundResource(R.color.blue7);
        }
        else if (position%10==3){
            holder.container.setBackgroundResource(R.color.orange7);
        }
        else if (position%10==4){
            holder.container.setBackgroundResource(R.color.green7);
        }
        else if (position%10==5){
            holder.container.setBackgroundResource(R.color.indigo7);
        }
        else if (position%10==6){
            holder.container.setBackgroundResource(R.color.pink7);
        }
        else if (position%10==7){
            holder.container.setBackgroundResource(R.color.lightblue7);
        }
        else if (position%10==8){
            holder.container.setBackgroundResource(R.color.yellow7);
        }
        else if (position%10==9){
            holder.container.setBackgroundResource(R.color.purple7);
        }
        Picasso.with(context)
                .load(store.getPhoto())
                .placeholder(R.drawable.default_photo)
                .into(holder.storeimage);
        holder.storename.setText(store.getName());
        holder.rating.setText("("+df.format(Double.parseDouble(store.getRating()))+")");
        holder.follower.setText(store.getSoldProduct());
        holder.product.setText(String.valueOf(store.getTransaction()));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailStore.class);
                intent.putExtra(STORE_ID, store.getStoreId().toString());
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemfavoritestore_img_delete) ImageView delete;
        @BindView(R.id.itemfavoritestore_rl_background) RelativeLayout background;
        @BindView(R.id.itemfavoritestore_ln_container) LinearLayout container;
        @BindView(R.id.itemfavoritestore_rl_container) RelativeLayout container1;
        @BindView(R.id.itemfavoritestore_ln_follower) LinearLayout container_follower;
//        @BindView(R.id.itemfavoritestore_ln_partnership) LinearLayout container_partnership;
        @BindView(R.id.itemfavoritestore_ln_product) LinearLayout container_product;
        @BindView(R.id.itemfavoritestore_ln_rating) LinearLayout container_rating;
        @BindView(R.id.itemfavoritestore_tv_follower) TextView follower;
//        @BindView(R.id.itemfavoritestore_tv_partnership) TextView partnership;
        @BindView(R.id.itemfavoritestore_tv_product) TextView product;
        @BindView(R.id.itemfavoritestore_tv_rating) TextView rating;
        @BindView(R.id.itemfavoritestore_tvname) TextView storename;
        @BindView(R.id.itemfavoritestore_imgStore) ImageView storeimage;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void removeItem(final int position) {
        service.req_delete_from_favorite(token, String.valueOf(results.get(position).getStoreId())).enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code()==200){
                try{
                    JSONObject jsonResults = new JSONObject(response.body().string());
                    if(jsonResults.getBoolean("status")){
                        results.remove(position);
                        notifyItemRemoved(position);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
        }
    });
    }

    public void restoreItem(FavoriteResult item, int position) {
        results.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
