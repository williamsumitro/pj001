package com.example.williamsumitro.dress.view.view.purchase.order.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by WilliamSumitro on 18/06/2018.
 */

public class PurchaseOrderRVapprove extends RecyclerView.Adapter<PurchaseOrderRVapprove.ViewHolder> {
    private Context context;
    private ArrayList<Purchase_OrderResult> orderresultArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private CheckoutProductRVAdapter rvadapter;
    String note;
    public PurchaseOrderRVapprove(Context context, ArrayList<Purchase_OrderResult> orderresultArrayList){
        this.context = context;
        this.orderresultArrayList = orderresultArrayList;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public PurchaseOrderRVapprove.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_orderstatus1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PurchaseOrderRVapprove.ViewHolder holder, int position) {
        final Purchase_OrderResult orderResult = orderresultArrayList.get(position);
        holder.status.setText(orderResult.getOrderStatus());
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.storename.setText("From " + orderResult.getStoreName() + " Store");
        holder.total.setText("Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getTotalPrice()))));
        holder.subtotal.setText("Sub Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getSubtotalPrice()))));
        holder.shipping.setText("Shipping Fee : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getShippingPrice()))));
        if (orderResult.getNote()== null)
            note = "";
        else
            note = ("Note : " + orderResult.getNote());

        holder.viewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName(), note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_purchaseorderstatus1_tv_total)
        TextView total;
        @BindView(R.id.item_purchaseorderstatus1_tv_subtotal) TextView subtotal;
        @BindView(R.id.item_purchaseorderstatus1_tv_storename) TextView storename;
        @BindView(R.id.item_purchaseorderstatus1_tv_shipping) TextView shipping;
        @BindView(R.id.item_purchaseorderstatus1_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchaseorderstatus1_tv_status) TextView status;
        @BindView(R.id.item_purchaseorderstatus1_btn_viewdetails)
        Button viewdetais;
        public ViewHolder(View itemView) {
            super(itemView);
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
