package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
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
import com.example.erashop.Model.TrendingOfferModel;
import com.example.erashop.R;
import com.example.erashop.databinding.SingleTrendingOfferBinding;

import java.util.ArrayList;

public class TrendingOfferAdapter extends RecyclerView.Adapter<TrendingOfferAdapter.Holder> {

    ArrayList<TrendingOfferModel> trendingOfferModels = new ArrayList<>();
    Context context;

    public TrendingOfferAdapter(ArrayList<TrendingOfferModel> trendingOfferModels, Context context) {
        this.trendingOfferModels = trendingOfferModels;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleTrendingOfferBinding binding = SingleTrendingOfferBinding.inflate(inflater,parent,false);
        return new TrendingOfferAdapter.Holder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.itemDiscount.setText("Up to "+trendingOfferModels.get(position).getDiscount_percentage()+" % OFF");

        Glide.with(context)
                .load(trendingOfferModels.get(position).getProduct_image())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.image);

        Glide.with(context)
                .load(trendingOfferModels.get(position).getProduct_title_image())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.logoImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                intent.putExtra("product_id",trendingOfferModels.get(position).getId());
                intent.putExtra("cat_id",trendingOfferModels.get(position).getCategory_id());
                intent.putExtra("sub_cat_id",trendingOfferModels.get(position).getSub_category_id());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        int size =0;
        if (trendingOfferModels.size()<6){
            size = trendingOfferModels.size();
        }else{
            size = 6;
        }
        return size;
    }

    public class Holder extends RecyclerView.ViewHolder {
        SingleTrendingOfferBinding binding;
        public Holder(@NonNull SingleTrendingOfferBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
