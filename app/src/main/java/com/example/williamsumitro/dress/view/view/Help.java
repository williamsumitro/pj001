package com.example.williamsumitro.dress.view.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Help extends AppCompatActivity {
    @BindView(R.id.help_toolbar) Toolbar toolbar;
    @BindView(R.id.help_button) Button help;
    @BindView(R.id.help_linear) LinearLayout linearLayout;
    private Context context;
    private int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initView();
        setuptoolbar();
        help.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                LinearLayout mainLinearLayout = new LinearLayout(context);
                mainLinearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mainLinearLayout.setLayoutParams(mainParams);
                mainLinearLayout.setGravity(Gravity.CENTER);

                LinearLayout firstChildLinearLayout = new LinearLayout(context);
                firstChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams firstChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                firstChildLinearLayout.setLayoutParams(firstChildParams);

                LinearLayout.LayoutParams etParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                etParams.weight = 1;

                EditText qtymin = new EditText(context);
                qtymin.setHint("Qty (Min)");
                qtymin.setLayoutParams(etParams);


                TextView textView = new TextView(context);
                LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txtParams.setMarginStart(5);
                txtParams.setMarginEnd(10);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setLayoutParams(txtParams);

                textView.setText("between");

                EditText qtymax = new EditText(context);
                qtymax.setHint("Qty (Max)");
                qtymax.setLayoutParams(etParams);

                EditText price = new EditText(context);
                price.setHint("Price");
                price.setLayoutParams(etParams);

                firstChildLinearLayout.addView(qtymin, 0);
                firstChildLinearLayout.addView(textView, 1);
                firstChildLinearLayout.addView(qtymax, 2);
                firstChildLinearLayout.addView(price, 3);

                LinearLayout secondChildLinearLayout = new LinearLayout(context);
                secondChildLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                secondChildLinearLayout.setBackgroundColor(getResources().getColor(R.color.grey));

                LinearLayout.LayoutParams secondChildParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                secondChildLinearLayout.setLayoutParams(secondChildParams);


                mainLinearLayout.addView(firstChildLinearLayout, 0);
                mainLinearLayout.addView(secondChildLinearLayout, 1);

                linearLayout.addView(mainLinearLayout, index);

                index++;
            }
        });
        getAllEditTextValues();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Help");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    public void getAllEditTextValues() {
        View v = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            v = linearLayout.getChildAt(i);
            if (v instanceof LinearLayout) {
                View tempView = ((LinearLayout) v).getChildAt(0);
                View et = ((LinearLayout) tempView).getChildAt(1);
                String etValue = null;
                if (et instanceof EditText) {
                    etValue = ((EditText) et).getText().toString();
                }
                Toast.makeText(this, "" + etValue, Toast.LENGTH_SHORT).show();
                // Use Array to Store all values of EditText
            }
        }
    }
}
