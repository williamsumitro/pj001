package com.example.williamsumitro.dress.view.view.purchase.history.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.model.Purchase_RejectResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.adapter.SpinBankAdapter;
import com.example.williamsumitro.dress.view.view.purchase.history.fragment.P_RejectedPayment;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PP_InvoiceDetail;
import com.example.williamsumitro.dress.view.view.purchase.payment.fragment.P_paymentFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseRejectAdapter extends RecyclerView.Adapter<PurchaseRejectAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Purchase_RejectResult> purchaserejectresults;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ArrayList<Bank> bankArrayList;
    private SpinBankAdapter spinBankAdapter;
    private String company_bank_id;
    private apiService service;
    private String token, year="";
    private SessionManagement sessionManagement;
    private ArrayList<OrderStore> orderStoreArrayList;
    private P_RejectedPayment p_rejectedPayment;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    private final static String ORDERSTORELIST = "ORDERSTORELIST";
    private final static String INVOICENUMBER = "INVOICENUMBER";
    private final static String GRANDTOTAL = "GRANDTOTAL";

    public PurchaseRejectAdapter(Context context, ArrayList<Purchase_RejectResult> purchaserejectresults, ArrayList<Bank> bankArrayList, P_RejectedPayment p_rejectedPayment){
        this.context = context;
        this.purchaserejectresults = purchaserejectresults;
        this.bankArrayList = bankArrayList;
        this.p_rejectedPayment = p_rejectedPayment;
        orderStoreArrayList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase_payment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Purchase_RejectResult purchaserejectresult = purchaserejectresults.get(position);
        holder.status.setText("Status : " + purchaserejectresult.getPaymentStatus());
        if (purchaserejectresult.getPaymentStatus().equals("Payment Confirmation Sent")){
            holder.status.setTextColor(context.getResources().getColor(R.color.green2));
        }
        holder.invoicenumber.setText("Invoice Number : " + String.valueOf(purchaserejectresult.getTransactionId()));
        holder.date.setText(purchaserejectresult.getInvoiceDate());
        holder.grandtotal.setText("Grand Total : " +  "IDR " + formatter.format(Double.parseDouble(purchaserejectresult.getInvoiceGrandTotal())));
        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, PP_InvoiceDetail.class);
                    intent.putExtra(ORDERSTORELIST, purchaserejectresult.getOrderStore());
                    intent.putExtra(GRANDTOTAL, purchaserejectresult.getInvoiceGrandTotal());
                    intent.putExtra(INVOICENUMBER, String.valueOf(purchaserejectresult.getTransactionId()));
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        holder.container1.setVisibility(View.VISIBLE);
        holder.container2.setVisibility(View.VISIBLE);
        holder.transferamount.setText("Transfer Amount : IDR " + formatter.format(Double.parseDouble(purchaserejectresult.getAmount().toString())));
        holder.receiveamount.setText("Receive Amount : IDR " + formatter.format(Double.parseDouble(purchaserejectresult.getReceiveAmount().toString())));
        holder.comment.setText("Comment : " + purchaserejectresult.getRejectComment());
        holder.rejectdate.setText("Date : " + purchaserejectresult.getDate());
        holder.transferto.setText("Transfer to : " + purchaserejectresult.getCompanyBankName() + " " + purchaserejectresult.getCompanyBankAccountNumber());
        holder.yourbank.setText("Your Bank : " + purchaserejectresult.getSenderBank());
        holder.yourbankaccountnumber.setText("Your Bank Account Number : "+purchaserejectresult.getSenderAccountNumber());
        holder.yournameinaccount.setText("Your Name in Account : " + purchaserejectresult.getSenderName());
        if (purchaserejectresult.getNote()==null){
            holder.note.setText("Note : ");
        }
        else {
            holder.note.setText("Note : "+ purchaserejectresult.getNote().toString());
        }
        holder.confirm.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return purchaserejectresults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasepayment_btn_confirm) Button confirm;
        @BindView(R.id.item_purchasepayment_tv_status) TextView status;
        @BindView(R.id.item_purchasepayment_tv_invoicenumber) TextView invoicenumber;
        @BindView(R.id.item_purchasepayment_tv_grandtotal) TextView grandtotal;
        @BindView(R.id.item_purchasepayment_tv_date) TextView date;
        @BindView(R.id.item_purchasepayment_btn_viewdetails) Button viewdetails;
        @BindView(R.id.item_purchasepayment_ln_rejectdetail1) LinearLayout container1;
        @BindView(R.id.item_purchasepayment_ln_rejectdetail2) LinearLayout container2;
        @BindView(R.id.item_purchasepayment_tv_transferamount) TextView transferamount;
        @BindView(R.id.item_purchasepayment_tv_receiveamount) TextView receiveamount;
        @BindView(R.id.item_purchasepayment_tv_comment) TextView comment;
        @BindView(R.id.item_purchasepayment_tv_rejectdate) TextView rejectdate;
        @BindView(R.id.item_purchasepayment_tv_transferto) TextView transferto;
        @BindView(R.id.item_purchasepayment_tv_yourbank) TextView yourbank;
        @BindView(R.id.item_purchasepayment_tv_yourbankaccountnumber) TextView yourbankaccountnumber;
        @BindView(R.id.item_purchasepayment_tv_yournameinaccount) TextView yournameinaccount;
        @BindView(R.id.item_purchasepayment_tv_note) TextView note;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
