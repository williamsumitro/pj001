package com.example.williamsumitro.dress.view.presenter.api;

/**
 * Created by WilliamSumitro on 05/04/2018.
 */

public class apiUtils {
<<<<<<< HEAD
    public static final String IP ="http://192.168.42.40/";
=======
    public static final String IP ="http://192.168.42.27/";
>>>>>>> 787e2f0907560b7932fb4057c1bf1d0ff7b08434

    public static final String BASE = IP+"dress_marketplace/";
    public static final String BASE_URL = BASE+"api/";
    public static final String BASE_IMAGE = IP+"dress_marketplace/public/storage/";

    public static apiService getAPIService(){
        return apiClient.getClient(BASE_URL).create(apiService.class);
    }
    public static String getUrlImage(){
        return BASE_IMAGE;
    }
}
