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
import com.example.erashop.databinding.CustomItemsInCartBinding;

import java.util.ArrayList;

public class CheckOutCartAdapter extends RecyclerView.Adapter<CheckOutCartAdapter.MyViewHolder> {

    ArrayList<CategoryModel> arrayList_catagory;
    Context context;

    public CheckOutCartAdapter(Context context, ArrayList<CategoryModel> arrayList_catagory) {
        this.arrayList_catagory = arrayList_catagory;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_items_in_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.name.setText(arrayList_catagory.get(position).getName());


        Glide
                .with(holder.itemView)
                .load(arrayList_catagory.get(position).getImage())
                .centerCrop()
                .into(holder.binding.image);



    }

    @Override
    public int getItemCount() {
        return arrayList_catagory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomItemsInCartBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomItemsInCartBinding.bind(itemView);

        }
    }
}
