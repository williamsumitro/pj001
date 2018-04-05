package com.example.williamsumitro.dress.view.presenter.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by WilliamSumitro on 05/04/2018.
 */

public interface apiService {
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> req_register(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("full_name") String fullname,
                                    @Field("gender") String gender,
                                    @Field("phone_number") String phonenumber);
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> req_login(@Field("email") String email,
                                 @Field("password") String password);
}
