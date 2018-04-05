package com.example.williamsumitro.dress.view.view.store.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformasioutletFragment extends Fragment {
    @BindView(R.id.informasioutlet_tvWA) TextView WA;
    @BindView(R.id.informasioutlet_tvTerakhirOnline) TextView terakhironline;
    @BindView(R.id.informasioutlet_tvNamaPemilik) TextView namapemilik;
    @BindView(R.id.informasioutlet_tvLokasi) TextView lokasi;
    @BindView(R.id.informasioutlet_tvLine) TextView line;
    @BindView(R.id.informasioutlet_tvJmlhTerjual) TextView jumlahterjual;
    @BindView(R.id.informasioutlet_tvJmlhProduk) TextView jumlahproduk;
    @BindView(R.id.informasioutlet_tvIG) TextView IG;
    @BindView(R.id.informasioutlet_tvFB) TextView FB;
    @BindView(R.id.informasioutlet_tvBukasejak) TextView bukasejak;
    @BindView(R.id.informasioutlet_lnContainer) LinearLayout container;

    public InformasioutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informasioutlet, container, false);
        initObject(view);
        return view;
    }
    private void initObject(View view){

        ButterKnife.bind(this, view);

    }

}
