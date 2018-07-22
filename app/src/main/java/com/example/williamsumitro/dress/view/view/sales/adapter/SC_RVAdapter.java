package com.example.williamsumitro.dress.view.view.sales.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
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
import com.example.williamsumitro.dress.view.view.sales.fragment.ShippingConfirmationFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 18/06/2018.
 */

public class SC_RVAdapter extends RecyclerView.Adapter<SC_RVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sales_OrderResult> orderresultArrayList;
    private ArrayList<Product> productArrayList;
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
    private ShippingConfirmationFragment shippingConfirmationFragment;

    public SC_RVAdapter(Context context, ArrayList<Sales_OrderResult> orderresultArrayList, ShippingConfirmationFragment shippingConfirmationFragment){
        this.orderresultArrayList = orderresultArrayList;
        this.context = context;
        this.shippingConfirmationFragment = shippingConfirmationFragment;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_shipping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SC_RVAdapter.ViewHolder holder, int position) {
        final Sales_OrderResult orderResult = orderresultArrayList.get(position);
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.total.setText("Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getTotalPrice()))));
        holder.subtotal.setText("Sub Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getSubtotalPrice()))));
        holder.shipping.setText("Shipping Fee : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getShippingPrice()))));
        if (orderResult.getNote()== null)
            note = "";
        else
            note = ("Note : " + orderResult.getNote());
        if (orderResult.getProduct().size()>0){
            rvadapter = new CheckoutProductRVAdapter(orderResult.getProduct(), context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rvadapter);
            alphaAdapter.setDuration(1000);
            alphaAdapter.setInterpolator(new OvershootInterpolator());
            holder.recyclerView.setAdapter(alphaAdapter);
            snapHelper.attachToRecyclerView(holder.recyclerView);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(holder.receiptnumber.getText())){
                        holder.receiptnumber.setError("This field can not be blank");
                    }
                    else {
                        service.req_input_receipt_number(token, String.valueOf(orderResult.getTransactionId()), String.valueOf(orderResult.getStoreId()), holder.receiptnumber.getText().toString()).enqueue(new Callback<ResponseBody>() {
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
                }
            });
        }else {
            holder.noproduct.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.GONE);
            holder.submit.setText("Finish");
            holder.container_receipt.setVisibility(View.GONE);
            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    service.req_finish_shippin(token, String.valueOf(orderResult.getTransactionId()), String.valueOf(orderResult.getStoreId())).enqueue(new Callback<ResponseBody>() {
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
                                        initDialog(message, 2);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {

                                initDialog("",2);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            initDialog("",3);
                        }
                    });
                }
            });
        }
        holder.viewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName() + " " + orderResult.getCourierService(), orderResult.getNote());
            }
        });


    }
    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_salesshipping_tv_total) TextView total;
        @BindView(R.id.item_salesshipping_tv_subtotal) TextView subtotal;
        @BindView(R.id.item_salesshipping_tv_shipping) TextView shipping;
        @BindView(R.id.item_salesshipping_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_salesshipping_rv) RecyclerView recyclerView;
        @BindView(R.id.item_salesshipping_et_receiptnumber) EditText receiptnumber;
        @BindView(R.id.item_salesshipping_btn_viewdetails) Button viewdetais;
        @BindView(R.id.item_salesshipping_btn_submit) Button submit;
        @BindView(R.id.item_salesshipping_tv_noproduct) TextView noproduct;
        @BindView(R.id.item_salesshipping_ln_receipt) LinearLayout container_receipt;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
                            shippingConfirmationFragment.initRefresh();
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
