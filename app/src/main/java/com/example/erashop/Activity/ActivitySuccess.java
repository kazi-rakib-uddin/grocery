package com.example.erashop.Activity;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.ActivitySuccessBinding;

public class ActivitySuccess extends AppCompatActivity {

    ActivitySuccessBinding binding;

    ApiInterface apiInterface;
    Session session;

    String address = "",payment_type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(ActivitySuccess.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        getSupportActionBar().hide();

//        binding.animationView.setSpeed(0.7f);

        fetch_ordered_details();

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

    private void fetch_ordered_details() {
        address = getIntent().getExtras().getString("address");
        payment_type = getIntent().getExtras().getString("payment_type");

        binding.address.setText(address );
        binding.paymentType.setText(payment_type);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ActivitySuccess.this,MainActivity.class));
        super.onBackPressed();
    }
}