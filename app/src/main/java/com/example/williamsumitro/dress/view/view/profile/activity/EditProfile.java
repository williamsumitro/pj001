package com.example.williamsumitro.dress.view.view.profile.activity;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.williamsumitro.dress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfile extends AppCompatActivity {
    @BindView(R.id.editprofile_toolbar) Toolbar toolbar;
    @BindView(R.id.editprofile_rbmale) RadioButton male;
    @BindView(R.id.editprofile_rbfemale) RadioButton female;
    @BindView(R.id.editprofile_radiogroup) RadioGroup radioGroup;
    @BindView(R.id.editprofile_layoutphonenumber) TextInputLayout layoutphonenumber;
    @BindView(R.id.editprofile_layoutfullname) TextInputLayout layoutfullname;
    @BindView(R.id.editprofile_etphonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editprofile_etfullname) TextInputEditText fullname;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        setupToolbar();
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_save, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
