package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.databinding.CustomAddressItemBinding;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    ArrayList<CategoryModel> arrayList_catagory;
    Context context;

    public AddressAdapter(Context context, ArrayList<CategoryModel> arrayList_catagory) {
        this.arrayList_catagory = arrayList_catagory;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_address_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.txtName.setText(arrayList_catagory.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrayList_catagory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomAddressItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomAddressItemBinding.bind(itemView);
        }
    }
}
