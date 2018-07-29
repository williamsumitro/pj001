package com.example.williamsumitro.dress.view.view.profile.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

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

public class ChangeProfilePicture extends AppCompatActivity {
    @BindView(R.id.changeprofilepicture_toolbar) Toolbar toolbar;
    @BindView(R.id.changeprofilepicture_civ_picture) CircleImageView pictures;
    @BindView(R.id.changeprofilepicture_btn_savechanges) Button savechanges;

    private Context context;
    private apiService service;
    private SessionManagement sessionManagement;
    private ProgressDialog progressDialog;
    private String mediapathPhoto;
    private AlertDialog dialog;
    private Boolean check = false;
    private String token;
    private SweetAlertDialog sweetAlertDialog;

    private static final int SELECT_PHOTO = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_picture);
        initView();
        api_getUserData();
        setupToolbar();
        pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initupload(SELECT_PHOTO);
            }
        });
        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api_update();
            }
        });
    }

    private void api_getUserData() {
        service.req_get_auth_user(token)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.code()==200){
                            if (response.body().getStatus()){
                                Picasso.with(context)
                                        .load(response.body().getUserDetails().getAvatar())
                                        .memoryPolicy(MemoryPolicy.NO_CACHE )
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .placeholder(R.drawable.logo404)
                                        .into(pictures);
                            }
                            else {
                                Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                            }
                        }
                        else {
                            Toasty.error(context, response.message(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("SEARCHERROR", t.toString());
                        progressDialog.dismiss();
                        initDialog(t.getMessage(), 3);
                    }
                });
    }

    private void api_update() {

        if (check){
            MediaType text = MediaType.parse("text/plain");
            RequestBody request_token = RequestBody.create(text, token);

            File filephoto = new File(mediapathPhoto);
            RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
            MultipartBody.Part bodyavatar = MultipartBody.Part.createFormData("avatar", filephoto.getName(), requestFileLogo);
            service.req_update_user_image(request_token, bodyavatar).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                            progressDialog.dismiss();
                            initDialog("Update Successful", 1);
//                        try{
//                            JSONObject jsonResults = new JSONObject(response.body().string());
//                            String message = jsonResults.getString("message");
//                            Toasty.info(context, "gg", Toast.LENGTH_SHORT,true).show();
//                            progressDialog.dismiss();
//                            initDialog(message, 1);
////                            if(jsonResults.getBoolean("status")){
////                                String message = jsonResults.getString("message");
////                                progressDialog.dismiss();
////                                initDialog(message, 1);
////                            }else{
////                                String message = jsonResults.getString("message");
////                                progressDialog.dismiss();
////                                initDialog(message, 0);
////                            }
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }catch (IOException e){
//                            e.printStackTrace();
//                        }
                    }
                    else {
                        progressDialog.dismiss();
                        initDialog(response.message(), 0);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                    progressDialog.dismiss();
                    initDialog(t.getMessage(), 3);
                }
            });
        }
        else {
            Toasty.error(context, "Please change your image first", Toast.LENGTH_SHORT, true).show();
        }
    }

    private void initView(){
        ButterKnife.bind(this);
        context = this;
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        progressDialog = new ProgressDialog(context);
        sessionManagement = new SessionManagement(getApplicationContext());
        HashMap<String, String> user = sessionManagement.getUserDetails();
        token = user.get(SessionManagement.TOKEN);
        service = apiUtils.getAPIService();
        PicassoTools.clearCache(Picasso.with(context));
    }
    private void setupToolbar(){
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Edit Profile Picture");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Toasty.error(context, "Unable to pick image", Toast.LENGTH_LONG, true).show();
                }
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if(cursor!=null){
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediapathPhoto = cursor.getString(columnIndex);
                    check = true;
                    Picasso.with(context).load(new File(mediapathPhoto))
                            .into(pictures);
                    cursor.close();
                    dialog.dismiss();
                }
            }
            else {
                Toasty.error(context, "Please Try Again", Toast.LENGTH_LONG, true).show();
            }
        } catch (Exception e){}
    }
    private void initDialog(String message, int stats){
        if(stats == 1) {
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Updated Success!")
                    .setContentText(message)
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(context, MainActivity.class);
                            initanim(intent);
                            finish();
                            Profile.PROFILE.finish();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if(stats == 0){
            sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.setTitleText("Invalid")
                    .setContentText(message)
                    .setConfirmText("Try Again")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
        }
        else if (stats == 3){
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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }
}
