package com.example.williamsumitro.dress.view.view.mystore.adapter;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CourierService;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.mystore.activity.CourierServiceActivity;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;

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
 * Created by William Sumitro on 7/25/2018.
 */

public class CourierServiceRVAdapter extends RecyclerView.Adapter<CourierServiceRVAdapter.ViewHolder> {
    private ArrayList<model_CourierService> courierServices;
    private Context context;
    private SessionManagement sessionManagement;
    private String token;
    private apiService service;
    private ProgressDialog progressDialog;
    private String store_id;
    private SweetAlertDialog sweetAlertDialog;
    private CourierServiceActivity courierServiceActivity;

    public CourierServiceRVAdapter(Context context, ArrayList<model_CourierService> courierServices, String store_id, CourierServiceActivity courierServiceActivity){
        this.context = context;
        this.courierServices = courierServices;
        this.store_id = store_id;
        this.courierServiceActivity = courierServiceActivity;
        progressDialog = new ProgressDialog(context);
        service = apiUtils.getAPIService();
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public CourierServiceRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_courierservice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourierServiceRVAdapter.ViewHolder holder, int position) {
        final model_CourierService courierService = courierServices.get(position);
        holder.couriername.setText(courierService.getCourierName());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.req_delete_user_store_courier(token, store_id, courierService.getCourierId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                String message = jsonResults.getString("message");
                                if(jsonResults.getBoolean("status")){
                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                    courierServiceActivity.initRefresh();
                                }else if (!jsonResults.getBoolean("status")){
                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        initDialog(3);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return courierServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_courierservice_tv) TextView couriername;
        @BindView(R.id.item_courierservice_btn) Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(int stats){
        if (stats == 3){
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
