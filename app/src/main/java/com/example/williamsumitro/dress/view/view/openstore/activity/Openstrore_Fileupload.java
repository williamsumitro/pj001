package com.example.williamsumitro.dress.view.view.openstore.activity;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
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

public class Openstrore_Fileupload extends AppCompatActivity {
    @BindView(R.id.openstore_fileupload_tdp) ImageView tdp;
    @BindView(R.id.openstore_fileupload_skdp) ImageView skdp;
    @BindView(R.id.openstore_fileupload_siup) ImageView siup;
    @BindView(R.id.openstore_fileupload_npwp) ImageView npwp;
    @BindView(R.id.openstore_fileupload_logo) CircleImageView logo;
    @BindView(R.id.openstore_fileupload_ktp) ImageView ktp;
    @BindView(R.id.openstore_fileupload_banner) ImageView banner;
    @BindView(R.id.openstore_fileupload_addtdp) ImageView add_tdp;
    @BindView(R.id.openstore_fileupload_addskdp) ImageView add_skdp;
    @BindView(R.id.openstore_fileupload_addsiup) ImageView add_siup;
    @BindView(R.id.openstore_fileupload_addnpwp) ImageView add_npwp;
    @BindView(R.id.openstore_fileupload_addktp) ImageView add_ktp;
    @BindView(R.id.openstore_fileupload_toolbar) Toolbar toolbar;

    private SessionManagement sessionManagement;
    private apiService service;
    private Context context;
    private Dialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private String token = "", store_name = "", store_contact_person_name = "", store_contact_person_job_title = "", store_contact_person_phone_number = "",
    store_established_year = "", store_province = "", store_city = "", store_description = "", store_courier = "", store_businesstype = "",
            mediaPathLogo = "", mediaPathBanner = "", mediaPathKTP = "", mediaPathSKDP = "", mediaPathSIUP = "", mediaPathNPWP = "", mediaPathTDP = "";

    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;
    private static final int SELECT_KTP = 3;
    private static final int SELECT_SKDP = 4;
    private static final int SELECT_SIUP = 5;
    private static final int SELECT_NPWP = 6;
    private static final int SELECT_TDP = 7;

