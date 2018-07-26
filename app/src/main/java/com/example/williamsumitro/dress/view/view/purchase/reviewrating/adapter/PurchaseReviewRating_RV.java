package com.example.williamsumitro.dress.view.view.purchase.reviewrating.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.ProductRating;
import com.example.williamsumitro.dress.view.model.Purchase_ReviewRatingResult;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.adapter.SpinBankAdapter;
import com.example.williamsumitro.dress.view.view.purchase.reviewrating.fragment.P_reviewratingFragment;
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

/**
 * Created by William Sumitro on 7/1/2018.
 */

public class PurchaseReviewRating_RV extends RecyclerView.Adapter<PurchaseReviewRating_RV.ViewHolder>{
    private Context context;
    private ArrayList<Purchase_ReviewRatingResult> result;
    private DecimalFormat formatter;
    private Dialog dialog;
    private SpinBankAdapter spinBankAdapter;
    private String company_bank_id;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private SweetAlertDialog sweetAlertDialog;
    private Boolean checked_star1 = false, checked_star2 = false, checked_star3 = false, checked_star4 = false, checked_star5 = false;
    private String storerating = "0";
    private PurchaseReviewRatingDetail_RV rvadapter;
    private ArrayList<ProductRating> productRatingArrayList;
    private P_reviewratingFragment reviewratingFragment;

    private final static String PRODUCT = "PRODUCT";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";
    private final static String STORE_ID = "STORE_ID";
    private final static String STORE_PHOTO = "STORE_PHOTO";
    private final static String STORE_NAME = "STORE_NAME";

    public PurchaseReviewRating_RV(Context context, ArrayList<Purchase_ReviewRatingResult> result, P_reviewratingFragment reviewratingFragment){
        this.context = context;
        this.result = result;
        this.reviewratingFragment = reviewratingFragment;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_reviewrating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Purchase_ReviewRatingResult reviewRatingResult = result.get(position);
        holder.accepted.setText("Accepted Product : " + reviewRatingResult.getAccepted());
        holder.rejected.setText("Rejected Product : " + reviewRatingResult.getRejected());
        holder.ordernumber.setText(reviewRatingResult.getOrderNumber());
        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_review(reviewRatingResult.getTransactionId().toString(), reviewRatingResult.getProduct(), reviewRatingResult.getStoreId().toString(),
                        reviewRatingResult.getStoreName(), reviewRatingResult.getStorePhoto());
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasereviewrating_tv_accept) TextView accepted;
        @BindView(R.id.item_purchasereviewrating_tv_reject) TextView rejected;
        @BindView(R.id.item_purchasereviewrating_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchasereviewrating_btn_rate) Button rate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    private void dialog_review(final String transactionid, final ArrayList<ProductInfo> productArrayList,
                               final String storeid, final String storename, final String storephoto){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_review);
        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.dialog_review_rv);
        final Button submit = (Button) dialog.findViewById(R.id.dialog_review_btn_submit);
        final CircleImageView store_photo = (CircleImageView) dialog.findViewById(R.id.dialog_review_circleimageview);
        final ImageView star1 = (ImageView) dialog.findViewById(R.id.dialog_review_img_star1);
        final ImageView star2 = (ImageView) dialog.findViewById(R.id.dialog_review_img_star2);
        final ImageView star3 = (ImageView) dialog.findViewById(R.id.dialog_review_img_star3);
        final ImageView star4 = (ImageView) dialog.findViewById(R.id.dialog_review_img_star4);
        final ImageView star5 = (ImageView) dialog.findViewById(R.id.dialog_review_img_star5);

        Picasso.with(context)
                .load(storephoto)
                .placeholder(R.drawable.default_photo)
                .into(store_photo);

        rvadapter = new PurchaseReviewRatingDetail_RV(context, productArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(rvadapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog("",2, transactionid, storeid, productArrayList);
            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setImageResource(0);
                star2.setImageResource(0);
                star3.setImageResource(0);
                star4.setImageResource(0);
                star5.setImageResource(0);
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
                star1.setImageResource(0);
                star2.setImageResource(0);
                star3.setImageResource(0);
                star4.setImageResource(0);
                star5.setImageResource(0);
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
                star1.setImageResource(0);
                star2.setImageResource(0);
                star3.setImageResource(0);
                star4.setImageResource(0);
                star5.setImageResource(0);
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
                star1.setImageResource(0);
                star2.setImageResource(0);
                star3.setImageResource(0);
                star4.setImageResource(0);
                star5.setImageResource(0);
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
                star1.setImageResource(0);
                star2.setImageResource(0);
                star3.setImageResource(0);
                star4.setImageResource(0);
                star5.setImageResource(0);
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
        dialog.show();
    }
    private void initDialog(final String message, int stats, final String transactionid, final String storeid, final ArrayList<ProductInfo> productArrayList){

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
                            api_submitreviewrating(transactionid, storeid, productArrayList);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }

    }
    private void api_submitreviewrating(String transactionid, String storeid, ArrayList<ProductInfo> productArrayList){
        productRatingArrayList = rvadapter.retrivedata();
        boolean chck = false;
        if (productRatingArrayList.size()>0){
            for (int i = 0; i<productRatingArrayList.size();i++){
                if (productRatingArrayList.get(i).getReview().equals("")){
                    Toasty.info(context, "Please insert your review for " + productArrayList.get(i).getProductName(), Toast.LENGTH_SHORT, true).show();
                    chck = true;
                    break;
                }
                else {
                    chck = false;
                }
            }
            if (!chck){
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
        else {
            Toasty.info(context, "Please insert your review to all product", Toast.LENGTH_SHORT, true).show();
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
                            sweetAlertDialog.dismiss();
                            dialog.dismiss();
                            reviewratingFragment.initRefresh();
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

}
