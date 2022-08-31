package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.databinding.ActivitySuccessBinding;

public class ActivitySuccess extends AppCompatActivity {

    ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

//        binding.animationView.setSpeed(0.7f);

        binding.btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitySuccess.this,TrackOrderActivity.class));
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivitySuccess.this,MainActivity.class));
            }
        });

    }
}