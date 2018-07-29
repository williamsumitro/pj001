package com.example.williamsumitro.dress.view.view.product.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.home.adapter.HotRVAdapter;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.example.williamsumitro.dress.view.view.product.activity.MyProduct;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 24/07/2018.
 */

public class MyProductRV extends RecyclerView.Adapter<MyProductRV.ViewHolder> {
    private ArrayList<ProductInfo> products;
    private int favoriteclick = 0;
    private Context context;
    private DecimalFormat formatter;
    private SessionManagement sessionManagement;
    private ArrayList<Price> priceList = new ArrayList<>();
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private Boolean detailclick = false, wishliststatus= false;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private apiService service;
    private MyProduct myProduct;

    public MyProductRV(ArrayList<ProductInfo> products, Context context, MyProduct myProduct) {
        this.products = products;
        this.context = context;
        this.myProduct = myProduct;
        progressDialog = new ProgressDialog(context);
        PicassoTools.clearCache(Picasso.with(context));
    }
    @Override
    public MyProductRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_myproduct, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyProductRV.ViewHolder holder, int position) {
        final ProductInfo product = products.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.name.setText(product.getProductName());
        priceList = product.getPrice();
        Picasso.with(context)
                .load(product.getPhoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(180, 200)
                .placeholder(R.drawable.default_product)
                .into(holder.image);
        holder.storename.setText(product.getStoreName());
        get_rating(product, holder);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, product.getProductId().toString());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(product.getProductId().toString());
            }
        });
    }

    private void get_rating(ProductInfo product, ViewHolder holder){
        if (product.getAverageRating() != null){
            if (Double.parseDouble(product.getAverageRating()) == 0){
                holder.star1.setImageResource(R.drawable.star0);
                holder.star2.setImageResource(R.drawable.star0);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
            else if(Double.parseDouble(product.getAverageRating())>0 && Double.parseDouble(product.getAverageRating())<1){
                holder.star1.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 1){
                holder.star1.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>1 && Double.parseDouble(product.getAverageRating())<2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>2 && Double.parseDouble(product.getAverageRating())<3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>3 && Double.parseDouble(product.getAverageRating())<4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>4 && Double.parseDouble(product.getAverageRating())<5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star);
            }
        }
        if (product.getAverageRating()!=null){
            if (Double.parseDouble(product.getAverageRating()) == 0){
                holder.star1.setImageResource(R.drawable.star0);
                holder.star2.setImageResource(R.drawable.star0);
                holder.star3.setImageResource(R.drawable.star0);
                holder.star4.setImageResource(R.drawable.star0);
                holder.star5.setImageResource(R.drawable.star0);
            }
            else if(Double.parseDouble(product.getAverageRating())>0 && Double.parseDouble(product.getAverageRating())<1){
                holder.star1.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 1){
                holder.star1.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>1 && Double.parseDouble(product.getAverageRating())<2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 2){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>2 && Double.parseDouble(product.getAverageRating())<3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 3){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>3 && Double.parseDouble(product.getAverageRating())<4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 4){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
            }
            else if(Double.parseDouble(product.getAverageRating())>4 && Double.parseDouble(product.getAverageRating())<5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star1);
            }
            else if (Double.parseDouble(product.getAverageRating()) == 5){
                holder.star1.setImageResource(R.drawable.star);
                holder.star2.setImageResource(R.drawable.star);
                holder.star3.setImageResource(R.drawable.star);
                holder.star4.setImageResource(R.drawable.star);
                holder.star5.setImageResource(R.drawable.star);
            }
        }

    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_myproduct_image)
        ImageView image;
        @BindView(R.id.item_myproduct_name)
        TextView name;
        @BindView(R.id.item_myproduct_container)
        CardView container;
        @BindView(R.id.item_myproduct_storename) TextView storename;
        @BindView(R.id.item_myproduct_star1) ImageView star1;
        @BindView(R.id.item_myproduct_star2) ImageView star2;
        @BindView(R.id.item_myproduct_star3) ImageView star3;
        @BindView(R.id.item_myproduct_star4) ImageView star4;
        @BindView(R.id.item_myproduct_star5) ImageView star5;
        @BindView(R.id.item_myproduct_btn_delete) Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void initDialog(final String product_id){
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Are you sure to delete ?")
                .setContentText("Once submit you cannot undo")
                .setConfirmText("Yes")
                .setCancelText("No")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        api_deleteproduct(product_id);
                        sweetAlertDialog.dismiss();
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }
    private void api_deleteproduct(String productid){
        service = apiUtils.getAPIService();
        service.req_deleteproduct(productid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            initDialog1(message, sweetAlertDialog);
                        }else{
                            String message = jsonResults.getString("message");
                            Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();;
            }
        });
    }

    private void initDialog1(String message, final SweetAlertDialog sweetAlertDialog1){
        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Registered Success!")
                .setContentText(message)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        myProduct.initRefresh();
                        sweetAlertDialog.dismiss();
                        sweetAlertDialog1.dismiss();
                    }
                }).show();
    }
}
