package com.example.williamsumitro.dress.view.view.purchase.payment.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.OrderStore;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResult;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.purchase.adapter.SpinBankAdapter;
import com.example.williamsumitro.dress.view.view.purchase.payment.activity.PP_InvoiceDetail;
import com.example.williamsumitro.dress.view.view.purchase.payment.fragment.P_paymentFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WilliamSumitro on 16/06/2018.
 */

public class PurchasePaymentRVInvoice extends RecyclerView.Adapter<PurchasePaymentRVInvoice.ViewHolder> {
    private Context context;
    private ArrayList<Purchase_PaymentResult> purchasePaymentResultArrayList;
    private DecimalFormat formatter;
    private Dialog dialog;
    private ArrayList<Bank> bankArrayList;
    private SpinBankAdapter spinBankAdapter;
    private String company_bank_id;
    private apiService service;
    private String token, year="";
    private SessionManagement sessionManagement;
    private ArrayList<OrderStore> orderStoreArrayList;
    private P_paymentFragment p_paymentFragment;
    private SwitchDateTimeDialogFragment dateTimeFragment;

    private final static String ORDERSTORELIST = "ORDERSTORELIST";
    private final static String INVOICENUMBER = "INVOICENUMBER";
    private final static String GRANDTOTAL = "GRANDTOTAL";
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    public PurchasePaymentRVInvoice(Context context, ArrayList<Purchase_PaymentResult> purchasePaymentResultArrayList, ArrayList<Bank> bankArrayList, P_paymentFragment p_paymentFragment){
        this.context = context;
        this.purchasePaymentResultArrayList = purchasePaymentResultArrayList;
        this.bankArrayList = bankArrayList;
        this.p_paymentFragment = p_paymentFragment;
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
        final Purchase_PaymentResult purchase_paymentResponse = purchasePaymentResultArrayList.get(position);
        holder.status.setText("Status : " + purchase_paymentResponse.getPaymentStatus());
        if (purchase_paymentResponse.getPaymentStatus().equals("Payment Confirmation Sent")){
            holder.status.setTextColor(context.getResources().getColor(R.color.green2));
        }
        holder.invoicenumber.setText("Invoice Number : " + String.valueOf(purchase_paymentResponse.getTransactionId()));
        holder.date.setText(purchase_paymentResponse.getInvoiceDate());
        holder.grandtotal.setText("Grand Total : " +  "IDR " + formatter.format(Double.parseDouble(purchase_paymentResponse.getInvoiceGrandTotal())));
        holder.viewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    Intent intent = new Intent(context, PP_InvoiceDetail.class);
                    intent.putExtra(ORDERSTORELIST, purchase_paymentResponse.getOrderStore());
                    intent.putExtra(GRANDTOTAL, purchase_paymentResponse.getInvoiceGrandTotal());
                    intent.putExtra(INVOICENUMBER, String.valueOf(purchase_paymentResponse.getTransactionId()));
                    Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
                    context.startActivity(intent, bundle);
                }
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(String.valueOf(purchase_paymentResponse.getTransactionId()), purchase_paymentResponse.getInvoiceGrandTotal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return purchasePaymentResultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_purchasepayment_btn_confirm) Button confirm;
        @BindView(R.id.item_purchasepayment_tv_status) TextView status;
        @BindView(R.id.item_purchasepayment_tv_invoicenumber) TextView invoicenumber;
        @BindView(R.id.item_purchasepayment_tv_grandtotal) TextView grandtotal;
        @BindView(R.id.item_purchasepayment_tv_date) TextView date;
        @BindView(R.id.item_purchasepayment_btn_viewdetails) Button viewdetails;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void initDialog(final String invoice_number, final String grand_total){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_payment);
        TextView invoicenumber = (TextView) dialog.findViewById(R.id.paymentdialog_tv_invoicenumber);
        final TextView total = (TextView) dialog.findViewById(R.id.paymentdialog_tv_total);
        final TextInputLayout layout_amount = (TextInputLayout) dialog.findViewById(R.id.paymentdialog_layout_amount);
        final TextInputLayout layout_bankname = (TextInputLayout) dialog.findViewById(R.id.paymentdialog_layout_bank);
        final TextInputLayout layout_bankaccount = (TextInputLayout) dialog.findViewById(R.id.paymentdialog_layout_banknumber);
        final TextInputLayout layout_name = (TextInputLayout) dialog.findViewById(R.id.paymentdialog_layout_nameholder);
        final TextInputLayout layout_note = (TextInputLayout) dialog.findViewById(R.id.paymentdialog_layout_note);
        final TextInputEditText amount = (TextInputEditText) dialog.findViewById(R.id.paymentdialog_amount);
        final TextInputEditText bankname = (TextInputEditText) dialog.findViewById(R.id.paymentdialog_bank);
        final TextInputEditText bankaccount = (TextInputEditText) dialog.findViewById(R.id.paymentdialog_banknumber);
        final TextInputEditText name = (TextInputEditText) dialog.findViewById(R.id.paymentdialog_nameholder);
        final TextInputEditText note = (TextInputEditText) dialog.findViewById(R.id.paymentdialog_note);
        Spinner spinner = (Spinner) dialog.findViewById(R.id.paymentdialog_spinner);
        final Button buttonok = (Button) dialog.findViewById(R.id.paymentdialog_btnok);
        final Button buttoncancel = (Button) dialog.findViewById(R.id.paymentdialog_btncancel);
        final LinearLayout container_year = (LinearLayout) dialog.findViewById(R.id.paymentdialog_ln_year);
        final TextView tv_year = (TextView) dialog.findViewById(R.id.paymentdialog_tv_year);

