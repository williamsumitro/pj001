package com.example.williamsumitro.dress.view.presenter.api;

import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.Price;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.dress_attribute.DressAttribute;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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


    @POST("get_dress_attributes")
    Call<DressAttribute> req_get_dress_attributes();

    @FormUrlEncoded
    @POST("get_city_by_province")
    Call<CityResponse> req_get_city(@Field("province_id") String province_id);

    @FormUrlEncoded
    @POST("get_user_store")
    Call<StoreResponse> req_get_user_store(@Field("token") String token);

    @Multipart
    @POST("add_product")
    Call<ResponseBody> req_add_product(
            @Part("token") RequestBody token,
            @Part("name") RequestBody name,
            @Part("min_order") RequestBody min_order,
            @Part("weight") RequestBody weight,
            @Part("description") RequestBody description,
            @Part("style_id") RequestBody style_id,
            @Part("season_id") RequestBody season_id,
            @Part("neckline_id") RequestBody neckline_id,
            @Part("sleevelength_id") RequestBody sleevelength_id,
            @Part("waiseline_id") RequestBody waiseline_id,
            @Part("material_id") RequestBody material_id,
            @Part("fabrictype_id") RequestBody fabrictype_id,
            @Part("decoration_id") RequestBody decoration_id,
            @Part("patterntype_id") RequestBody patterntype_id,
            @Part MultipartBody.Part[] size,
            @Part MultipartBody.Part[] price,
//            @Query("size[]") ArrayList<String> size,
//            @Query("price[]") List<Price> priceList,
            @Part MultipartBody.Part photo
    );
    @Multipart
    @POST("register_store")
    Call<ResponseBody> req_register_store(
            @Part("token") RequestBody token,
            @Part("store_name") RequestBody store_name,
            @Part MultipartBody.Part photo,
            @Part MultipartBody.Part banner,
            @Part("description") RequestBody description,
            @Part("established_year") RequestBody established_year,
            @Part("province") RequestBody province,
            @Part("city") RequestBody city,
            @Part("business_type") RequestBody business_type,
            @Part("contact_person_name") RequestBody contact_person_name,
            @Part("contact_person_job_title") RequestBody contact_person_job_title,
            @Part("contact_person_phone_number") RequestBody contact_person_phone_number,
            @Part MultipartBody.Part[] courier,
            @Part MultipartBody.Part ktp,
            @Part MultipartBody.Part siup,
            @Part MultipartBody.Part npwp,
            @Part MultipartBody.Part skdp,
            @Part MultipartBody.Part tdp
    );

    @FormUrlEncoded
    @POST("get_product_detail")
    Call<ProductDetail> req_get_product_detail(@Field("product_id") String product_id);
}
