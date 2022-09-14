package com.example.erashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erashop.Adapter.AllCatagoryAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.FragmentCategoryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    AllCatagoryAdapter allCatagoryAdapter;
    ArrayList<HomeCatagoryModel> homeCatagoryModels = new ArrayList<>();
    ApiInterface apiInterface;
    Session session;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false);

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(getActivity());

        binding.rvCatagory.setHasFixedSize(true);

        fetch_categories();

        return binding.getRoot();
    }

    public void fetch_categories() {
        ProgressUtils.showLoadingDialog(getContext());
        Call<String> call = apiInterface.fetch_categories();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()!=0){
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                homeCatagoryModels.add(new HomeCatagoryModel(jsonObject.getString("name"),
                                        Utils.Category_images+jsonObject.getString("image"),
                                        jsonObject.getString("id")));
                            }
                            allCatagoryAdapter = new AllCatagoryAdapter(getActivity(),homeCatagoryModels);
                            binding.rvCatagory.setAdapter(allCatagoryAdapter);
                        }else {

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.cancelLoading();
            }
        });

    }
}