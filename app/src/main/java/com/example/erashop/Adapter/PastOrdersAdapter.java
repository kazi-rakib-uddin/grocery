package com.example.erashop.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.PastOrdersModel;
import com.example.erashop.R;

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.ViewHolder> {
    Context context;
    PastOrdersModel[] pastOrdersModels;

    public PastOrdersAdapter(Context context, PastOrdersModel[] pastOrdersModels) {
        this.context = context;
        this.pastOrdersModels = pastOrdersModels;
    }

    @NonNull
    @Override
    public PastOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_delivery_orders,parent,false);
        return new PastOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersAdapter.ViewHolder holder, int position) {
        holder.past_order_name.setText(pastOrdersModels[position].getName());
        holder.past_order_qty.setText(pastOrdersModels[position].getQty());
        holder.past_order_image.setImageResource(pastOrdersModels[position].getImage());

    }

    @Override
    public int getItemCount() {
        return pastOrdersModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView past_order_name,past_order_qty;
        ImageView past_order_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            past_order_name = itemView.findViewById(R.id.past_order_name);
            past_order_qty = itemView.findViewById(R.id.past_order_qty);
            past_order_image = itemView.findViewById(R.id.past_order_image);
        }
    }
}
