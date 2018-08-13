package com.example.williamsumitro.dress.view.view.home.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.NotificationResult;
import com.example.williamsumitro.dress.view.model.PartnershipResponse;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.home.activity.Notification;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.product.activity.DetailProduct;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<NotificationResult> notificationResults;
    private Context context;
    private final static String NOTIFICATION = "NOTIFICATION";
    private final static String REJECT = "REJECT";
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;

    public NotificationAdapter(ArrayList<NotificationResult> notificationResults, Context context){
        this.notificationResults = notificationResults;
        this.context = context;
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NotificationResult notificationresult = notificationResults.get(position);
        holder.code.setText(notificationresult.getTransactionCode());
        holder.date.setText(notificationresult.getDate());
        if (notificationresult.getStatusRead()==0){
            holder.status.setBackgroundColor(context.getResources().getColor(R.color.red1));
            holder.status.setText("New");
        }
        else {
            holder.status.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.status.setTextColor(context.getResources().getColor(R.color.gray2));
            holder.status.setText("Read");
        }
        if (notificationresult.getTransactionCode().equals("PAYMENT-ACCEPT")){
            holder.container.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    service = apiUtils.getAPIService();
                    service.req_read_notification(token, notificationresult.getId().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    intent.putExtra(NOTIFICATION, "NOTIFICATION");
                    context.startActivity(intent, bundle);
                }
            });
        }
        else {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    service = apiUtils.getAPIService();
                    service.req_read_notification(token, notificationresult.getId().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                    intent.putExtra(REJECT, "REJECT");
                    context.startActivity(intent, bundle);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return notificationResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_notification_code) TextView code;
        @BindView(R.id.item_notification_date) TextView date;
        @BindView(R.id.item_notification_status) TextView status;
        @BindView(R.id.item_notification_container) CardView container;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
