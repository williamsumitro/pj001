package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;
import com.example.williamsumitro.dress.view.model.Bank;
import com.example.williamsumitro.dress.view.model.Shipping;
import com.example.williamsumitro.dress.view.view.checkout.activity.Checkout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class CheckoutRVAdapter extends RecyclerView.Adapter<CheckoutRVAdapter.ViewHolder> {
    private List<Bag> bagList;
    private Context context;
    private DecimalFormat formatter;
    private boolean open = false;

    public CheckoutRVAdapter(List<Bag> bagList, Context context){
        this.context = context;
        this.bagList = bagList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Bag bag = bagList.get(position);
        formatter = new DecimalFormat("#,###,###");
        holder.namatoko.setText(bag.getNamatoko());
        holder.namabarang.setText(bag.getNamaproduk());
        holder.gambarproduk.setImageResource(bag.getGambarproduk());
        holder.total.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getTotalharga()))));
        holder.price.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaproduk()))));
        holder.qtyXL.setText("(" + String.valueOf(bag.getQtyXL())+ ")");
        holder.qtyL.setText("(" + String.valueOf(bag.getQtyL())+ ")");
        holder.qtyM.setText("(" + String.valueOf(bag.getQtyM())+ ")");
        holder.qtyS.setText("(" + String.valueOf(bag.getQtyS())+ ")");
        holder.qtySubtotal.setText("(" + String.valueOf(bag.getQtySubtotal())+ ")");
        holder.priceXL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaXL()))));
        holder.priceL.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaL()))));
        holder.priceM.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaM()))));
        holder.priceS.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaS()))));
        holder.priceSubtotal.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getHargaSubtotal()))));
        holder.discount.setText("IDR " + formatter.format(Double.parseDouble(String.valueOf(bag.getDiscount()))));
        holder.persen.setText(String.valueOf(bag.getPersen())+ " %");
        holder.x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.informationdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!open){
                    holder.detail.setVisibility(View.VISIBLE);
                    open = true;
                    holder.caret.setImageResource(R.drawable.caret1);
                    holder.detail.setAlpha(0.0f);
                    holder.detail.animate().translationY(1).alpha(1.0f).setListener(null);
                }
                else{
                    holder.detail.setVisibility(View.GONE);
                    open = false;
                    holder.caret.setImageResource(R.drawable.caret);
                    holder.detail.animate()
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.detail.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
        List<Shipping> shippings = bag.getShippingList();
        final int size = shippings.size();
        final List<String> name = new ArrayList<String>();
        final List<String> price = new ArrayList<String>();
        for(int i = 0; i<size; i++){
            name.add(shippings.get(i).getName());
            price.add(String.valueOf(shippings.get(i).getPrice()));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, name);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        holder.spinnershipping.setAdapter(adapter);
        for(int i = 0; i<size; i++){
            if(holder.spinnershipping.getSelectedItem().equals(name.get(i))){
                holder.shippingfee.setText(price.get(i));
            }
        }
        holder.spinnershipping.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(int m = 0; m<size; m++){
                    if(holder.spinnershipping.getSelectedItem().equals(name.get(m))){
                        holder.shippingfee.setText(price.get(m));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        List<Bank> banks = bag.getBankList();
        final int ukuran = banks.size();
        final List<String> bankname = new ArrayList<String>();
        final List<String> bankaccount = new ArrayList<String>();
        final List<String> bankholder = new ArrayList<String>();
        for (int i = 0; i<ukuran;i++){
            bankname.add(banks.get(i).getBankname());
            bankaccount.add(banks.get(i).getAccountnumber());
            bankholder.add(banks.get(i).getAccountholder());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, bankname);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        holder.spinnerbank.setAdapter(adapter1);
        for(int i = 0; i<ukuran; i++){
            if(holder.spinnerbank.getSelectedItem().equals(bankname.get(i))){
                holder.accountnumber.setText(bankaccount.get(i));
                holder.holdername.setText(bankholder.get(i));
            }
        }
        holder.spinnerbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for(int m = 0; m<ukuran; m++){
                    if(holder.spinnerbank.getSelectedItem().equals(bankname.get(i))){
                        holder.accountnumber.setText(bankaccount.get(i));
                        holder.holdername.setText(bankholder.get(i));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return bagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemcheckout_tvx) TextView x;
        @BindView(R.id.itemcheckout_tvtotal) TextView total;
        @BindView(R.id.itemcheckout_tvprice) TextView price;
        @BindView(R.id.itemcheckout_tvnamatoko) TextView namatoko;
        @BindView(R.id.itemcheckout_tvnamabarang) TextView namabarang;
        @BindView(R.id.itemcheckout_lninformationdetail) LinearLayout informationdetail;
        @BindView(R.id.itemcheckout_qtyXL) TextView qtyXL;
        @BindView(R.id.itemcheckout_qtySubtotal) TextView qtySubtotal;
        @BindView(R.id.itemcheckout_qtyM) TextView qtyM;
        @BindView(R.id.itemcheckout_qtyL) TextView qtyL;
        @BindView(R.id.itemcheckout_qtyS) TextView qtyS;
        @BindView(R.id.itemcheckout_priceXL) TextView priceXL;
        @BindView(R.id.itemcheckout_priceSubtotal) TextView priceSubtotal;
        @BindView(R.id.itemcheckout_priceM) TextView priceM;
        @BindView(R.id.itemcheckout_priceL) TextView priceL;
        @BindView(R.id.itemcheckout_priceS) TextView priceS;
        @BindView(R.id.itemcheckout_percentage) TextView persen;
        @BindView(R.id.itemcheckout_lndetail) LinearLayout detail;
        @BindView(R.id.itemcheckout_imgbarang) ImageView gambarproduk;
        @BindView(R.id.itemcheckout_discount) TextView discount;
        @BindView(R.id.itemcheckout_imgcaret) ImageView caret;
        @BindView(R.id.itemcheckout_tvshippingfee) TextView shippingfee;
        @BindView(R.id.itemcheckout_etnote) EditText note;
        @BindView(R.id.itemcheckout_spinnershipping) Spinner spinnershipping;
        @BindView(R.id.itemcheckout_spinnerbank) Spinner spinnerbank;
        @BindView(R.id.itemcheckout_tvaccountnumber) TextView accountnumber;
        @BindView(R.id.itemcheckout_tvholdername) TextView holdername;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
