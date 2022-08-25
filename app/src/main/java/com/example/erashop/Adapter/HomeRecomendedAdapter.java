package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.SinglePopulerItemBinding;
import com.example.erashop.databinding.SingleRecomendedItemBinding;

import java.util.List;

public class HomeRecomendedAdapter extends RecyclerView.Adapter<HomeRecomendedAdapter.MyViewHolder> {

    private Context context;
    private List<HomeCatagoryModel> homeCatagoryModels;

    public HomeRecomendedAdapter(Context context, List<HomeCatagoryModel> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleRecomendedItemBinding binding = SingleRecomendedItemBinding.inflate(inflater,parent,false);
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
        
        SingleRecomendedItemBinding binding;
        public MyViewHolder(@NonNull SingleRecomendedItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
