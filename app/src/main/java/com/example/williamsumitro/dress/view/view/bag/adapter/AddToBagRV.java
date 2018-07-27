package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Product_Size_Qty;
import com.example.williamsumitro.dress.view.model.dress_attribute.Size;
import com.example.williamsumitro.dress.view.view.bag.activity.AddToBagActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by WilliamSumitro on 27/07/2018.
 */

public class AddToBagRV extends RecyclerView.Adapter<AddToBagRV.ViewHolder> {

    private Context context;
    private ArrayList<Size> sizes;
    int qty;
    private ArrayList<Size> data;
    private Product_Size_Qty data_size;
    private AddToBagActivity addToBagActivity;

    public AddToBagRV(Context context, ArrayList<Size> sizes, AddToBagActivity addToBagActivity){
        this.context = context;
        this.sizes = sizes;
        this.addToBagActivity = addToBagActivity;
        data = new ArrayList<>();
        qty = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sizes, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Size size = sizes.get(position);
        holder.size.setText(size.getSizeName());
        holder.quantityView.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Change Quantity");
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_qty, null, false);
                final EditText et = (EditText) inflate.findViewById(R.id.dialog_qty_et_qty);

                et.setText(String.valueOf(holder.quantityView.getQuantity()));

                builder.setView(inflate);
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = et.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        qty = Integer.parseInt(newQuantity);
                        if (qty>100){
                            Toasty.error(context, "Maximum quantity you can order only 100 / size", Toast.LENGTH_LONG, true).show();
                        }
                        holder.quantityView.setQuantity(qty);
                        setData(size.getSizeId(), size.getSizeName(), qty);
                    }
                }).setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        holder.quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                qty = holder.quantityView.getQuantity();
                setData(size.getSizeId(), size.getSizeName(), qty);
            }

            @Override
            public void onLimitReached() {

            }
        });
    }
    private void setData(Integer sizeid, String sizename, int qty){
        this.data_size = new Product_Size_Qty(sizeid, qty);
        addToBagActivity.RetriveData();

    }
    @Override
    public int getItemCount() {
        return sizes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemsize_qv) QuantityView quantityView;
        @BindView(R.id.itemsize_tv) TextView size;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public Product_Size_Qty retreiveData(){
        return this.data_size;
    }
}
