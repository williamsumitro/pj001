package com.example.williamsumitro.dress.view.presenter.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.williamsumitro.dress.view.view.main.MainActivity;

import java.util.HashMap;

/**
 * Created by WilliamSumitro on 06/04/2018.
 */

public class SessionManagement {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

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
    public static final String STORE_BANK_NAME = "STORE_BANK_NAME";
    public static final String STORE_BRANCH = "STORE_BRANCH";
    public static final String STORE_BANK_ACCOUNT_NUMBER = "STORE_BANK_ACCOUNT_NUMBER";
    public static final String STORE_NAME_IN_BANK_ACCOUNT = "STORE_NAME_IN_BANK_ACCOUNT";
    public static final String STORE_COURIER = "STORE_COURIER";

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
}
