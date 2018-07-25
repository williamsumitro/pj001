package com.example.williamsumitro.dress.view.view.sales.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.ApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.CheckApproveOrderProduct;
import com.example.williamsumitro.dress.view.model.Product;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.Sales_OrderResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sales.fragment.OrderConfirmationFragment;

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
    private SweetAlertDialog sweetAlertDialog;
    private OrderConfirmationFragment orderConfirmationFragment;


    private final static String PRODUCT = "PRODUCT";
    private final static String TRANSACTION_ID = "TRANSACTION_ID";
    private final static String STORE_ID = "STORE_ID";

    public OC_RVAdapter(Context context, ArrayList<Sales_OrderResult> orderresultArrayList, OrderConfirmationFragment orderConfirmationFragment){
        this.context = context;
        this.orderresultArrayList = orderresultArrayList;
        this.orderConfirmationFragment = orderConfirmationFragment;
        formatter = new DecimalFormat("#,###,###");
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        approveOrderProductArrayList = new ArrayList<>();
        checkApproveOrderProductArrayList = new ArrayList<>();

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales_orderstatus, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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
        holder.viewdetais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_detail(orderResult.getInvoiceDate(), orderResult.getReceiverName(), orderResult.getAddress(),
                        orderResult.getProvinceName(), orderResult.getCityName(), orderResult.getPhoneNumber(),
                        orderResult.getPostalCode(), orderResult.getCourierName() + " " + orderResult.getCourierService(), orderResult.getNote());
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog_approve(orderResult.getTransactionId().toString(), orderresultArrayList.get(position).getProduct(), orderResult.getStoreId().toString());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

//                    Intent intent = new Intent(context, OrderApproveProduct.class);
//                    intent.putExtra(TRANSACTION_ID, orderResult.getTransactionId().toString());
//                    intent.putExtra(PRODUCT, productArrayList);
//                    intent.putExtra(STORE_ID, orderResult.getStoreId().toString());
//                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
//                    context.startActivity(intent, bundle);
//                }
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
    private void dialog_detail(final String invoicedate, final String receivername, final String shippingaddress,
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

    private void dialog_approve(final String transactionid, final ArrayList<ProductInfo> productArrayList, final String storeid){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_approveproduct);

        final RecyclerView rv = (RecyclerView) dialog.findViewById(R.id.dialog_approveproduct_rv);
        final Button submit = (Button) dialog.findViewById(R.id.dialog_approveproduct_btn_submit);

        rvadapter = new OC_ProductRVAdapter(productArrayList,  context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rvadapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        rv.setAdapter(alphaAdapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog("",2, transactionid, storeid);
            }
        });
        dialog.show();
    }
    private void initDialog(final String message, int stats, final String transactionid, final String storeid){
        if (stats == 2){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Confirm")
                    .setContentText("Are you sure submit ?")
                    .setContentText("Once submit you cannot undo")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            approveOrderProductArrayList = rvadapter.retrivedata();
                            api_approveproduct(transactionid, storeid);
                            sweetAlertDialog.dismiss();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void api_approveproduct(String transactionid, String storeid){
        service = apiUtils.getAPIService();
        ApproveOrder approveOrder = new ApproveOrder(token, transactionid, storeid, approveOrderProductArrayList);
        service.req_approve_order_product(approveOrder).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            progressDialog.dismiss();
                            initDialog1(message, 1);
                        }else{
                            String message = jsonResults.getString("message");
                            progressDialog.dismiss();
                            initDialog1(message, 0);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initDialog("",3, "", "");
            }
        });
    }
    private void initDialog1(final String message, int stats){

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
                            orderConfirmationFragment.initRefresh();
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
