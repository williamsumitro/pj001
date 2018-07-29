package com.example.williamsumitro.dress.view.view.store.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductCourierRVAdapter;
import com.example.williamsumitro.dress.view.view.store.adapter.StoreDetailVPAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStore extends AppCompatActivity {
    @BindView(R.id.detailstore_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.detailstore_viewpager) ViewPager viewPager;
    @BindView(R.id.detailstore_tvNamaToko) TextView namatoko;
    @BindView(R.id.detailstore_Toolbar) Toolbar toolbar;
    @BindView(R.id.detailstore_tablayout) TabLayout tabLayout;
    @BindView(R.id.detailstore_imgBanner) ImageView banner;
    @BindView(R.id.detailstore_img_star1) ImageView one;
    @BindView(R.id.detailstore_img_star2) ImageView two;
    @BindView(R.id.detailstore_img_star3) ImageView three;
    @BindView(R.id.detailstore_img_star4) ImageView four;
    @BindView(R.id.detailstore_img_star5) ImageView five;
    @BindView(R.id.detailstore_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailstore_circleLogo) CircleImageView circleImageView;
    @BindView(R.id.detailstore_product) TextView product;
    @BindView(R.id.detailstore_soldout) TextView soldout;
    @BindView(R.id.detailstore_transaction) TextView transaction;
    @BindView(R.id.detailstore_btn_favorite) Button favorite;
    @BindView(R.id.detailstore_tv_courier) TextView courier;
    @BindView(R.id.detailstore_ln_courier) LinearLayout container_courier;

    private final static String STORE_ID = "STORE_ID";
    private final static String STORE_RESULT = "STORE_RESULT";

    private String storeid, token;
    private Context context;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private apiService service;
    private StoreDetails storeDetails;
    private DecimalFormat df;
    private ArrayList<model_CourierService> courierServiceList;
    private DetailProductCourierRVAdapter adapter;
    private Boolean favoritestatus =false;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        iniView();
        initGetIntent();
        setupToolbar();
        initCollapToolbar();
    }

    private void get_detailstore() {
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    service.req_get_user_store_detail(token, storeid).enqueue(new Callback<StoreDetailResponse>() {
                        @Override
                        public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
//                            Toasty.info(context, storeid, Toast.LENGTH_SHORT, true).show();
                            if (response.code()==200){
                                storeDetails = response.body().getResult();
                                courierServiceList = storeDetails.getCourierService();
                                favoritestatus = response.body().getFavoriteStatus();
                                if (favoritestatus){
                                    favorite.setBackgroundResource(R.drawable.button3_2);
                                    favorite.setText("Unfavorite");
                                }
                                else {
                                    favorite.setBackgroundResource(R.drawable.button3_1);
                                    favorite.setText("Favorite");
                                }
                                initData();
                                initClick();
                            }
                        }

                        @Override
                        public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toasty.info(context, t.getMessage(), Toast.LENGTH_LONG, true).show();
                            Toasty.error(context, "Please Try again", Toast.LENGTH_LONG, true).show();
                        }
                    });
                }
                else {
                    service.req_get_store_detail(storeid).enqueue(new Callback<StoreDetailResponse>() {
                        @Override
                        public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                            if (response.code()==200){
                                storeDetails = response.body().getResult();
                                courierServiceList = storeDetails.getCourierService();
                                initData();
                                initClick();
                            }
                        }

                        @Override
                        public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                initDialog(3);
            }
        });

    }

    private void initClick() {
        container_courier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(courierServiceList);
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_favorite();
            }
        });

    }

    private void initData() {
        namatoko.setText(storeDetails.getName());
        product.setText(String.valueOf(storeDetails.getProduct().size()));
        soldout.setText(storeDetails.getSoldProduct());
        transaction.setText(storeDetails.getTransaction().toString());
        Picasso.with(context)
                .load(storeDetails.getBanner())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_banner)
                .into(banner);
        Picasso.with(context)
                .load(storeDetails.getPhoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE )
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.default_photo)
                .into(circleImageView);
        if (Double.parseDouble(storeDetails.getRating()) == 0){
            one.setImageResource(R.drawable.star0);
            two.setImageResource(R.drawable.star0);
            three.setImageResource(R.drawable.star0);
            four.setImageResource(R.drawable.star0);
            five.setImageResource(R.drawable.star0);
        }
        else if(Double.parseDouble(storeDetails.getRating())>0 && Double.parseDouble(storeDetails.getRating())<1){
            one.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(storeDetails.getRating()) == 1){
            one.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(storeDetails.getRating())>1 && Double.parseDouble(storeDetails.getRating())<2){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(storeDetails.getRating()) == 2){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(storeDetails.getRating())>2 && Double.parseDouble(storeDetails.getRating())<3){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(storeDetails.getRating()) == 3){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(storeDetails.getRating())>3 && Double.parseDouble(storeDetails.getRating())<4){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(storeDetails.getRating()) == 4){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
        }
        else if(Double.parseDouble(storeDetails.getRating())>4 && Double.parseDouble(storeDetails.getRating())<5){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
            five.setImageResource(R.drawable.star1);
        }
        else if (Double.parseDouble(storeDetails.getRating()) == 5){
            one.setImageResource(R.drawable.star);
            two.setImageResource(R.drawable.star);
            three.setImageResource(R.drawable.star);
            four.setImageResource(R.drawable.star);
            five.setImageResource(R.drawable.star);
        }
        courier.setText(String.valueOf(storeDetails.getCourierService().size()));
        setupViewPager();
    }

    private void initGetIntent(){
        Intent getintent = getIntent();
        if (getintent.hasExtra(STORE_ID)){
            storeid = getintent.getExtras().getString(STORE_ID);
            get_detailstore();
        }
    }
    private void iniView() {
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        service = apiUtils.getAPIService();
        token = user.get(SessionManagement.TOKEN);
        df = new DecimalFormat("###.#");
        courierServiceList = new ArrayList<>();
        PicassoTools.clearCache(Picasso.with(context));
    }
    private void initCollapToolbar(){
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollrange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollrange == -1){
                    scrollrange = appBarLayout.getTotalScrollRange();
                }
                if(scrollrange + verticalOffset == 0){
                    toolbar.setTitle(namatoko.getText().toString());
                    isShow = true;
                } else if (isShow) {
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(" ");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void setupViewPager(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(STORE_RESULT, storeDetails);
        viewPager.setAdapter(new StoreDetailVPAdapter(getSupportFragmentManager(),bundle));
        tabLayout.setupWithViewPager(viewPager);
        progressDialog.dismiss();
    }
    private void initDialog(ArrayList<model_CourierService> courierServices){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_courier);

        final RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.courierdialog_rv);
        final Button buttonok = (Button) dialog.findViewById(R.id.courierdialog_btn_ok);
        adapter = new DetailProductCourierRVAdapter(courierServices, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        buttonok.setText("Ok");
        buttonok.setBackgroundResource(R.drawable.button1_green);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void api_favorite(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        service.req_get_auth_user(token).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code()==200){
                    if (favoritestatus){
                        service.req_delete_from_favorite(token, String.valueOf(storeDetails.getStoreId())).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    try{
                                        JSONObject jsonResults = new JSONObject(response.body().string());
                                        if(jsonResults.getBoolean("status")){
                                            String message = jsonResults.getString("message");
                                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                            favorite.setBackgroundResource(R.drawable.button3_1);
                                            favorite.setText("Favorite");
                                            favoritestatus = false;
                                            progressDialog.dismiss();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                                initDialog(3);
                            }
                        });
                    }
                    else {
                        service.req_add_to_favorite(token, String.valueOf(storeDetails.getStoreId())).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    try{
                                        JSONObject jsonResults = new JSONObject(response.body().string());
                                        if(jsonResults.getBoolean("status")){
                                            String message = jsonResults.getString("message");
                                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                            favorite.setBackgroundResource(R.drawable.button3_2);
                                            favorite.setText("Unfavorite");
                                            favoritestatus = true;
                                            progressDialog.dismiss();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                                initDialog(3);
                            }
                        });
                    }
                }
                else {
                    progressDialog.dismiss();
                    initDialog(1);
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                initDialog(3);
                progressDialog.dismiss();
            }
        });
    }
    private void initDialog(int stats){
        if (stats == 1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Authorization")
                    .setContentText("You need to login first !")
                    .setConfirmText("Login")
                    .setCancelText("Cancel")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(context, Login.class);
                            initanim(intent);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }
        if (stats == 3){
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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbarhome) {
            Intent intent = new Intent(context, MainActivity.class);
            initanim(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
