package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.SingleHomeCatagoryBinding;

import java.util.List;

public class HomeCatagoryAdapter extends RecyclerView.Adapter<HomeCatagoryAdapter.MyViewHolder> {

    private Context context;
    private List<HomeCatagoryModel> homeCatagoryModels;

    public HomeCatagoryAdapter(Context context, List<HomeCatagoryModel> homeCatagoryModels) {
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
