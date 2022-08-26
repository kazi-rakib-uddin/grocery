package com.example.erashop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    Context context;
    WishlistModel wishlistModel[];

    public WishlistAdapter(Context context, WishlistModel[] wishlistModel) {
        this.context = context;
        this.wishlistModel = wishlistModel;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_wish_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {
        holder.wishlist_image.setImageResource(wishlistModel[position].getImage());
        holder.item_name.setText(wishlistModel[position].getItem_name());
        holder.item_price.setText("₹"+wishlistModel[position].getItem_price());
        holder.item_OG_price.setText("₹"+wishlistModel[position].getItem_OG_price());
        holder.item_size.setText(wishlistModel[position].getItem_size()+"kg");
        holder.item_discount.setText(wishlistModel[position].getItem_discount()+"%");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SingleProduct.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return wishlistModel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_size,item_price,item_OG_price,item_discount;
        ImageView wishlist_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_image = itemView.findViewById(R.id.image);
            item_name = itemView.findViewById(R.id.item_name);
            item_size = itemView.findViewById(R.id.item_size);
            item_price = itemView.findViewById(R.id.item_price);
            item_OG_price = itemView.findViewById(R.id.item_OG_price);
            item_OG_price.setPaintFlags(item_OG_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            item_discount = itemView.findViewById(R.id.item_discount);

        }
    }
}
