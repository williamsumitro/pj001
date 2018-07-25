package com.example.williamsumitro.dress.view.view.mystore.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.williamsumitro.dress.view.view.store.fragment.InformasioutletFragment;
import com.example.williamsumitro.dress.view.view.store.fragment.ProdukoutletFragment;

/**
 * Created by William Sumitro on 7/25/2018.
 */

public class MyStoreVP extends FragmentPagerAdapter{
    public static int int_items = 1;
    private final Bundle fragmentbundle;

    public MyStoreVP(FragmentManager fm, Bundle fragmentbundle) {
        super(fm);
        this.fragmentbundle = fragmentbundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
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
                return "Company Profile";
        }
        return null;
    }
}
