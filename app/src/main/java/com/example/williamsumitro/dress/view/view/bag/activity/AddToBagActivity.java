package com.example.williamsumitro.dress.view.view.bag.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.williamsumitro.dress.view.model.AddToBag;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductInfo;
import com.example.williamsumitro.dress.view.model.Product_Size_Qty;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.bag.adapter.AddToBagRV;
import com.example.williamsumitro.dress.view.view.bag.adapter.BuyRVAdapter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

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
//    @BindView(R.id.addtobag_lnfree) LinearLayout container_free;
//    @BindView(R.id.addtobag_lnl) LinearLayout container_l;
//    @BindView(R.id.addtobag_lnm) LinearLayout container_m;
//    @BindView(R.id.addtobag_lnxl) LinearLayout container_xl;
//    @BindView(R.id.addtobag_lns) LinearLayout container_s;
//    @BindView(R.id.addtobag_qv_l) QuantityView qv_l;
//    @BindView(R.id.addtobag_qv_xl) QuantityView qv_xl;
//    @BindView(R.id.addtobag_qv_free) QuantityView qv_free;
//    @BindView(R.id.addtobag_qv_s) QuantityView qv_s;
//    @BindView(R.id.addtobag_qv_m) QuantityView qv_m;
    @BindView(R.id.addtobag_rv) RecyclerView rv;

    private List<Price> priceDetailsList;
    private Context context;
    private BuyRVAdapter pricedetailsadapter;
    private AddToBagRV adapter;
    private int qtyS = 0, qtyM = 0, qtyL = 0, qtyXL = 0, qtyFree = 0, subtot = 0, disc = 0;
    private Double percen = 0.0, tot = 0.0;
    private DecimalFormat formatter;
    private apiService service;
    private SessionManagement sessionManagement;
    private ArrayList<Size> sizes;
    private ArrayList<Product_Size_Qty> data;
    private Product_Size_Qty data_size;

    private final static String NAMAPRODUCT = "NAMAPRODUCT";
    private final static String QTYMINORDER = "QTYMINORDER";
    private final static String QTYMAXORDER = "QTYMAXORDER";
    private final static String GAMBARPRODUCT = "GAMBARPRODUCT";
    private final static String MINORDER = "MINORDER";
    private final static String PRICELIST = "PRICELIST";
    private final static String SIZELIST = "SIZELIST";
    private final static String PRODUCT_ID = "PRODUCT_ID";
    private final static String INBAG = "INBAG";

    private String extra_namaproduct, extra_gambarproduct, extra_minorder, token, extra_productid, extra_inbag;
    private ArrayList<String> extra_priceminlist, extra_qtyminorder, extra_sizelist, extra_qtymaxorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_bag);
        initView();
        setuptoolbar();
        getactivityIntent();
        initData();
//        initQV();
//        initGetRV();
        initClick();
    }

    private void initClick() {
        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_addtobag();
//                RetriveData();
            }
        });
    }

    public void RetriveData(){
        data_size = adapter.retreiveData();
        Boolean check = false;
        Integer index = -1;
        if (data.size()>0){
            for (int i = 0; i <data.size();i++){
                if (String.valueOf(data_size.getSize_id()).equals(String.valueOf(data.get(i).getSize_id()))){
                    check = true;
                    index = i;
                    break;
                }else {
                    check = false;
                }
            }
            if (check){
                Product_Size_Qty sizedata;
                sizedata = new Product_Size_Qty(data_size.getSize_id(),data_size.getQty());
                data.set(index, sizedata);
            }
            else {
                Product_Size_Qty sizedata;
                sizedata = new Product_Size_Qty(data_size.getSize_id(), data_size.getQty());
                data.add(sizedata);
            }
        }
        else {
            Product_Size_Qty sizedata;
            sizedata = new Product_Size_Qty(data_size.getSize_id(), data_size.getQty());
            data.add(sizedata);
        }
        changeprice();
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
        sizes = new ArrayList<>();
        data = new ArrayList<>();

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
            sizes = (ArrayList<Size>) getintent.getSerializableExtra(SIZELIST);
            extra_inbag = getintent.getExtras().getString(INBAG);
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
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_product)
                .into(imageproduct);
        if (extra_inbag.equals("true")){
            minorder.setText("1");
        }
        else {
            minorder.setText(extra_minorder);
        }
        setupRV();
    }
    private void setupRV(){
        adapter = new AddToBagRV(context, sizes, AddToBagActivity.this);
        RecyclerView.LayoutManager verticallayout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(verticallayout);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }
    private void api_addtobag() {
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
        com.example.williamsumitro.dress.view.model.AddToBag addToBag = new com.example.williamsumitro.dress.view.model.AddToBag(token, extra_productid, data);
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
        int sum = 0;
        for (int i = 0; i < data.size(); i++){
            sum += data.get(i).getQty();
        }
        int min, max;
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
        if (extra_inbag.equals("true")){
            continues.setEnabled(true);
            continues.setBackgroundColor(getResources().getColor(R.color.green1));
        }
        else {
            if(sum < Integer.parseInt(extra_minorder)){
                continues.setEnabled(false);
                continues.setBackgroundColor(getResources().getColor(R.color.gray1));
            }
            else {
                continues.setEnabled(true);
                continues.setBackgroundColor(getResources().getColor(R.color.green1));
            }
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
