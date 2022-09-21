package com.example.erashop.ApiInterface;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @FormUrlEncoded
    @POST("fetch_single_product_details.php")
    Call<String> fetch_single_product_details(
            @Field("product_id") String product_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST("fetch_product_images.php")
    Call<String> fetch_product_images(
            @Field("product_id") String product_id,
            @Field("sub_category_id") String sub_category_id,
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST("fetch_product_by_category.php")
    Call<String> fetch_product_by_category(
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST("fetch_category_name.php")
    Call<String> fetch_category_name(
            @Field("category_id") String category_id
    );

    @GET("fetch_category_id.php")
    Call<String> fetch_category_id();

    @GET("fetch_multiple_banner.php")
    Call<String> fetch_multiple_banner();


    @GET("fetch_bottom_multiple_banner.php")
    Call<String> fetch_bottom_multiple_banner();

    @FormUrlEncoded
    @POST("fetch_product_by_product_id.php")
    Call<String> fetch_product_by_product_id(
            @Field("product_id") String product_id
    );

    @FormUrlEncoded
    @POST("fetch_wishlist_by_user_id.php")
    Call<String> fetch_wishlist_by_user_id(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("add_wishlist.php")
    Call<String> add_wishlist(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id
    );

    @FormUrlEncoded
    @POST("delete_from_wishlist.php")
    Call<String> delete_from_wishlist(
            @Field("user_id") String user_id,
            @Field("product_id") String product_id
    );

    @FormUrlEncoded
    @POST("search_products.php")
    Call<String> search_products(
            @Field("searched") String searched
    );

    @FormUrlEncoded
    @POST("add_to_cart.php")
    Call<String> add_to_cart(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id,
            @Field("price") String price,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("cart_quantity_increase.php")
    Call<String> cart_quantity_increase(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id,
            @Field("price") String price,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("cart_quantity_decrease.php")
    Call<String> cart_quantity_decrease(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id,
            @Field("price") String price,
            @Field("quantity") String quantity
    );



    @GET("fetch_trending_offers.php")
    Call<String> fetch_trending_offers();

    @FormUrlEncoded
    @POST("fetch_cart.php")
    Call<String> fetch_cart(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("fetch_delivery_charge.php")
    Call<String> fetch_delivery_charge(
            @Field("bill_amount") String bill_amount
    );


    @FormUrlEncoded
    @POST("remove_from_cart.php")
    Call<String> remove_from_cart(
            @Field("product_id") String product_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("order_placed.php")
    Call<String> order_placed(
            @Field("user_id") String user_id,
            @Field("total_amount") String total_amount,
            @Field("full_address") String full_address,
            @Field("pincode") String pincode,
            @Field("phone_no") String phone_no,
            @Field("payment_method") String payment_method
    );
}
