package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    SearchModel[] searchModels;
    Context context;

    public SearchAdapter(SearchModel[] searchModels, Context context) {
        this.context = context;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_search_result,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.search_item_name.setText(searchModels[position].getName());
        holder.search_item_OG_price.setText("₹"+searchModels[position].getOG_price());
        holder.search_item_price.setText("₹"+searchModels[position].getPrice());
        holder.search_image.setImageResource(searchModels[position].getImage());

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
        return searchModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView search_image;
        TextView search_item_name,search_item_price,search_item_OG_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_image = itemView.findViewById(R.id.search_image);
            search_item_name = itemView.findViewById(R.id.search_item_name);
            search_item_price = itemView.findViewById(R.id.search_item_price);
            search_item_OG_price = itemView.findViewById(R.id.search_item_OG_price);


        }
    }
}
