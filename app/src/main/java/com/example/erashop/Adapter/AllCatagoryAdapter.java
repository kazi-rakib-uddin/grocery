package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.SingleAllCatagoryBinding;
import com.example.erashop.databinding.SingleHomeCatagoryBinding;

import java.util.List;

public class AllCatagoryAdapter extends RecyclerView.Adapter<AllCatagoryAdapter.MyViewHolder> {

    private Context context;
    private List<HomeCatagoryModel> homeCatagoryModels;

    public AllCatagoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleAllCatagoryBinding binding = SingleAllCatagoryBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        //holder.binding.image.setImageResource(homeCatagoryModels.get(position).getImage());

        holder.binding.AllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SearchActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleAllCatagoryBinding binding;
        public MyViewHolder(@NonNull SingleAllCatagoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
