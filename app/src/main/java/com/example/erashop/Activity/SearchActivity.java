package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.SearchAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.ActivitySearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    SearchAdapter searchAdapter;
    ArrayList<SearchModel> searchModels = new ArrayList<>();

    ApiInterface apiInterface;
    Session session;

    String cat_id, sub_cat_id,source="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        session = new Session(SearchActivity.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        cat_id = getIntent().getStringExtra("cat_id");
        sub_cat_id = getIntent().getStringExtra("sub_cat_id");


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rvSearch.setLayoutManager(layoutManager);

        source = getIntent().getStringExtra("source");



        if (source.equals("from_sub_Cat")){
            ShowProductList();
        }else if(source.equals("from_home_fragment")){
            binding.lottie.setVisibility(View.GONE);
            binding.lottieTXT.setVisibility(View.GONE);
        }else{
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    private void ShowProductList() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_product_by_sub_category(cat_id, sub_cat_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")) {
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() != 0) {
                            binding.lottie.setVisibility(View.GONE);
                            binding.lottieTXT.setVisibility(View.GONE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                searchModels.add(new SearchModel(
                                        cat_id,sub_cat_id,
                                        jsonObject.getString("id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("discounted_price"),
                                        jsonObject.getString("original_price"),
                                        Utils.product_images+jsonObject.getString("image1"),
                                        jsonObject.getString("discount_percentage")

                                ));
                            }
                            searchAdapter = new SearchAdapter(searchModels, SearchActivity.this);
                            binding.rvSearch.setAdapter(searchAdapter);
                            ProgressUtils.cancelLoading();
                        } else {
                            ProgressUtils.cancelLoading();
                            binding.rvSearch.setVisibility(View.GONE);
//                            binding.searchBar.setVisibility(View.GONE);
                            binding.searchEdittext.setVisibility(View.GONE);
                            Toast.makeText(SearchActivity.this, "not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SearchActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                        Toast.makeText(SearchActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.cancelLoading();
            }
        });

    }


}