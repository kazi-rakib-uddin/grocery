package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.databinding.ActivityEnterOtpactivityBinding;

public class EnterOTPActivity extends AppCompatActivity {

    ActivityEnterOtpactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.ChangePasswordBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EnterOTPActivity.this, "Password changed! Login again with new password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EnterOTPActivity.this,LoginActivity.class));
            }
        });

    }
}