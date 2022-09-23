package com.example.erashop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erashop.Activity.AddAddessActivity;
import com.example.erashop.Activity.CheckOutActivity;
import com.example.erashop.Activity.MyAddressActivity;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.Model.MyAddressModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.CustomAddressItemBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    ArrayList<MyAddressModel> arrayList_catagory;
    Context context;
    ApiInterface apiInterface;
    Session session;

    public AddressAdapter(Context context, ArrayList<MyAddressModel> arrayList_catagory) {
        this.arrayList_catagory = arrayList_catagory;
        this.context=context;

        session = new Session(context);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_address_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.txtName.setText(arrayList_catagory.get(position).getName());
        holder.binding.type.setText(arrayList_catagory.get(position).getType());
        holder.binding.phone.setText("Phone no: "+arrayList_catagory.get(position).getNumber());
        String houseNO = arrayList_catagory.get(position).getFlatNO();
        String area = arrayList_catagory.get(position).getArea();
        String landmark = arrayList_catagory.get(position).getLandmark();
        String pin_code = arrayList_catagory.get(position).getPin();
        String state = arrayList_catagory.get(position).getState();

        holder.binding.address.setText(houseNO+", "+area+", "+landmark+", "+pin_code+", "+state);

        holder.binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(holder);
            }
        });


        holder.binding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAddessActivity.class);
                context.startActivity(intent);
            }
        });

    }
    private void show(MyViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to remove your address ?");
        builder.setTitle("Remove address ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            RemoveAddress(holder);
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void RemoveAddress(MyViewHolder holder) {
        ProgressUtils.showLoadingDialog(context);
        Call<String> call = apiInterface.remove_address(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            ((Activity)context).finish();
                            context.startActivity(new Intent(context,CheckOutActivity.class));
                        }else {

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{

                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList_catagory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CustomAddressItemBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomAddressItemBinding.bind(itemView);
        }
    }
}
