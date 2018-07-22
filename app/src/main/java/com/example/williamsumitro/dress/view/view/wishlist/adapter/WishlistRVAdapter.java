package com.example.williamsumitro.dress.view.view.wishlist.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.StoreInfo;
import com.example.williamsumitro.dress.view.model.WishlistResult;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductCourierRVAdapter;
import com.example.williamsumitro.dress.view.view.store.activity.DetailStore;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 04/04/2018.
 */

public class WishlistRVAdapter extends RecyclerView.Adapter<WishlistRVAdapter.ViewHolder> {
    private apiService service;
    private ProgressDialog progressDialog;
    private ArrayList<WishlistResult> wishlistArraylist;
    private Context context;
    private String token;
    private SessionManagement sessionManagement;
    private final static String NAMAPRODUCT = "NAMAPRODUCT";
    private final static String QTYMINORDER = "QTYMINORDER";
    private final static String GAMBARPRODUCT = "GAMBARPRODUCT";
    private final static String MINORDER = "MINORDER";
    private final static String PRICELIST = "PRICELIST";
    private final static String SIZELIST = "SIZELIST";
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private BuyRVAdapter pricedetailsadapter;
    private ArrayList<Price> priceList;
    private ArrayList<Size> sizeList;
    private ArrayList<String> qtyminlist, priceminlist, availablesizelist;
    private Dialog dialog;
    private DetailProductCourierRVAdapter adapter;
    private final static String STORE_ID = "STORE_ID";

