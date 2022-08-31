package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erashop.Adapter.AddressAdapter;
import com.example.erashop.Adapter.CheckOutCartAdapter;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.databinding.ActivityCheckOutBinding;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {
    ActivityCheckOutBinding binding;
    ArrayList<CategoryModel> arrayList_top_brand = new ArrayList<>();
    ArrayList<CategoryModel> arrayList_top_cart = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this,AddAddessActivity.class));
            }
        });

        binding.checkOutBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this,CartActivity.class));
            }
        });

        binding.btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this,ActivitySuccess.class));
            }
        });

        address();
        TopBrand();

    }
    private void address() {
        arrayList_top_brand.add(new CategoryModel("Kazi Sani","https://m.media-amazon.com/images/I/71hEzQGO5qL._SL1500_.jpg"));

        binding.rvCheckoutAddress.setAdapter(new AddressAdapter(this,arrayList_top_brand));

    }

    private void TopBrand()
    {
        arrayList_top_cart.add(new CategoryModel("Banana","https://m.media-amazon.com/images/I/71hEzQGO5qL._SL1500_.jpg"));

        binding.rvCheckoutItem.setAdapter(new CheckOutCartAdapter(this,arrayList_top_cart));

    }


}