package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.erashop.Adapter.TrackingAdapter;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.Model.TrackingModel;
import com.example.erashop.databinding.ActivityTrackOrderBinding;

import java.util.ArrayList;

public class TrackOrderActivity extends AppCompatActivity {
    ActivityTrackOrderBinding binding;
    ArrayList<TrackingModel> arrayList_top_brand1 = new ArrayList<>();
    TrackingAdapter trackingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrackOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        Track();
        
    }

    private void Track()
    {
        arrayList_top_brand1.add(new TrackingModel("Ordered","14-2-24"));
        arrayList_top_brand1.add(new TrackingModel("Ready To Ship","14-2-24"));
        arrayList_top_brand1.add(new TrackingModel("Out For Delivery","14-2-24"));
        binding.rvTrack.setAdapter(new TrackingAdapter(arrayList_top_brand1));

    }
}