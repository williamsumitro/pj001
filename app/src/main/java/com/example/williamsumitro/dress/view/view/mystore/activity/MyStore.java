package com.example.williamsumitro.dress.view.view.mystore.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.model_CourierService;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.mystore.adapter.MyStoreVP;
import com.example.williamsumitro.dress.view.view.product.adapter.DetailProductCourierRVAdapter;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Util;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStore extends AppCompatActivity {
    public static MyStore MYSTORE;
    @BindView(R.id.mystore_boom) BoomMenuButton bmb;
    @BindView(R.id.mystore_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.mystore_viewpager) ViewPager viewPager;
    @BindView(R.id.mystore_tvNamaToko) TextView namatoko;
    @BindView(R.id.mystore_Toolbar) Toolbar toolbar;
    @BindView(R.id.mystore_tablayout) TabLayout tabLayout;
    @BindView(R.id.mystore_imgBanner) ImageView banner;
    @BindView(R.id.mystore_img_star1) ImageView one;
    @BindView(R.id.mystore_img_star2) ImageView two;
    @BindView(R.id.mystore_img_star3) ImageView three;
    @BindView(R.id.mystore_img_star4) ImageView four;
    @BindView(R.id.mystore_img_star5) ImageView five;
    @BindView(R.id.mystore_collaptoolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.mystore_circleLogo) CircleImageView circleImageView;
    @BindView(R.id.mystore_product) TextView product;
    @BindView(R.id.mystore_soldout) TextView soldout;
    @BindView(R.id.mystore_transaction) TextView transaction;
    @BindView(R.id.mystore_tv_courier) TextView courier;
    @BindView(R.id.mystore_ln_courier) LinearLayout container_courier;

    private final static String STORE_ID = "STORE_ID";
    private final static String STORE_RESULT = "STORE_RESULT";
    private final static String RESULT = "RESULT";

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
        setContentView(R.layout.activity_my_store);
        initView();
        initBoom();
        api_getuserstore();
        setupToolbar();
        initCollapToolbar();
    }

    private void api_getuserstore() {
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.code()==200){
                    storeid = response.body().getStore().getStoreId().toString();
                    api_getstoredetail(storeid);
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }
    private void api_getstoredetail(String store_id){
        service.req_get_store_detail(store_id).enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                if (response.code()==200){
                    storeDetails = response.body().getResult();
                    courierServiceList = storeDetails.getCourierService();
                    initData();
                    initClick();
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
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
                .placeholder(R.drawable.default_banner)
                .into(banner);
        Picasso.with(context)
                .load(storeDetails.getPhoto())
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
        progressDialog.dismiss();
        setupViewPager();
    }

    private void initClick() {
        container_courier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(courierServiceList);
            }
        });

    }
    private void initView() {
        ButterKnife.bind(this);
        MYSTORE = this;
        supportPostponeEnterTransition();
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        df = new DecimalFormat("###.#");
        courierServiceList = new ArrayList<>();
        service = apiUtils.getAPIService();
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
        viewPager.setAdapter(new MyStoreVP(getSupportFragmentManager(),bundle));
        tabLayout.setupWithViewPager(viewPager);
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
    private void initBoom(){
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            if (i == 0){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.store_settings)
                        .shadowEffect(true)
                        .shadowCornerRadius(Util.dp2px(5))
                        .imagePadding(new Rect(10, 10, 10, 10))
                        .normalText("Store Information")
                        .subNormalText("Edit Store Information")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.green9)
                        .normalColorRes(R.color.green5)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, EditStore.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
            else if (i == 1){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.store_documents)
                        .shadowEffect(true)
                        .shadowCornerRadius(Util.dp2px(5))
                        .imagePadding(new Rect(10, 10, 10, 10))
                        .normalText("Supporting Documents")
                        .subNormalText("Edit Supporting Documents")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.brown9)
                        .normalColorRes(R.color.brown6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, SupportingDocument.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
            else if (i == 2){
                HamButton.Builder builder = new HamButton.Builder()
                        .normalImageRes(R.drawable.store_delivery)
                        .shadowEffect(true)
                        .shadowCornerRadius(Util.dp2px(5))
                        .imagePadding(new Rect(10, 10, 10, 10))
                        .normalText("Courier Service")
                        .subNormalText("Edit Courier Service")
                        .subTextGravity(Gravity.RIGHT)
                        .subTypeface(Typeface.SERIF)
                        .rippleEffect(true)
                        .highlightedColorRes(R.color.orange9)
                        .normalColorRes(R.color.orange6)
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                Intent intent = new Intent(context, CourierServiceActivity.class);
                                initanim(intent);
                            }
                        });
                bmb.addBuilder(builder);
            }
        }
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
}
