package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Model.BottomBannerModel;
import com.example.erashop.R;
import com.example.erashop.databinding.CustomBannerBinding;

import java.util.ArrayList;

public class BottomBannerAdapter extends RecyclerView.Adapter<BottomBannerAdapter.Holder> {

    ArrayList<BottomBannerModel> bottomBannerModels = new ArrayList<>();
    Context context;

    public BottomBannerAdapter(ArrayList<BottomBannerModel> bottomBannerModels, Context context) {
        this.bottomBannerModels = bottomBannerModels;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomBannerBinding binding = CustomBannerBinding.inflate(inflater,parent,false);
        return new BottomBannerAdapter.Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Glide.with(context)
                .load(bottomBannerModels.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.imageView);
    }

    @Override
    public int getItemCount() {
        int size =0;
        if (bottomBannerModels.size()<3){
            size = bottomBannerModels.size();
        }else{
            size = 3;
        }
        return size;

    }

    public class Holder extends RecyclerView.ViewHolder {
        CustomBannerBinding binding;
        public Holder(@NonNull CustomBannerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
