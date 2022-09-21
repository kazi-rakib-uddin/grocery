package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.PastOrdersModel;
import com.example.erashop.Activity.OrderDetailsActivity;
import com.example.erashop.databinding.CustomDeliveryOrdersBinding;

import java.util.ArrayList;

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.ViewHolder> {
    Context context;
    ArrayList<PastOrdersModel> pastOrdersModels;

    public PastOrdersAdapter(Context context, ArrayList<PastOrdersModel> pastOrdersModels) {
        this.context = context;
        this.pastOrdersModels = pastOrdersModels;
    }

    @NonNull
    @Override
    public PastOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomDeliveryOrdersBinding binding = CustomDeliveryOrdersBinding.inflate(inflater,parent,false);
        return new PastOrdersAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.binding.pendingInvoiceNo.setText(pastOrdersModels.get(position).getInvoice_no());
//        holder.binding.pendingTotalAmount.setText(pastOrdersModels.get(position).getTotal_amount());
//        holder.binding.paymentType.setText(pastOrdersModels.get(position).getPayment_method());
//        holder.binding.orderedDate.setText(pastOrdersModels.get(position).getDate());

        holder.binding.pendingInvoiceNo.setText("Invoice no: "+pastOrdersModels.get(position).getInvoice_no());
        holder.binding.pendingTotalAmount.setText("Total amount: ₹"+pastOrdersModels.get(position).getTotal_amount());
        holder.binding.paymentType.setText("Payment mode: "+pastOrdersModels.get(position).getPayment_method());
        holder.binding.orderedDate.setText("Order placed on:\n"+pastOrdersModels.get(position).getDate());

        holder.binding.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("INVNO",pastOrdersModels.get(position).getInvoice_no());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pastOrdersModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomDeliveryOrdersBinding binding;
        public ViewHolder(@NonNull CustomDeliveryOrdersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
