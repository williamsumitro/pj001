package com.example.williamsumitro.dress.view.view.purchase.order.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.example.williamsumitro.dress.view.model.Purchase_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.checkout.adapter.CheckoutProductRVAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 17/06/2018.
 */

public class PurchaseOrderRV extends RecyclerView.Adapter<PurchaseOrderRV.ViewHolder> {

    private Context context;
    private ArrayList<Purchase_OrderResult> orderresultArrayList;
    private apiService service;
    private String token;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private DecimalFormat formatter;
    private CheckoutProductRVAdapter rvadapter;
    private ArrayList<Product> productArrayList;
    private SnapHelper snapHelper;
    private Boolean click_accepted =false, click_rejected = false, click_pending = false;
    String note;

    public PurchaseOrderRV(Context context, ArrayList<Purchase_OrderResult> orderresultArrayList){
        this.context = context;
        this.orderresultArrayList = orderresultArrayList;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        productArrayList = new ArrayList<>();
        snapHelper = new LinearSnapHelper();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_orderstatus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Purchase_OrderResult orderResult = orderresultArrayList.get(position);
        if (orderResult.getOrderStatus().toLowerCase().equals("order approved")){
            holder.status.setTextColor(context.getResources().getColor(R.color.green3));
            holder.status.setTypeface(Typeface.DEFAULT_BOLD);
            holder.top.setBackgroundColor(context.getResources().getColor(R.color.green3));
            if (orderResult.getProductStatus()!=null){
                productArrayList = new ArrayList<>();
                for (int i = 0; i<orderResult.getProductStatus().size(); i++){
                    Product product = new Product(orderResult.getProductStatus().get(i).getProductId(),
                            orderResult.getProductStatus().get(i).getProductName(),
                            orderResult.getProductStatus().get(i).getProductPhoto(),
                            orderResult.getProductStatus().get(i).getPriceUnit(),
                            orderResult.getProductStatus().get(i).getTotalQty(),
                            orderResult.getProductStatus().get(i).getPriceTotal(),
                            orderResult.getProductStatus().get(i).getSizeInfo());
                    productArrayList.add(product);
                }
                if (productArrayList.size()>0){
                    holder.accepted.setVisibility(View.VISIBLE);
                    rvadapter = new CheckoutProductRVAdapter(productArrayList, context);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.rv_accepted.setLayoutManager(layoutManager);
                    holder.rv_accepted.setItemAnimator(new DefaultItemAnimator());
                    holder.rv_accepted.setAdapter(rvadapter);
                    snapHelper.attachToRecyclerView(holder.rv_accepted);
                }
            }
            if (orderResult.getProductRejected()!=null){
                productArrayList = new ArrayList<>();
                for (int i = 0; i<orderResult.getProductRejected().size();i++){
                    Product product = new Product(orderResult.getProductRejected().get(i).getProductId(),
                            orderResult.getProductRejected().get(i).getProductName(),
                            orderResult.getProductRejected().get(i).getProductPhoto(),
                            orderResult.getProductRejected().get(i).getPriceUnit(),
                            orderResult.getProductRejected().get(i).getTotalQty(),
                            orderResult.getProductRejected().get(i).getPriceTotal(),
                            orderResult.getProductRejected().get(i).getSizeInfo());
                    productArrayList.add(product);
                }
                if (productArrayList.size()>0){
                    holder.rejected.setVisibility(View.VISIBLE);
                    rvadapter = new CheckoutProductRVAdapter(productArrayList, context);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    holder.rv_rejected.setLayoutManager(layoutManager);
                    holder.rv_rejected.setItemAnimator(new DefaultItemAnimator());
                    holder.rv_rejected.setAdapter(rvadapter);
                    snapHelper.attachToRecyclerView(holder.rv_rejected);
                }
            }
        }
        else {
            holder.pending.setVisibility(View.VISIBLE);
            rvadapter = new CheckoutProductRVAdapter(orderResult.getProduct(), context);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setAdapter(rvadapter);
            snapHelper.attachToRecyclerView(holder.recyclerView);
        }
        holder.status.setText(orderResult.getOrderStatus());
        holder.ordernumber.setText(orderResult.getOrderNumber());
        holder.storename.setText("From " + orderResult.getStoreName() + " Store");
        holder.total.setText("Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getTotalPrice()))));
        holder.subtotal.setText("Sub Total : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getSubtotalPrice()))));
        holder.shipping.setText("Shipping Fee : IDR " + formatter.format(Double.parseDouble(String.valueOf(orderResult.getShippingPrice()))));
        if (orderResult.getNote()== null)
            note = "";
        else
            note = (orderResult.getNote());

        holder.viewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName() + " " + orderResult.getCourierService(), orderResult.getNote());
            }
        });

        holder.container_accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!click_accepted){
                    holder.container_accepteddetail.setVisibility(View.VISIBLE);
                    click_accepted = true;
                    holder.caret_accepted.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_accepteddetail.setVisibility(View.GONE);
                    click_accepted = false;
                    holder.caret_accepted.setImageResource(R.drawable.caret);
                }
            }
        });
        holder.container_rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_rejected){
                    holder.container_rejecteddetail.setVisibility(View.VISIBLE);
                    click_rejected = true;
                    holder.caret_rejected.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_rejecteddetail.setVisibility(View.GONE);
                    click_rejected = false;
                    holder.caret_rejected.setImageResource(R.drawable.caret);
                }
            }
        });
        holder.container_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!click_pending){
                    holder.container_pendingdetail.setVisibility(View.VISIBLE);
                    click_pending = true;
                    holder.caret_pending.setImageResource(R.drawable.caret1);
                }
                else {
                    holder.container_pendingdetail.setVisibility(View.GONE);
                    click_pending = false;
                    holder.caret_pending.setImageResource(R.drawable.caret);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderresultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchaseorderstatus_tv_total) TextView total;
        @BindView(R.id.item_purchaseorderstatus_tv_subtotal) TextView subtotal;
        @BindView(R.id.item_purchaseorderstatus_tv_storename) TextView storename;
        @BindView(R.id.item_purchaseorderstatus_tv_shipping) TextView shipping;
        @BindView(R.id.item_purchaseorderstatus_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_purchaseorderstatus_rv) RecyclerView recyclerView;
        @BindView(R.id.item_purchaseorderstatus_tv_status) TextView status;
        @BindView(R.id.item_purchaseorderstatus_btn_viewdetails) Button viewdetais;
        @BindView(R.id.item_purchaseorderstatus_img_acceptedcaret) ImageView caret_accepted;
        @BindView(R.id.item_purchaseorderstatus_ln_acceptedproduct) LinearLayout container_accepted;
        @BindView(R.id.item_purchaseorderstatus_ln_acceptedproductdetail) LinearLayout container_accepteddetail;
        @BindView(R.id.item_purchaseorderstatus_rv_acceptedproduct) RecyclerView rv_accepted;
        @BindView(R.id.item_purchaseorderstatus_img_rejectedcaret) ImageView caret_rejected;
        @BindView(R.id.item_purchaseorderstatus_ln_rejectedproduct) LinearLayout container_rejected;
        @BindView(R.id.item_purchaseorderstatus_ln_rejectedproductdetail) LinearLayout container_rejecteddetail;
        @BindView(R.id.item_purchaseorderstatus_rv_rejectedproduct) RecyclerView rv_rejected;
        @BindView(R.id.item_purchaseorderstatus_ln_top) LinearLayout top;
        @BindView(R.id.item_purchaseorderstatus_ln_mid_accepted) LinearLayout accepted;
        @BindView(R.id.item_purchaseorderstatus_ln_mid_rejected) LinearLayout rejected;
        @BindView(R.id.item_purchaseorderstatus_img_pendingcaret) ImageView caret_pending;
        @BindView(R.id.item_purchaseorderstatus_ln_pendingproductdetail) LinearLayout container_pendingdetail;
        @BindView(R.id.item_purchaseorderstatus_ln_pendingproduct) LinearLayout container_pending;
        @BindView(R.id.item_purchaseorderstatus_ln_pending) LinearLayout pending;

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
}
