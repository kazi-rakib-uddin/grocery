package com.example.erashop.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erashop.Adapter.AllCatagoryAdapter;
import com.example.erashop.R;
import com.example.erashop.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    AllCatagoryAdapter allCatagoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false);

        allCatagoryAdapter = new AllCatagoryAdapter();
        binding.rvCatagory.setAdapter(allCatagoryAdapter);

        return binding.getRoot();
    }
}