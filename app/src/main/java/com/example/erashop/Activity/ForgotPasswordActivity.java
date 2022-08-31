package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erashop.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.OTPEDTXT.setVisibility(View.GONE);
        binding.btnEnterOTP.setVisibility(View.GONE);

        binding.btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.phoneEDTXT.getText().toString().length() == 10){
                    binding.OTPEDTXT.setVisibility(View.VISIBLE);
                    binding.btnEnterOTP.setVisibility(View.VISIBLE);

                    binding.phoneEDTXT.setVisibility(View.GONE);
                    binding.btnSendOTP.setVisibility(View.GONE);
                    binding.input.setVisibility(View.GONE);

                }else{
                    binding.phoneEDTXT.setError("Number must be 10 digits");
                }
            }
        });

        binding.btnEnterOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPasswordActivity.this,EnterOTPActivity.class));
            }
        });

    }
}