package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.TrackOrderActivity;
import com.example.erashop.Model.RecentOrdersModel;
import com.example.erashop.Activity.OrderDetailsActivity;
import com.example.erashop.databinding.CustomOrdersBinding;

import java.util.ArrayList;

public class RecentOrdedrsAdapter extends RecyclerView.Adapter<RecentOrdedrsAdapter.ViewHolder> {
    Context context;
    ArrayList<RecentOrdersModel> recentOrdersModel;

    public RecentOrdedrsAdapter(Context context, ArrayList<RecentOrdersModel> recentOrdersModel) {
        this.context = context;
        this.recentOrdersModel = recentOrdersModel;
    }

    @NonNull
    @Override
    public RecentOrdedrsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomOrdersBinding binding = CustomOrdersBinding.inflate(inflater,parent,false);
        return new RecentOrdedrsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentOrdedrsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.pendingInvoiceNo.setText("Invoice no: "+recentOrdersModel.get(position).getInvoice_no());
        holder.binding.pendingTotalAmount.setText("Total amount: ₹"+recentOrdersModel.get(position).getTotal_amount());
        holder.binding.paymentType.setText("Payment mode: "+recentOrdersModel.get(position).getPayment_type());
        holder.binding.orderedDate.setText("Order placed on:\n"+recentOrdersModel.get(position).getDate());


        holder.binding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("INVNO",recentOrdersModel.get(position).getInvoice_no());
                context.startActivity(intent);
            }
        });

        holder.binding.trackRecentOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TrackOrderActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return recentOrdersModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomOrdersBinding binding;
        public ViewHolder(@NonNull CustomOrdersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
