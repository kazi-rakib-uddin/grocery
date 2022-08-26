package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erashop.Adapter.MyAddressAdapter;
import com.example.erashop.Model.MyAddressModel;
import com.example.erashop.databinding.ActivityMyAddressBinding;

import java.util.Objects;


public class MyAddressActivity extends AppCompatActivity {
    MyAddressModel[] myAddressModels;

    ActivityMyAddressBinding binding;
    MyAddressAdapter myAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAddressActivity.this,AddAddessActivity.class);
                startActivity(intent);
            }
        });

        binding.addressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAddressActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        myaddress();

    }

    private void myaddress() {
        myAddressModels= new MyAddressModel[]{
                new MyAddressModel("Something das","7894561230","something@mail.com","789456","5-C","Something Apartment","Near something","WB","Office",true),
                new MyAddressModel("Something das","7894561230","something@mail.com","789456","5-C","Something Apartment","Near something","WB","Home",true),
                new MyAddressModel("Something das","7894561230","something@mail.com","789456","5-C","Something Apartment","Near something","WB","Office",true),
        };
        myAddressAdapter = new MyAddressAdapter(this,myAddressModels);
        binding.rvAddress.setHasFixedSize(true);
        binding.rvAddress.setAdapter(myAddressAdapter);
    }
}