package com.example.williamsumitro.dress.view.view.dashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.DashboardResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.dashboard_toolbar) Toolbar toolbar;
    @BindView(R.id.dashboard_date) TextView date;
    @BindView(R.id.dashboard_tv_status) TextView status;
    @BindView(R.id.dashboard_total) TextView total;
    @BindView(R.id.dashboard_spinner) Spinner spinner;
    @BindView(R.id.dashboard_swiperefreshlayout) SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private ProgressDialog progressDialog;
    private SessionManagement sessionManagement;
    private apiService service;
    private String token, choosen;
    private DecimalFormat df;
    private ArrayAdapter<CharSequence> dashboardadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initView();
        setupToolbar();
        initspinner();
        initRefresh();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRefresh();
            }
        });
    }

    private void initRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        api_dashboard();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initspinner(){
        dashboardadapter = ArrayAdapter.createFromResource(context, R.array.dashboard, R.layout.item_spinner);
        dashboardadapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(dashboardadapter);
        choosen = String.valueOf(spinner.getSelectedItemPosition()+1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosen = String.valueOf(i+1);
                api_dashboard();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void api_dashboard(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        service.req_dashboard(token, choosen).enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (response.code()==200){
                    if (response.body().getStatus()){
                        date.setText(response.body().getResult().getDescription());
                        total.setText("Total Price : IDR " + df.format(Double.parseDouble(response.body().getResult().getTotal())));
                        progressDialog.dismiss();
                    }
                    else {
                        Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                        progressDialog.dismiss();
                    }
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                Log.d("SEARCHERROR", t.toString());
                Toasty.error(context, "Please swipe down to refresh", Toast.LENGTH_SHORT, true).show();
                progressDialog.dismiss();
            }
        });
    }
    private void initView() {
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
        df = new DecimalFormat("#,###,###");
        PicassoTools.clearCache(Picasso.with(context));
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
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
