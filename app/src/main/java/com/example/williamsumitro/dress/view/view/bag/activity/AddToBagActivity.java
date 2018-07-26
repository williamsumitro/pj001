package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.Product_Size_Qty;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
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
import es.dmoral.toasty.Toasty;
import me.himanshusoni.quantityview.QuantityView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToBagActivity extends AppCompatActivity implements QuantityView.OnQuantityChangeListener {
    @BindView(R.id.addtobag_tvnameproduct) TextView nameproduct;
    @BindView(R.id.addtobag_tvpriceproduct) TextView priceproduct;
    @BindView(R.id.addtobag_tvtotal) TextView total;
    @BindView(R.id.addtobag_toolbar) Toolbar toolbar;
    @BindView(R.id.addtobag_imgproduct) ImageView imageproduct;
    @BindView(R.id.addtobag_tvMinOrder) TextView minorder;
    @BindView(R.id.addtobag_btncontinue) Button continues;
    @BindView(R.id.addtobag_lnfree) LinearLayout container_free;
    @BindView(R.id.addtobag_lnl) LinearLayout container_l;
    @BindView(R.id.addtobag_lnm) LinearLayout container_m;
    @BindView(R.id.addtobag_lnxl) LinearLayout container_xl;
    @BindView(R.id.addtobag_lns) LinearLayout container_s;
    @BindView(R.id.addtobag_qv_l) QuantityView qv_l;
    @BindView(R.id.addtobag_qv_xl) QuantityView qv_xl;
    @BindView(R.id.addtobag_qv_free) QuantityView qv_free;
    @BindView(R.id.addtobag_qv_s) QuantityView qv_s;
    @BindView(R.id.addtobag_qv_m) QuantityView qv_m;

    private List<Price> priceDetailsList;
    private Context context;
    private BuyRVAdapter pricedetailsadapter;
    private int qtyS = 0, qtyM = 0, qtyL = 0, qtyXL = 0, qtyFree = 0, subtot = 0, disc = 0;
    private Double percen = 0.0, tot = 0.0;
    private DecimalFormat formatter;
    private apiService service;
    private SessionManagement sessionManagement;
    private ArrayList<Product_Size_Qty> product_size_qtyArrayList;

    private final static String NAMAPRODUCT = "NAMAPRODUCT";
    private final static String QTYMINORDER = "QTYMINORDER";
    private final static String QTYMAXORDER = "QTYMAXORDER";
    private final static String GAMBARPRODUCT = "GAMBARPRODUCT";
    private final static String MINORDER = "MINORDER";
    private final static String PRICELIST = "PRICELIST";
    private final static String SIZELIST = "SIZELIST";
    private final static String PRODUCT_ID = "PRODUCT_ID";

    private String extra_namaproduct, extra_gambarproduct, extra_minorder, token, extra_productid;
    private ArrayList<String> extra_priceminlist, extra_qtyminorder, extra_sizelist, extra_qtymaxorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_bag);
        initView();
        setuptoolbar();
        getactivityIntent();
        initData();
        initQV();
        initGetRV();
        initClick();
    }

    private void initClick() {
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_addtobag();
            }
        });
    }

    private void initQV(){
        qv_s.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToBagActivity.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(AddToBagActivity.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);

                et.setText(String.valueOf(qv_s.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyS = Integer.parseInt(newQuantity);
                        if (qtyS>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }
                        qv_s.setQuantity(qtyS);
                        changeprice();
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        qv_m.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToBagActivity.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(AddToBagActivity.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);
                et.setText(String.valueOf(qv_m.getQuantity()));


                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyM = Integer.parseInt(newQuantity);
                        if (qtyM>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }

                        qv_m.setQuantity(qtyM);
                        changeprice();
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        qv_l.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToBagActivity.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(AddToBagActivity.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);
                et.setText(String.valueOf(qv_l.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyL = Integer.parseInt(newQuantity);
                        if (qtyL>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }

                        qv_l.setQuantity(qtyL);
                        changeprice();
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        qv_xl.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToBagActivity.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(AddToBagActivity.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);
                et.setText(String.valueOf(qv_xl.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyXL = Integer.parseInt(newQuantity);
                        if (qtyXL>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }

                        qv_xl.setQuantity(qtyXL);
                        changeprice();
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        qv_free.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToBagActivity.this);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(AddToBagActivity.this).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);
                et.setText(String.valueOf(qv_free.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qtyFree = Integer.parseInt(newQuantity);
                        if (qtyFree>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }

                        qv_free.setQuantity(qtyFree);
                        changeprice();
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }
    private void initGetRV(){
        qv_s.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qtyS = qv_s.getQuantity();
                changeprice();
            }

            @Override
            public void onLimitReached() {

            }
        });
        qv_m.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qtyM = qv_m.getQuantity();
                changeprice();
            }

            @Override
            public void onLimitReached() {

            }
        });
        qv_l.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qtyL = qv_l.getQuantity();
                changeprice();
            }

            @Override
            public void onLimitReached() {

            }
        });
        qv_xl.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qtyXL = qv_xl.getQuantity();
                changeprice();
            }

            @Override
            public void onLimitReached() {

            }
        });
        qv_free.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qtyFree = qv_free.getQuantity();
                changeprice();
            }

            @Override
            public void onLimitReached() {

            }
        });
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        priceDetailsList = new ArrayList<>();
        formatter = new DecimalFormat("#,###,###");
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
        continues.setEnabled(false);
        continues.setBackgroundColor(getResources().getColor(R.color.gray1));
        sessionManagement = new SessionManagement(getApplicationContext());
    }
    private void getactivityIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(NAMAPRODUCT)){
            extra_productid = getintent.getExtras().getString(PRODUCT_ID);
            extra_namaproduct = getintent.getExtras().getString(NAMAPRODUCT);
            extra_gambarproduct = getintent.getExtras().getString(GAMBARPRODUCT);
            extra_minorder = getintent.getExtras().getString(MINORDER);
            extra_priceminlist = getintent.getStringArrayListExtra(PRICELIST);
            extra_qtyminorder = getintent.getStringArrayListExtra(QTYMINORDER);
            extra_qtymaxorder = getintent.getStringArrayListExtra(QTYMAXORDER);
            extra_sizelist = getintent.getStringArrayListExtra(SIZELIST);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Product to Bag");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }

    private void initData(){
        nameproduct.setText(extra_namaproduct);
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(extra_priceminlist.get(0)))));
        Picasso.with(context)
                .load(extra_gambarproduct)
                .placeholder(R.drawable.default_product)
                .into(imageproduct);
        minorder.setText(extra_minorder);
        for (int i = 0; i<extra_sizelist.size(); i++){
            if (extra_sizelist.get(i).toLowerCase().equals("1"))
                container_s.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("2"))
                container_m.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("3"))
                container_l.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("4"))
                container_xl.setVisibility(View.VISIBLE);
            else if (extra_sizelist.get(i).toLowerCase().equals("5"))
                container_free.setVisibility(View.VISIBLE);
        }
    }

    private void api_addtobag() {
        product_size_qtyArrayList = new ArrayList<>();
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        for (int i = 0; i<extra_sizelist.size(); i++){
            Product_Size_Qty product_size_qty = null;
            if (extra_sizelist.get(i).toLowerCase().equals("1"))
                product_size_qty = new Product_Size_Qty(1, qtyS);
            else if (extra_sizelist.get(i).toLowerCase().equals("2"))
                product_size_qty = new Product_Size_Qty(2, qtyM);
            else if (extra_sizelist.get(i).toLowerCase().equals("3"))
                product_size_qty = new Product_Size_Qty(3, qtyL);
            else if (extra_sizelist.get(i).toLowerCase().equals("4"))
                product_size_qty = new Product_Size_Qty(4, qtyXL);
            else if (extra_sizelist.get(i).toLowerCase().equals("5"))
                product_size_qty = new Product_Size_Qty(5, qtyFree);
            product_size_qtyArrayList.add(product_size_qty);
        }
        service = apiUtils.getAPIService();
        com.example.williamsumitro.dress.view.model.AddToBag addToBag = new com.example.williamsumitro.dress.view.model.AddToBag(token, extra_productid, product_size_qtyArrayList);
        service.req_add_to_bag(addToBag).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getBoolean("status")){
                            String message = jsonResults.getString("message");
                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                            Intent intent = new Intent(context, ShoppingBag.class);
                            initanim(intent);
                            finish();
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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }

    private void changeprice(){
        int min, max;
        int sum = qtyS + qtyM + qtyL + qtyXL +qtyFree;
        for (int i = 0; i<extra_qtyminorder.size(); i++){
            min = Integer.parseInt(extra_qtyminorder.get(i));
            if (extra_qtymaxorder.get(i).equals("max")){
                percen = Double.parseDouble(extra_priceminlist.get(i));
            }
            else {
                max = Integer.parseInt(extra_qtymaxorder.get(i));
                if(sum>=min && sum<=max) {
                    percen = Double.parseDouble(extra_priceminlist.get(i));
                    break;
                }
                else {
                    percen = Double.parseDouble(extra_priceminlist.get(0));
                }
            }

        }

        if(sum < Integer.parseInt(extra_minorder)){
            continues.setEnabled(false);
            continues.setBackgroundColor(getResources().getColor(R.color.gray1));
        }
        else {
            continues.setEnabled(true);
            continues.setBackgroundColor(getResources().getColor(R.color.green1));
        }
        priceproduct.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(percen))));
        tot = sum * percen;
        total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(tot))));
    }

    @Override
    public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
    }

    @Override
    public void onLimitReached() {
        Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
    }
}
