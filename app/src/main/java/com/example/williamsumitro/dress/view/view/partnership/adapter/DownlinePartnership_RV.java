package com.example.williamsumitro.dress.view.view.partnership.adapter;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Addproduct_Price;
import com.example.williamsumitro.dress.view.model.DownlinePartnershipItem;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.partnership.activity.DownlinePartnership;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William Sumitro on 7/8/2018.
 */

public class DownlinePartnership_RV extends RecyclerView.Adapter<DownlinePartnership_RV.ViewHolder> {


    private Context context;
    private ArrayList<DownlinePartnershipItem> itemArrayList;
    private Dialog dialog;
    private apiService service;
    private BuyRVAdapter pricedetailsadapter;
    private ArrayList<Price> priceList;
    private List<Addproduct_Price> container_price;
    private DecimalFormat formatter;
    private int index;
    private ProgressDialog progressDialog;
    private SessionManagement sessionManagement;
    private String token;

    public DownlinePartnership_RV(Context context, ArrayList<DownlinePartnershipItem> itemArrayList){
        this.context = context;
        this.itemArrayList = itemArrayList;
        priceList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approve_partnership, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        service = apiUtils.getAPIService();
        final DownlinePartnershipItem item = itemArrayList.get(position);
        holder.storename.setText(item.getProduct().getStoreNamePartner());
        holder.productname.setText(item.getProduct().getProductName());
        Picasso.with(context)
                .load(item.getProduct().getPhoto())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Uploading, please wait ....");
                progressDialog.show();
                service.req_accept_partnership(token, item.getProduct().getPartnershipId().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {if(response.isSuccessful()){
                        Log.i("debug", "onResponse: SUCCESS");
                        try{
                            JSONObject jsonResults = new JSONObject(response.body().string());
                            if(jsonResults.getString("message").toLowerCase().equals("partnership accepted")){
                                String message = jsonResults.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                Bundle bundle = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                    Intent intent = new Intent(context, DownlinePartnership.class);
                                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                                    context.startActivity(intent, bundle);
                                    DownlinePartnership.DOWNLINEPARTNERSHIP.finish();
                                }
                            }else{
                                String message = jsonResults.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.i("debug", "onResponse: FAILED");
                    }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Uploading, please wait ....");
                progressDialog.show();
                service.req_reject_partnership(token, item.getProduct().getPartnershipId().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").toLowerCase().equals("partnership rejected")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    Bundle bundle = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        Intent intent = new Intent(context, DownlinePartnership.class);
                                        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                                        context.startActivity(intent, bundle);
                                        DownlinePartnership.DOWNLINEPARTNERSHIP.finish();
                                    }
                                }else{
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(item.getProduct().getStoreName(), item.getStore_name_partner(), item.getProduct().getPrice(), item.getProduct().getRequestPrice());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_approvepartnership_btn_accept) Button accept;
        @BindView(R.id.item_approvepartnership_tv_productname) TextView productname;
        @BindView(R.id.item_approvepartnership_tv_storename) TextView storename;
        @BindView(R.id.item_approvepartnership_img_product) ImageView imageView;
        @BindView(R.id.item_approvepartnership_container) CardView container;
        @BindView(R.id.item_approvepartnership_btn_viewdetails) Button viewdetails;
        @BindView(R.id.item_approvepartnership_btn_reject) Button reject;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void initDialog(final String store_name, final String storerequest_name, final ArrayList<Price> priceList, final ArrayList<Price> requestPriceArrayList){
        index = 0;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.approvepartnership_dialog);

        final TextView storerequestname = (TextView) dialog.findViewById(R.id.approvepartnershipdialog_tv_storerequestname);
        final TextView storename = (TextView) dialog.findViewById(R.id.approvepartnershipdialog_tv_storename);
        final RecyclerView rvrequest = (RecyclerView) dialog.findViewById(R.id.approvepartnershipdialog_rv_request);
        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.approvepartnershipdialog_rv);
        final Button close = (Button) dialog.findViewById(R.id.approvepartnershipdialog_btn_close);
        storename.setText(store_name);
        storerequestname.setText(storerequest_name);
        pricedetailsadapter = new BuyRVAdapter(priceList, context);
        RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(horizontallayout);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(pricedetailsadapter);

        pricedetailsadapter = new BuyRVAdapter(requestPriceArrayList, context);
        RecyclerView.LayoutManager horizontallayout1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvrequest.setLayoutManager(horizontallayout1);
        rvrequest.setItemAnimator(new DefaultItemAnimator());
        rvrequest.setAdapter(pricedetailsadapter);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
