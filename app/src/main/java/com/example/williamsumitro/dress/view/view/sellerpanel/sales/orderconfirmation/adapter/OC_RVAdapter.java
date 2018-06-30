package com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.CheckApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResult;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PP_InvoiceDetail;
import com.example.williamsumitro.dress.view.view.sellerpanel.sales.orderconfirmation.activity.OrderApproveProduct;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 28/05/2018.
 */

public class OC_RVAdapter extends RecyclerView.Adapter<OC_RVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sales_OrderResult> orderresultArrayList;
    private ArrayList<ApproveOrderProduct> approveOrderProductArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private OC_ProductRVAdapter rvadapter;
    private ArrayList<CheckApproveOrderProduct> checkApproveOrderProductArrayList;
    private String note;
    private ArrayList<Product> productArrayList;

    private final static String PRODUCT = "PRODUCT";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";
    private final static String STORE_ID = "STORE_ID";

    public OC_RVAdapter(Context context, ArrayList<Sales_OrderResult> orderresultArrayList){
        this.context = context;
        this.orderresultArrayList = orderresultArrayList;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        approveOrderProductArrayList = new ArrayList<>();
        checkApproveOrderProductArrayList = new ArrayList<>();
        productArrayList = new ArrayList<>();

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_orderstatus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Sales_OrderResult orderResult = orderresultArrayList.get(position);
        holder.status.setText("Order By : " + orderResult.getUserName());
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.storename.setVisibility(View.GONE);
        holder.total.setText("Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getTotalPrice()))));
        holder.subtotal.setText("Sub Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getSubtotalPrice()))));
        holder.shipping.setText("Shipping Fee : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getShippingPrice()))));
        if (orderResult.getNote()== null)
            note = "";
        else
            note = ("Note : " + orderResult.getNote());
        productArrayList = orderResult.getProduct();
//        rvadapter = new OC_ProductRVAdapter(String.valueOf(orderResult.getTransactionId()), approveOrderProductArrayList,  checkApproveOrderProductArrayList, orderResult.getProduct(), context);
//        holder.view.setVisibility(View.GONE);
//        SnapHelper snapHelper = new LinearSnapHelper();
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        holder.recyclerView.setLayoutManager(layoutManager);
//        holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
//        holder.recyclerView.setAdapter(rvadapter);
//        snapHelper.attachToRecyclerView(holder.recyclerView);
        holder.viewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName(), note);
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rvadapter.retrivedata();
//                checkApproveOrderProductArrayList = rvadapter.retrivedata();
//                for(int i = 0; i<checkApproveOrderProductArrayList.size();i++)
//                    Toast.makeText(context, checkApproveOrderProductArrayList.get(i).getTransaction_id(), Toast.LENGTH_LONG).show();
//                rvadapter.retrivedata();
//                for (int i = 0; i < checkApproveOrderProductArrayList.size(); i++)
//                    if (String.valueOf(orderResult.getTransactionId()).equals(checkApproveOrderProductArrayList.get(i).getTransaction_id())) {
//                        Toast.makeText(context, String.valueOf(checkApproveOrderProductArrayList.get(i).getTransaction_id()), Toast.LENGTH_LONG).show();
//                        for (int j = 0; j < checkApproveOrderProductArrayList.get(i).getApproveOrderProductArrayList().size(); j++)
//                            Toast.makeText(context, checkApproveOrderProductArrayList.get(i).getApproveOrderProductArrayList().get(j).getProduct_id() + " - " + approveOrderProductArrayList.get(j).getStatus(), Toast.LENGTH_SHORT).show();
//                    }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, OrderApproveProduct.class);
                    intent.putExtra(TRANSACTION_ID, orderResult.getTransactionId().toString());
                    intent.putExtra(PRODUCT, productArrayList);
                    intent.putExtra(STORE_ID, orderResult.getStoreId().toString());
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_salesorderstatus_view) View view;
        @BindView(R.id.item_salesorderstatus_tv_total) TextView total;
        @BindView(R.id.item_salesorderstatus_tv_subtotal) TextView subtotal;
        @BindView(R.id.item_salesorderstatus_tv_storename) TextView storename;
        @BindView(R.id.item_salesorderstatus_tv_shipping) TextView shipping;
        @BindView(R.id.item_salesorderstatus_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_salesorderstatus_tv_status) TextView status;
        @BindView(R.id.item_salesorderstatus_btn_viewdetails) Button viewdetais;
        @BindView(R.id.item_salesorderstatus_btn_confirm) Button confirm;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(final String invoicedate, final String receivername, final String shippingaddress,
                            final String province, final String city, final String phonenumber,
                            final String postalcode, final String courier, final String note){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.order_dialog);

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
