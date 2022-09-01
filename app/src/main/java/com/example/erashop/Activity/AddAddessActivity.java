package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.erashop.databinding.ActivityAddAddessBinding;

import java.util.Objects;

public class AddAddessActivity extends AppCompatActivity {

    ActivityAddAddessBinding binding;
    private static final String[] states = new String[] {
            "West Bangel", "Mumbai", "Delhi"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, states);
        binding.spinnerState.setAdapter(adapter);

        binding.addAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}