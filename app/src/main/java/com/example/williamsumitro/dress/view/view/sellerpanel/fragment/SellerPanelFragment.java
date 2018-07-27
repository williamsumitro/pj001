package com.example.williamsumitro.dress.view.view.sellerpanel.fragment;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.openstore.activity.OpenStore;
import com.example.williamsumitro.dress.view.view.sellerpanel.adapter.SellerPanelPagerAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerPanelFragment extends Fragment {
    @BindView(R.id.seller_panel_hicvp) HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    @BindView(R.id.seller_panel_tv_status) TextView status;
    @BindView(R.id.seller_panel_tv_comment) TextView comment;
    @BindView(R.id.seller_panel_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.seller_panel_ln_top_bottom)LinearLayout top_bottom;
    @BindView(R.id.seller_panel_img_status) ImageView img_status;
    @BindView(R.id.seller_panel_btn_openstore) Button openstore;
    @BindView(R.id.seller_panel_ln_bottom) LinearLayout bottom;
    @BindView(R.id.seller_panel_ln_top) LinearLayout top;

    private Context context;
    private SessionManagement sessionManagement;
    private String token;
    private apiService service;
    private StoreDetails storeDetails;

    private SweetAlertDialog sweetAlertDialog;
    public SellerPanelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller_panel, container, false);
        initView(view);

        checkstatus();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkstatus();
            }
        });
        horizontalInfiniteCycleViewPager.setAdapter(new SellerPanelPagerAdapter(getContext()));
        return view;
    }
    private void checkstatus() {
        swipeRefreshLayout.setRefreshing(true);

        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if(response.code()==200){
                    if(response.body().getHaveStore()){
                        storeDetails = response.body().getStore();
                        switch (storeDetails.getStoreActiveStatus()) {
                            case "0":
                                top.setVisibility(View.GONE);
                                bottom.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                status.setText("Waiting for admin approval");
                                top_bottom.setVisibility(View.GONE);
                                horizontalInfiniteCycleViewPager.setVisibility(View.GONE);
                                img_status.setImageResource(R.drawable.pending);
                                break;
                            case "1":
                                swipeRefreshLayout.setVisibility(View.GONE);
                                top_bottom.setVisibility(View.GONE);
                                horizontalInfiniteCycleViewPager.setVisibility(View.VISIBLE);
                                break;
                            default:
                                top.setVisibility(View.GONE);
                                bottom.setVisibility(View.VISIBLE);
                                swipeRefreshLayout.setVisibility(View.VISIBLE);
                                status.setText("Rejected");
                                top_bottom.setVisibility(View.VISIBLE);
                                horizontalInfiniteCycleViewPager.setVisibility(View.GONE);
                                comment.setText(response.body().getStore().getRejectComment().toString());
                                img_status.setImageResource(R.drawable.reject);
                                break;
                        }
                    }
                    else {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        top.setVisibility(View.VISIBLE);
                        bottom.setVisibility(View.GONE);
                        initOnClick();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                    Toasty.error(context, "Please swipe down to refresh again", Toast.LENGTH_SHORT, true).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                initDialog(3);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView(View view){
        ButterKnife.bind(this,view);
        context = getContext();
        sessionManagement = new SessionManagement(getContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
    }
    private void initDialog(int stats) {
        if (stats == 3) {
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
                    });
            if (!((Activity) context).isFinishing()) {
                sweetAlertDialog.show();
            }
        }
    }
    private void initOnClick() {
        openstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpenStore.class);
                initanim(intent);
            }
        });
    }
    private void initanim(Intent intent){
        Bundle bundle = ActivityOptions.makeCustomAnimation(context,R.anim.slideright, R.anim.fadeout).toBundle();
        context.startActivity(intent, bundle);
    }

}
