package com.example.williamsumitro.dress.view.presenter.api;

/**
 * Created by WilliamSumitro on 05/04/2018.
 */

public class apiUtils {
    public static final String IP ="http://192.168.100.10/";

    public static final String BASE = IP+"dress_marketplace/";
    public static final String BASE_URL = BASE+"api/";
    public static final String BASE_IMAGE = IP+"franchise_marketplace_api/storage/app/public/";

    public static apiService getAPIService(){
        return apiClient.getClient(BASE_URL).create(apiService.class);
    }
    public static String getUrlImage(){
        return BASE_IMAGE;
    }
}
