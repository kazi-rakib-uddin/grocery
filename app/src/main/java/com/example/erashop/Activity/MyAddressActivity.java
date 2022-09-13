package com.example.erashop.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.MyAddressAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.MyAddressModel;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.ActivityMyAddressBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAddressActivity extends AppCompatActivity {

    ActivityMyAddressBinding binding;
    ApiInterface apiInterface;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        session = new Session(MyAddressActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        binding.addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAddressActivity.this,AddAddessActivity.class);
                startActivity(intent);
            }
        });

        binding.addressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        GetMyAddress();

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAddressActivity.this,AddAddessActivity.class));
            }
        });

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

    }

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyAddressActivity.this);
        builder.setMessage("Do you want to remove your address ?");
        builder.setTitle("Remove address ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            RemoveAddress();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void RemoveAddress() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.remove_address(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            finish();
                            startActivity(new Intent(MyAddressActivity.this,MyAddressActivity.class));
                            binding.addAddress.setVisibility(View.VISIBLE);
                        }else {

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MyAddressActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    private void GetMyAddress() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.get_address(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            binding.txtName.setText(jsonObject.getString("full_name"));
                            binding.type.setText(jsonObject.getString("address_type"));
                            binding.phone.setText(String.format("Phone No: %s", jsonObject.getString("phone_number")));

                            String houseNO = jsonObject.getString("house_no");
                            String area = jsonObject.getString("area");
                            String landmark = jsonObject.getString("landmark");
                            String pin_code = jsonObject.getString("pin_code");
                            String state = jsonObject.getString("state");

                            binding.AddressCard.setVisibility(View.VISIBLE);

                            binding.address.setText(houseNO+","+area+","+landmark+","+pin_code+","+state);
                            ProgressUtils.cancelLoading();
                        }else {
                            binding.addAddress.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MyAddressActivity.this, "No address found", Toast.LENGTH_SHORT).show();
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
}