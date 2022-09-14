package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Activity.SubCategoryActivity;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.R;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.SingleAllCatagoryBinding;
import com.example.erashop.databinding.SingleHomeCatagoryBinding;

import java.util.ArrayList;
import java.util.List;

public class AllCatagoryAdapter extends RecyclerView.Adapter<AllCatagoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HomeCatagoryModel> homeCatagoryModels;

//    public AllCatagoryAdapter(Context context) {
//        this.context = context;
//    }

    public AllCatagoryAdapter(Context context, ArrayList<HomeCatagoryModel> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleAllCatagoryBinding binding = SingleAllCatagoryBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        Glide.with(context)
                .load(homeCatagoryModels.get(position).getCategory_image())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.image);

        holder.binding.AllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubCategoryActivity.class);
                intent.putExtra("cat_name",homeCatagoryModels.get(position).getName());
                intent.putExtra("cat_id",homeCatagoryModels.get(position).getCategory_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        int size =0;
        if (homeCatagoryModels.size()<10){
            size = homeCatagoryModels.size();
        }else{
            size = 10;
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleAllCatagoryBinding binding;
        public MyViewHolder(@NonNull SingleAllCatagoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
