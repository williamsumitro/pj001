package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 14/06/2018.
 */

public class CheckoutProductRVAdapter extends RecyclerView.Adapter<CheckoutProductRVAdapter.ViewHolder> {
    private ArrayList<ProductInfo> productArrayList;
    private Context context;
    private DecimalFormat formatter;
    private List<String> sizelist;
    private Boolean detailclick = false;
    private String token;
    SessionManagement sessionManagement;
    private apiService service;

    public CheckoutProductRVAdapter(ArrayList<ProductInfo> productArrayList, Context context){
        this.context = context;
        this.productArrayList = productArrayList;
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkout_product, parent, false);
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
                .placeholder(R.drawable.default_product)
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
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemcheckoutproduct_tvsubtotal) TextView subtotal;
        @BindView(R.id.itemcheckoutproduct_tvprice) TextView price;
        @BindView(R.id.itemcheckoutproduct_tv_productname) TextView productname;
        @BindView(R.id.itemcheckoutproduct_totalqty) TextView totalqty;
        @BindView(R.id.itemcheckoutproduct_qtyXL) TextView qtyXL;
        @BindView(R.id.itemcheckoutproduct_qtyM) TextView qtyM;
        @BindView(R.id.itemcheckoutproduct_qtyL) TextView qtyL;
        @BindView(R.id.itemcheckoutproduct_qtyFree) TextView qtyFree;
        @BindView(R.id.itemcheckoutproduct_qtyS) TextView qtyS;
        @BindView(R.id.itemcheckoutproduct_ln_xl) LinearLayout container_xl;
        @BindView(R.id.itemcheckoutproduct_ln_l) LinearLayout container_l;
        @BindView(R.id.itemcheckoutproduct_ln_s) LinearLayout container_s;
        @BindView(R.id.itemcheckoutproduct_ln_m) LinearLayout container_m;
        @BindView(R.id.itemcheckoutproduct_ln_free) LinearLayout container_free;
        @BindView(R.id.itemcheckoutproduct_ln_qtydetail) LinearLayout container_qtydetail;
        @BindView(R.id.itemcheckoutproduct_ln_qty) LinearLayout container_qty;
        @BindView(R.id.itemcheckoutproduct_imgcaret) ImageView caret;
        @BindView(R.id.itemcheckoutproduct_img_product) ImageView product;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
