package com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.adapter;

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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.CheckApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class OC_ProductRVAdapter extends RecyclerView.Adapter<OC_ProductRVAdapter.ViewHolder>{
    private ArrayList<Product> productArrayList;
    private Context context;
    private DecimalFormat formatter;
    private List<String> sizelist;
    private Boolean detailclick = false;
    private String token, status;
    SessionManagement sessionManagement;
    private apiService service;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    private ArrayList<CheckApproveOrderProduct> checkApproveOrderProductArrayList;
    private RadioButton acceptreject;
    private RadioButton lastCheckedRB = null;
    private String product_id, transactionid;

//    public OC_ProductRVAdapter(ArrayList<ApproveOrderProduct> approveOrderProductArrayList, ArrayList<Product> productArrayList, Context context){
//        this.context = context;
//        this.productArrayList = productArrayList;
//        this.approveOrderProductArrayList = approveOrderProductArrayList;
//        sessionManagement = new SessionManagement(context);
//        HashMap<String, String> user = sessionManagement.getUserDetails();
//        token = user.get(SessionManagement.TOKEN);
//    }
    public OC_ProductRVAdapter(String transactionid,ArrayList<Product> productArrayList, Context context){
        this.context = context;
        this.productArrayList = productArrayList;
        this.transactionid = transactionid;
        approveOrderProductArrayList = new ArrayList<>();
        checkApproveOrderProductArrayList = new ArrayList<>();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
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

//        int size_transaction = checkApproveOrderProductArrayList.size();
//        int size_order;
//        int temp_j = 0, temp_i = 0;
//        CheckApproveOrderProduct checkApproveOrderProduct;
//        ApproveOrderProduct approveOrderProduct;
//        String temp_transactionid = null, temp_productid = null;
//        boolean check_transaction = false, check_order = false;
//        Toast.makeText(context, "Transaction id yang berhasil masuk : " + transactionid, Toast.LENGTH_LONG).show();
//        if (size_transaction>0){
//            Toast.makeText(context, "Panjang transaction : " + size_transaction, Toast.LENGTH_LONG).show();
//            for (int i = 0; i<size_transaction;i++){
//                temp_transactionid = checkApproveOrderProductArrayList.get(i).getTransaction_id();
//                if (temp_transactionid.equals(transactionid)){
//                    Toast.makeText(context, "Transaction id yang berhasil masuk : " + temp_transactionid, Toast.LENGTH_LONG).show();
//                    size_order = checkApproveOrderProductArrayList.get(i).getApproveOrderProductArrayList().size();
//                    Toast.makeText(context, "Panjang order : " + size_order, Toast.LENGTH_LONG).show();
//                    for (int j = 0; j<size_order;j++){
//                        temp_productid = checkApproveOrderProductArrayList.get(i).getApproveOrderProductArrayList().get(j).getProduct_id();
//                        if (temp_productid.equals(product_id)){
//                            Toast.makeText(context, "Product id yang berhasil masuk : " + temp_productid, Toast.LENGTH_LONG).show();
//                            check_order = true;
//                            temp_j = j;
//                            break;
//                        }
//                        else {
//                            check_order = false;
//                        }
//                    }
//                    temp_i = i;
//                    check_transaction = true;
//                    break;
//                }else {
//                    check_transaction = false;
//                }
//            }
//            if (check_transaction){
//                approveOrderProduct = new ApproveOrderProduct(product_id, status);
//                if (check_order){
//                    Toast.makeText(context, "Transaction id yang dua2nya di set : " + transactionid, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Product id yang dua2nya di set : " + product_id, Toast.LENGTH_LONG).show();
//                    approveOrderProductArrayList.set(temp_j, approveOrderProduct);
//                    checkApproveOrderProduct = new CheckApproveOrderProduct(transactionid, approveOrderProductArrayList);
//                    checkApproveOrderProductArrayList.set(temp_i, checkApproveOrderProduct);
//                }
//                else {
//                    Toast.makeText(context, "Transaction id yang satu di set : " + transactionid, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Product id yang satu di set : " + product_id, Toast.LENGTH_LONG).show();
//                    approveOrderProductArrayList.add(approveOrderProduct);
//                    checkApproveOrderProduct = new CheckApproveOrderProduct(transactionid, approveOrderProductArrayList);
//                    checkApproveOrderProductArrayList.set(temp_i, checkApproveOrderProduct);
//                }
//            }else {
//                Toast.makeText(context, "Transaction id yang dua2nya di add : " + transactionid, Toast.LENGTH_LONG).show();
//                Toast.makeText(context, "Product id yang dua2nya di add : " + product_id, Toast.LENGTH_LONG).show();
//                approveOrderProductArrayList = new ArrayList<>();
//                approveOrderProduct = new ApproveOrderProduct(product_id, status);
//                approveOrderProductArrayList.add(approveOrderProduct);
//                checkApproveOrderProduct = new CheckApproveOrderProduct(transactionid, approveOrderProductArrayList);
//                checkApproveOrderProductArrayList.add(checkApproveOrderProduct);
//            }
//        }else {
//            approveOrderProductArrayList = new ArrayList<>();
//            Toast.makeText(context, "Masuk duluan", Toast.LENGTH_LONG).show();
//            approveOrderProduct = new ApproveOrderProduct(product_id, status);
//            approveOrderProductArrayList.add(approveOrderProduct);
//            checkApproveOrderProduct = new CheckApproveOrderProduct(transactionid, approveOrderProductArrayList);
//            checkApproveOrderProductArrayList.add(checkApproveOrderProduct);
//        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        sizelist = new ArrayList<>();
        final Product product = productArrayList.get(position);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,5,10,5);
        holder.cardView.setLayoutParams(lp);
        formatter = new DecimalFormat("#,###,###");
        holder.productname.setText(product.getProductName());
        Picasso.with(context)
                .load(product.getProductPhoto())
                .placeholder(R.drawable.logo404)
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