        container_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeFragment = (SwitchDateTimeDialogFragment) p_paymentFragment.getFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
                if(dateTimeFragment == null) {
                    dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                            context.getString(R.string.label_datetime_dialog),
                            context.getString(android.R.string.ok),
                            context.getString(android.R.string.cancel)
                    );
                }
                dateTimeFragment.setTimeZone(TimeZone.getDefault());

                final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
                dateTimeFragment.set24HoursMode(true);
                dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime());
                dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime());

                try {
                    dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
                } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                    Log.e("TAG", e.getMessage());
                }

                dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
                    @Override
                    public void onPositiveButtonClick(Date date) {
                        year = myDateFormat.format(date);
                        tv_year.setText("Year Established : " + year);
                    }

                    @Override
                    public void onNegativeButtonClick(Date date) {
                    }

                    @Override
                    public void onNeutralButtonClick(Date date) {
                    }
                });
                dateTimeFragment.startAtCalendarView();
                if (year.equals("")){
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2018, Calendar.MARCH, 8, 3, 20).getTime());
                }else {
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(Integer.parseInt(year), Calendar.MARCH, 4, 15, 20).getTime());
                }
                dateTimeFragment.show(p_paymentFragment.getFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });

        if (grand_total.equals("0")){
            amount.setText("0");
            amount.setEnabled(false);
        }
        amount.addTextChangedListener(new FinancialTextWatcher(amount));

        invoicenumber.setText("Invoice Number : " + invoice_number);
        total.setText("Total : IDR " + formatter.format(Double.parseDouble(grand_total)));
        spinBankAdapter = new SpinBankAdapter(context, R.layout.item_spinner, bankArrayList);
        spinner.setAdapter(spinBankAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Bank bank = spinBankAdapter.getItem(position);
                company_bank_id = String.valueOf(bank.getBankId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttonok.setText("Yes");
        buttoncancel.setVisibility(View.VISIBLE);
        buttoncancel.setText("No");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttoncancel.setBackgroundResource(R.drawable.button1_1);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_amount.setErrorEnabled(false);
                layout_bankname.setErrorEnabled(false);
                layout_bankaccount.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_note.setErrorEnabled(false);
                if (TextUtils.isEmpty(year)){
                    Toasty.warning(context, "Please choose your date", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                else if (TextUtils.isEmpty(amount.getText().toString())){
                    layout_amount.setErrorEnabled(true);
                    layout_amount.setError("Amount is required");
                    return;
                }else if (TextUtils.isEmpty(bankname.getText().toString())){
                    layout_bankname.setErrorEnabled(true);
                    layout_bankname.setError("Bank Name is required");
                    return;
                }else if(TextUtils.isEmpty(bankaccount.getText().toString())){
                    layout_bankaccount.setErrorEnabled(true);
                    layout_bankaccount.setError("Account Number is required");
                    return;
                }else if  (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Holder Name is required");
                    return;
                }

                service = apiUtils.getAPIService();
                String amounts = FinancialTextWatcher.trimCommaOfString(amount.getText().toString());
                service.req_confirm_payment(token, invoice_number, year,  company_bank_id, amounts, bankname.getText().toString(), bankaccount.getText().toString(),
                        name.getText().toString(), note.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if (jsonResults.getBoolean("status")) {
                                    String message = jsonResults.getString("message");
                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                    p_paymentFragment.initRefresh();
                                }
                                else {
                                    String message = jsonResults.getString("message");
                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
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
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
