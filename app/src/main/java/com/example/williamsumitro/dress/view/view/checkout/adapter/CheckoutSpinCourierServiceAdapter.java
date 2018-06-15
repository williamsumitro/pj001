package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.williamsumitro.dress.view.model.CourierService;
import com.example.williamsumitro.dress.view.model.CourierSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 14/06/2018.
 */

public class CheckoutSpinCourierServiceAdapter extends ArrayAdapter<CourierSpinner> {
    private Context context;
    private ArrayList<CourierSpinner> courierspinnerlist;
    private DecimalFormat formatter;


    public CheckoutSpinCourierServiceAdapter(@NonNull Context context, int textviewResourceId, ArrayList<CourierSpinner> courierspinnerlist) {
        super(context, textviewResourceId, courierspinnerlist);
        this.context = context;
        this.courierspinnerlist = courierspinnerlist;
        formatter = new DecimalFormat("#,###,###");
    }

    @Override
    public int getCount(){
        return courierspinnerlist.size();
    }

    @Override
    public CourierSpinner getItem(int position){
        return courierspinnerlist.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setTextSize(10);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (UserResponse class)
        label.setText(courierspinnerlist.get(position).getCourier_name() + " " +
                courierspinnerlist.get(position).getCourier_service() + " : IDR " +
                formatter.format(Double.parseDouble(String.valueOf(courierspinnerlist.get(position).getFee()))) + " (Estimated " +
                        courierspinnerlist.get(position).getEstd() + " day(s))");

        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setTextSize(10);
        label.setText(courierspinnerlist.get(position).getCourier_name() + " " +
                courierspinnerlist.get(position).getCourier_service() + " : IDR " +
                formatter.format(Double.parseDouble(String.valueOf(courierspinnerlist.get(position).getFee()))) + " (Estimated " +
                courierspinnerlist.get(position).getEstd() + " day(s))");
        return label;
    }
}
