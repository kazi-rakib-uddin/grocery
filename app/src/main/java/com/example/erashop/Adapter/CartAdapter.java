package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.CartActivity;
import com.example.erashop.Activity.MainActivity;
import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CartModel;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.CustomBannerBinding;
import com.example.erashop.databinding.CustomCartItemBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    ArrayList<CartModel> cartModels;
    Context context;
    String quantity = "";
    String original_price = "";
    String price = "";
//    String cat_id = "", sub_cat_id = "", product_id = "";

    ApiInterface apiInterface;
    Session session;


    public CartAdapter(ArrayList<CartModel> cartModels, Context context) {
        this.cartModels = cartModels;
        this.context = context;

        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CustomCartItemBinding customCartItemBinding = CustomCartItemBinding.inflate(layoutInflater, parent, false);
        return new MyViewHolder(customCartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        fetch_cart_items(holder, position);
        IncreaseDecrease(holder,position);

        holder.binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressUtils.showLoadingDialog(context);

                Intent intent = new Intent(context, SingleProduct.class);

                Call<String> call = apiInterface.fetch_product_by_product_id(cartModels.get(position).getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)) {
                            try {
                                JSONObject jsonObject = new JSONObject(res);

                                intent.putExtra("cat_id",jsonObject.getString("category_id"));
                                intent.putExtra("sub_cat_id",jsonObject.getString("sub_category_id"));
                                intent.putExtra("product_id",jsonObject.getString("id"));
                                context.startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

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
        return cartModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomCartItemBinding binding;

        public MyViewHolder(@NonNull CustomCartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void fetch_cart_items(MyViewHolder holder, int position) {
        Call<String> call = apiInterface.fetch_product_by_product_id(cartModels.get(position).getId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        holder.binding.name.setText(jsonObject.getString("product_name"));
                        holder.binding.quantity.setText(jsonObject.getString("quantity"));

                        price = jsonObject.getString("discounted_price");
                        holder.binding.price.setText("₹" + price);

                        holder.binding.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        original_price = jsonObject.getString("original_price");
                        holder.binding.originalPrice.setText("₹" + original_price);

                        holder.binding.discountPercentage.setText(jsonObject.getString("discount_percentage") + "% Off");
                        quantity = cartModels.get(position).getQuantity();
                        holder.binding.IncDecText.setText(cartModels.get(position).getQuantity());

                        Glide.with(context)
                                .load(Utils.product_images + jsonObject.getString("image1"))
                                .centerCrop()
                                .placeholder(R.drawable.not_found)
                                .into(holder.binding.image);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void IncreaseDecrease(MyViewHolder holder, int position) {
        holder.binding.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<String> call = apiInterface.cart_quantity_increase(
                        cartModels.get(position).getId(),
                        session.getUser_id(),
                        price,""
                );

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    Toast.makeText(context, "Increase", Toast.LENGTH_SHORT).show();
                                    ((Activity)context).finish();
                                    context.startActivity(new Intent(context, CartActivity.class));
                                    fetch_cart();
                                }else if (jsonObject.getString("rec").equals("2")){
                                    Toast.makeText(context, "Not increased", Toast.LENGTH_SHORT).show();
                                    fetch_cart();
                                }else{
                                    Toast.makeText(context, "Can't increase", Toast.LENGTH_SHORT).show();
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

        holder.binding.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<String> call = apiInterface.cart_quantity_decrease(
                        cartModels.get(position).getId(),
                        session.getUser_id(),
                        price,""
                );
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
                                    ((Activity)context).finish();
                                    fetch_cart();
                                    context.startActivity(new Intent(context, CartActivity.class));
                                }else if (jsonObject.getString("rec").equals("2")){
                                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    fetch_cart();
                                }else if (jsonObject.getString("rec").equals("3")){
                                    Toast.makeText(context, "Can't decrease", Toast.LENGTH_SHORT).show();
                                    fetch_cart();
                                }else{
                                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
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


    private void fetch_cart(){
        Call<String> call = apiInterface.fetch_cart(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        int size = jsonArray.length();
                        if (size > 0){
                            MainActivity.binding.viewCart.setVisibility(View.VISIBLE);
                            MainActivity.binding.itemNo.setText(String.format("%s items", String.valueOf(size)));
                            int price=0;
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                price = price+Integer.valueOf(jsonObject.getString("price"));
                            }
                            MainActivity.binding.price.setText("₹"+String.valueOf(price));
                            MainActivity.binding.itemNo.setText(jsonArray.length()+" items");
                        }else{
                            MainActivity.binding.viewCart.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

}
