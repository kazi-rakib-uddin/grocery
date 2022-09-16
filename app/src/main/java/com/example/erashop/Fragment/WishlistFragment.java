package com.example.erashop.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.erashop.Activity.SingleProduct;
import com.example.erashop.Adapter.WishlistAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.FragmentWishlistBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {

    FragmentWishlistBinding binding;
    ArrayList<WishlistModel> wishlistModel = new ArrayList<>();
    WishlistAdapter wishlistAdapter;

    ApiInterface apiInterface;
    Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentWishlistBinding.inflate(layoutInflater,container,false);

        session = new Session(getContext());
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);

        GetWishlist();

        binding.rvWishlist.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvWishlist.setHasFixedSize(true);


        return binding.getRoot();
    }

    private void GetWishlist() {
        ProgressUtils.showLoadingDialog(getContext());
        Call<String> call = apiInterface.fetch_wishlist_by_user_id(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if(jsonArray.length()!=0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                fetchProductDetails(jsonObject.getString("product_id"));
                            }
                        }else{
                            Toast.makeText(getContext(), "Blank wishlist", Toast.LENGTH_SHORT).show();
                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    private void fetchProductDetails(String product_id) {
        Call<String> call = apiInterface.fetch_product_by_product_id(product_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals(null)){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.length()!=0){
                            wishlistModel.add(new WishlistModel(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("category_id"),
                                    jsonObject.getString("sub_category_id"),
                                    jsonObject.getString("product_name"),
                                    jsonObject.getString("quantity"),
                                    jsonObject.getString("discounted_price"),
                                    jsonObject.getString("original_price"),
                                    jsonObject.getString("discount_percentage"),
                                    Utils.product_images+jsonObject.getString("image1"),
                                    jsonObject.getString("quantity")
                                    ));

                            wishlistAdapter = new WishlistAdapter(getContext(),wishlistModel);
                            binding.rvWishlist.setAdapter(wishlistAdapter);

                        }else{

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