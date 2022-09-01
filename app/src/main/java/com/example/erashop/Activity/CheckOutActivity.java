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
                onBackPressed();
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
        arrayList_top_brand.add(new CategoryModel("Kazi Sani","https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"));

        binding.rvCheckoutAddress.setAdapter(new AddressAdapter(this,arrayList_top_brand));

    }

    private void TopBrand()
    {
        arrayList_top_cart.add(new CategoryModel("Banana","https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1480&q=80"));

        binding.rvCheckoutItem.setAdapter(new CheckOutCartAdapter(this,arrayList_top_cart));

    }


}