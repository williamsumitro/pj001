package com.example.williamsumitro.dress.view.view.purchase.reviewrating.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductRating;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.adapter.PurchaseReviewRatingDetail_RV;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseReviewRatingDetail extends AppCompatActivity {
    @BindView(R.id.purchasereviewratingdetail_rv) RecyclerView recyclerView;
    @BindView(R.id.purchasereviewratingdetail_toolbar) Toolbar toolbar;
    @BindView(R.id.purchasereviewratingdetail_btn_submit) Button submit;
    @BindView(R.id.purchasereviewratingdetail_circleimageview) CircleImageView store_photo;
    @BindView(R.id.purchasereviewratingdetail_img_star1) ImageView star1;
    @BindView(R.id.purchasereviewratingdetail_img_star2) ImageView star2;
    @BindView(R.id.purchasereviewratingdetail_img_star3) ImageView star3;
    @BindView(R.id.purchasereviewratingdetail_img_star4) ImageView star4;
    @BindView(R.id.purchasereviewratingdetail_img_star5) ImageView star5;

    private Context context;
    private apiService service;
    private String token, transactionid, storeid, storephoto, storename, storerating = "0";
    private SessionManagement sessionManagement;
    private DecimalFormat formatter;
    private ProgressDialog progressDialog;
    private ArrayList<Product> productArrayList;
    private ArrayList<ProductRating> productRatingArrayList;
    private Dialog dialog;
    private PurchaseReviewRatingDetail_RV rvadapter;
    private Boolean checked_star1 = false, checked_star2 = false, checked_star3 = false, checked_star4 = false, checked_star5 = false;

    private final static String PRODUCT = "PRODUCT";
    private final static String STORE_ID = "STORE_ID";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";
    private final static String STORE_PHOTO = "STORE_PHOTO";
    private final static String STORE_NAME = "STORE_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_review_rating_detail);
        initView();
        setuptoolbar();
        initGetIntent();
        initObject();
        setupRV();
        initOnClick();
    }

    private void initObject() {
        Picasso.with(context)
                .load(storephoto)
                .placeholder(R.drawable.logo404)
                .into(store_photo);
    }
    private void clearImage(){
        star1.setImageResource(0);
        star2.setImageResource(0);
        star3.setImageResource(0);
        star4.setImageResource(0);
        star5.setImageResource(0);
    }
    private void initOnClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productRatingArrayList = rvadapter.retrivedata();