    private final static String FILEUPLOAD = "FILEUPLOAD";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openstrore__fileupload);
        initView();
        setuptoolbar();
        initOnClick();
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
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_LOGO);
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initupload(SELECT_BANNER);
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
            if (requestCode == SELECT_LOGO && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathLogo = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathLogo))
                            .into(logo);
                    Picasso.with(context).cancelRequest(logo);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            if (requestCode == SELECT_BANNER && resultCode == RESULT_OK && null != data) {
                if(data == null){
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPathBanner = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathBanner))
                            .into(banner);
                    Picasso.with(context).cancelRequest(banner);
                    cursor.close();
                    dialog.dismiss();
                }
            }
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
                    mediaPathKTP = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathKTP))
                            .into(ktp);
                    Picasso.with(context).cancelRequest(ktp);
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
                    mediaPathNPWP = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathNPWP))
                            .into(npwp);
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
                    mediaPathTDP = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathTDP))
                            .into(tdp);
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
                    mediaPathSKDP = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathSKDP))
                            .into(skdp);
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
                    mediaPathSIUP = cursor.getString(columnIndex);
                    Picasso.with(context).load(new File(mediaPathSIUP))
                            .into(siup);
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
        HashMap<String, String> store = sessionManagement.getStoreInformation();
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        store_name = store.get(SessionManagement.STORE_NAME);
        store_contact_person_name = store.get(SessionManagement.STORE_CONTACT_PERSON_NAME);
        store_contact_person_job_title = store.get(SessionManagement.STORE_CONTACT_PERSON_JOB_TITLE);
        store_contact_person_phone_number = store.get(SessionManagement.STORE_CONTACT_PERSON_PHONE_NUMBER);
        store_established_year = store.get(SessionManagement.STORE_ESTABLISHED_YEAR);
        store_province = store.get(SessionManagement.STORE_ID_PROVINCE);
        store_city = store.get(SessionManagement.STORE_ID_CITY);
        store_description = store.get(SessionManagement.STORE_DESCRIPTION);
        store_courier = store.get(SessionManagement.STORE_COURIER);
        store_businesstype = store.get(SessionManagement.STORE_BUSINESS_TYPE);


        progressDialog = new ProgressDialog(this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save) {
            initDialog(1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void api_registerstore(){
        progressDialog.setMessage("Uploading, please wait ....");
        progressDialog.show();
        service = apiUtils.getAPIService();
        MultipartBody.Part body_photo = null;
        MultipartBody.Part body_banner = null;
        MultipartBody.Part body_KTP = null;
        MultipartBody.Part body_siup = null;
        MultipartBody.Part body_NPWP = null;
        MultipartBody.Part body_SKDP = null;
        MultipartBody.Part body_TDP = null;
        MediaType text = MediaType.parse("text/plain");
        if (!mediaPathLogo.equals("")){
            File filephoto = new File(mediaPathLogo);
            RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
            body_photo = MultipartBody.Part.createFormData("photo", filephoto.getName(), requestFileLogo);
        }

        if (!mediaPathBanner.equals("")){
            File filebanner = new File(mediaPathBanner);
            RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
            body_banner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
        }

        if (!mediaPathKTP.equals("")){
            File filektp = new File(mediaPathKTP);
            RequestBody requestFileKTP = RequestBody.create(MediaType.parse("multipart/form-data"), filektp);
            body_KTP = MultipartBody.Part.createFormData("ktp", filektp.getName(), requestFileKTP);
        }

        if (!mediaPathSIUP.equals("")){
            File filesiup = new File(mediaPathSIUP);
            RequestBody requestFileSIUP = RequestBody.create(MediaType.parse("multipart/form-data"), filesiup);
            body_siup = MultipartBody.Part.createFormData("siup", filesiup.getName(), requestFileSIUP);
        }

        if (!mediaPathNPWP.equals("")){
            File filenpwp = new File(mediaPathNPWP);
            RequestBody requestFileNPWP = RequestBody.create(MediaType.parse("multipart/form-data"), filenpwp);
            body_NPWP = MultipartBody.Part.createFormData("npwp", filenpwp.getName(), requestFileNPWP);
        }

        if (!mediaPathSKDP.equals("")){
            File fileSKDP = new File(mediaPathSKDP);
            RequestBody requestFileSKDP = RequestBody.create(MediaType.parse("multipart/form-data"), fileSKDP);
            body_SKDP = MultipartBody.Part.createFormData("skdp", fileSKDP.getName(), requestFileSKDP);
        }

        if (!mediaPathTDP.equals("")){
            File fileTDP = new File(mediaPathTDP);
            RequestBody requestFileTDP = RequestBody.create(MediaType.parse("multipart/form-data"), fileTDP);
            body_TDP = MultipartBody.Part.createFormData("tdp", fileTDP.getName(), requestFileTDP);
        }

        String result[] = store_courier.split(",");
        MultipartBody.Part[] couriers = new MultipartBody.Part[result.length];
        for(int i = 0; i<(result.length);i++){
            couriers[i] = MultipartBody.Part.createFormData("courier["+i+"]",result[i].toString());
        }

        RequestBody request_token = RequestBody.create(text, token);
        RequestBody request_store_name = RequestBody.create(text, store_name);
        RequestBody request_store_description = RequestBody.create(text, store_description);
        RequestBody request_store_established_year = RequestBody.create(text, store_established_year);
        RequestBody request_store_province = RequestBody.create(text, store_province);
        RequestBody request_store_city = RequestBody.create(text, store_city);
        RequestBody request_store_businesstype = RequestBody.create(text, store_businesstype);
        RequestBody request_store_contact_person_name = RequestBody.create(text, store_contact_person_name);
        RequestBody request_store_contact_person_job_title = RequestBody.create(text, store_contact_person_job_title);
        RequestBody request_store_contact_person_phone_number = RequestBody.create(text, store_contact_person_phone_number);

        service.req_register_store(request_token, request_store_name, body_photo, body_banner, request_store_description, request_store_established_year, request_store_province,
                request_store_city, request_store_businesstype, request_store_contact_person_name, request_store_contact_person_job_title, request_store_contact_person_phone_number,
                couriers, body_KTP, body_siup, body_NPWP, body_SKDP, body_TDP).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Log.i("debug", "onResponse: SUCCESS");
                    try{
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if(jsonResults.getString("message").equals("Store registered successfully ")){
                            String message = jsonResults.getString("message");
                            Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                            MainActivity.mainactivity.finish();
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra(FILEUPLOAD, "FILEUPLOAD");
                            initanim(intent);
                            finish();
                            sweetAlertDialog.dismiss();
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
                initDialog(3);
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
                            api_registerstore();
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
