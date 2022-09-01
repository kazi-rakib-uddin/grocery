package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.databinding.CustomBannerBinding;
import com.example.erashop.databinding.CustomCartItemBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    ArrayList<CategoryModel> arrayList_catagory;
    Context context;

    public CartAdapter(Context context, ArrayList<CategoryModel> arrayList_catagory) {
        this.arrayList_catagory = arrayList_catagory;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CustomCartItemBinding customCartItemBinding = CustomCartItemBinding.inflate(layoutInflater,parent,false);
        return new MyViewHolder(customCartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


//        Glide
//                .with(holder.itemView)
//                .load(arrayList_catagory.get(position).getImage())
//                .centerCrop()
//                .into(holder.binding.image);



    }

    @Override
    public int getItemCount() {
//        return arrayList_catagory.size();

        return 1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomCartItemBinding binding;

        public MyViewHolder(@NonNull CustomCartItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
