package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.SinglePopulerItemBinding;
import com.example.erashop.databinding.SingleTrendingOfferBinding;

import java.util.List;

public class HomeTrendingOfferAdapter extends RecyclerView.Adapter<HomeTrendingOfferAdapter.MyViewHolder> {

    private Context context;
    private List<HomeCatagoryModel> homeCatagoryModels;

    /*public HomeTrendingOfferAdapter(Context context, List<HomeCatagoryModel> homeCatagoryModels) {
        this.context = context;
        this.homeCatagoryModels = homeCatagoryModels;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleTrendingOfferBinding binding = SingleTrendingOfferBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        //holder.binding.image.setImageResource(homeCatagoryModels.get(position).getImage());
        //Glide.with(context).load(homeCatagoryModels.get(position).getImage()).into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleTrendingOfferBinding binding;
        public MyViewHolder(@NonNull SingleTrendingOfferBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
