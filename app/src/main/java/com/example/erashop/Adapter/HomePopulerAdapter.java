package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.Model.HomeItemModel;
import com.example.erashop.databinding.SingleHomeCatagoryBinding;
import com.example.erashop.databinding.SinglePopulerItemBinding;

import java.util.List;

public class HomePopulerAdapter extends RecyclerView.Adapter<HomePopulerAdapter.MyViewHolder> {

    private Context context;
    private List<HomeCatagoryModel> homeCatagoryModels;

    public HomePopulerAdapter(Context context, List<HomeCatagoryModel> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SinglePopulerItemBinding binding = SinglePopulerItemBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        holder.binding.image.setImageResource(homeCatagoryModels.get(position).getImage());
        //Glide.with(context).load(homeCatagoryModels.get(position).getImage()).into(holder.binding.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeCatagoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SinglePopulerItemBinding binding;
        public MyViewHolder(@NonNull SinglePopulerItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
