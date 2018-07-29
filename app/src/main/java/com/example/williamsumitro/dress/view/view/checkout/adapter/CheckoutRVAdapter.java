package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CheckoutInfo;
import com.example.williamsumitro.dress.view.model.Checkout_Courier;
import com.example.williamsumitro.dress.view.model.Checkout_CourierArrayList;
import com.example.williamsumitro.dress.view.model.CourierSpinner;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class CheckoutRVAdapter extends RecyclerView.Adapter<CheckoutRVAdapter.ViewHolder> {
    private ArrayList<CheckoutInfo> checkoutInfoArrayList;
    private Context context;
    private DecimalFormat formatter;
    private apiService service;
    private ArrayList<CourierSpinner> courierSpinners;
    CourierSpinner courierSpinner;
    private String idcourier;
    private CheckoutSpinCourierServiceAdapter adapter;
    private CheckoutProductRVAdapter rvadapter;
    private ArrayList<Checkout_Courier> checkout_courierArrayList;
    private SessionManagement sessionManagement;

    public CheckoutRVAdapter(ArrayList<CheckoutInfo> checkoutInfoArrayList, Context context){
        this.context = context;
        this.checkoutInfoArrayList = checkoutInfoArrayList;
        checkout_courierArrayList = new ArrayList<>();
        sessionManagement = new SessionManagement(context);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (position%5==0){
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.brown9));
            }
            else if (position%5==1){
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.blue9));
            }
            else if (position%5==2){
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.red9));
            }
            else if (position%5==3){
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.orange9));
            }
            else if (position%5==4){
                holder.cardView.setCardBackgroundColor(context.getColor(R.color.purple9));
            }
        }

        final CheckoutInfo checkoutInfo = checkoutInfoArrayList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.storename.setText(checkoutInfo.getStoreName());
        holder.totalitem.setText("Total : " + String.valueOf(checkoutInfo.getTotalQty())+" item(s)");
        holder.totalprice.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(checkoutInfo.getTotalPrice()))));
        int size = checkoutInfo.getCourierService().size();
        courierSpinners = new ArrayList<>();
        for (int i = 0; i<size; i++){
            int panjang = checkoutInfo.getCourierService().get(i).getCost().size();
            for(int j = 0; j<panjang; j++){
                courierSpinner = new CourierSpinner(checkoutInfo.getCourierService().get(i).getCourierId(),
                        checkoutInfo.getCourierService().get(i).getCourierName(),
                        checkoutInfo.getCourierService().get(i).getCost().get(j).getService(),
                        String.valueOf(checkoutInfo.getCourierService().get(i).getCost().get(j).getCost().get(0).getValue()),
                        checkoutInfo.getCourierService().get(i).getCost().get(j).getCost().get(0).getEtd());
                courierSpinners.add(courierSpinner);
            }
        }
        adapter = new CheckoutSpinCourierServiceAdapter(context, R.layout.item_spinner, courierSpinners);
        holder.courierservice.setAdapter(adapter);
        holder.note.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean check = false;
                int j = -1;
                for (int i = 0; i<checkout_courierArrayList.size();i++){
                    if (String.valueOf(checkoutInfo.getStoreId()).equals(checkout_courierArrayList.get(i).getStore_id())){
                        check = true;
                        j = i;
                        break;
                    }
                    else
                        check = false;
                }
                if (check){
                    int selected = holder.courierservice.getSelectedItemPosition();
                    CourierSpinner cs = new CourierSpinner(adapter.getItem(selected));
                    Checkout_Courier checkout_courier = new Checkout_Courier(String.valueOf(checkoutInfo.getStoreId()),
                            checkout_courierArrayList.get(j).getCourier_id(),
                            checkout_courierArrayList.get(j).getCourier_service(),
                            checkout_courierArrayList.get(j).getFee(),
                            s.toString());
                    checkout_courierArrayList.set(j, checkout_courier);
                    sessionManagement.keepCheckoutCourierService(new Checkout_CourierArrayList(checkout_courierArrayList));
                }else {
                    int selected = holder.courierservice.getSelectedItemPosition();
                    CourierSpinner cs = new CourierSpinner(adapter.getItem(selected));
                    Checkout_Courier checkout_courier = new Checkout_Courier(String.valueOf(checkoutInfo.getStoreId()),
                            courierSpinner.getCourier_id(),
                            courierSpinner.getCourier_service(),
                            courierSpinner.getFee(),
                            s.toString());
                    checkout_courierArrayList.add(checkout_courier);
                    sessionManagement.keepCheckoutCourierService(new Checkout_CourierArrayList(checkout_courierArrayList));
                }

            }
        });

        holder.courierservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CourierSpinner courierSpinner = (CourierSpinner) parent.getItemAtPosition(position);
                idcourier = courierSpinner.getCourier_id();
                boolean check = false;
                int j = -1;
                for (int i = 0; i<checkout_courierArrayList.size();i++){
                    if (String.valueOf(checkoutInfo.getStoreId()).equals(checkout_courierArrayList.get(i).getStore_id())){
                        check = true;
                        j = i;
                        break;
                    }
                    else
                        check = false;
                }
                if (check){
                    Checkout_Courier checkout_courier = new Checkout_Courier(String.valueOf(checkoutInfo.getStoreId()),
                                    courierSpinner.getCourier_id(),
                                    courierSpinner.getCourier_service(),
                                    courierSpinner.getFee(),
                                    holder.note.getText().toString());
                            checkout_courierArrayList.set(j, checkout_courier);
                            sessionManagement.keepCheckoutCourierService(new Checkout_CourierArrayList(checkout_courierArrayList));
                }else {
                    Checkout_Courier checkout_courier = new Checkout_Courier(String.valueOf(checkoutInfo.getStoreId()),
                            courierSpinner.getCourier_id(),
                            courierSpinner.getCourier_service(),
                            courierSpinner.getFee(),
                            holder.note.getText().toString());
                    checkout_courierArrayList.add(checkout_courier);
                    sessionManagement.keepCheckoutCourierService(new Checkout_CourierArrayList(checkout_courierArrayList));
                }
        }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.totalprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout_CourierArrayList ccal = sessionManagement.getcheckoutcourierService();
                for (int i = 0; i < ccal.getCheckout_courierArrayList().size(); i++){
                    Toast.makeText(context,String.valueOf(i) +"\n" +String.valueOf(ccal.getCheckout_courierArrayList().get(i).getStore_id()) + "\n" +
                            ccal.getCheckout_courierArrayList().get(i).getCourier_id() +"\n"+
                            ccal.getCheckout_courierArrayList().get(i).getCourier_service() + "\n"+
                            ccal.getCheckout_courierArrayList().get(i).getFee() + "\n" +
                            ccal.getCheckout_courierArrayList().get(i).getNote(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvadapter = new CheckoutProductRVAdapter(checkoutInfo.getProduct(), context);
        SnapHelper snapHelper = new LinearSnapHelper();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rv.setLayoutManager(layoutManager);
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rvadapter);
        alphaAdapter.setDuration(1000);
        alphaAdapter.setInterpolator(new OvershootInterpolator());
        holder.rv.setAdapter(alphaAdapter);
        snapHelper.attachToRecyclerView(holder.rv);
    }

    @Override
    public int getItemCount() {
        return checkoutInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemcheckout_cardview) CardView cardView;
        @BindView(R.id.itemcheckout_rv) RecyclerView rv;
        @BindView(R.id.itemcheckout_tv_storename) TextView storename;
        @BindView(R.id.itemcheckout_tv_totalitem) TextView totalitem;
        @BindView(R.id.itemcheckout_tv_totalprice) TextView totalprice;
        @BindView(R.id.itemcheckout_spinner_courierservice) Spinner courierservice;
        @BindView(R.id.itemcheckout_layout_note) TextInputLayout layout_note;
        @BindView(R.id.itemcheckout_note) TextInputEditText note;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
