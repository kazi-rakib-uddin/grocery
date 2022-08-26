package com.example.erashop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erashop.Activity.MyAddressActivity;
import com.example.erashop.Activity.MyOrdersActivity;
import com.example.erashop.Activity.WishlistActivity;
import com.example.erashop.R;
import com.example.erashop.databinding.FragmentProfileBinding;
import com.example.erashop.databinding.FragmentWishlistBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false);
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

        return binding.getRoot();
    }
}