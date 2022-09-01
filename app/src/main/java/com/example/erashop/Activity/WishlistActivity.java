package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.erashop.Adapter.WishlistAdapter;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivityMyOrdersBinding;
import com.example.erashop.databinding.ActivityWishlistBinding;

public class WishlistActivity extends AppCompatActivity {

    ActivityWishlistBinding binding;
    WishlistModel[] wishlistModel;
    WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWishlistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        wishlistModel = new WishlistModel[]{
                new WishlistModel("Banana","1","14","28","50", R.drawable.banana),
                new WishlistModel("Banana","1","14","28","50",R.drawable.apple),
                new WishlistModel("Banana","1","14","28","50",R.drawable.graps),
                new WishlistModel("Banana","1","14","28","50",R.drawable.banana),
                new WishlistModel("Banana","1","14","28","50",R.drawable.apple),
                new WishlistModel("Banana","1","14","28","50",R.drawable.graps),
                new WishlistModel("Banana","1","14","28","50",R.drawable.banana)
        };

        wishlistAdapter = new WishlistAdapter(this,wishlistModel);
        binding.rvActivityWishlist.setHasFixedSize(true);
        binding.rvActivityWishlist.setLayoutManager(new LinearLayoutManager(this));
        binding.rvActivityWishlist.setAdapter(wishlistAdapter);

    }
}