package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.williamsumitro.dress.view.model.dress_attribute.Patterntype;

import java.util.List;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Patterntype_Spinner_Adapter extends ArrayAdapter<Patterntype>{
    private Context context;
    private List<Patterntype> patterntypeList;
    public Patterntype_Spinner_Adapter(Context context, int textviewResourceId, List<Patterntype> patterntypeList) {
        super(context, textviewResourceId, patterntypeList);
        this.context = context;
        this.patterntypeList = patterntypeList;
    }
    @Override
    public int getCount(){
        return patterntypeList.size();
    }

    @Override
    public Patterntype getItem(int position){
        return patterntypeList.get(position);
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
        label.setText(patterntypeList.get(position).getPatterntypeName());

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
        label.setText(patterntypeList.get(position).getPatterntypeName());
        return label;
    }
}
