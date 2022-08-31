package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.erashop.Adapter.SearchAdapter;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    SearchAdapter searchAdapter;
    SearchModel[] searchModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        searchModels = new SearchModel[]{
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee),
                new SearchModel("Tea","200","350", R.drawable.tea_coffee)
        };

        searchAdapter = new SearchAdapter(searchModels,this);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        binding.rvSearch.setLayoutManager(layoutManager);
        binding.rvSearch.setAdapter(searchAdapter);

    }
}