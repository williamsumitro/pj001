package com.example.williamsumitro.dress.view.presenter.api;

import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.UserResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @FormUrlEncoded
    @POST("check_store_name")
    Call<ResponseBody> req_check_store(@Field("store_name") String store_name);

    @FormUrlEncoded
    @POST("get_auth_user")
    Call<UserResponse> req_get_auth_user(@Field("token") String token);

    @FormUrlEncoded
    @POST("register_store_name")
    Call<ResponseBody> req_register_store_name(@Field("token") String token,
                                               @Field("store_name") String storename);

    @POST("get_province_list")
    Call<ProvinceResponse> req_get_province_list();

    @POST("get_courier_list")
    Call<CourierResponse> req_get_courier_list();

}
