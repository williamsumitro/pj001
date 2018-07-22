package com.example.williamsumitro.dress.view.view.request.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Offer;
import com.example.williamsumitro.dress.view.model.RFQ_ActiveResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.request.fragment.ActiveRequestFragment;
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
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William Sumitro on 7/15/2018.
 */

public class ActiveRequestRV extends RecyclerView.Adapter<ActiveRequestRV.ViewHolder> {
    private Context context;
    private ArrayList<RFQ_ActiveResult> results;
    private DecimalFormat formatter;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private ProgressDialog progressDialog;
    private Boolean click = false;
    private ActiveRequestOfferRV rvadapter;
    private SnapHelper snapHelper;
    private String token;
    private SessionManagement sessionManagement;
    private apiService service;
    private ActiveRequestFragment activeRequestFragment;

    public ActiveRequestRV(Context context, ArrayList<RFQ_ActiveResult> results, ActiveRequestFragment activeRequestFragment){
        this.context = context;
        this.results = results;
        this.activeRequestFragment = activeRequestFragment;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        snapHelper = new LinearSnapHelper();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public ActiveRequestRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activerequest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActiveRequestRV.ViewHolder holder, int position) {
        final RFQ_ActiveResult result = results.get(position);
        holder.qty.setText(formatter.format(Double.parseDouble(String.valueOf(result.getQty()))));
        holder.description.setText(result.getDescription());
        holder.dateexpired.setText(result.getRequestExpired());
        holder.budget.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMin()))) + " - IDR" + formatter.format(Double.parseDouble(String.valueOf(result.getBudgetUnitMax()))));
        Picasso.with(context)
                .load(result.getPhoto().getFilePath())
                .placeholder(R.drawable.logo404)
                .into(holder.image_product);
        holder.viewoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(result.getOffer());
            }
        });
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog1(token, result.getRfqRequestId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_activerequest_tv_qty) TextView qty;
        @BindView(R.id.item_activerequest_tv_description) TextView description;
        @BindView(R.id.item_activerequest_tv_dateexpired) TextView dateexpired;
        @BindView(R.id.item_activerequest_tv_budget) TextView budget;
        @BindView(R.id.item_activerequest_btn_close) Button close;
        @BindView(R.id.item_activerequest_btn_viewoffer) Button viewoffer;
        @BindView(R.id.item_activerequest_img_product) ImageView image_product;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(final ArrayList<Offer> offers){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_viewoffer);
        final LinearLayout container_nooffer = (LinearLayout) dialog.findViewById(R.id.dialog_viewoffer_ln_nooffer);
        final LinearLayout container_offer = (LinearLayout) dialog.findViewById(R.id.dialog_viewoffer_ln_offer);
        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.dialog_viewoffer_rv);
        final Button buttoncancel = (Button) dialog.findViewById(R.id.dialog_viewoffer_btn_close);

        if (offers.size()>0){
            container_offer.setVisibility(View.VISIBLE);
            rvadapter = new ActiveRequestOfferRV(context, offers, activeRequestFragment, dialog);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rv.setLayoutManager(layoutManager);
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setAdapter(rvadapter);
            snapHelper.attachToRecyclerView(rv);
            dialog.dismiss();

        }else {
            container_nooffer.setVisibility(View.VISIBLE);
        }

        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void initDialog1(final String token, final String rfq_request_id){

        sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText("Close Request")
                .setContentText("Are you sure you want to close this request ?")
                .setContentText("Once you close, you can't reopen again")
                .setConfirmText("Yes")
                .setCancelText("No")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        api_close_request(token, rfq_request_id);
                    }
                })
                .show();
    }
    private void api_close_request(String token, String rfq_request_id){
        service = apiUtils.getAPIService();
        service.req_close_rfq_request(token, rfq_request_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").toLowerCase().equals("submitted successfully")){
                            sweetAlertDialog.dismiss();
                            activeRequestFragment.initRefresh();
                        }else{
                            String message = jsonResults.getString("message");
                            Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
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
