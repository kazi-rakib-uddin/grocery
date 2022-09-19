package com.example.erashop.Adapter;

import android.annotation.SuppressLint;
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
import com.example.erashop.Activity.MainActivity;
import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.FreshFruitsModel;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.ActivityMainBinding;
import com.example.erashop.databinding.SinglePopulerItemBinding;
import com.example.erashop.databinding.SingleRecomendedItemBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRecomendedAdapter extends RecyclerView.Adapter<HomeRecomendedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<FreshFruitsModel> freshFruitsModels;
    ApiInterface apiInterface;
    Session session;

    public HomeRecomendedAdapter(Context context, ArrayList<FreshFruitsModel> freshFruitsModels) {
        this.context = context;
        this.freshFruitsModels = freshFruitsModels;
        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SingleRecomendedItemBinding binding = SingleRecomendedItemBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(freshFruitsModels.get(position).getName());
        holder.binding.discountPercentage.setText(freshFruitsModels.get(position).getDiscount()+"% OFF");
        holder.binding.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.originalPrice.setText("₹"+freshFruitsModels.get(position).getOG_price());
        holder.binding.discountedPrice.setText("₹"+freshFruitsModels.get(position).getPrice());

        Glide.with(context)
                .load(freshFruitsModels.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.image);
        holder.binding.IncDec.setVisibility(View.GONE);

        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.IncDecText.setText("1");
                holder.binding.addBtn.setVisibility(View.GONE);
                holder.binding.IncDec.setVisibility(View.VISIBLE);

                Call<String> call = apiInterface.add_to_cart(
                        freshFruitsModels.get(position).getProduct_id(),
                        session.getUser_id(),
                        freshFruitsModels.get(position).getPrice(),
                        freshFruitsModels.get(position).getQuantity()
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
                                    fetch_cart();
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
                IncreaseDecrease(holder,position);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                intent.putExtra("product_id",freshFruitsModels.get(position).getProduct_id());
                intent.putExtra("cat_id",freshFruitsModels.get(position).getCat_id());
                intent.putExtra("sub_cat_id",freshFruitsModels.get(position).getSub_cat_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return freshFruitsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SingleRecomendedItemBinding binding;
        public MyViewHolder(@NonNull SingleRecomendedItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    private void IncreaseDecrease(MyViewHolder holder, int position) {
        final int[] count = {1};

        holder.binding.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] += 1;
                holder.binding.IncDecText.setText(""+ count[0]);
                Call<String> call = apiInterface.cart_quantity_increase(
                        freshFruitsModels.get(position).getProduct_id(),
                        session.getUser_id(),
                        freshFruitsModels.get(position).getPrice(),
                        freshFruitsModels.get(position).getQuantity()
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
                if (count[0] == 1){
                    holder.binding.addBtn.setVisibility(View.VISIBLE);
                    holder.binding.IncDec.setVisibility(View.GONE);
                }else{
                    count[0] -= 1;
                    holder.binding.IncDecText.setText(""+ count[0]);
                }
                Call<String> call = apiInterface.cart_quantity_decrease(
                        freshFruitsModels.get(position).getProduct_id(),
                        session.getUser_id(),
                        freshFruitsModels.get(position).getPrice(),
                        freshFruitsModels.get(position).getQuantity()
                );

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        if (!res.equals(null)){
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                if (jsonObject.getString("rec").equals("1")){
                                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    fetch_cart();
                                }else if (jsonObject.getString("rec").equals("2")){
                                    Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
                                    fetch_cart();
                                }else if (jsonObject.getString("rec").equals("3")){
                                    Toast.makeText(context, "Can't decrease", Toast.LENGTH_SHORT).show();
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
