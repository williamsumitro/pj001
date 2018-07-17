package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.williamsumitro.dress.view.FullScreenImage;
import com.example.williamsumitro.dress.view.model.Offer;
import com.example.williamsumitro.dress.view.model.RFQ_ActiveResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.offer.activity.AddMyOffer;
import com.example.williamsumitro.dress.view.view.request.activity.ActiveRequest;
import com.example.williamsumitro.dress.view.view.request.activity.RequestForQuotation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class ActiveRequestOfferRV extends RecyclerView.Adapter<ActiveRequestOfferRV.ViewHolder> {
    private Context context;
    private ArrayList<Offer> offers;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ProgressDialog progressDialog;
    private final static String IMAGE = "IMAGE";
    private String token;
    private SessionManagement sessionManagement;
    private apiService service;

    public ActiveRequestOfferRV(Context context, ArrayList<Offer> offers){
        this.context = context;
        this.offers = offers;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offerdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Offer offer = offers.get(position);
        Picasso.with(context)
                .load(offer.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.imageView);
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getTotalPrice()))));
        holder.storename.setText(offer.getStoreName());
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(offer.getPriceUnit()))));
        holder.location.setText(offer.getCityName());
        holder.description.setText(offer.getDescription());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, FullScreenImage.class);
                    intent.putExtra(IMAGE, offer.getPhoto().getFilePath());
                    bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_accept_request(token, offer.getRfqOfferId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_offerdetail_img) ImageView imageView;
        @BindView(R.id.item_offerdetail_btn_accept) Button accept;
        @BindView(R.id.item_offerdetail_tv_total) TextView total;
        @BindView(R.id.item_offerdetail_tv_storename) TextView storename;
        @BindView(R.id.item_offerdetail_tv_price) TextView price;
        @BindView(R.id.item_offerdetail_tv_location) TextView location;
        @BindView(R.id.item_offerdetail_tv_description) TextView description;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void api_accept_request(String token, String rfq_offer_id){
        service = apiUtils.getAPIService();
        service.req_accept_rfq_offer(token, rfq_offer_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").toLowerCase().equals("submitted successfully")){
                            Bundle bundle = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                Intent intent = new Intent(context, ActiveRequest.class);
                                bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                                ActiveRequest.ACTIVEREQUEST.finish();
                                context.startActivity(intent, bundle);
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
}
