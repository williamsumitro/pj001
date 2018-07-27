package com.example.williamsumitro.dress.view.view.offer.activity;

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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.helper.FinancialTextWatcher;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.offer.fragment.RequestListFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMyOffer extends AppCompatActivity {
    @BindView(R.id.addmyoffer_toolbar) Toolbar toolbar;
    @BindView(R.id.addmyoffer_img) ImageView image;
    @BindView(R.id.addmyoffer_et_price) EditText price;
    @BindView(R.id.addmyoffer_et_description) EditText description;
    @BindView(R.id.addmyoffer_et_weight) EditText weight;
    @BindView(R.id.addmyoffer_btn_submit) Button submit;
    @BindView(R.id.addmyoffer_container) LinearLayout container;

    private static final int SELECT_PHOTO = 0;
    private Context context;
    private DecimalFormat formatter;
    private apiService service;
    private Drawable picture;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private String mediapathPhoto, token, rfqid;
    private Dialog dialog;

    private final static String RFQID = "RFQID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_offer);
        initView();
        setuptoolbar();
        initGetIntent();
        initEdittext();
        initClick();
    }
    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(RFQID)){
            rfqid = getintent.getStringExtra(RFQID);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
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
        getSupportActionBar().setTitle("Add My Offer");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initEdittext() {
        price.addTextChangedListener(new FinancialTextWatcher(price));
        weight.addTextChangedListener(new FinancialTextWatcher(weight));
    }

    private void initClick() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(description.getText())){
                    description.setError("Description is required");
                    return;
                }
                else if (TextUtils.isEmpty(price.getText())){
                    price.setError("Price per Unit is required");
                    return;
                }
                else if (TextUtils.isEmpty(weight.getText())){
                    weight.setError("Weight per Unit is required");
                    return;
                }
                else if(image.getDrawable().equals(picture)) {
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
                RequestBody request_rfqid = RequestBody.create(text, rfqid);
                RequestBody request_description = RequestBody.create(text, description.getText().toString());
                RequestBody request_price = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(price.getText().toString()));
                RequestBody request_weight = RequestBody.create(text, FinancialTextWatcher.trimCommaOfString(weight.getText().toString()));

                api_add_rfq(request_token, request_rfqid, request_description, request_price, request_weight, body_photo);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_PHOTO);
            }
        });
    }

    private void api_add_rfq(RequestBody request_token, RequestBody request_rfqid, RequestBody request_description, RequestBody request_price, RequestBody request_weight, MultipartBody.Part body_photo) {
        service = apiUtils.getAPIService();
        service.req_add_rfq_offer(request_token, request_rfqid, request_description, request_price, request_weight, body_photo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").toLowerCase().equals("submitted successfully")){
                            Toasty.success(context, "Success!", Toast.LENGTH_SHORT, true).show();
                            Toasty.info(context, "Please swipe down to refresh", Toast.LENGTH_SHORT, true).show();
                            finish();
                        }else{
                            String message = jsonResults.getString("message");
                            Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
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
                    Toasty.error(this, "Unable to pick image", Toast.LENGTH_SHORT, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapathPhoto = cursor.getString(columnIndex);
                    Picasso.with(context)
                            .load(new File(mediapathPhoto))
                            .into(image);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toasty.error(this, "Please Try Again", Toast.LENGTH_SHORT, true).show();
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
