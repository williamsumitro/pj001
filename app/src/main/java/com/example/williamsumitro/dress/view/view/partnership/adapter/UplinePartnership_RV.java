package com.example.williamsumitro.dress.view.view.partnership.adapter;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Addproduct_Price;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.UplinePartnershipItem;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.example.williamsumitro.dress.view.view.partnership.activity.UplinePartnership;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by William Sumitro on 7/7/2018.
 */

public class UplinePartnership_RV extends RecyclerView.Adapter<UplinePartnership_RV.ViewHolder>{
    private Context context;
    private ArrayList<UplinePartnershipItem> itemArrayList;
    private Dialog dialog;
    private apiService service;
    private BuyRVAdapter pricedetailsadapter;
    private ArrayList<Price> priceList;
    private List<Addproduct_Price> container_price;
    private DecimalFormat formatter;
    private int index;
    private ProgressDialog progressDialog;
    private SessionManagement sessionManagement;
    private String token;

    public UplinePartnership_RV(Context context, ArrayList<UplinePartnershipItem> itemArrayList){
        this.context = context;
        this.itemArrayList = itemArrayList;
        priceList = new ArrayList<>();
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public UplinePartnership_RV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_req_partnership, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UplinePartnership_RV.ViewHolder holder, int position) {
        final UplinePartnershipItem item = itemArrayList.get(position);
        holder.orderdate.setText(item.getOrderdate());
        holder.ordernumber.setText(item.getOrdernumber());
        holder.productname.setText(item.getProductname());
        Picasso.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.logo404)
                .into(holder.image);
        holder.storename.setText(item.getStorename());
        if (item.getHaspartnership()){
            holder.status.setText("Waiting Approval");
            holder.status.setTextColor(context.getResources().getColor(R.color.grey3));
            holder.partnership.setEnabled(false);
            holder.partnership.setImageResource(0);
            holder.partnership.setImageResource(R.drawable.partnership1);
        }
        else {
            holder.partnership.setImageResource(0);
            holder.partnership.setImageResource(R.drawable.partnership);
        }
        holder.partnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(item.getStorename(), item.getProduct_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_reqpartnership_container) CardView container;
        @BindView(R.id.item_reqpartnership_tv_productname) TextView productname;
        @BindView(R.id.item_reqpartnership_tv_ordernumber) TextView ordernumber;
        @BindView(R.id.item_reqpartnership_tv_oderdate) TextView orderdate;
        @BindView(R.id.item_reqpartnership_tv_storename) TextView storename;
        @BindView(R.id.item_reqpartnership_tv_status) TextView status;
        @BindView(R.id.item_reqpartnership_img_product) ImageView image;
        @BindView(R.id.item_reqpartnership_img_partnership) ImageView partnership;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(final String store_name, final String prodcut_id){
        index = 0;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_requestpartnership);

