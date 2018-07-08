package com.example.williamsumitro.dress.view.view.sellerpanel.partnership.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
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
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private int index=0;

    public UplinePartnership_RV(Context context, ArrayList<UplinePartnershipItem> itemArrayList){
        this.context = context;
        this.itemArrayList = itemArrayList;
        priceList = new ArrayList<>();
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
        @BindView(R.id.item_reqpartnership_img_product) ImageView image;
        @BindView(R.id.item_reqpartnership_img_partnership) ImageView partnership;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private void initDialog(final String store_name, final String prodcut_id){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.requestpartnership_dialog);

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
                etParams.width = 150;

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
                    if (!check) {
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        return;
                    }
                }
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
