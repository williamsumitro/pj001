package com.example.williamsumitro.dress.view.presenter.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.williamsumitro.dress.view.model.Checkout_Courier;
import com.example.williamsumitro.dress.view.model.Checkout_CourierArrayList;
import com.example.williamsumitro.dress.view.view.authentication.Login;
import com.example.williamsumitro.dress.view.view.main.MainActivity;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by WilliamSumitro on 06/04/2018.
 */

public class SessionManagement {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String PREF_NAME = "DressB2B";
    private static final String IS_LOGIN = "IsLogggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String TOKEN = "TOKEN";
    public static final String STORE_NAME = "STORE_NAME";
    public static final String STORE_DESCRIPTION = "STORE_DESCRIPTION";
    public static final String STORE_ESTABLISHED_YEAR = "STORE_ESTABLISHED YEAR";
    public static final String STORE_PROVINCE = "STORE_PROVINCE";
    public static final String STORE_CITY = "STORE_CITY";
    public static final String STORE_BUSINESS_TYPE = "STORE_BUSINESS_TYPE";
    public static final String STORE_CONTACT_PERSON_NAME = "STORE_CONTACT_PERSON_NAME";
    public static final String STORE_CONTACT_PERSON_JOB_TITLE = "STORE_CONTACT_PERSON_JOB_TITLE";
    public static final String STORE_CONTACT_PERSON_PHONE_NUMBER = "STORE_CONTACT_PERSON_PHONE_NUMBER";
    public static final String STORE_ID_PROVINCE = "STORE_ID_PROVINCE";
    public static final String STORE_ID_CITY = "STORE_ID_CITY";
    public static final String STORE_COURIER = "STORE_COURIER";

