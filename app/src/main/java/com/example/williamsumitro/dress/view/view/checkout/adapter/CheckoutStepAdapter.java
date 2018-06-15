package com.example.williamsumitro.dress.view.view.checkout.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.williamsumitro.dress.view.view.checkout.fragment.Checkout_AddressFragment;
import com.example.williamsumitro.dress.view.view.checkout.fragment.Checkout_CourierFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by WilliamSumitro on 13/06/2018.
 */

public class CheckoutStepAdapter extends AbstractFragmentStepAdapter {
    private static final String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";

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
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
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
        return builder.create();
    }
}
