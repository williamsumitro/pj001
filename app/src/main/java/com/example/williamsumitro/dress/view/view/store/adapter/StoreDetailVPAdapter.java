package com.example.williamsumitro.dress.view.view.store.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.williamsumitro.dress.view.view.store.fragment.InformasioutletFragment;
import com.example.williamsumitro.dress.view.view.store.fragment.ProdukoutletFragment;

/**
 * Created by WilliamSumitro on 14/03/2018.
 */

public class StoreDetailVPAdapter extends FragmentPagerAdapter {
    public static int int_items = 2;
    private final Bundle fragmentbundle;


    public StoreDetailVPAdapter(FragmentManager fm, Bundle fragmentbundle) {
        super(fm);
        this.fragmentbundle = fragmentbundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                final Fragment g = new ProdukoutletFragment();
                g.setArguments(this.fragmentbundle);
                return g;
            case 1:
                final Fragment f = new InformasioutletFragment();
                f.setArguments(this.fragmentbundle);
                return f;
        }
        return null;
    }

    @Override
    public int getCount() {
        return int_items;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Product";
            case 1:
                return "Company Profile";
        }
        return null;
    }
}
