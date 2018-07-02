package com.example.williamsumitro.dress.view.view.purchase.reviewrating.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductRating;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class PurchaseReviewRatingDetail_RV extends RecyclerView.Adapter<PurchaseReviewRatingDetail_RV.ViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;
    private ArrayList<ProductRating> productRatingArrayList;
    private String rating = "0";
    private Boolean checked_star1 = false, checked_star2 = false, checked_star3 = false, checked_star4 = false, checked_star5 = false;

    public PurchaseReviewRatingDetail_RV(Context context, ArrayList<Product> productArrayList){
        this.context = context;
        this.productArrayList = productArrayList;
        productRatingArrayList = new ArrayList<>();
    }

    public ArrayList<ProductRating> retrivedata(){
        return productRatingArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reviewandrating, parent, false);
        return new ViewHolder(itemView);
    }
    private void clearImage(ViewHolder holder){
        holder.star1.setImageResource(0);
        holder.star2.setImageResource(0);
        holder.star3.setImageResource(0);
        holder.star4.setImageResource(0);
        holder.star5.setImageResource(0);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Product product = productArrayList.get(position);
        holder.product_name.setText(product.getProductName());
        Picasso.with(context)
                .load(product.getProductPhoto())
                .placeholder(R.drawable.logo404)
                .into(holder.product);
        holder.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage(holder);
                if (checked_star1){
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star0);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "0", holder.review.getText().toString());
                }else {
                    checked_star1 = true;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "1", holder.review.getText().toString());
                }
            }
        });
        holder.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage(holder);
                if (checked_star2){
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star0);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "0", holder.review.getText().toString());
                }
                else {
                    checked_star2 = true;
                    checked_star1 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star);
                    holder.star2.setImageResource(R.drawable.star);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "2", holder.review.getText().toString());
                }

            }
        });
        holder.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage(holder);
                if (checked_star3){
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star0);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "0", holder.review.getText().toString());
                }
                else {
                    checked_star3 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star);
                    holder.star2.setImageResource(R.drawable.star);
                    holder.star3.setImageResource(R.drawable.star);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "3", holder.review.getText().toString());
                }
            }
        });
        holder.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage(holder);
                if (checked_star4){
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star0);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "0", holder.review.getText().toString());
                }
                else {
                    checked_star4 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star);
                    holder.star2.setImageResource(R.drawable.star);
                    holder.star3.setImageResource(R.drawable.star);
                    holder.star4.setImageResource(R.drawable.star);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "4", holder.review.getText().toString());
                }
            }
        });
        holder.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage(holder);
                if (checked_star5){
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    holder.star1.setImageResource(R.drawable.star0);
                    holder.star2.setImageResource(R.drawable.star0);
                    holder.star3.setImageResource(R.drawable.star0);
                    holder.star4.setImageResource(R.drawable.star0);
                    holder.star5.setImageResource(R.drawable.star0);
                    updateProductArrayList(product, "0", holder.review.getText().toString());
                }
                else {
                    checked_star5 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    holder.star1.setImageResource(R.drawable.star);
                    holder.star2.setImageResource(R.drawable.star);
                    holder.star3.setImageResource(R.drawable.star);
                    holder.star4.setImageResource(R.drawable.star);
                    holder.star5.setImageResource(R.drawable.star);
                    updateProductArrayList(product, "5", holder.review.getText().toString());
                }
            }
        });
        holder.review.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateProductArrayList(product, "", s.toString());
            }
        });
    }
    private void updateProductArrayList(Product product, String rating, String review){
        Boolean ketemu = false;
        Integer index = -1;
        if (productRatingArrayList.size()>0){
            for (int i = 0; i<productRatingArrayList.size();i++){
                if (productRatingArrayList.get(i).getProduct_id().equals(product.getProductId().toString())){
                    ketemu = true;
                    index = i;
                    break;
                }else {
                    ketemu = false;
                }
            }
            if (ketemu){
                ProductRating productRating;
                if (rating.equals("")){
                    productRating = new ProductRating(product.getProductId().toString(), productRatingArrayList.get(index).getRating(), review);
                }else {
                    productRating = new ProductRating(product.getProductId().toString(), rating, review);
                }
                productRatingArrayList.set(index, productRating);
            }
            else {
                ProductRating productRating = new ProductRating(product.getProductId().toString(), rating, review);
                productRatingArrayList.add(productRating);
            }
        }
        else{
            ProductRating productRating = new ProductRating(product.getProductId().toString(), rating, review);
            productRatingArrayList.add(productRating);
        }
    }
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_reviewandrating_et_review) EditText review;
        @BindView(R.id.item_reviewandrating_tv_productname) TextView product_name;
        @BindView(R.id.item_reviewandrating_img_star5) ImageView star5;
        @BindView(R.id.item_reviewandrating_img_star4) ImageView star4;
        @BindView(R.id.item_reviewandrating_img_star3) ImageView star3;
        @BindView(R.id.item_reviewandrating_img_star2) ImageView star2;
        @BindView(R.id.item_reviewandrating_img_star1) ImageView star1;
        @BindView(R.id.item_reviewandrating_img_product) ImageView product;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
