package com.example.williamsumitro.dress.view.view.purchase.receipt.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;
import com.example.williamsumitro.dress.view.view.purchase.receipt.fragment.P_receiptFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private ArrayList<ProductInfo> productArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private DecimalFormat formatter;
    private String note;
    private SnapHelper snapHelper;
    private CheckoutProductRVAdapter rvadapter;
    private Boolean click_receipt = false;
    private P_receiptFragment p_receiptFragment;
    public PurchaseReceiptRV(Context context, ArrayList<Sales_OrderResult> orderresultArrayList, P_receiptFragment p_receiptFragment){
        this.orderresultArrayList = orderresultArrayList;
        this.context = context;
        this.p_receiptFragment = p_receiptFragment;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Sales_OrderResult orderResult = orderresultArrayList.get(position);
        if (orderResult.getNote()== null)
            note = "";
        else
            note = ("Note : " + orderResult.getNote());

        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName() + " " + orderResult.getCourierService(), orderResult.getNote());
            }
        });
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.receiptnumber.setText("Receipt Number : " + orderResult.getReceiptNumber());

        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog1(1, orderResult);
            }
        });

        holder.container_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_receipt){
                    holder.container_receiptdetail.setVisibility(View.VISIBLE);
                    click_receipt = true;
                    holder.caret_receipt.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_receiptdetail.setVisibility(View.GONE);
                    click_receipt = false;
                    holder.caret_receipt.setImageResource(R.drawable.caret);
                }
            }
        });
        rvadapter = new CheckoutProductRVAdapter(orderResult.getProduct(), context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.recyclerView.setAdapter(rvadapter);
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }
    private void api_submit(Sales_OrderResult orderResult){
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
                        else {
                            String message = jsonResults.getString("message");
                            initDialog(message, 0);
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
                initDialog("",3);
            }
        });
    }
    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasereceipt_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchasereceipt_rv) RecyclerView recyclerView;
        @BindView(R.id.item_purchasereceipt_btn_submit) Button submit;
        @BindView(R.id.item_purchasereceipt_tv_receiptnumber) TextView receiptnumber;
        @BindView(R.id.item_purchasereceipt_btn_viewdetails) Button viewdetails;
        @BindView(R.id.item_purchasereceipt_img_receiptcaret) ImageView caret_receipt;
        @BindView(R.id.item_purchasereceipt_ln_receiptproductdetail) LinearLayout container_receiptdetail;
        @BindView(R.id.item_purchasereceipt_ln_receiptproduct) LinearLayout container_receipt;
        @BindView(R.id.item_purchasereceipt_ln_receipt) LinearLayout receipt;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog1(int stats, final Sales_OrderResult orderResult){
        if(stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Receive")
                    .setContentText("Please check again closely. After you received, you can't undo again")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            api_submit(orderResult);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
    }
    private void initDialog(String message, int stats){

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
                            p_receiptFragment.initRefresh();
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
    private void initDialog(final String invoicedate, final String receivername, final String shippingaddress,
                            final String province, final String city, final String phonenumber,
                            final String postalcode, final String courier, final String note){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order);

        final TextView tv_invoicedate = (TextView) dialog.findViewById(R.id.orderdialog_tv_invoicedate);
        final TextView tv_receivername = (TextView) dialog.findViewById(R.id.orderdialog_tv_receivername);
        final TextView tv_shippingaddress = (TextView) dialog.findViewById(R.id.orderdialog_tv_shippingaddress);
        final TextView tv_province = (TextView) dialog.findViewById(R.id.orderdialog_tv_province);
        final TextView tv_city = (TextView) dialog.findViewById(R.id.orderdialog_tv_city);
        final TextView tv_phonenumber = (TextView) dialog.findViewById(R.id.orderdialog_tv_phonenumber);
        final TextView tv_postalcode = (TextView) dialog.findViewById(R.id.orderdialog_tv_postalcode);
        final TextView tv_courier = (TextView) dialog.findViewById(R.id.orderdialog_tv_courier);
        final TextView tv_note = (TextView) dialog.findViewById(R.id.orderdialog_tv_note);
        final Button buttonok = (Button) dialog.findViewById(R.id.orderdialog_btnok);

        tv_invoicedate.setText(invoicedate);
        tv_receivername.setText(receivername);
        tv_shippingaddress.setText(shippingaddress);
        tv_province.setText(province);
        tv_city.setText(city);
        tv_phonenumber.setText(phonenumber);
        tv_postalcode.setText(postalcode);
        tv_courier.setText(courier);
        tv_note.setText(note);

        buttonok.setText("Yes");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