    public static final String CHECKOUT_RECEIVER_NAME = "CHECKOUT_RECEIVER_NAME";
    public static final String CHECKOUT_ADDRESS = "CHECKOUT_ADDRESS";
    public static final String CHECKOUT_IDPROVINCE = "CHECKOUT_IDPROVINCE";
    public static final String CHECKOUT_IDCITY = "CHECKOUT_IDCITY";
    public static final String CHECKOUT_PHONE_NUMBER = "CHECKOUT_PHONE_NUMBER";
    public static final String CHECKOUT_POSTAL_CODE = "CHECKOUT_POSTAL_CODE";
    public static final String CHECKOUT_COURIER = "CHECKOUT_COURIER";
    public static final String CHECKOUT_TOTAL_PRICE = "CHECKOUT_TOTAL_PRICE";
    public static final String CHECKOUT_TOTAL_QTY = "CHECKOUT_TOTAL_QTY";
    public static final String CHECKOUT_POINT = "CHECKOUT_POINT";
    private static final String CHECKOUT_NOTE = "CHECKOUT_NOTE";
    public SessionManagement(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void keepStoreName(String name){
        editor.putString(STORE_NAME, name);
        editor.commit();
    }
    public void keepStoreCourier(String courier){
        editor.putString(STORE_COURIER, courier);
        editor.commit();
    }
    public void keepStoreInformation(String establishedyear, String id_province,
                                     String id_city, String name, String jobtitle,
                                     String phonenumber, String description, String province, String city, String businesstype){
        editor.putString(STORE_ESTABLISHED_YEAR, establishedyear);
        editor.putString(STORE_ID_PROVINCE, id_province);
        editor.putString(STORE_ID_CITY, id_city);
        editor.putString(STORE_CONTACT_PERSON_NAME, name);
        editor.putString(STORE_CONTACT_PERSON_JOB_TITLE, jobtitle);
        editor.putString(STORE_CONTACT_PERSON_PHONE_NUMBER, phonenumber);
        editor.putString(STORE_DESCRIPTION, description);
        editor.putString(STORE_PROVINCE, province);
        editor.putString(STORE_CITY, city);
        editor.putString(STORE_BUSINESS_TYPE, businesstype);
        editor.commit();
    }
    public void keepCheckoutAddress(String receivername, String address, String phonenumber,
                                     String id_province, String id_city, String postalcode){
        editor.putString(CHECKOUT_RECEIVER_NAME, receivername);
        editor.putString(CHECKOUT_ADDRESS, address);
        editor.putString(CHECKOUT_PHONE_NUMBER, phonenumber);
        editor.putString(CHECKOUT_IDPROVINCE, id_province);
        editor.putString(CHECKOUT_IDCITY, id_city);
        editor.putString(CHECKOUT_POSTAL_CODE, postalcode);
        editor.commit();
    }
    public HashMap<String, String> getCheckoutAddress(){
        HashMap<String, String> address = new HashMap<>();
        address.put(CHECKOUT_RECEIVER_NAME, pref.getString(CHECKOUT_RECEIVER_NAME, null));
        address.put(CHECKOUT_ADDRESS, pref.getString(CHECKOUT_ADDRESS, null));
        address.put(CHECKOUT_PHONE_NUMBER, pref.getString(CHECKOUT_PHONE_NUMBER, null));
        address.put(CHECKOUT_IDPROVINCE, pref.getString(CHECKOUT_IDPROVINCE, null));
        address.put(CHECKOUT_IDCITY, pref.getString(CHECKOUT_IDCITY, null));
        address.put(CHECKOUT_POSTAL_CODE, pref.getString(CHECKOUT_POSTAL_CODE, null));
        return address;
    }
    public void keepCheckoutCourier(String total_price, String total_qty, String point){
        editor.putString(CHECKOUT_TOTAL_PRICE, total_price);
        editor.putString(CHECKOUT_TOTAL_QTY, total_qty);
        editor.putString(CHECKOUT_POINT, point);
        editor.commit();
    }
    public void keepCheckoutCourierService(Checkout_CourierArrayList checkout_courier){
        Gson gson = new Gson();
        String json = gson.toJson(checkout_courier);
        editor.putString(CHECKOUT_COURIER, json);
        editor.commit();
    }
    public Checkout_CourierArrayList getcheckoutcourierService(){
        Gson gson = new Gson();
        String json = pref.getString(CHECKOUT_COURIER, "");
        Checkout_CourierArrayList obj = gson.fromJson(json, Checkout_CourierArrayList.class);
        return obj;
    }
    public void keepCheckoutNote(String note){
        editor.putString(CHECKOUT_NOTE, note);
        editor.commit();
    }
    public HashMap<String, String> getCheckoutNote(){
        HashMap<String, String> courier = new HashMap<>();
        courier.put(CHECKOUT_NOTE, pref.getString(CHECKOUT_NOTE, null));
        return courier;
    }
    public HashMap<String, String> getcheckoutcourier(){
        HashMap<String, String> courier = new HashMap<>();
        courier.put(CHECKOUT_TOTAL_PRICE, pref.getString(CHECKOUT_TOTAL_PRICE, null));
        courier.put(CHECKOUT_TOTAL_QTY, pref.getString(CHECKOUT_TOTAL_QTY, null));
        courier.put(CHECKOUT_POINT, pref.getString(CHECKOUT_POINT, null));
        return courier;
    }
    public HashMap<String, String> getCheckoutIdCity(){
        HashMap<String, String> id_city = new HashMap<>();
        id_city.put(CHECKOUT_IDCITY, pref.getString(CHECKOUT_IDCITY, null));
        return id_city;
    }
    public HashMap<String , String> getStoreInformation(){
        HashMap<String, String> store = new HashMap<String, String>();
        store.put(STORE_NAME, pref.getString(STORE_NAME, null));
        store.put(STORE_ESTABLISHED_YEAR, pref.getString(STORE_ESTABLISHED_YEAR, null));
        store.put(STORE_ID_PROVINCE, pref.getString(STORE_ID_PROVINCE, null));
        store.put(STORE_ID_CITY, pref.getString(STORE_ID_CITY, null));
        store.put(STORE_CONTACT_PERSON_NAME, pref.getString(STORE_CONTACT_PERSON_NAME, null));
        store.put(STORE_CONTACT_PERSON_JOB_TITLE, pref.getString(STORE_CONTACT_PERSON_JOB_TITLE, null));
        store.put(STORE_CONTACT_PERSON_PHONE_NUMBER, pref.getString(STORE_CONTACT_PERSON_PHONE_NUMBER, null));
        store.put(STORE_DESCRIPTION, pref.getString(STORE_DESCRIPTION, null));
        store.put(STORE_COURIER, pref.getString(STORE_COURIER, null));
        store.put(STORE_BUSINESS_TYPE, pref.getString(STORE_BUSINESS_TYPE, null));
        return store;
    }
//    public void createLoginSession(String name, String email, String jwt){
//        editor.putBoolean(IS_LOGIN, true);
//        editor.putString(KEY_NAME, name);
//        editor.putString(KEY_EMAIL, email);
//        editor.putString(JWT, jwt);
//        editor.commit();
//    }
    public void clearSession(){
        editor.clear();
        editor.commit();
    }
    public void createLoginSession(String token){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public void checkLogin(){
        if (!this.isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }
    }
    public boolean getcheckLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(i);
            return true;
        }
        else {
            return false;
        }
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(TOKEN, pref.getString(TOKEN,null));
        // return user
        return user;
    }
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }
    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }
    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
