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

import com.example.erashop.Activity.TrackOrderActivity;
import com.example.erashop.Model.RecentOrdersModel;
import com.example.erashop.R;

public class RecentOrdedrsAdapter extends RecyclerView.Adapter<RecentOrdedrsAdapter.ViewHolder> {
    Context context;
    RecentOrdersModel recentOrdersModel[];

    public RecentOrdedrsAdapter(Context context, RecentOrdersModel[] recentOrdersModel) {
        this.context = context;
        this.recentOrdersModel = recentOrdersModel;
    }

    @NonNull
    @Override
    public RecentOrdedrsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_orders,parent,false);
        return new RecentOrdedrsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdedrsAdapter.ViewHolder holder, int position) {
        holder.recent_order_name.setText(recentOrdersModel[position].getName());
        holder.recent_order_qty.setText(recentOrdersModel[position].getQty());
        holder.recent_order_image.setImageResource(recentOrdersModel[position].getImage());

        holder.track_recent_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TrackOrderActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return recentOrdersModel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recent_order_name,recent_order_qty,track_recent_order;
        ImageView recent_order_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recent_order_name = itemView.findViewById(R.id.recent_order_name);
            recent_order_qty = itemView.findViewById(R.id.recent_order_qty);
            recent_order_image = itemView.findViewById(R.id.recent_order_image);
            track_recent_order = itemView.findViewById(R.id.track_recent_order);
        }
    }
}
