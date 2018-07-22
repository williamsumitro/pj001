package com.example.williamsumitro.dress.view;

import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.PaymentResponse;
import com.google.gson.Gson;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FullScreenImage extends AppCompatActivity {
    @BindView(R.id.fullscreenimage) ZoomageView image;
    private final static String IMAGE = "IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        ButterKnife.bind(this);

        Intent getintent = getIntent();
        if (getintent.hasExtra(IMAGE)){
            Picasso.with(this)
                    .load(getintent.getStringExtra(IMAGE))
                    .into(image);
        }
        else{
            Toasty.error(this, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }

    }
}
