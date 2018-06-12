package com.example.williamsumitro.dress.view.view.sellerpanel.store.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment.Openstore_choosestorenameFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment.Openstore_courierserviceFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.store.fragment.Openstore_storeinformationFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * Created by WilliamSumitro on 01/05/2018.
 */

public class StepAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION = "CURRENT_STEP_POSITION";

    public StepAdapter(@NonNull FragmentManager fm, @NonNull Context context) {
        super(fm, context);
        getPagerAdapter().notifyDataSetChanged();
    }

    @Override
    public Step createStep(int position) {
        switch (position){
            case 0:
                return new Openstore_choosestorenameFragment();
            case 1:
                return new Openstore_storeinformationFragment();
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
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        if (position == 0) {
            builder.setTitle("Choose Store Name");
        }
        else if(position == 1){
            builder.setTitle("Store Information");
        }
        return builder.create();
    }

}
