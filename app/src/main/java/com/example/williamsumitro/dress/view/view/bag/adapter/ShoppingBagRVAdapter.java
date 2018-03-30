package com.example.williamsumitro.dress.view.view.bag.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.Bag;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WilliamSumitro on 30/03/2018.
 */

public class ShoppingBagRVAdapter extends RecyclerView.Adapter<ShoppingBagRVAdapter.ViewHolder> {
    private List<Bag> bagList;
    private Context context;
    private DecimalFormat formatter;
    private boolean open = false;
    public ShoppingBagRVAdapter(List<Bag> bagList, Context context){
        this.context = context;
        this.bagList = bagList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shoppingbag, parent, false);
        return new ViewHolder(itemView);
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
//                    Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slidedown);
                    holder.detail.setVisibility(View.VISIBLE);
                    open = true;
                    holder.caret.setImageResource(R.drawable.caret1);
                    holder.detail.setAlpha(0.0f);
                    holder.detail.animate().translationY(1).alpha(1.0f).setListener(null);
//                    holder.detail.setAnimation(slideDown);
                }
                else {
//                    Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slidedown);
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
//                    holder.detail.setAnimation(slideDown);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemshoppingbag_tvx) TextView x;
        @BindView(R.id.itemshoppingbag_tvtotal) TextView total;
        @BindView(R.id.itemshoppingbag_tvprice) TextView price;
        @BindView(R.id.itemshoppingbag_tvnamatoko) TextView namatoko;
        @BindView(R.id.itemshoppingbag_tvnamabarang) TextView namabarang;
        @BindView(R.id.itemshoppingbag_lninformationdetail) LinearLayout informationdetail;
        @BindView(R.id.itemshoppingbag_qtyXL) TextView qtyXL;
        @BindView(R.id.itemshoppingbag_qtySubtotal) TextView qtySubtotal;
        @BindView(R.id.itemshoppingbag_qtyM) TextView qtyM;
        @BindView(R.id.itemshoppingbag_qtyL) TextView qtyL;
        @BindView(R.id.itemshoppingbag_qtyS) TextView qtyS;
        @BindView(R.id.itemshoppingbag_priceXL) TextView priceXL;
        @BindView(R.id.itemshoppingbag_priceSubtotal) TextView priceSubtotal;
        @BindView(R.id.itemshoppingbag_priceM) TextView priceM;
        @BindView(R.id.itemshoppingbag_priceL) TextView priceL;
        @BindView(R.id.itemshoppingbag_priceS) TextView priceS;
        @BindView(R.id.itemshoppingbag_percentage) TextView persen;
        @BindView(R.id.itemshoppingbag_lndetail) LinearLayout detail;
        @BindView(R.id.itemshoppingbag_imgbarang) ImageView gambarproduk;
        @BindView(R.id.itemshoppingbag_discount) TextView discount;
        @BindView(R.id.itemshoppingbag_imgcaret) ImageView caret;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
