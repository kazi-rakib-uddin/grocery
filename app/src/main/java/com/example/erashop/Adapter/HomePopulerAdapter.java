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
import com.example.erashop.Fragment.ProfileFragment;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.SinglePopulerItemBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePopulerAdapter extends RecyclerView.Adapter<HomePopulerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SearchModel> searchModels=new ArrayList<>();
    ApiInterface apiInterface;
    Session session;
    MainActivity mainActivity = new MainActivity();

    public HomePopulerAdapter(Context context, ArrayList<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;
        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        SinglePopulerItemBinding binding = SinglePopulerItemBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.name.setText(searchModels.get(position).getName());
        holder.binding.originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.originalPrice.setText("₹"+searchModels.get(position).getOG_price());
        holder.binding.discountedPrice.setText("₹"+searchModels.get(position).getPrice());
        holder.binding.discountPercentage.setText(searchModels.get(position).getDiscount()+"% OFF");

//        holder.binding.image.setImageResource(searchModels.get(position).getImage());
        Glide.with(context)
                .load(searchModels.get(position).getImage())
                .centerCrop()
                .placeholder(R.drawable.not_found)
                .into(holder.binding.image);


        holder.binding.IncDec.setVisibility(View.GONE);

        holder.binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressUtils.showLoadingDialog(context);
//                holder.binding.IncDecText.setText("1");
                holder.binding.addBtn.setVisibility(View.GONE);
                holder.binding.IncDec.setVisibility(View.VISIBLE);
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
                                    holder.binding.IncDecText.setText("1");
                                    fetch_cart(holder);
                                    ProgressUtils.cancelLoading();
                                }else if (jsonObject.getString("rec").equals("2")){
                                    Toast.makeText(context, "Can't add to cart", Toast.LENGTH_SHORT).show();
                                    ProgressUtils.cancelLoading();
                                }else{
                                    Toast.makeText(context, "Already exists", Toast.LENGTH_SHORT).show();
                                    products_no_on_cart(holder,position);
                                    ProgressUtils.cancelLoading();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                ProgressUtils.cancelLoading();
                            }
                        }else{
                            ProgressUtils.cancelLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        ProgressUtils.cancelLoading();
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                IncreaseDecrease(holder,position);

            }
        });

        holder.binding.image.setOnClickListener(new View.OnClickListener() {
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
        if (searchModels.size()<3){
            size = searchModels.size();
        }else{
            size = 3;
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        
        SinglePopulerItemBinding binding;
        public MyViewHolder(@NonNull SinglePopulerItemBinding binding) {
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
//                                    Toast.makeText(context, "Increase", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
                                }else if (jsonObject.getString("rec").equals("2")){
//                                    Toast.makeText(context, "Not increased", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
                                }else{
//                                    Toast.makeText(context, "Can't increase", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
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
//                                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
                                }else if (jsonObject.getString("rec").equals("2")){
//                                    Toast.makeText(context, "Decreased", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
                                }else if (jsonObject.getString("rec").equals("3")){
//                                    Toast.makeText(context, "Can't decrease", Toast.LENGTH_SHORT).show();
                                    fetch_cart(holder);
                                }else{
//                                    Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
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

    private void fetch_cart(MyViewHolder holder){
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
//                                holder.binding.IncDecText.setText(jsonObject.getString("quantity"));
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

    private void products_no_on_cart(MyViewHolder holder, int position){
        Call<String> call = apiInterface.fetch_cart(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() > 0){
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (searchModels.get(position).getProduct_id().equals(jsonObject.getString("product_id")) &&
                                        session.getUser_id().equals(jsonObject.getString("user_id"))){
                                    holder.binding.IncDecText.setText(jsonObject.getString("quantity"));
                                }else{
//                                    holder.binding.IncDecText.setText("1");
                                }
                            }

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
