package com.example.erashop.ApiInterface;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
            @Field("image") String image
    );

//    @Multipart
//    @POST("upload") upload_profile_image(
//            @Part MultipartBody.Part photo
//    );

}
