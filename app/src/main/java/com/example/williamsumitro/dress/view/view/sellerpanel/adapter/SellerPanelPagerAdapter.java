package com.example.williamsumitro.dress.view.view.sellerpanel.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.view.dashboard.DashboardActivity;
import com.example.williamsumitro.dress.view.view.offer.activity.OfferActivity;
import com.example.williamsumitro.dress.view.view.partnership.activity.Partnership;
import com.example.williamsumitro.dress.view.view.product.activity.MyProduct;
import com.example.williamsumitro.dress.view.view.sales.activity.SalesActivity;
import com.example.williamsumitro.dress.view.view.sellerpanel.Utils;
import com.example.williamsumitro.dress.view.view.mystore.activity.MyStore;

import static com.example.williamsumitro.dress.view.view.sellerpanel.Utils.setupItem;

/**
 * Created by WilliamSumitro on 21/07/2018.
 */

public class SellerPanelPagerAdapter extends PagerAdapter {
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.dashboard,
                    "Dashboard"
            ),
            new Utils.LibraryObject(
                    R.drawable.store6,
                    "Store Settings"
            ),
            new Utils.LibraryObject(
                    R.drawable.product,
                    "Product Settings"
            ),
            new Utils.LibraryObject(
                    R.drawable.sales,
                    "Sales"
            ),
            new Utils.LibraryObject(
                    R.drawable.bid,
                    "Request for Quotation"
            ),
            new Utils.LibraryObject(
                    R.drawable.partnership,
                    "Partnership"
            )
    };
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public SellerPanelPagerAdapter(final Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
            view = mLayoutInflater.inflate(R.layout.item_sellerpanel, container, false);
            setupItem(view, LIBRARIES[position]);
        container.addView(view);
        if (LIBRARIES[position].getTitle().equals("Dashboard")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DashboardActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        else if (LIBRARIES[position].getTitle().equals("Store Settings")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyStore.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        else if (LIBRARIES[position].getTitle().equals("Product Settings")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyProduct.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        else if (LIBRARIES[position].getTitle().equals("Sales")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SalesActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        else if (LIBRARIES[position].getTitle().equals("Request for Quotation")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OfferActivity.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        else if (LIBRARIES[position].getTitle().equals("Partnership")){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Partnership.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext,R.anim.slideright, R.anim.fadeout).toBundle();
                    mContext.startActivity(intent, bundle);
                }
            });
        }
        return view;
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
