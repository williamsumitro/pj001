package com.example.williamsumitro.dress.view.view.request.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.sellerpanel.activity.SellerPanel;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewRequest extends AppCompatActivity {
    @BindView(R.id.addnewrequest_toolbar) Toolbar toolbar;
    @BindView(R.id.addnewrequest_img) ImageView image;
    @BindView(R.id.addnewrequest_et_qty) EditText qty;
    @BindView(R.id.addnewrequest_et_min) EditText min;
    @BindView(R.id.addnewrequest_et_max) EditText max;
    @BindView(R.id.addnewrequest_et_itemname) EditText itemname;
    @BindView(R.id.addnewrequest_et_description) EditText description;
    @BindView(R.id.addnewrequest_ln_dateexpired) LinearLayout container_datexpired;
    @BindView(R.id.addnewrequest_tv_dateexpired) TextView datexpired;
    @BindView(R.id.addnewrequest_btn_save) Button save;
    @BindView(R.id.addnewrequest_container) LinearLayout container;

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final int SELECT_PHOTO = 0;
    private Context context;
    private DecimalFormat formatter;
    private apiService service;
    private Drawable picture;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private String mediapathPhoto, token;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_request);
        initView();
        setuptoolbar();
        initEdittext();
        initClick();
    }

    private void initEdittext() {
        min.addTextChangedListener(new FinancialTextWatcher(min));
        max.addTextChangedListener(new FinancialTextWatcher(max));
        qty.addTextChangedListener(new FinancialTextWatcher(qty));
    }

    private void initClick() {
        container_datexpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTime();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(itemname.getText())){
                    itemname.setError("Item's name is required");
                    return;
                }
                else if (TextUtils.isEmpty(description.getText())){
                    description.setError("Description is required");
                    return;
                }
                else if (TextUtils.isEmpty(qty.getText())){
                    qty.setError("Quantity is required");
                    return;
                }
                else if (datexpired.getText().equals("Click to choose your expired date")){
                    Snackbar.make(container, "Choose the expired date", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(min.getText())){
                    min.setError("Budget min is required");
                    return;
                }
                else if (TextUtils.isEmpty(max.getText())){
                    max.setError("Budget max is required");
                    return;
                }else if(image.getDrawable().equals(picture)) {
                    Snackbar.make(container, "Please select your image", Snackbar.LENGTH_LONG).show();
                    return;
                }
                progressDialog.setMessage("Uploading, please wait ....");
                progressDialog.show();

                MediaType text = MediaType.parse("text/plain");

                File filephoto = new File(mediapathPhoto);
                RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
                MultipartBody.Part body_photo = MultipartBody.Part.createFormData("photo", filephoto.getName(), requestFileLogo);

                RequestBody request_token = RequestBody.create(text, token);
                RequestBody request_itemname = RequestBody.create(text, itemname.getText().toString());
                RequestBody request_description = RequestBody.create(text, description.getText().toString());
                RequestBody request_qty = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(qty.getText().toString()));
                RequestBody request_datexpired = RequestBody.create(text, datexpired.getText().toString());
                RequestBody request_min = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(min.getText().toString()));
                RequestBody request_max = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(max.getText().toString()));

                api_add_rfq(body_photo, request_token, request_itemname, request_description, request_qty, request_datexpired, request_min, request_max);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_PHOTO);
            }
        });
    }

    private void api_add_rfq(MultipartBody.Part body_photo, RequestBody request_token, RequestBody request_itemname, RequestBody request_description, RequestBody request_qty, RequestBody request_datexpired, RequestBody request_min, RequestBody request_max) {
        service = apiUtils.getAPIService();
        service.req_add_rfq_request(request_token, request_itemname, request_description, request_qty, request_datexpired, request_min, request_max, body_photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").toLowerCase().equals("submitted successfully")){
                            Intent intent = new Intent(context, RequestForQuotation.class);
                            initanim(intent);
                            RequestForQuotation.REQUESTFORQUOTATION.finish();
                            finish();
                        }else{
                            String message = jsonResults.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i("debug", "onResponse: FAILED");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void DateTime(){
        dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
        if(dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }
        dateTimeFragment.setTimeZone(TimeZone.getDefault());

        final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        dateTimeFragment.set24HoursMode(true);
        dateTimeFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2050, Calendar.DECEMBER, 31).getTime());

        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("TAG", e.getMessage());
        }

        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                datexpired.setText(myDateFormat.format(date));
            }

            @Override
            public void onNegativeButtonClick(Date date) {
            }

            @Override
            public void onNeutralButtonClick(Date date) {
            }
        });
        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2020, Calendar.MARCH, 4, 15, 20).getTime());
        dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        picture = getResources().getDrawable(R.drawable.picture);
        image.setImageDrawable(picture);
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(this);
        formatter = new DecimalFormat("#,###,###");
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add New Request");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                return false;
            }
        }
        else {
            return true;
        }
    }
    private void initupload(int RequestCode){
        if(isStoragePermissionGranted()){
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, RequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            galleryIntent.setType("image/*");
            final Intent chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.string_choose_image));
            startActivityForResult(chooserIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toast.makeText(this, "Unable to pick image", Toast.LENGTH_LONG).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapathPhoto = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediapathPhoto))
                            .into(image);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toast.makeText(this, "Please Try Again", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }

    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
