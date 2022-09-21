package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.PastOrdersAdapter;
import com.example.erashop.Adapter.RecentOrdedrsAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.PastOrdersModel;
import com.example.erashop.Model.RecentOrdersModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.databinding.ActivityMyOrdersBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    ArrayList<RecentOrdersModel> recentOrdersModels = new ArrayList<>();
    RecentOrdedrsAdapter recentOrdedrsAdapter;

    ArrayList<PastOrdersModel> pastOrdersModels = new ArrayList<>();
    PastOrdersAdapter pastOrdersAdapter;

    ActivityMyOrdersBinding binding;

    ApiInterface apiInterface;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        session = new Session(MyOrdersActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        back();
        recent();
        past();

    }

    private void back() {
        binding.myorderBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                onBackPressed();
            }
        });
    }


    private void past() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_delivered_order(session.getUser_id());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                try {
                    JSONArray jsonArray = new JSONArray(res);

                    if (jsonArray.length()>0){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            pastOrdersModels.add(new PastOrdersModel(
                                    jsonObject.getString("invoice_no"),
                                    jsonObject.getString("total_amount"),
                                    jsonObject.getString("date"),
                                    jsonObject.getString("payment_method")
                            ));
                        }
                        pastOrdersAdapter = new PastOrdersAdapter(MyOrdersActivity.this, pastOrdersModels);
                        binding.rvDeliveredOrder.setHasFixedSize(true);
                        binding.rvDeliveredOrder.setAdapter(pastOrdersAdapter);

                    }else{
                        binding.rvDeliveredOrder.setVisibility(View.GONE);
                        binding.txt2.setVisibility(View.GONE);
                    }
                    ProgressUtils.cancelLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyOrdersActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.cancelLoading();
                Toast.makeText(MyOrdersActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void recent() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_pending_order(session.getUser_id());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                try {
                    JSONArray jsonArray = new JSONArray(res);

                    if (jsonArray.length()>0){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            recentOrdersModels.add(new RecentOrdersModel(
                                    jsonObject.getString("invoice_no"),
                                    jsonObject.getString("total_amount"),
                                    jsonObject.getString("date"),
                                    jsonObject.getString("payment_method")
                            ));
                        }
                        recentOrdedrsAdapter = new RecentOrdedrsAdapter(MyOrdersActivity.this, recentOrdersModels);
                        binding.rvRecentOrder.setHasFixedSize(true);
                        binding.rvRecentOrder.setAdapter(recentOrdedrsAdapter);
                    }else{
                        binding.rvRecentOrder.setVisibility(View.GONE);
                        binding.txt1.setVisibility(View.GONE);
                    }
                    ProgressUtils.cancelLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyOrdersActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.cancelLoading();
                Toast.makeText(MyOrdersActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}