package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.FullScreenImage;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.activity.ShoppingBag;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
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
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 11/06/2018.
 */

public class ShoppingBagProductRVAdapter extends RecyclerView.Adapter<ShoppingBagProductRVAdapter.ViewHolder> {
    private ArrayList<ProductInfo> productArrayList;
    private Context context;
    private DecimalFormat formatter;
    private List<String> sizelist;
    private Boolean detailclick = false;
    private String token;
    SessionManagement sessionManagement;
    private apiService service;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private final static String IMAGE = "IMAGE";
    public ShoppingBagProductRVAdapter(ArrayList<ProductInfo> productArrayList, Context context){
        this.context = context;
        this.productArrayList = productArrayList;
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppingbag_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        sizelist = new ArrayList<>();
        final ProductInfo product = productArrayList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.productname.setText(product.getProductName());
        Picasso.with(context)
                .load(product.getProductPhoto())
                .placeholder(R.drawable.logo404)
                .into(holder.product);
        holder.totalqty.setText(formatter.format(Double.parseDouble(product.getTotalQty())));
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(product.getPriceUnit()))));
        holder.subtotal.setText("IDR " + formatter.format(Double.parseDouble(product.getPriceTotal())));
        for (int i = 0; i<product.getSizeInfo().size(); i++){
            if (product.getSizeInfo().get(i).getProductSizeId() == 1){
                holder.container_s.setVisibility(View.VISIBLE);
                holder.qtyS.setText(String.valueOf(product.getSizeInfo().get(i).getProductQty()));
            }
            else if (product.getSizeInfo().get(i).getProductSizeId() == 2){
                holder.container_m.setVisibility(View.VISIBLE);
                holder.qtyM.setText(String.valueOf(product.getSizeInfo().get(i).getProductQty()));
            }
            else if (product.getSizeInfo().get(i).getProductSizeId() == 3){
                holder.container_l.setVisibility(View.VISIBLE);
                holder.qtyL.setText(String.valueOf(product.getSizeInfo().get(i).getProductQty()));
            }
            else if (product.getSizeInfo().get(i).getProductSizeId() == 4){
                holder.container_xl.setVisibility(View.VISIBLE);
                holder.qtyXL.setText(String.valueOf(product.getSizeInfo().get(i).getProductQty()));
            }
            else if (product.getSizeInfo().get(i).getProductSizeId() == 5){
                holder.container_free.setVisibility(View.VISIBLE);
                holder.qtyFree.setText(String.valueOf(product.getSizeInfo().get(i).getProductQty()));
            }
        }
        holder.container_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailclick){
                    holder.container_qtydetail.setVisibility(View.VISIBLE);
                    detailclick = true;
                    holder.caret.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_qtydetail.setVisibility(View.GONE);
                    detailclick = false;
                    holder.caret.setImageResource(R.drawable.caret);
                }
            }
        });
        holder.viewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra(PRODUCT_ID, product.getProductId().toString());
                Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                context.startActivity(intent, bundle);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(1, product);
            }
        });
    }
    private void api_deleteproduct(final ProductInfo product){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        service = apiUtils.getAPIService();
        service.req_delete_product(token, String.valueOf(product.getProductId())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(context, ShoppingBag.class);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                initanim(intent);
                            }
                            ((Activity) context).finish();
                        }
                        else{
                            String message = jsonResults.getString("message");
                            Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initDialog(3, product);
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemshoppingbagproduct_tvsubtotal) TextView subtotal;
        @BindView(R.id.itemshoppingbagproduct_tvprice) TextView price;
        @BindView(R.id.itemshoppingbagproduct_tv_productname) TextView productname;
        @BindView(R.id.itemshoppingbagproduct_totalqty) TextView totalqty;
        @BindView(R.id.itemshoppingbagproduct_qtyXL) TextView qtyXL;
        @BindView(R.id.itemshoppingbagproduct_qtyM) TextView qtyM;
        @BindView(R.id.itemshoppingbagproduct_qtyL) TextView qtyL;
        @BindView(R.id.itemshoppingbagproduct_qtyFree) TextView qtyFree;
        @BindView(R.id.itemshoppingbagproduct_qtyS) TextView qtyS;
        @BindView(R.id.itemshoppingbagproduct_ln_xl) LinearLayout container_xl;
        @BindView(R.id.itemshoppingbagproduct_ln_l) LinearLayout container_l;
        @BindView(R.id.itemshoppingbagproduct_ln_s) LinearLayout container_s;
        @BindView(R.id.itemshoppingbagproduct_ln_m) LinearLayout container_m;
        @BindView(R.id.itemshoppingbagproduct_ln_free) LinearLayout container_free;
        @BindView(R.id.itemshoppingbagproduct_ln_qtydetail) LinearLayout container_qtydetail;
        @BindView(R.id.itemshoppingbagproduct_ln_qty) LinearLayout container_qty;
        @BindView(R.id.itemshoppingbagproduct_imgcaret) ImageView caret;
        @BindView(R.id.itemshoppingbagproduct_img_product) ImageView product;
        @BindView(R.id.itemshoppingbagproduct_img_delete) ImageView delete;
        @BindView(R.id.itemshoppingbagproduct_btn_viewproduct) Button viewproduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void initDialog(int stats, final ProductInfo product){

        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Delete Confirmation")
                    .setContentText("Are you sure you want to delete ?")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance
                            api_deleteproduct(product);
                            sDialog.setTitleText("Deleted!")
                                    .setContentText("Your imaginary file has been deleted!")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if (stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
    private void initanim(Intent intent){
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
            context.startActivity(intent,bundle);
        }
    }
}
