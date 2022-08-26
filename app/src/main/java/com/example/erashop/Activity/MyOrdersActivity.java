package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erashop.Adapter.PastOrdersAdapter;
import com.example.erashop.Adapter.RecentOrdedrsAdapter;
import com.example.erashop.Fragment.ProfileFragment;
import com.example.erashop.Model.PastOrdersModel;
import com.example.erashop.Model.RecentOrdersModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivityMainBinding;
import com.example.erashop.databinding.ActivityMyOrdersBinding;

public class MyOrdersActivity extends AppCompatActivity {

    RecentOrdersModel[] recentOrdersModels;
    RecentOrdedrsAdapter recentOrdedrsAdapter;

    PastOrdersModel[] pastOrdersModels;
    PastOrdersAdapter pastOrdersAdapter;

    ActivityMyOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("My Orders");
        getSupportActionBar().hide();


        back();
        recent();
        past();

    }

    private void back() {
        binding.myorderBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOrdersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void past() {
        pastOrdersModels = new PastOrdersModel[]{
                new PastOrdersModel("Banana", "1kg", R.drawable.banana),
                new PastOrdersModel("Banana", "1kg", R.drawable.banana),
                new PastOrdersModel("Banana", "1kg", R.drawable.banana),
                new PastOrdersModel("Banana", "1kg", R.drawable.banana),
        };

        pastOrdersAdapter = new PastOrdersAdapter(this, pastOrdersModels);
        binding.rvDeliveredOrder.setHasFixedSize(true);
        binding.rvDeliveredOrder.setAdapter(pastOrdersAdapter);
    }

    private void recent() {
        recentOrdersModels = new RecentOrdersModel[]{
                new RecentOrdersModel("Banana", "1kg", R.drawable.banana),
                new RecentOrdersModel("Apple", "1kg", R.drawable.apple),
                new RecentOrdersModel("Grapes", "1kg", R.drawable.graps),
        };

        recentOrdedrsAdapter = new RecentOrdedrsAdapter(this, recentOrdersModels);
        binding.rvRecentOrder.setHasFixedSize(true);
        binding.rvRecentOrder.setAdapter(recentOrdedrsAdapter);
    }
}