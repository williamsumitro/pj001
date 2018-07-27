package com.example.williamsumitro.dress.view.view.sales.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.CheckApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class OC_ProductRVAdapter extends RecyclerView.Adapter<OC_ProductRVAdapter.ViewHolder>{
    private ArrayList<ProductInfo> productArrayList;
    private Context context;
    private DecimalFormat formatter;
    private List<String> sizelist;
    private Boolean detailclick = false;
    private String status;
    private apiService service;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    private ArrayList<CheckApproveOrderProduct> checkApproveOrderProductArrayList;
    private RadioButton acceptreject;
    private RadioButton lastCheckedRB = null;
    private String product_id;

    public OC_ProductRVAdapter(ArrayList<ProductInfo> productArrayList, Context context){
        this.context = context;
        this.productArrayList = productArrayList;
        approveOrderProductArrayList = new ArrayList<>();
        checkApproveOrderProductArrayList = new ArrayList<>();
        PicassoTools.clearCache(Picasso.with(context));
    }
    public ArrayList<ApproveOrderProduct> retrivedata(){
        return approveOrderProductArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_checkout_product, parent, false);
        return new ViewHolder(itemView);
    }
    private void checkstat(String status, String product_id){
        int size_approveproduct = approveOrderProductArrayList.size();
        boolean ketemu = false;
        int posisi = -1;
        if (size_approveproduct>0){
            for (int i = 0; i<approveOrderProductArrayList.size();i ++){
                if (approveOrderProductArrayList.get(i).getProduct_id().equals(product_id)){
                    ketemu = true;
                    posisi = i;
                    break;
                }
            }
            if (ketemu){
                ApproveOrderProduct approveOrderProduct = new ApproveOrderProduct(product_id, status);
                approveOrderProductArrayList.set(posisi, approveOrderProduct);
            }
            else {
                ApproveOrderProduct approveOrderProduct = new ApproveOrderProduct(product_id, status);
                approveOrderProductArrayList.add(approveOrderProduct);
            }
        }
        else {
            ApproveOrderProduct approveOrderProduct = new ApproveOrderProduct(product_id, status);
            approveOrderProductArrayList.add(approveOrderProduct);
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        sizelist = new ArrayList<>();
        final ProductInfo product = productArrayList.get(position);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,5,10,5);
        holder.cardView.setLayoutParams(lp);
        formatter = new DecimalFormat("#,###,###");
        holder.productname.setText(product.getProductName());
        Picasso.with(context)
                .load(product.getProductPhoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_product)
                .into(holder.product);
        holder.container_acceptreject.setVisibility(View.VISIBLE);
        holder.totalqty.setText(formatter.format(Double.parseDouble(product.getTotalQty())));
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(product.getPriceUnit()))));
        holder.subtotal.setText("IDR " + formatter.format(Double.parseDouble(product.getPriceTotal())));
        holder.accept.setChecked(true);
        if (holder.accept.isChecked()){
            status = "1";
            product_id = String.valueOf(product.getProductId());
            checkstat(status, product_id);
        }
        if (holder.reject.isChecked()){
            status = "2";
            product_id = String.valueOf(product.getProductId());
            checkstat(status, product_id);
        }
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "1";
                product_id = String.valueOf(product.getProductId());
                checkstat(status, product_id);
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "2";
                product_id = String.valueOf(product.getProductId());
                checkstat(status, product_id);
            }
        });
//        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton checked_rb = (RadioButton) group.findViewById(checkedId);
//                if (lastCheckedRB != null) {
//                    lastCheckedRB.setChecked(false);
//                }
//                lastCheckedRB = checked_rb;
//            }
//        });

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
        @BindView(R.id.itemcheckoutproduct_tvsubtotal)
        TextView subtotal;
        @BindView(R.id.itemcheckoutproduct_tvprice) TextView price;
        @BindView(R.id.itemcheckoutproduct_tv_productname) TextView productname;
        @BindView(R.id.itemcheckoutproduct_totalqty) TextView totalqty;
        @BindView(R.id.itemcheckoutproduct_qtyXL) TextView qtyXL;
        @BindView(R.id.itemcheckoutproduct_qtyM) TextView qtyM;
        @BindView(R.id.itemcheckoutproduct_qtyL)
        TextView qtyL;
        @BindView(R.id.itemcheckoutproduct_qtyFree) TextView qtyFree;
        @BindView(R.id.itemcheckoutproduct_qtyS) TextView qtyS;
        @BindView(R.id.itemcheckoutproduct_ln_xl)
        LinearLayout container_xl;
        @BindView(R.id.itemcheckoutproduct_ln_l) LinearLayout container_l;
        @BindView(R.id.itemcheckoutproduct_ln_s) LinearLayout container_s;
        @BindView(R.id.itemcheckoutproduct_ln_m) LinearLayout container_m;
        @BindView(R.id.itemcheckoutproduct_ln_free) LinearLayout container_free;
        @BindView(R.id.itemcheckoutproduct_ln_qtydetail) LinearLayout container_qtydetail;
        @BindView(R.id.itemcheckoutproduct_ln_qty) LinearLayout container_qty;
        @BindView(R.id.itemcheckoutproduct_imgcaret)
        ImageView caret;
        @BindView(R.id.itemcheckoutproduct_img_product) ImageView product;
        @BindView(R.id.itemcheckoutproduct_ln_acceptreject) LinearLayout container_acceptreject;
        @BindView(R.id.itemcheckoutproduct_radiogroup)
        RadioGroup radioGroup;
        @BindView(R.id.itemcheckoutproduct_rbAccept)
        RadioButton accept;
        @BindView(R.id.itemcheckoutproduct_rbReject) RadioButton reject;
        @BindView(R.id.itemcheckoutproduct_cardview) CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
