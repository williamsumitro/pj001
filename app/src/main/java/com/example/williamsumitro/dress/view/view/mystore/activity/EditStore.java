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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.williamsumitro.dress.R;
import com.example.williamsumitro.dress.view.model.CityDetails;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierDetails;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.ProvinceDetails;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.Purchase_TransactionHistoryResult;
import com.example.williamsumitro.dress.view.model.StoreDetails;
import com.example.williamsumitro.dress.view.presenter.api.apiService;
import com.example.williamsumitro.dress.view.presenter.api.apiUtils;
import com.example.williamsumitro.dress.view.presenter.session.SessionManagement;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.example.williamsumitro.dress.view.view.openstore.activity.OpenStore;
import com.example.williamsumitro.dress.view.view.openstore.adapter.OpenStore_CourierAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinCityAdapter;
import com.example.williamsumitro.dress.view.view.sellerpanel.SpinProvinceAdapter;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

public class EditStore extends AppCompatActivity {
    @BindView(R.id.editstore_toolbar) Toolbar toolbar;
    @BindView(R.id.editstore_spinner_province) Spinner province;
    @BindView(R.id.editstore_spinner_city) Spinner city;
    @BindView(R.id.editstore_phonenumber) TextInputEditText phonenumber;
    @BindView(R.id.editstore_name) TextInputEditText name;
    @BindView(R.id.editstore_layout_phonenumber) TextInputLayout layout_phonenumber;
    @BindView(R.id.editstore_layout_name) TextInputLayout layout_name;
    @BindView(R.id.editstore_layout_jobtitle) TextInputLayout layout_jobtitle;
    @BindView(R.id.editstore_layout_detail) TextInputLayout layout_detail;
    @BindView(R.id.editstore_jobtitle) TextInputEditText jobtitle;
    @BindView(R.id.editstore_detail) TextInputEditText detail;
    @BindView(R.id.editstore_btn_check) Button check;
    @BindView(R.id.editstore_spinner_businesstype) Spinner spinner_businesstype;
    @BindView(R.id.editstore_ln_year) LinearLayout container_year;
    @BindView(R.id.editstore_tv_year) TextView tv_year;
    @BindView(R.id.editstore_container) LinearLayout container;
    @BindView(R.id.editstore_logo) CircleImageView logo;
    @BindView(R.id.editstore_banner) ImageView banner;
    @BindView(R.id.editstore_tv_storename) TextView storename;


    private apiService service;
    private SweetAlertDialog sweetAlertDialog;
    private Context context;
    private ProgressDialog progressDialog;
    private List<ProvinceDetails> provinceDetailsList;
    private List<CityDetails> cityDetailsList;
    private SpinProvinceAdapter spinProvinceAdapter;
    private SpinCityAdapter spinCityAdapter;
    private SessionManagement sessionManagement;
    private String token, idprovince, idcity, choosen_province, choosen_city, chosen_businesstype, year="", mediaPathLogo = "", mediaPathBanner = "";
    private Boolean checked = false, click_logo = false, click_banner = false;
    private List<String> bisnis = new ArrayList<>();
    private List<CourierDetails> courierDetailsList;
    private OpenStore_CourierAdapter adapter;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    private static final String[] PERMISSIONS_READ_STORAGE = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int SELECT_LOGO = 1;
    private static final int SELECT_BANNER = 2;
    private Dialog dialog;
    private StoreDetails storeDetails;

