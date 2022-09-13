package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Model.SubCategoryModel;
import com.example.erashop.R;
import com.example.erashop.databinding.SingleAllCatagoryBinding;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.holder> {
    ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();
    Context context;

    public SubCategoryAdapter(ArrayList<SubCategoryModel> subCategoryModels, Context context) {
        this.subCategoryModels = subCategoryModels;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleAllCatagoryBinding binding = SingleAllCatagoryBinding.inflate(inflater,parent,false);
        return new SubCategoryAdapter.holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.name.setText(subCategoryModels.get(position).getName());
        Glide.with(context)
                .load(subCategoryModels.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,SearchActivity.class);
                intent.putExtra("sub_cat_id",subCategoryModels.get(position).getSub_Category_id());
                intent.putExtra("cat_id",subCategoryModels.get(position).getCategory_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryModels.size();
    }


    public class holder extends RecyclerView.ViewHolder {
        SingleAllCatagoryBinding binding;
        public holder(@NonNull SingleAllCatagoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}


