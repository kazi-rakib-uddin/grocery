package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.CartAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CartModel;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.ActivityCartBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    ApiInterface apiInterface;
    Session session;
    ArrayList<CartModel> cartModels = new ArrayList<>();
    CartAdapter cartAdapter;
    int total_price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(CartActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().hide();

        binding.cartBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.cartBtnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,CheckOutActivity.class));
            }
        });

//        TopBrand();
        fetch_cart_id();

    }

    private void fetch_cart_id(){
        Call<String> call = apiInterface.fetch_cart(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() > 0){
                            for(int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                cartModels.add(new CartModel(
                                        jsonObject.getString("product_id"),
                                        jsonObject.getString("quantity"),
                                        jsonObject.getString("price")
                                ));
                                total_price = total_price+Integer.parseInt(jsonObject.getString("price"));
                            }
                            binding.subTotal.setText("₹"+String.valueOf(total_price));
                            binding.total.setText("₹"+String.valueOf(total_price));
                            binding.overAllTotal.setText("₹"+String.valueOf(total_price));
                            cartAdapter = new CartAdapter(cartModels,CartActivity.this);
                            binding.rvCart.setAdapter(cartAdapter);
                        }else{
                            binding.nestedScroll.setVisibility(View.GONE);
                            binding.bottomLin.setVisibility(View.GONE);
                            binding.cartBtnBack2.setVisibility(View.VISIBLE);
                            binding.lottie.setVisibility(View.VISIBLE);
                            binding.lottieTXT.setVisibility(View.VISIBLE);
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



//    private void TopBrand() {
//        arrayList_top_brand.add(new CategoryModel("Banana","https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"));
//        binding.rvCart.setAdapter(new CartAdapter(this,arrayList_top_brand));
//    }

}