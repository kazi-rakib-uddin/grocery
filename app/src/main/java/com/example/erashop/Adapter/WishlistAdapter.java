package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    Context context;
    ArrayList<WishlistModel> wishlistModel=new ArrayList<>();
    Session session;
    ApiInterface apiInterface;

    public WishlistAdapter(Context context,ArrayList<WishlistModel> wishlistModel) {
        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
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
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.wishlist_image.setImageResource(wishlistModel.get(position).getImage());
        Glide.with(context)
                .load(wishlistModel.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.wishlist_image);

        holder.item_name.setText(wishlistModel.get(position).getItem_name());
        holder.item_price.setText("₹"+wishlistModel.get(position).getItem_price());
        holder.item_OG_price.setText("₹"+wishlistModel.get(position).getItem_OG_price());
        holder.item_size.setText(wishlistModel.get(position).getItem_size());
        holder.item_discount.setText(wishlistModel.get(position).getItem_discount()+"%");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                intent.putExtra("product_id",wishlistModel.get(position).getId());
                intent.putExtra("cat_id",wishlistModel.get(position).getCategory_id());
                intent.putExtra("sub_cat_id",wishlistModel.get(position).getSub_category_id());
                context.startActivity(intent);
            }
        });

        holder.removeWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressUtils.showLoadingDialog(context);
                Call<String> call = apiInterface.delete_from_wishlist(session.getUser_id(),wishlistModel.get(position).getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    ProgressUtils.cancelLoading();
                                }else{
                                    Toast.makeText(context, "Can't be removed", Toast.LENGTH_SHORT).show();
                                    ProgressUtils.cancelLoading();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                ProgressUtils.cancelLoading();
                            }
                        }else{
                            Toast.makeText(context, "not found", Toast.LENGTH_SHORT).show();
                            ProgressUtils.cancelLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                });
            }
        });

        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call = apiInterface.add_to_cart(
                        wishlistModel.get(position).getId(),
                        session.getUser_id(),
                        wishlistModel.get(position).getItem_price(),
                        wishlistModel.get(position).getQuantity()
                );
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                                }else if (jsonObject.getString("rec").equals("2")){
                                    Toast.makeText(context, "Can't add to cart", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Already exists", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return wishlistModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_size,item_price,item_OG_price,item_discount;
        ImageView wishlist_image;
        LinearLayout removeWishlist,add_to_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_image = itemView.findViewById(R.id.image);
            item_name = itemView.findViewById(R.id.item_name);
            item_size = itemView.findViewById(R.id.item_size);
            item_price = itemView.findViewById(R.id.item_price);
            item_OG_price = itemView.findViewById(R.id.item_OG_price);
            item_OG_price.setPaintFlags(item_OG_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            item_discount = itemView.findViewById(R.id.item_discount);
            removeWishlist = itemView.findViewById(R.id.removeWishlist);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);

        }
    }
}
