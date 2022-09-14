package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Activity.SubCategoryActivity;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.SingleHomeCatagoryBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeCatagoryAdapter extends RecyclerView.Adapter<HomeCatagoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<HomeCatagoryModel> homeCatagoryModels=new ArrayList<>();

    public HomeCatagoryAdapter(Context context, ArrayList<HomeCatagoryModel> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleHomeCatagoryBinding binding = SingleHomeCatagoryBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        holder.binding.image.setImageResource(homeCatagoryModels.get(position).getImage());
        holder.binding.HomeTopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SubCategoryActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeCatagoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleHomeCatagoryBinding binding;
        public MyViewHolder(@NonNull SingleHomeCatagoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