    public WishlistRVAdapter(ArrayList<WishlistResult> wishlistArraylist, Context context){
        this.wishlistArraylist = wishlistArraylist;
        this.context = context;
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        priceList = new ArrayList<>();
        qtyminlist = new ArrayList<>();
        priceminlist = new ArrayList<>();
        availablesizelist = new ArrayList<>();
        sizeList = new ArrayList<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wishlist, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WishlistResult wishlist = wishlistArraylist.get(position);
        if (position%10==0){
            holder.container1.setBackgroundResource(R.color.brown9);
        }
        else if (position%10==1){
            holder.container1.setBackgroundResource(R.color.red9);
        }
        else if (position%10==2){
            holder.container1.setBackgroundResource(R.color.blue9);
        }
        else if (position%10==3){
            holder.container1.setBackgroundResource(R.color.green9);
        }
        else if (position%10==4){
            holder.container1.setBackgroundResource(R.color.indigo9);
        }
        else if (position%10==5){
            holder.container1.setBackgroundResource(R.color.pink9);
        }
        else if (position%10==6){
            holder.container1.setBackgroundResource(R.color.yellow9);
        }
        else if (position%10==7){
            holder.container1.setBackgroundResource(R.color.purple9);
        }
        holder.name.setText(wishlist.getProductName());
        holder.storename.setText(wishlist.getStoreName());
        Picasso.with(context).load(wishlist.getPhoto()).into(holder.imageView);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, wishlist.getProductId().toString());
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        holder.minorder.setText(String.valueOf(wishlist.getMinOrder()));
        getRating(wishlist, holder);
        initProductDetails(wishlist, holder);
    }

    @Override
    public int getItemCount() {
        return wishlistArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_wishlist_tv_name) TextView name;
        @BindView(R.id.item_wishlist_img) ImageView imageView;
        @BindView(R.id.item_wishlist_img_star1) ImageView star1;
        @BindView(R.id.item_wishlist_img_star2) ImageView star2;
        @BindView(R.id.item_wishlist_img_star3) ImageView star3;
        @BindView(R.id.item_wishlist_img_star4) ImageView star4;
        @BindView(R.id.item_wishlist_img_star5) ImageView star5;
        @BindView(R.id.item_wishlist_container) CardView container;
        @BindView(R.id.item_wishlist_tv_store) TextView storename;
        @BindView(R.id.item_wishlist_rl_container) RelativeLayout container1;
        @BindView(R.id.item_wishlist_rl_background) RelativeLayout background;
        @BindView(R.id.item_wishlist_tvMinOrder) TextView minorder;
        @BindView(R.id.item_wishlist_tv_courier) TextView courier;
        @BindView(R.id.item_wishlist_lncourier) LinearLayout container_courier;
        @BindView(R.id.item_wishlist_lnstore) LinearLayout container_store;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void removeItem(final int position) {
        service.req_delete_from_wishlist(token, String.valueOf(wishlistArraylist.get(position).getProductId())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            wishlistArraylist.remove(position);
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
    private void dialog_store(final StoreInfo storeInfo){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_store);

        final TextView tv_storename = (TextView) dialog.findViewById(R.id.storedialog_tv_storename);
        final TextView tv_soldproduct = (TextView) dialog.findViewById(R.id.storedialog_tv_soldproduct);
        final TextView tv_totaltransaction = (TextView) dialog.findViewById(R.id.storedialog_tv_totaltransaction);
        final TextView tv_establishedyear = (TextView) dialog.findViewById(R.id.storedialog_tv_establishedyear);
        final TextView tv_province = (TextView) dialog.findViewById(R.id.storedialog_tv_province);
        final TextView tv_city = (TextView) dialog.findViewById(R.id.storedialog_tv_city);
        final TextView tv_businesstype = (TextView) dialog.findViewById(R.id.storedialog_tv_businesstype);
        final TextView tv_description = (TextView) dialog.findViewById(R.id.storedialog_tv_description);
        final TextView tv_contactpersonname = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonname);
        final TextView tv_contactpersonjobtitle = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonjobtitle);
        final TextView tv_contactpersonphonenumber = (TextView) dialog.findViewById(R.id.storedialog_tv_contactpersonphonenumber);
        final Button buttonok = (Button) dialog.findViewById(R.id.storedialog_btn_ok);
        final Button buttonviewstore = (Button) dialog.findViewById(R.id.storedialog_btn_viewstore);

        tv_storename.setText(storeInfo.getName());
        tv_soldproduct.setText(storeInfo.getSoldProduct());
        tv_totaltransaction.setText(storeInfo.getTransaction().toString());
        tv_establishedyear.setText(storeInfo.getEstablishedYear());
        tv_province.setText(storeInfo.getProvinceName());
        tv_city.setText(storeInfo.getCityName());
        tv_businesstype.setText(storeInfo.getBusinessType());
        tv_description.setText(storeInfo.getDescription());
        tv_contactpersonname.setText(storeInfo.getContactPersonName());
        tv_contactpersonjobtitle.setText(storeInfo.getContactPersonJobTitle());
        tv_contactpersonphonenumber.setText(storeInfo.getContactPersonPhoneNumber());

        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        buttonviewstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, DetailStore.class);
                    intent.putExtra(STORE_ID, storeInfo.getStoreId().toString());
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        dialog.show();
    }
    public void restoreItem(final WishlistResult item, final int position) {
        service.req_add_to_wishlist(token, String.valueOf(item.getProductId())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            wishlistArraylist.add(position, item);
                            notifyItemInserted(position);
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

    private void getRating(WishlistResult wishlist, ViewHolder holder){
        if (Double.parseDouble(wishlist.getRating()) == 0){
            holder.star1.setImageResource(R.drawable.star0);
            holder.star2.setImageResource(R.drawable.star0);
            holder.star3.setImageResource(R.drawable.star0);
            holder.star4.setImageResource(R.drawable.star0);
            holder.star5.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(wishlist.getRating())>0 && Double.parseDouble(wishlist.getRating())<1){
            holder.star1.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(wishlist.getRating()) == 1){
            holder.star1.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(wishlist.getRating())>1 && Double.parseDouble(wishlist.getRating())<2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(wishlist.getRating()) == 2){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(wishlist.getRating())>2 && Double.parseDouble(wishlist.getRating())<3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(wishlist.getRating()) == 3){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(wishlist.getRating())>3 && Double.parseDouble(wishlist.getRating())<4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(wishlist.getRating()) == 4){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(wishlist.getRating())>4 && Double.parseDouble(wishlist.getRating())<5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(wishlist.getRating()) == 5){
            holder.star1.setImageResource(R.drawable.star);
            holder.star2.setImageResource(R.drawable.star);
            holder.star3.setImageResource(R.drawable.star);
            holder.star4.setImageResource(R.drawable.star);
            holder.star5.setImageResource(R.drawable.star);
        }
    }

    private void initProductDetails(WishlistResult wishlistResult, final ViewHolder holder){
        service.req_get_product_detail(token, wishlistResult.getProductId().toString()).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    holder.courier.setText(String.valueOf(response.body().getStoreInfo().getCourierService().size()));
                }
            }
            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
    }

    private void initDialog(ArrayList<model_CourierService> courierServices){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_courier);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.courierdialog_rv);
        final Button buttonok = (Button) dialog.findViewById(R.id.courierdialog_btn_ok);
        adapter = new DetailProductCourierRVAdapter(courierServices, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        buttonok.setText("Ok");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
