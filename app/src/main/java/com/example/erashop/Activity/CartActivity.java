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

        TopBrand();

    }

    private void TopBrand()
    {
        arrayList_top_brand.add(new CategoryModel("Redmi 9A 32 GB","https://m.media-amazon.com/images/I/71hEzQGO5qL._SL1500_.jpg"));
        arrayList_top_brand.add(new CategoryModel("Realme C11 32 GB","https://m.media-amazon.com/images/I/618UBhFmaQS._SL1500_.jpg"));
        arrayList_top_brand.add(new CategoryModel("Tecno Spark 7 32 GB","https://m.media-amazon.com/images/I/71qdbEfle6S._SL1500_.jpg"));
        binding.rvCart.setAdapter(new CartAdapter(this,arrayList_top_brand));

    }

}