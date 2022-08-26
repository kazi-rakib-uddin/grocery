package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Model.MyAddressModel;
import com.example.erashop.R;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    Context context;
    MyAddressModel[] myAddressModels;

    public MyAddressAdapter(Context context, MyAddressModel[] myAddressModels) {
        this.context = context;
        this.myAddressModels = myAddressModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_address_item,parent,false);
        return new MyAddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(myAddressModels[position].getName());
        String address = myAddressModels[position].getFlatNO()+" "+myAddressModels[position].getArea()+" "
            +myAddressModels[position].getLandmark()+" "+myAddressModels[position].getState();
        holder.address.setText(address);
        holder.phone.setText(myAddressModels[position].getNumber());
        holder.type.setText(myAddressModels[position].getType());
    }

    @Override
    public int getItemCount() {
        return myAddressModels.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,phone,type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            type = itemView.findViewById(R.id.type);

        }
    }
}
