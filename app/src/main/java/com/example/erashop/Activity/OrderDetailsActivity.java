package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.OrderDetailsAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.OrderDetailsModel;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.ActivityOrderDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    ActivityOrderDetailsBinding binding;

    String invoice_no = "";

    ApiInterface apiInterface;
    Session session;

    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<OrderDetailsModel> orderDetailsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        session = new Session(OrderDetailsActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        invoice_no = getIntent().getExtras().getString("INVNO");

        binding.rvOrderDetails.setLayoutManager(new LinearLayoutManager(this));

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fetch_order_details();

    }

    private void fetch_order_details() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_order_details(invoice_no);
        binding.invoiceNo.setText(invoice_no);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                orderDetailsModels.add(new OrderDetailsModel(
                                        jsonObject.getString("product_id"),
                                        jsonObject.getString("quantity"),
                                        jsonObject.getString("amount"),
                                        jsonObject.getString("date")
                                ));
                            }
                            orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsActivity.this,orderDetailsModels);
                            binding.rvOrderDetails.setAdapter(orderDetailsAdapter);

                        }else{

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(OrderDetailsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{

                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                ProgressUtils.cancelLoading();
            }
        });


    }


}