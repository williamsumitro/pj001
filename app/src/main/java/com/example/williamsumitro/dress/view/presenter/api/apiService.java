package com.example.williamsumitro.dress.view.presenter.api;

import com.example.williamsumitro.dress.view.model.AddToBag;
import com.example.williamsumitro.dress.view.model.ApproveOrder;
import com.example.williamsumitro.dress.view.model.BagResponse;
import com.example.williamsumitro.dress.view.model.Checkout;
import com.example.williamsumitro.dress.view.model.CheckoutResponse;
import com.example.williamsumitro.dress.view.model.CityResponse;
import com.example.williamsumitro.dress.view.model.CourierResponse;
import com.example.williamsumitro.dress.view.model.PaymentResponse;
import com.example.williamsumitro.dress.view.model.ProductDetail;
import com.example.williamsumitro.dress.view.model.ProvinceResponse;
import com.example.williamsumitro.dress.view.model.Purchase_OrderResponse;
import com.example.williamsumitro.dress.view.model.Purchase_PaymentResponse;
import com.example.williamsumitro.dress.view.model.Purchase_ReviewRatingResponse;
import com.example.williamsumitro.dress.view.model.Purchase_TransactionHistoryResponse;
import com.example.williamsumitro.dress.view.model.Sales_OrderResponse;
import com.example.williamsumitro.dress.view.model.StoreResponse;
import com.example.williamsumitro.dress.view.model.SubmitReviewRating;
import com.example.williamsumitro.dress.view.model.UserResponse;
import com.example.williamsumitro.dress.view.model.WishlistResponse;
import com.example.williamsumitro.dress.view.model.dress_attribute.DressAttribute;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("get_auth_user")
    Call<UserResponse> req_get_auth_user(@Field("token") String token);

    @FormUrlEncoded
    @POST("check_store_name")
    Call<ResponseBody> req_check_store(@Field("store_name") String store_name);

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

    @FormUrlEncoded
    @POST("get_product_detail")
    Call<ProductDetail> req_get_product_detail(@Field("token") String token,
                                               @Field("product_id") String product_id);

    @POST("add_to_bag")
    Call<ResponseBody> req_add_to_bag(@Body AddToBag addToBag);


    @FormUrlEncoded
    @POST("view_shopping_bag")
    Call<BagResponse> req_view_shopping_bag(@Field("token") String token);

    @FormUrlEncoded
    @POST("delete_product_from_bag")
    Call<ResponseBody> req_delete_product(@Field("token") String token,
                                          @Field("product_id") String product_id);


    @FormUrlEncoded
    @POST("get_checkout_info")
    Call<CheckoutResponse> req_get_checkout_info(@Field("token") String token,
                                              @Field("destination_city") String destination_city);

    @POST("checkout")
    Call<PaymentResponse> req_checkout(@Body Checkout checkout_courier);

    @FormUrlEncoded
    @POST("get_purchase_payment")
    Call<Purchase_PaymentResponse> req_get_purchase_payment(@Field("token") String token);

    @FormUrlEncoded
    @POST("get_order_status")
    Call<Purchase_OrderResponse> req_get_purchase_orderstatu(@Field("token") String token);

    @FormUrlEncoded
    @POST("confirm_payment")
    Call<ResponseBody> req_confirm_payment(@Field("token") String token,
                                           @Field("transaction_id") String transaction_id,
                                           @Field("company_bank_id") String company_bank_id,
                                           @Field("amount") String amount,
                                           @Field("sender_bank") String sender_bank,
                                           @Field("sender_account_number") String sender_account_number,
                                           @Field("sender_name") String sender_name,
                                           @Field("note") String note);


    @FormUrlEncoded
    @POST("seller_get_shipping_confirmation")
    Call<Sales_OrderResponse> req_seller_get_shipping(@Field("token") String token);

    @FormUrlEncoded
    @POST("seller_get_order")
    Call<Sales_OrderResponse> req_seller_get_order(@Field("token") String token);

    @POST("approve_order_product")
    Call<ResponseBody> req_approve_order_product(@Body ApproveOrder approveOrder);

    @FormUrlEncoded
    @POST("input_receipt_number")
    Call<ResponseBody> req_input_receipt_number(@Field("token") String token,
                                                @Field("transaction_id") String transaction_id,
                                                @Field("store_id") String store_id,
                                                @Field("receipt_number") String receipt_number);

    @FormUrlEncoded
    @POST("finish_shipping")
    Call<ResponseBody> req_finish_shippin(@Field("token") String token,
                                                @Field("transaction_id") String transaction_id,
                                                @Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("get_receipt_confirmation")
    Call<Sales_OrderResponse> req_get_receipt_confirmation(@Field("token") String token);

    @FormUrlEncoded
    @POST("confirm_receipt")
    Call<ResponseBody> req_confirm_receipt(@Field("token") String token,
                                          @Field("transaction_id") String transaction_id,
                                          @Field("store_id") String store_id);

    @FormUrlEncoded
    @POST("add_to_wishlist")
    Call<ResponseBody> req_add_to_wishlist(@Field("token") String token,
                                           @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("delete_from_wishlist")
    Call<ResponseBody> req_delete_from_wishlist(@Field("token") String token,
                                                @Field("product_id") String product_id);

    @FormUrlEncoded
    @POST("my_wishlist")
    Call<WishlistResponse> req_get_my_wishlist(@Field("token") String token);

    @FormUrlEncoded
    @POST("withdraw")
    Call<ResponseBody> req_withdraw(@Field("token") String token,
                                    @Field("amount") String amount,
                                    @Field("bank_name") String bank_name,
                                    @Field("branch") String branch,
                                    @Field("account_number") String account_number,
                                    @Field("name_in_account") String name_in_account,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("get_review_rating")
    Call<Purchase_ReviewRatingResponse> req_get_review_rating(@Field("token") String token);

    @POST("submit_review_rating")
    Call<ResponseBody> req_submit_review_rating(@Body SubmitReviewRating submitReviewRating);

    @FormUrlEncoded
    @POST("transaction_history")
    Call<Purchase_TransactionHistoryResponse> req_transaction_history(@Field("token") String token);
}
