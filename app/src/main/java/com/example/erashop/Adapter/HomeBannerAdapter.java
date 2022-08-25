package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.databinding.CustomBannerBinding;
import com.example.erashop.databinding.SingleRecomendedItemBinding;

import java.util.List;

public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.MyViewHolder> {

    private Context context;
    //private List<HomeCatagoryModel> homeCatagoryModels;

    private String[] items;

    public HomeBannerAdapter(Context context, String[] items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomBannerBinding binding = CustomBannerBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Glide.with(context).load(items[position])
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imageView);

        //holder.binding.name.setText(homeCatagoryModels.get(position).getName());
        //holder.binding.image.setImageResource(homeCatagoryModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        CustomBannerBinding binding;
        public MyViewHolder(@NonNull CustomBannerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