        final Button addpricerange = (Button) dialog.findViewById(R.id.requestpartnershipdialog_btn_addpricerange);
        final TextView storename = (TextView) dialog.findViewById(R.id.requestpartnershipdialog_tv_storename);
        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.requestpartnershipdialog_rv);
        final LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.requestpartnershipdialog_ln_layout);
        final TextInputLayout layout_minorder = (TextInputLayout) dialog.findViewById(R.id.requestpartnershipdialog_layout_minorder);
        final TextInputEditText minorder = (TextInputEditText) dialog.findViewById(R.id.requestpartnershipdialog_et_minorder);
        final Button sendconfirmation = (Button) dialog.findViewById(R.id.requestpartnershipdialog_btn_sendconfirmation);
        final Button close = (Button) dialog.findViewById(R.id.requestpartnershipdialog_btn_close);

        storename.setText(store_name);

        service = apiUtils.getAPIService();
        service.req_get_product_detail(prodcut_id).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.code()==200){
                    priceList = response.body().getProductInfo().getPrice();
                    pricedetailsadapter = new BuyRVAdapter(priceList, context);
                    RecyclerView.LayoutManager horizontallayout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(horizontallayout);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(pricedetailsadapter);
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {

            }
        });
        addpricerange.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                formatter = new DecimalFormat("#,###,###");

                LinearLayout mainLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
                mainLinearLayout.setLayoutParams(mainParams);
                mainLinearLayout.setGravity(Gravity.CENTER);

                LinearLayout firstChildLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams firstChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                firstChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                firstChildLinearLayout.setLayoutParams(firstChildParams);

                LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                etParams.width = 100;

                TextView row = new TextView(context);
                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txtParams.setMarginStart(2);
                txtParams.setMarginEnd(2);
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setLayoutParams(txtParams);
                row.setTextSize(12);
                row.setText(String.valueOf(index+1) + ". ");

                final EditText qtymin = new EditText(context);
                qtymin.setInputType(InputType.TYPE_CLASS_NUMBER);
                qtymin.setHint("Qty (Min)");
                qtymin.setLayoutParams(etParams);
                qtymin.setTextSize(12);
                qtymin.addTextChangedListener(new FinancialTextWatcher(qtymin));


                TextView textView = new TextView(context);
                txtParams.setMarginStart(5);
                txtParams.setMarginEnd(10);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setLayoutParams(txtParams);
                textView.setTextSize(12);
                textView.setText("between");

                final EditText qtymax = new EditText(context);
                qtymax.setHint("Qty (Max)");
                qtymax.setLayoutParams(etParams);
                qtymax.setInputType(InputType.TYPE_CLASS_NUMBER);
                qtymax.setTextSize(12);
                qtymax.addTextChangedListener(new FinancialTextWatcher(qtymax));

                Button button = new Button(context);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                buttonParams.width = 100;
                buttonParams.height = 50;
                button.setText("Max");
                button.setTextColor(context.getResources().getColor(R.color.white));
                button.setBackgroundResource(R.drawable.button_1_3_mini);
                button.setTextSize(10);
                button.setWidth(20);
                button.setHeight(10);
                button.setLayoutParams(buttonParams);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        qtymax.setText("max");
                    }
                });


                LinearLayout.LayoutParams etprice = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                etprice.weight = 1;
                EditText price = new EditText(context);
                price.setHint("Price");
                price.setLayoutParams(etprice);
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                price.setTextSize(12);
                price.addTextChangedListener(new FinancialTextWatcher(price));

                firstChildLinearLayout.addView(row, 0);
                firstChildLinearLayout.addView(qtymin, 1);
                firstChildLinearLayout.addView(textView, 2);
                firstChildLinearLayout.addView(qtymax, 3);
                firstChildLinearLayout.addView(button, 4);
                firstChildLinearLayout.addView(price, 5);

                LinearLayout secondChildLinearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams secondChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                secondChildParams.setMargins(0,5,0,5);
                secondChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                secondChildLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.grey));
                secondChildLinearLayout.setLayoutParams(secondChildParams);

                mainLinearLayout.addView(firstChildLinearLayout, 0);
                mainLinearLayout.addView(secondChildLinearLayout, 1);
                layout.addView(mainLinearLayout, index);
                index++;
            }
        });
        sendconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View z = null;
                container_price = new ArrayList<>();
                for (int i = 0; i < layout.getChildCount(); i++) {
                    z = layout.getChildAt(i);
                    if (z instanceof LinearLayout) {
                        View tempView = ((LinearLayout) z).getChildAt(0);
                        View et_qtymin = ((LinearLayout) tempView).getChildAt(1);
                        View et_qtymax = ((LinearLayout) tempView).getChildAt(3);
                        View et_price = ((LinearLayout) tempView).getChildAt(5);
                        String et_minvalue = null;
                        String et_maxvalue = null;
                        String et_pricevalue = null;
                        if (et_qtymin instanceof EditText) {
                            et_minvalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_qtymin).getText().toString());
                            et_maxvalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_qtymax).getText().toString());
                            et_pricevalue = FinancialTextWatcher.trimCommaOfString(((EditText) et_price).getText().toString());
                            container_price.add(new Addproduct_Price(et_minvalue, et_maxvalue, et_pricevalue));
                        }
                    }
                }
                layout_minorder.setErrorEnabled(false);
                if (TextUtils.isEmpty(minorder.getText())) {
                    layout_minorder.setErrorEnabled(true);
                    layout_minorder.setError("Minimum order is required");
                    return;
                } else if (container_price.size() == 0) {
                    Toast.makeText(context, "Please add your price", Toast.LENGTH_LONG).show();
                    return;
                } else if (container_price.size() != 0) {
                    Boolean check = true;
                    int k = 0;
                    String error = null;
                    String regexStr = "^[0-9]*$";
                    for (int i = 0; i < container_price.size() * 3; i++) {
                        String qty_min = container_price.get(k).getQty_min(), qty_max = container_price.get(k).getQty_max(), price = container_price.get(k).getprice();
                        int min, max, pric;
                        if (qty_min.equals("")) {
                            check = false;
                            error = "Please insert the qty minimum at row " + String.valueOf(k + 1);
                            break;
                        }
                        if (qty_max.equals("")) {
                            check = false;
                            error = "Please insert the qty maximum at row " + String.valueOf(k + 1);
                            break;
                        }
                        if (!qty_max.trim().matches(regexStr) && !qty_max.equals("max")){
                            check = false;
                            error = "Qty maximum at row " + String.valueOf(k+1) + " can only be input as number or max";
                            break;
                        }
                        if (price.equals("")) {
                            check = false;
                            error = "Please insert the price at row " + String.valueOf(k + 1);
                            break;
                        } else {
                            min = Integer.parseInt(qty_min);
                            pric = Integer.parseInt(price);
                            if (!qty_max.equals("max")) {
                                max = Integer.parseInt(qty_max);
                                if (min > max) {
                                    check = false;
                                    error = "Qty minimum cannot bigger than qty maximum at row " + String.valueOf(k + 1);
                                    break;
                                }
                            }
                        }

                        if (i % 3 == 2) {
                            k++;
                        }
                    }
                    k = 0;
                    for (int i = 0; i <container_price.size() * 3; i++){
                        if (k>0){
                            String qty_min = container_price.get(k).getQty_min(), qty_max = container_price.get(k-1).getQty_max(),
                                    price_before = container_price.get(k-1).getprice(), price_after = container_price.get(k).getprice();
                            if (qty_max.equals("max")){
                                check = false;
                                error = "Qty max "+ String.valueOf(k)+ " can't set to be max because it is no the last from the price range";
                                break;
                            }
                            if (Integer.parseInt(qty_min) <= Integer.parseInt(qty_max)){
                                check = false;
                                error = "Qty minimum at row " + String.valueOf(k+1) + " must bigger than " + "qty maximum at row " + String.valueOf(k);
                                break;
                            }
                            if (Integer.parseInt(price_after) >= Integer.parseInt((price_before))){
                                check = false;
                                error = "Price at row " + String.valueOf(k+1) + " must below than " + "price at row " + String.valueOf(k);
                                break;
                            }
                        }
                        if (i % 3 == 2) {
                            k++;
                        }
                    }
                    if (!check) {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                progressDialog.setMessage("Uploading, please wait ....");
                progressDialog.show();

                MediaType text = MediaType.parse("text/plain");

                HashMap<String, String> user = sessionManagement.getUserDetails();
                token = user.get(SessionManagement.TOKEN);

                RequestBody request_token = RequestBody.create(text, token);
                RequestBody request_productid = RequestBody.create(text, prodcut_id);
                RequestBody request_minorder = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(minorder.getText().toString()));
                int panjang = container_price.size() * 3;
                int k = 0;
                MultipartBody.Part[] prices = new MultipartBody.Part[panjang];
                for(int i = 0; i<(panjang);i++){
                    if(i%3==0){
                        prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[qty_min]", container_price.get(k).getQty_min());
                    }
                    else if (i%3==1){
                        prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[qty_max]", container_price.get(k).getQty_max());
                    }
                    else {
                        prices[i] = MultipartBody.Part.createFormData("price[" + k + "]" + "[price]", container_price.get(k).getprice());
                    }
                    if(i%3==2){
                        k++;
                    }

                }
                service = apiUtils.getAPIService();
                service.req_submit_request_partnership(request_token, request_productid, request_minorder, prices).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Log.i("debug", "onResponse: SUCCESS");
                            try{
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if(jsonResults.getString("message").toLowerCase().equals("request partnership submitted successfully")){
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                    Bundle bundle = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                        Intent intent = new Intent(context, UplinePartnership.class);
                                        bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slideright, R.anim.fadeout).toBundle();
                                        context.startActivity(intent, bundle);
                                        UplinePartnership.UPLINEPARTNERSHIP.finish();
                                    }
                                }else{
                                    String message = jsonResults.getString("message");
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                                }
                                //"message": "Nama Franchise sudah didaftarkan"
                                //"message": "Franchise registered successfully",
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                        else {
                            Log.i("debug", "onResponse: FAILED");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
