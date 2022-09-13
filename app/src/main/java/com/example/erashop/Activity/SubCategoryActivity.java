package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.erashop.Adapter.AllCatagoryAdapter;
import com.example.erashop.Adapter.SubCategoryAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.SubCategoryModel;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.ActivitySubCategoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {
    ActivitySubCategoryBinding binding;
    ApiInterface apiInterface;
    Session session;

    ArrayList<SubCategoryModel> subCategoryModels = new ArrayList<>();
    SubCategoryAdapter subCategoryAdapter;

    String head_txt, Category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(SubCategoryActivity.this);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        head_txt = intent.getStringExtra("cat_name");
        Category_id = intent.getStringExtra("cat_id");


        binding.headTxt.setText(head_txt);

        binding.BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ShowSubCategory();

    }

    private void ShowSubCategory() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_sub_categories(Category_id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")) {
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() != 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                subCategoryModels.add(new SubCategoryModel(
                                        jsonObject.getString("sub_category_name"),
                                        Utils.sub_category_images + jsonObject.getString("image"),
                                        jsonObject.getString("id"),Category_id
                                ));
                            }
                            subCategoryAdapter = new SubCategoryAdapter(subCategoryModels,SubCategoryActivity.this);
                            binding.rvSubCatagory.setAdapter(subCategoryAdapter);
                        } else {

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SubCategoryActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}