    private final static String RESULT = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);
        initView();
        setuptoolbar();
        initGetIntent();
        initProvinceSpinner();
        initBisnisSpinner();
        initData();
        initOnClick();
    }

    private void initData() {
        Picasso.with(context)
                .load(storeDetails.getPhoto())
                .placeholder(R.drawable.logo404)
                .into(logo);
        Picasso.with(context)
                .load(storeDetails.getBanner())
                .placeholder(R.drawable.logo404)
                .into(banner);
        storename.setText(storeDetails.getStoreName());
        if (storeDetails.getBusinessType().equals("Manufacture / Factory")){
            spinner_businesstype.setSelection(0);
        }
        else if (storeDetails.getBusinessType().equals("Trading Company")){
            spinner_businesstype.setSelection(1);
        }
        else if (storeDetails.getBusinessType().equals("Distributor / Wholesaler")){
            spinner_businesstype.setSelection(2);
        }
        else if (storeDetails.getBusinessType().equals("Retailer")){
            spinner_businesstype.setSelection(3);
        }
        else if (storeDetails.getBusinessType().equals("Buying Office")){
            spinner_businesstype.setSelection(4);
        }
        else if (storeDetails.getBusinessType().equals("Online Shop / Store")){
            spinner_businesstype.setSelection(5);
        }
        else if (storeDetails.getBusinessType().equals("Individual")){
            spinner_businesstype.setSelection(6);
        }
        else if (storeDetails.getBusinessType().equals("Other")){
            spinner_businesstype.setSelection(7);
        }
        name.setText(storeDetails.getContactPersonName());
        jobtitle.setText(storeDetails.getContactPersonJobTitle());
        phonenumber.setText(storeDetails.getContactPersonPhoneNumber());
        detail.setText(storeDetails.getDescription());
        year = storeDetails.getEstablishedYear();
        tv_year.setText("Year Established : " + year);
        chosen_businesstype = storeDetails.getBusinessType();
    }

    private void initGetIntent() {
        Intent getintent = getIntent();
        if (getintent.hasExtra(RESULT)){
            storeDetails = (StoreDetails) getintent.getSerializableExtra(RESULT);
        }
        else{
            Toasty.error(context, "SOMETHING WRONG", Toast.LENGTH_SHORT, true).show();
        }
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
                    click_logo = true;
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
                    click_banner = true;
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
        getSupportActionBar().setTitle("Edit Store");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.slideleft, R.anim.fadeout);
                finish();
            }
        });
    }
    private void initBisnisSpinner() {
        bisnis.add("Manufacture / Factory");
        bisnis.add("Trading Company");
        bisnis.add("Distributor / Wholesaler");
        bisnis.add("Retailer");
        bisnis.add("Buying Office");
        bisnis.add("Online Shop / Store");
        bisnis.add("Individual");
        bisnis.add("Other");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bisnis);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_businesstype.setAdapter(dataAdapter);
        spinner_businesstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chosen_businesstype = dataAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initOnClick(){
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
        container_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeFragment = (SwitchDateTimeDialogFragment) getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);
                if(dateTimeFragment == null) {
                    dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                            getString(R.string.label_datetime_dialog),
                            getString(android.R.string.ok),
                            getString(android.R.string.cancel)
                    );
                }
                dateTimeFragment.setTimeZone(TimeZone.getDefault());

                final SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy", java.util.Locale.getDefault());
                dateTimeFragment.setMinimumDateTime(new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime());
                dateTimeFragment.setMaximumDateTime(new GregorianCalendar(2018, Calendar.DECEMBER, 31).getTime());

                try {
                    dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("MMMM dd", Locale.getDefault()));
                } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
                    Log.e("TAG", e.getMessage());
                }

                dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
                    @Override
                    public void onPositiveButtonClick(Date date) {
                        year = myDateFormat.format(date);
                        tv_year.setText("Year Established : " + year);
                    }

                    @Override
                    public void onNegativeButtonClick(Date date) {
                    }

                    @Override
                    public void onNeutralButtonClick(Date date) {
                    }
                });
                dateTimeFragment.startAtYearView();
                if (year.equals("")){
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(2000, Calendar.MARCH, 4, 15, 20).getTime());
                }else {
                    dateTimeFragment.setDefaultDateTime(new GregorianCalendar(Integer.parseInt(year), Calendar.MARCH, 4, 15, 20).getTime());
                }
                dateTimeFragment.show(getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        jobtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checked = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                layout_detail.setErrorEnabled(false);
                layout_jobtitle.setErrorEnabled(false);
                layout_name.setErrorEnabled(false);
                layout_phonenumber.setErrorEnabled(false);
                if (TextUtils.isEmpty(name.getText().toString())){
                    layout_name.setErrorEnabled(true);
                    layout_name.setError("Name of contact person is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(jobtitle.getText().toString())){
                    layout_jobtitle.setErrorEnabled(true);
                    layout_jobtitle.setError("Job title is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(phonenumber.getText().toString())){
                    layout_phonenumber.setErrorEnabled(true);
                    layout_phonenumber.setError("Phone number is required");
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(year)){
                    Snackbar.make(container, "Please choose your year established", Snackbar.LENGTH_SHORT).show();
                    checked = false;
                    return;
                }if (TextUtils.isEmpty(detail.getText().toString())){
                    layout_detail.setErrorEnabled(true);
                    layout_detail.setError("Description is required");
                    checked = false;
                    return;
                }
                if (checked) {
                    MediaType text = MediaType.parse("text/plain");
                    progressDialog.setMessage("Uploading, please wait ....");
                    progressDialog.show();
                    RequestBody request_token = RequestBody.create(text, token);
                    RequestBody request_store_id = RequestBody.create(text, storeDetails.getStoreId().toString());
                    RequestBody request_store_businesstype = RequestBody.create(text, chosen_businesstype);
                    RequestBody request_store_established_year = RequestBody.create(text, year);
                    RequestBody request_store_province = RequestBody.create(text, choosen_province);
                    RequestBody request_store_city = RequestBody.create(text, choosen_city);
                    RequestBody request_store_contact_person_name = RequestBody.create(text, name.getText().toString());
                    RequestBody request_store_contact_person_job_title = RequestBody.create(text, jobtitle.getText().toString());
                    RequestBody request_store_contact_person_phone_number = RequestBody.create(text, phonenumber.getText().toString());
                    RequestBody request_store_description = RequestBody.create(text, detail.getText().toString());
                    RequestBody request_store_name = RequestBody.create(text, storeDetails.getName());
                    if (click_banner && click_logo){
                        MultipartBody.Part body_photo = null;
                        MultipartBody.Part body_banner = null;
                        File filephoto = new File(mediaPathLogo);
                        RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
                        body_photo = MultipartBody.Part.createFormData("photo", filephoto.getName(), requestFileLogo);
                        File filebanner = new File(mediaPathBanner);
                        RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
                        body_banner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
                        service.req_update_store_information(request_token, request_store_id, request_store_businesstype, request_store_established_year, request_store_province, request_store_city, request_store_contact_person_name, request_store_contact_person_job_title, request_store_contact_person_phone_number, request_store_description, body_photo, body_banner, request_store_name)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.code()==200){
                                            try{
                                                JSONObject jsonResults = new JSONObject(response.body().string());
                                                String message = jsonResults.getString("message");
                                                if(jsonResults.getBoolean("status")){
                                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                }else if (!jsonResults.getBoolean("status")){
                                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                                }else {
                                                    Snackbar.make(container, "WHAT ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                                }
                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }catch (IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        initDialog(3);
                                    }
                                });
                    }
                    else if (click_banner){
                        MultipartBody.Part body_banner = null;
                        File filebanner = new File(mediaPathBanner);
                        RequestBody requestFileBanner = RequestBody.create(MediaType.parse("multipart/form-data"), filebanner);
                        body_banner = MultipartBody.Part.createFormData("banner", filebanner.getName(), requestFileBanner);
                        service.req_update_store_information_without_photo(request_token, request_store_id, request_store_businesstype, request_store_established_year, request_store_province, request_store_city, request_store_contact_person_name, request_store_contact_person_job_title, request_store_contact_person_phone_number, request_store_description, body_banner, request_store_name)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.code()==200){
                                            try{
                                                JSONObject jsonResults = new JSONObject(response.body().string());
                                                String message = jsonResults.getString("message");
                                                if(jsonResults.getBoolean("status")){
                                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                    finish();
                                                    MyStore.MYSTORE.finish();
                                                }else if (!jsonResults.getBoolean("status")){
                                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                                }else {
                                                    Snackbar.make(container, "WHAT ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                                }
                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }catch (IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        initDialog(3);
                                    }
                                });
                    }
                    else if (click_logo){
                        MultipartBody.Part body_photo = null;
                        File filephoto = new File(mediaPathLogo);
                        RequestBody requestFileLogo = RequestBody.create(MediaType.parse("multipart/form-data"), filephoto);
                        body_photo = MultipartBody.Part.createFormData("photo", filephoto.getName(), requestFileLogo);
                        service.req_update_store_information_without_banner(request_token, request_store_id, request_store_businesstype, request_store_established_year, request_store_province, request_store_city, request_store_contact_person_name, request_store_contact_person_job_title, request_store_contact_person_phone_number, request_store_description, body_photo, request_store_name)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.code()==200){
                                            try{
                                                JSONObject jsonResults = new JSONObject(response.body().string());
                                                String message = jsonResults.getString("message");
                                                if(jsonResults.getBoolean("status")){
                                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                    finish();
                                                    MyStore.MYSTORE.finish();
                                                }else if (!jsonResults.getBoolean("status")){
                                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                                }else {
                                                    Snackbar.make(container, "WHAT ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                                }
                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }catch (IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        initDialog(3);
                                    }
                                });
                    }
                    else {
                        service.req_update_store_information_without_photo_banner(request_token, request_store_id, request_store_businesstype, request_store_established_year, request_store_province, request_store_city, request_store_contact_person_name, request_store_contact_person_job_title, request_store_contact_person_phone_number, request_store_description, request_store_name)
                                .enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.code()==200){
                                            try{
                                                JSONObject jsonResults = new JSONObject(response.body().string());
                                                String message = jsonResults.getString("message");
                                                if(jsonResults.getBoolean("status")){
                                                    Toasty.success(context, message, Toast.LENGTH_SHORT, true).show();
                                                    finish();
                                                    MyStore.MYSTORE.finish();
                                                }else if (!jsonResults.getBoolean("status")){
                                                    Toasty.error(context, message, Toast.LENGTH_SHORT, true).show();
                                                }else {
                                                    Snackbar.make(container, "WHAT ERROR IS THIS ?", Snackbar.LENGTH_LONG).show();
                                                }
                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }catch (IOException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        initDialog(3);
                                    }
                                });
                    }
                }
                else {
                    Toasty.error(context, "Please check the information again, something is error", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    private void initCitySpinner(){
        service = apiUtils.getAPIService();
        service.req_get_city(idprovince).enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.code() == 200){
                    cityDetailsList = response.body().getCityDetails();
                    spinCityAdapter = new SpinCityAdapter(context, android.R.layout.simple_spinner_item, cityDetailsList);
                    city.setAdapter(spinCityAdapter);
                    city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            CityDetails citydetails = spinCityAdapter.getItem(position);
                            idcity = citydetails.getCityId();
                            choosen_city = citydetails.getCityName();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }
    private void initProvinceSpinner(){
        service = apiUtils.getAPIService();
        service.req_get_province_list().enqueue(new Callback<ProvinceResponse>() {
            @Override
            public void onResponse(Call<ProvinceResponse> call, Response<ProvinceResponse> response) {
                if(response.code() == 200){
                    provinceDetailsList = response.body().getProvinceDetails();
                    spinProvinceAdapter = new SpinProvinceAdapter(context, android.R.layout.simple_spinner_item, provinceDetailsList);
                    province.setAdapter(spinProvinceAdapter);
                    province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            ProvinceDetails provinceDetails = spinProvinceAdapter.getItem(position);
                            idprovince = provinceDetails.getProvinceId();
                            choosen_province = provinceDetails.getProvinceName();
                            initCitySpinner();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProvinceResponse> call, Throwable t) {
                initDialog(3);
            }
        });

    }
    private void initDialog(int stats){
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
    private void initanim(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(R.anim.slideright, R.anim.fadeout);
    }

}
