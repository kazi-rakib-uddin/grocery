package com.example.erashop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.erashop.Activity.EditProfile;
import com.example.erashop.Activity.LoginActivity;
import com.example.erashop.Activity.MyAddressActivity;
import com.example.erashop.Activity.MyOrdersActivity;
import com.example.erashop.Activity.WishlistActivity;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.databinding.FragmentProfileBinding;
import com.example.erashop.databinding.FragmentWishlistBinding;
import com.google.android.material.snackbar.Snackbar;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    Session session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false);

        session = new Session(getActivity());
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

        return binding.getRoot();
    }

    private void massage(String msg) {
        Snackbar snackbar = Snackbar.make(binding.rel, msg, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

}