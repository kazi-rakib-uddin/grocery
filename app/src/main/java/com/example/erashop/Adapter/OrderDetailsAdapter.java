package com.example.erashop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.OrderDetailsModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.CustomOrderDetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.Viewholder> {

    Context context;
    ArrayList<OrderDetailsModel> orderDetailsModels;
    ApiInterface apiInterface;
    Session session;

    public OrderDetailsAdapter(Context context, ArrayList<OrderDetailsModel> orderDetailsModels) {
        this.context = context;
        this.orderDetailsModels = orderDetailsModels;
        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        CustomOrderDetailsBinding binding = CustomOrderDetailsBinding.inflate(inflater,parent,false);
        return new OrderDetailsAdapter.Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.binding.orderedDate.setText("Ordered date:"+orderDetailsModels.get(position).getDate());
        holder.binding.quantity.setText("Qty: "+orderDetailsModels.get(position).getQuantity());
        holder.binding.amount.setText("Price: "+orderDetailsModels.get(position).getAmount());


        Call<String> call = apiInterface.fetch_product_by_product_id(orderDetailsModels.get(position).getProduct_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            holder.binding.name.setText(jsonObject.getString("product_name"));

                            Glide.with(context)
                                    .load(Utils.product_images+jsonObject.getString("image1"))
                                    .centerCrop()
                                    .placeholder(R.drawable.not_found)
                                    .into(holder.binding.image);
                        }else{
                            Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDetailsModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        CustomOrderDetailsBinding binding;
        public Viewholder(@NonNull CustomOrderDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
