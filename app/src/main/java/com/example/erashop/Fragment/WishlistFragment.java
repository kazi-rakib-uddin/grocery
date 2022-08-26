package com.example.erashop.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erashop.Adapter.WishlistAdapter;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.Model.WishlistModel;
import com.example.erashop.R;
import com.example.erashop.databinding.FragmentWishlistBinding;

import java.util.ArrayList;

public class WishlistFragment extends Fragment {

    FragmentWishlistBinding binding;
    WishlistModel[] wishlistModel;
    WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentWishlistBinding.inflate(layoutInflater,container,false);

        wishlistModel = new WishlistModel[]{
                new WishlistModel("Banana","1","14","28","50",R.drawable.banana),
                new WishlistModel("Banana","1","14","28","50",R.drawable.apple),
                new WishlistModel("Banana","1","14","28","50",R.drawable.graps),
                new WishlistModel("Banana","1","14","28","50",R.drawable.banana),
                new WishlistModel("Banana","1","14","28","50",R.drawable.apple),
                new WishlistModel("Banana","1","14","28","50",R.drawable.graps),
                new WishlistModel("Banana","1","14","28","50",R.drawable.banana)
        };

        wishlistAdapter = new WishlistAdapter(getActivity(),wishlistModel);
        binding.rvWishlist.setHasFixedSize(true);
        binding.rvWishlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvWishlist.setAdapter(wishlistAdapter);



        return binding.getRoot();
    }
}