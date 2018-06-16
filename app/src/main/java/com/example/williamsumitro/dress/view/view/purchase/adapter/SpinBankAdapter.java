package com.example.williamsumitro.dress.view.view.purchase.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.williamsumitro.dress.view.model.Bank;

import java.util.ArrayList;

/**
 * Created by WilliamSumitro on 16/06/2018.
 */

public class SpinBankAdapter extends ArrayAdapter<Bank> {
    private Context context;
    private ArrayList<Bank> bankArrayList;

    public SpinBankAdapter(Context context, int textviewResourceId, ArrayList<Bank> bankArrayList){
        super(context, textviewResourceId, bankArrayList);
        this.context = context;
        this.bankArrayList = bankArrayList;
    }
    @Override
    public int getCount(){
        return bankArrayList.size();
    }

    @Override
    public Bank getItem(int position){
        return bankArrayList.get(position);
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
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (UserResponse class)
        label.setText(bankArrayList.get(position).getBankName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(bankArrayList.get(position).getBankName());
        return label;
    }
}
