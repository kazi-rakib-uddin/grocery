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
import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.CustomSearchResultBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ArrayList<SearchModel> searchModels = new ArrayList<>();
    Context context;
    ApiInterface apiInterface;
    Session session;

    public SearchAdapter(ArrayList<SearchModel> searchModels, Context context) {
        this.context = context;
        this.searchModels = searchModels;
        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomSearchResultBinding binding = CustomSearchResultBinding.inflate(inflater,parent,false);
        return new SearchAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.searchItemName.setText(searchModels.get(position).getName());
        holder.binding.searchItemOGPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.searchItemOGPrice.setText(String.format("₹%s", searchModels.get(position).getOG_price()));
        holder.binding.searchItemPrice.setText(String.format("₹%s", searchModels.get(position).getPrice()));
//        holder.binding.searchImage.setImageResource(Integer.parseInt(searchModels.get(position).getImage()));
        Glide.with(context)
                .load(searchModels.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.searchImage);

        holder.binding.discountPercentage.setText(String.format("%s%% Off", searchModels.get(position).getDiscount()));

        holder.binding.IncDec.setVisibility(View.GONE);

        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.binding.IncDecText.setText("1");
                holder.binding.addBtn.setVisibility(View.GONE);
                holder.binding.IncDec.setVisibility(View.VISIBLE);
                IncreaseDecrease(holder,position);

                Call<String> call = apiInterface.add_to_cart(
                        searchModels.get(position).getProduct_id(),
                        session.getUser_id(),
                        searchModels.get(position).getPrice(),
                        searchModels.get(position).getQuantity()
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
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleProduct.class);
                intent.putExtra("product_id",searchModels.get(position).getProduct_id());
                intent.putExtra("cat_id",searchModels.get(position).getCat_id());
                intent.putExtra("sub_cat_id",searchModels.get(position).getSub_cat_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int size =0;
        if (searchModels.size()<4){
            size = searchModels.size();
        }else{
            size = 4;
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CustomSearchResultBinding binding;


        public ViewHolder(@NonNull CustomSearchResultBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
//            search_image = itemView.findViewById(R.id.search_image);
//            search_item_name = itemView.findViewById(R.id.search_item_name);
//            search_item_price = itemView.findViewById(R.id.search_item_price);
//            search_item_OG_price = itemView.findViewById(R.id.search_item_OG_price);


        }
    }

    private void IncreaseDecrease(ViewHolder holder, int position) {
        final int[] count = {1};

        holder.binding.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] += 1;
                holder.binding.IncDecText.setText(""+ count[0]);
                Increase(position);
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

                Decrease(position);

            }
        });

    }

    private void Increase(int position){
        Call<String> call = apiInterface.cart_quantity_increase(
                searchModels.get(position).getProduct_id(),
                session.getUser_id(),
                searchModels.get(position).getPrice(),
                searchModels.get(position).getQuantity()
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
                        }else if (jsonObject.getString("rec").equals("2")){
                            Toast.makeText(context, "Not increased", Toast.LENGTH_SHORT).show();
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

    private void Decrease(int position){
        Call<String> call = apiInterface.cart_quantity_decrease(
                searchModels.get(position).getProduct_id(),
                session.getUser_id(),
                searchModels.get(position).getPrice(),
                searchModels.get(position).getQuantity()
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
                        }else if (jsonObject.getString("rec").equals("2")){
                            Toast.makeText(context, "Not decreased", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Can't decrease", Toast.LENGTH_SHORT).show();
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

}
