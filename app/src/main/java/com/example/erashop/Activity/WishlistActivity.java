package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.WishlistAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.ActivityMyOrdersBinding;
import com.example.erashop.databinding.ActivityWishlistBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistActivity extends AppCompatActivity {

    ActivityWishlistBinding binding;
    ArrayList<WishlistModel> wishlistModel=new ArrayList<>();
    WishlistAdapter wishlistAdapter;

    ApiInterface apiInterface;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        session = new Session(this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);


        GetWishlist();


        binding.rvActivityWishlist.setHasFixedSize(true);
        binding.rvActivityWishlist.setLayoutManager(new LinearLayoutManager(this));

    }

    private void GetWishlist() {
        ProgressUtils.showLoadingDialog(WishlistActivity.this);
        Call<String> call = apiInterface.fetch_wishlist_by_user_id(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if(jsonArray.length()!=0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                fetchProductDetails(jsonObject.getString("product_id"));
                            }
                        }else{
                            Toast.makeText(WishlistActivity.this, "Blank wishlist", Toast.LENGTH_SHORT).show();
                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(WishlistActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{

                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void fetchProductDetails(String product_id) {
        Call<String> call = apiInterface.fetch_product_by_product_id(product_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.length()!=0){
                            wishlistModel.add(new WishlistModel(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("category_id"),
                                    jsonObject.getString("sub_category_id"),
                                    jsonObject.getString("product_name"),
                                    jsonObject.getString("quantity"),
                                    jsonObject.getString("discounted_price"),
                                    jsonObject.getString("original_price"),
                                    jsonObject.getString("discount_percentage"),
                                    Utils.product_images+jsonObject.getString("image1")
                            ));

                            wishlistAdapter = new WishlistAdapter(WishlistActivity.this,wishlistModel);
                            binding.rvActivityWishlist.setAdapter(wishlistAdapter);

                        }else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}