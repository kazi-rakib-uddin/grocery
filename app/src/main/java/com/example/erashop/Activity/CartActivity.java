package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.CartAdapter;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    ArrayList<CategoryModel> arrayList_top_brand = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().hide();

        binding.cartBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this,CheckOutActivity.class));
            }
        });

        TopBrand();

    }

    private void TopBrand()
    {
        arrayList_top_brand.add(new CategoryModel("Redmi 9A 32 GB","https://m.media-amazon.com/images/I/71hEzQGO5qL._SL1500_.jpg"));
        binding.rvCart.setAdapter(new CartAdapter(this,arrayList_top_brand));
    }

}