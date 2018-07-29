package com.example.williamsumitro.dress.view.view.purchase.reviewrating.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.ProductRating;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
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
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
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
    private ArrayList<ProductInfo> productArrayList;
    private ArrayList<ProductRating> productRatingArrayList;
    private SweetAlertDialog sweetAlertDialog;
    private PurchaseReviewRatingDetail_RV rvadapter;
    private Boolean checked_star1 = false, checked_star2 = false, checked_star3 = false, checked_star4 = false, checked_star5 = false;
    private SnapHelper snapHelper = new LinearSnapHelper();

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
                .placeholder(R.drawable.default_photo)
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
                    initDialog("",2);
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
                    Toasty.info(context, "Please insert your review for " + productArrayList.get(i).getProductName(), Toast.LENGTH_SHORT, true).show();
                    check = false;
                    break;
                }
                else
                    check = true;
            }
        }
        else {
            check = false;
            Toasty.info(context, "Please insert your review to all product", Toast.LENGTH_SHORT, true).show();
        }
        return check;
    }
    private void api_submitreviewrating(){
        productRatingArrayList = rvadapter.retrivedata();
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
                                initDialog1(message, 1);
                            }else{
                                String message = jsonResults.getString("message");
                                progressDialog.dismiss();
                                initDialog1(message, 0);
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
                    progressDialog.dismiss();
                    initDialog1("", 0);
                }
            });
        }
    }

    private void setupRV(){
        rvadapter = new PurchaseReviewRatingDetail_RV(context, productArrayList);
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
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
            productArrayList = (ArrayList<ProductInfo>) getintent.getSerializableExtra(PRODUCT);
            transactionid = getintent.getStringExtra(TRANSACTION_ID);
            storeid = getintent.getStringExtra(STORE_ID);
            storename = getintent.getStringExtra(STORE_NAME);
            storephoto = getintent.getStringExtra(STORE_PHOTO);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initDialog(final String message, int stats){

        if (stats == 2){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Save")
                    .setContentText("Are you sure submit ?")
                    .setContentText("Once submit you cannot undo")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            api_submitreviewrating();
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }

    }
    private void initDialog1(final String message, int stats){
        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Success!")
                    .setContentText(message)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Toasty.info(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                            finish();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if (stats == 3){
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
