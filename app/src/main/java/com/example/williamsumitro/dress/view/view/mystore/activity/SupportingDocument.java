package com.example.williamsumitro.dress.view.view.mystore.activity;

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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.StoreDetailResponse;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportingDocument extends AppCompatActivity {

    @BindView(R.id.supportingdocument_tdp) ImageView tdp;
    @BindView(R.id.supportingdocument_skdp) ImageView skdp;
    @BindView(R.id.supportingdocument_siup) ImageView siup;
    @BindView(R.id.supportingdocument_npwp) ImageView npwp;
    @BindView(R.id.supportingdocument_ktp) ImageView ktp;
    @BindView(R.id.supportingdocument_addtdp) ImageView add_tdp;
    @BindView(R.id.supportingdocument_addskdp) ImageView add_skdp;
    @BindView(R.id.supportingdocument_addsiup) ImageView add_siup;
    @BindView(R.id.supportingdocument_addnpwp) ImageView add_npwp;
    @BindView(R.id.supportingdocument_addktp) ImageView add_ktp;
    @BindView(R.id.supportingdocument_toolbar) Toolbar toolbar;
    @BindView(R.id.supportingdocument_save) Button save;

    private SessionManagement sessionManagement;
    private apiService service;
    private Context context;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private String token = "", mediaPathktp = "", mediaPathskdp = "", mediaPathsiup = "", mediaPathnpwp = "", mediaPathtdp = "";
    private boolean click_KTP = false, click_SKDP = false, click_SIUP = false, click_NPWP = false, click_TDP = false;

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_KTP = 3;
    private static final int SELECT_SKDP = 4;
    private static final int SELECT_SIUP = 5;
    private static final int SELECT_NPWP = 6;
    private static final int SELECT_TDP = 7;

    private final static String FILEUPLOAD = "FILEUPLOAD";

    private ProgressDialog progressDialog;
    private final static String STORE_RESULT = "STORE_RESULT";
    private StoreDetails storeDetails;
    private String storeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supporting_document);
        initView();
        setuptoolbar();
        api_getuserstore();
    }
    private void api_getuserstore() {
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        service.req_get_user_store(token).enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.code()==200){
                    storeid = response.body().getStore().getStoreId().toString();
                    api_getstoredetail(storeid);
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }
    private void api_getstoredetail(String store_id){
        service.req_get_store_detail(store_id).enqueue(new Callback<StoreDetailResponse>() {
            @Override
            public void onResponse(Call<StoreDetailResponse> call, Response<StoreDetailResponse> response) {
                if (response.code()==200){
                    storeDetails = response.body().getResult();
                    initData();
                    initOnClick();
                    progressDialog.dismiss();
                }
                else {
                    Toasty.error(context, response.message(), Toast.LENGTH_SHORT,true).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoreDetailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        });
    }
    private void initData(){

        storeid = storeDetails.getStoreId().toString();
        Picasso.with(context)
                .load(storeDetails.getKtp())
                .placeholder(R.drawable.document)
                .into(ktp);
        Picasso.with(context)
                .load(storeDetails.getSiup())
                .placeholder(R.drawable.document)
                .into(siup);
        Picasso.with(context)
                .load(storeDetails.getNpwp())
                .placeholder(R.drawable.document)
                .into(npwp);
        Picasso.with(context)
                .load(storeDetails.getSkdp())
                .placeholder(R.drawable.document)
                .into(skdp);
        Picasso.with(context)
                .load(storeDetails.getTdp())
                .placeholder(R.drawable.document)
                .into(tdp);
    }

    private void initOnClick() {
        ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_KTP);
            }
        });
        add_ktp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_KTP);
            }
        });
        tdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_TDP);
            }
        });
        add_tdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_TDP);
            }
        });
        siup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_SIUP);
            }
        });
        add_siup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_SIUP);
            }
        });
        skdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_SKDP);
            }
        });
        add_skdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_SKDP);
            }
        });
        npwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_NPWP);
            }
        });
        add_npwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_NPWP);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(1);
            }
        });
    }
    public  boolean isStoragePermissionGranted() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

            if (requestCode == SELECT_KTP && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathktp = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathktp))
                            .into(ktp);
                    click_KTP = true;
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_NPWP && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathnpwp = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathnpwp))
                            .into(npwp);
                    click_NPWP = true;
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_TDP && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathtdp = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathtdp))
                            .into(tdp);
                    click_TDP = true;
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_SKDP && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathskdp = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathskdp))
                            .into(skdp);
                    click_SKDP = true;
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_SIUP && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathsiup = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathsiup))
                            .into(siup);
                    click_SIUP = true;
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toasty.error(context, "Please try again", Toast.LENGTH_LONG, true).show();
            }
        } catch (Exception e){}
    }
    private void initView(){
        ButterKnife.bind(this);
        context = this;
        sessionManagement = new SessionManagement(context);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        progressDialog = new ProgressDialog(this);
        service = apiUtils.getAPIService();
    }
    private void setuptoolbar(){
        setSupportActionBar(toolbar);
        final Drawable arrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        arrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("File Upload");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void api_updatelegaldoc(){
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        MultipartBody.Part body_ktp;
        MultipartBody.Part body_siup;
        MultipartBody.Part body_npwp;
        MultipartBody.Part body_skdp;
        MultipartBody.Part body_tdp;
        MediaType text = MediaType.parse("text/plain");
        RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");


        RequestBody request_token = RequestBody.create(text, token);
        RequestBody request_store_id = RequestBody.create(text, storeDetails.getStoreId().toString());
        RequestBody request_store_name = RequestBody.create(text, storeDetails.getName());
        if (click_KTP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_NPWP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_TDP){
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
        }
        else if (click_SKDP){
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
        }
        else if (click_SIUP){
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_TDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_SIUP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_SKDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_TDP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_SIUP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_SKDP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_TDP && click_SKDP){
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_TDP && click_SIUP){
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_SKDP && click_SIUP){
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_TDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_SIUP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_SKDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_TDP && click_SKDP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_TDP && click_SIUP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_NPWP && click_SKDP && click_SIUP){
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_TDP && click_SKDP && click_SIUP){
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_SKDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_TDP && click_SIUP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_TDP && click_SKDP){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
        }
        else if (click_KTP && click_NPWP && click_TDP && click_SKDP && click_SIUP ){
            File file_ktp = new File(mediaPathktp);
            RequestBody requestfile_ktp = RequestBody.create(MediaType.parse("multipart/form-data"), file_ktp);
            body_ktp = MultipartBody.Part.createFormData("ktp", file_ktp.getName(), requestfile_ktp);
            File file_npwp = new File(mediaPathnpwp);
            RequestBody requestfile_npwp = RequestBody.create(MediaType.parse("multipart/form-data"), file_npwp);
            body_npwp = MultipartBody.Part.createFormData("npwp", file_npwp.getName(), requestfile_npwp);
            File file_tdp = new File(mediaPathtdp);
            RequestBody requestfile_tdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_tdp);
            body_tdp = MultipartBody.Part.createFormData("tdp", file_tdp.getName(), requestfile_tdp);
            File file_skdp = new File(mediaPathskdp);
            RequestBody requestfile_skdp = RequestBody.create(MediaType.parse("multipart/form-data"), file_skdp);
            body_skdp = MultipartBody.Part.createFormData("skdp", file_skdp.getName(), requestfile_skdp);
            File file_siup = new File(mediaPathsiup);
            RequestBody requestfile_siup = RequestBody.create(MediaType.parse("multipart/form-data"), file_siup);
            body_siup = MultipartBody.Part.createFormData("siup", file_siup.getName(), requestfile_siup);
        }
        else {
            body_ktp = MultipartBody.Part.createFormData("ktp", "", attachmentEmpty);
            body_siup = MultipartBody.Part.createFormData("siup", "", attachmentEmpty);
            body_npwp = MultipartBody.Part.createFormData("npwp", "", attachmentEmpty);
            body_skdp = MultipartBody.Part.createFormData("skdp", "", attachmentEmpty);
            body_tdp = MultipartBody.Part.createFormData("tdp", "", attachmentEmpty);
            Toasty.error(context, "You need to change one of documents to save", Toast.LENGTH_SHORT, true).show();
            return;
        }
        service.req_update_store_legaldoc(request_token, request_store_id, request_store_name, body_ktp, body_siup, body_npwp, body_skdp, body_tdp)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            Toasty.success(context, "Updated Successfully", Toast.LENGTH_SHORT, true).show();
                            finish();
                            MyStore.MYSTORE.finish();
                            progressDialog.dismiss();
                        }
                        else {
                            Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        initDialog(3);
                        progressDialog.dismiss();
                    }
                });

    }
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
    private void initDialog(int stats){
        if (stats==1){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Save")
                    .setContentText("Are you sure want to save ?")
                    .setConfirmText("Yes")
                    .setCancelText("No")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            api_updatelegaldoc();
                        }
                    })
                        .show();
        }
        if (stats == 3){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Sorry")
                    .setContentText("There is a problem with internet connection or the server")
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
    }
}
