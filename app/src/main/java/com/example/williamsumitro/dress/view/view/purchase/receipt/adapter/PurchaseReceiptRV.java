package com.example.williamsumitro.dress.view.view.purchase.receipt.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;
import com.example.williamsumitro.dress.view.view.purchase.receipt.activity.PurchaseReceiptConfirmation;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.shippingconfirmation.activity.ShippingConfirmation;

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
 * Created by WilliamSumitro on 19/06/2018.
 */

public class PurchaseReceiptRV extends RecyclerView.Adapter<PurchaseReceiptRV.ViewHolder> {
    private Context context;
    private ArrayList<Sales_OrderResult> orderresultArrayList;
    private ArrayList<Product> productArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private String note;
    private SnapHelper snapHelper;
    private CheckoutProductRVAdapter rvadapter;
    public PurchaseReceiptRV(Context context, ArrayList<Sales_OrderResult> orderresultArrayList){
        this.orderresultArrayList = orderresultArrayList;
        this.context = context;
        productArrayList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        snapHelper = new LinearSnapHelper();
        service = apiUtils.getAPIService();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_receipt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Sales_OrderResult orderResult = orderresultArrayList.get(position);
        if (orderResult.getNote()== null)
            note = "";
        else
            note = ("Note : " + orderResult.getNote());
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.tv_invoicedate.setText(orderResult.getInvoiceDate());
        holder.tv_receivername.setText(orderResult.getReceiverName());
        holder.tv_shippingaddress.setText(orderResult.getAddress());
        holder.tv_province.setText(orderResult.getProvinceName());
        holder.tv_city.setText(orderResult.getCityName());
        holder.tv_phonenumber.setText(orderResult.getPhoneNumber());
        holder.tv_postalcode.setText(orderResult.getPostalCode());
        holder.tv_courier.setText(orderResult.getCourierName());
        holder.tv_note.setText(note);
        holder.receiptnumber.setText(orderResult.getReceiptNumber());

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.req_confirm_receipt(token, String.valueOf(orderResult.getTransactionId()), String.valueOf(orderResult.getStoreId())).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if (jsonResults.getBoolean("status")){
                                    String message = jsonResults.getString("message");
                                    initDialog(message, 1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        rvadapter = new CheckoutProductRVAdapter(orderResult.getProduct(), context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(rvadapter);
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasereceipt_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchasereceipt_rv) RecyclerView recyclerView;
        @BindView(R.id.item_purchasereceipt_btn_submit) Button submit;
        @BindView(R.id.item_purchasereceipt_tv_invoicedate) TextView tv_invoicedate;
        @BindView(R.id.item_purchasereceipt_tv_receivername) TextView tv_receivername;
        @BindView(R.id.item_purchasereceipt_tv_shippingaddress) TextView tv_shippingaddress;
        @BindView(R.id.item_purchasereceipt_tv_province) TextView tv_province;
        @BindView(R.id.item_purchasereceipt_tv_city) TextView tv_city;
        @BindView(R.id.item_purchasereceipt_tv_phonenumber) TextView tv_phonenumber;
        @BindView(R.id.item_purchasereceipt_tv_postalcode) TextView tv_postalcode;
        @BindView(R.id.item_purchasereceipt_tv_courier) TextView tv_courier;
        @BindView(R.id.item_purchasereceipt_tv_note) TextView tv_note;
        @BindView(R.id.item_purchasereceipt_tv_receiptnumber) TextView receiptnumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(String message, int stats){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        LinearLayout bg = (LinearLayout) dialog.findViewById(R.id.customdialog_lnBg);
        TextView status = (TextView) dialog.findViewById(R.id.customdialog_tvStatus);
        TextView detail = (TextView) dialog.findViewById(R.id.customdialog_tvDetail);
//        ImageView imageView = (ImageView) dialog.findViewById(R.id.customdialog_img);
        Button button = (Button) dialog.findViewById(R.id.customdialog_btnok);
        if(stats == 1){
            status.setText("Success!");
            detail.setText(message);
            bg.setBackgroundResource(R.color.green7);
            button.setBackgroundResource(R.drawable.button1_green);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    context.startActivity(new Intent(context, PurchaseReceiptConfirmation.class));
                    ((Activity) context).finish();
                }
            });
            dialog.show();
        }
        else if(stats == 0){
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
}
