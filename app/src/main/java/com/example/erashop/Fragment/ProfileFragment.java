package com.example.erashop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.EditProfile;
import com.example.erashop.Activity.LoginActivity;
import com.example.erashop.Activity.MyAddressActivity;
import com.example.erashop.Activity.MyOrdersActivity;
import com.example.erashop.Activity.WishlistActivity;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.FragmentProfileBinding;
import com.example.erashop.databinding.FragmentWishlistBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    Session session;
    ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false);

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(getContext());
        // Inflate the layout for this fragment

        binding.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
                startActivity(intent);
            }
        });

        binding.wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WishlistActivity.class);
                startActivity(intent);
            }
        });

        binding.myAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyAddressActivity.class);
                startActivity(intent);
            }
        });

        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.remove();
                massage("Logout");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        setImage();

        return binding.getRoot();
    }


    private void setImage() {
        ProgressUtils.showLoadingDialog(getContext());
        Call<String> call = apiInterface.fetch_profile(session.getUser_id());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if(!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){

                            Glide.with(getActivity())
                                    .load(Utils.DocUrl+jsonObject.getString("image"))
                                    .centerCrop()
                                    .placeholder(R.drawable.not_found)
                                    .into(binding.ProfileImg);

                            binding.name.setText(String.format("Hi,%s", jsonObject.getString("name")));
                            binding.mailId.setText(jsonObject.getString("email"));

                        }else{

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                }else{
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void massage(String msg) {
        Snackbar snackbar = Snackbar.make(binding.rel, msg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

}