//                for (int i = 0; i<productRatingArrayList.size();i++){
//                    Toast.makeText(context, productRatingArrayList.get(i).getProduct_id().toLowerCase() + " " + productRatingArrayList.get(i).getRating() + " " + productRatingArrayList.get(i).getReview(), Toast.LENGTH_SHORT).show();
//                }
                api_submitreviewrating();
            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage();
                if (checked_star1){
                    storerating = "0";
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star0);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }else {
                    storerating = "1";
                    checked_star1 = true;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage();
                if (checked_star2){
                    storerating = "0";
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star0);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
                else {
                    storerating = "2";
                    checked_star2 = true;
                    checked_star1 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star);
                    star2.setImageResource(R.drawable.star);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }

            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage();
                if (checked_star3){
                    storerating = "0";
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star0);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
                else {
                    storerating = "3";
                    checked_star3 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star);
                    star2.setImageResource(R.drawable.star);
                    star3.setImageResource(R.drawable.star);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage();
                if (checked_star4){
                    storerating = "0";
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star0);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
                else {
                    storerating = "4";
                    checked_star4 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star);
                    star2.setImageResource(R.drawable.star);
                    star3.setImageResource(R.drawable.star);
                    star4.setImageResource(R.drawable.star);
                    star5.setImageResource(R.drawable.star0);
                }
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearImage();
                if (checked_star5){
                    storerating = "0";
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    checked_star5 = false;
                    star1.setImageResource(R.drawable.star0);
                    star2.setImageResource(R.drawable.star0);
                    star3.setImageResource(R.drawable.star0);
                    star4.setImageResource(R.drawable.star0);
                    star5.setImageResource(R.drawable.star0);
                }
                else {
                    storerating = "5";
                    checked_star5 = true;
                    checked_star1 = false;
                    checked_star2 = false;
                    checked_star3 = false;
                    checked_star4 = false;
                    star1.setImageResource(R.drawable.star);
                    star2.setImageResource(R.drawable.star);
                    star3.setImageResource(R.drawable.star);
                    star4.setImageResource(R.drawable.star);
                    star5.setImageResource(R.drawable.star);
                }
            }
        });
    }
    private boolean checkreviewproduct(){
        Boolean check = false;
        if (productRatingArrayList.size()>0){
            for (int i = 0; i<productRatingArrayList.size();i++){
                if (productRatingArrayList.get(i).getReview().equals("")){
                    Toast.makeText(context, "Please insert your review for " + productArrayList.get(i).getProductName(), Toast.LENGTH_SHORT).show();
                    check = false;
                    break;
                }
                else
                    check = true;
            }
        }
        else {
            check = false;
            Toast.makeText(context, "Please insert your review to all product", Toast.LENGTH_SHORT).show();
        }
        return check;
    }
    private void api_submitreviewrating(){
        if (checkreviewproduct()){
            service = apiUtils.getAPIService();
            SubmitReviewRating submitreviewrating = new SubmitReviewRating(token, transactionid, storeid, storerating, productRatingArrayList);
            service.req_submit_review_rating(submitreviewrating).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code()==200){
                        try{
                            JSONObject jsonResults = new JSONObject(response.body().string());
                            if(jsonResults.getBoolean("status")){
                                String message = jsonResults.getString("message");
                                progressDialog.dismiss();
                                initDialog(message, 2);
                            }else{
                                String message = jsonResults.getString("message");
                                progressDialog.dismiss();
                                initDialog(message, 0);
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
                    initDialog("",3);
                }
            });
        }
    }

    private void setupRV(){
        rvadapter = new PurchaseReviewRatingDetail_RV(context, productArrayList);
        SnapHelper snapHelper = new LinearSnapHelper();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rvadapter);
        snapHelper.attachToRecyclerView(recyclerView);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(this);
        productArrayList = new ArrayList<>();
        productRatingArrayList = new ArrayList<>();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Review store and product");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(TRANSACTION_ID)){
            productArrayList = (ArrayList<Product>) getintent.getSerializableExtra(PRODUCT);
            transactionid = getintent.getStringExtra(TRANSACTION_ID);
            storeid = getintent.getStringExtra(STORE_ID);
            storename = getintent.getStringExtra(STORE_NAME);
            storephoto = getintent.getStringExtra(STORE_PHOTO);
        }
        else{
            Toast.makeText(context, "SOMETHING WRONG", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDialog(final String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 0){
            status.setText("Oops!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.red6);
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 2){
            status.setText("Confirmation");
            detail.setText("Are you sure to submit ? Once submit you cannot undo");
            bg.setBackgroundResource(R.color.orange7);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setText("I'm sure");
            buttoncancel.setVisibility(View.VISIBLE);
            buttoncancel.setBackgroundResource(R.drawable.button1_red);
            buttoncancel.setText("No");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    initDialog1(message, 1);
                }
            });
            buttoncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else if (stats == 3){
            bg.setBackgroundResource(R.color.red7);
            status.setText("Uh Oh!");
            detail.setText("There is a problem with internet connection or the server");
            button.setBackgroundResource(R.drawable.button1_red);
            button.setText("Try Again");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    private void initDialog1(final String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        Button buttoncancel = (Button) dialog.findViewById(R.id.customdialog_btncancel);
        if(stats == 1){
            status.setText("Success !");
            detail.setText(message);
            bg.setBackgroundResource(R.color.green7);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setText("Ok");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PurchaseReviewRating.PURCHASEREVIEWRATING.finish();
                    finish();
                    Intent intent = new Intent(context, PurchaseReviewRating.class);
                    initanim(intent);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
