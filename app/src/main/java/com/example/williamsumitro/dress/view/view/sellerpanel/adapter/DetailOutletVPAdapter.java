package com.example.williamsumitro.dress.view.view.sellerpanel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.williamsumitro.dress.view.view.sellerpanel.fragment.InformasioutletFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.fragment.ProdukoutletFragment;
import com.example.williamsumitro.dress.view.view.sellerpanel.fragment.UlasanoutletFragment;

/**
 * Created by WilliamSumitro on 14/03/2018.
 */

public class DetailOutletVPAdapter extends FragmentPagerAdapter {
    public static int int_items = 3;


    public DetailOutletVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                final Fragment f = new InformasioutletFragment();
//                f.setArguments(this.fragmentbundle);
                return f;
            case 1:
                final Fragment g = new ProdukoutletFragment();
//                g.setArguments(this.fragmentbundle);
                return g;
            case 2:
                final Fragment i = new UlasanoutletFragment();
//                i.setArguments(this.fragmentbundle);
                return i;
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
                return "Informasi";
            case 1:
                return "Produk";
            case 2:
                return "Ulasan";
        }
        return null;
    }
}
