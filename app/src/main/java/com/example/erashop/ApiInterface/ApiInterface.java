package com.example.erashop.ApiInterface;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<String> update(
            @Field("id") String id,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("upload_profile_image.php")
    Call<String> upload_profile_image(
            @Field("id") String id,
            @Field("image") String image
    );



    @FormUrlEncoded
    @POST("address.php")
    Call<String> AddAddress(
            @Field("user_id") String user_id,
            @Field("full_name") String full_name,
            @Field("phone_number") String phone_number,
            @Field("mail_id") String mail_id,
            @Field("pin_code") String pin_code,
            @Field("house_no") String house_no,
            @Field("area") String area,
            @Field("landmark") String landmark,
            @Field("state") String state,
            @Field("address_type") String address_type

    );

    @FormUrlEncoded
    @POST("get_address.php")
    Call<String> get_address(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("remove_address.php")
    Call<String> remove_address(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("fetch_profile.php")
    Call<String> fetch_profile(
            @Field("user_id") String user_id
    );

    @GET("fetch_categories.php")
    Call<String> fetch_categories();

    @FormUrlEncoded
    @POST("fetch_sub_categories.php")
    Call<String> fetch_sub_categories(
            @Field("category_id") String category_id
    );


    @FormUrlEncoded
    @POST("fetch_product_by_sub_category.php")
    Call<String> fetch_product_by_sub_category(
            @Field("category_id") String category_id,
            @Field("sub_category_id") String sub_category_id
    );

}
