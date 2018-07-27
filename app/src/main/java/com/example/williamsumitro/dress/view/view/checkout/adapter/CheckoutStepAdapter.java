package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.example.williamsumitro.dress.view.model.Checkout_Courier;
import com.example.williamsumitro.dress.view.view.checkout.fragment.Checkout_AddressFragment;
import com.example.williamsumitro.dress.view.view.checkout.fragment.Checkout_CourierFragment;
import com.example.williamsumitro.dress.view.view.checkout.fragment.Checkout_PaymentFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.HashMap;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CheckoutStepAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";
    private Checkout_AddressFragment checkout_addressFragment;
    private Checkout_CourierFragment checkout_courierFragment;
    private Checkout_PaymentFragment checkout_paymentFragment;
    public CheckoutStepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
        getPagerAdapter().notifyDataSetChanged();
    }

    @Override
    public Step createStep(int position) {
        switch (position){
            case 0:
                return new Checkout_AddressFragment();
            case 1:
                return new Checkout_CourierFragment();
            case 2:
                return new Checkout_PaymentFragment();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        if (position == 0) {
            builder.setTitle("Fill Address");
        }
        else if(position == 1){
            builder.setTitle("Courier Service");
        }
        else if(position == 2){
            builder.setTitle("Payment");
        }
        return builder.create();
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        // save the appropriate reference depending on position
        switch (position) {
            case 0:
                checkout_addressFragment = (Checkout_AddressFragment) createdFragment;
                String firstTag = createdFragment.getTag();
                break;
            case 1:
                checkout_courierFragment = (Checkout_CourierFragment) createdFragment;
                String secondTag = createdFragment.getTag();
                break;
            case 2:
                checkout_paymentFragment = (Checkout_PaymentFragment) createdFragment;
                String thirdTag = createdFragment.getTag();
                break;
        }
        return createdFragment;
    }
    public void someMethod() {
        // do work on the referenced Fragments, but first check if they
        // even exist yet, otherwise you'll get an NPE.

        if (checkout_addressFragment != null) {

        }

        if (checkout_courierFragment != null) {
            checkout_courierFragment.initRefresh();
        }
        if (checkout_paymentFragment != null) {
            // m2ndFragment.doSomeWorkToo();
        }
    }

}
