package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.erashop.Adapter.SinglePageImageAdapter;
import com.example.erashop.Adapter.TopBrandAdapter;
import com.example.erashop.Adapter.ViewSlmilarAdapter;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivitySingleProductBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Objects;

public class SingleProduct extends AppCompatActivity {
    ArrayList<CategoryModel> arrayList_top_brand = new ArrayList<>();
    ArrayList<CategoryModel> arrayList_banner_faction = new ArrayList<>();
    ActivitySingleProductBinding binding;
    RecyclerView rv_top_brand;
    BottomSheetDialog bottomSheetDialog;
    RecyclerView rv_view_similar;
    ImageView img_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingleProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.txtDisc.setPaintFlags(binding.txtDisc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        newMobile();
        TopBrand();

        binding.singleBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleProduct.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.rvImageSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                binding.txtCount.setText("0" + String.valueOf(position + 1) + " / " + "0" + SinglePageImageAdapter.arrayList_catagory.size());
            }

            @Override
            public void onPageSelected(int position) {

                //Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        binding.viewSimiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog = new BottomSheetDialog(SingleProduct.this, R.style.BottomSheetDialogTheme);
                View view1 = LayoutInflater.from(SingleProduct.this).inflate(R.layout.bottom_sheet_view_similar,
                        (LinearLayout) view.findViewById(R.id.buttom_sheet_content));

                rv_view_similar = view1.findViewById(R.id.rv_view_similar);
                img_cancel = view1.findViewById(R.id.img_cancel);

                img_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        bottomSheetDialog.dismiss();
                    }
                });

                TopBrand2();

                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();


            }
        });


    }

    private void TopBrand() {
        arrayList_top_brand.add(new CategoryModel("Banana", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"));
        arrayList_top_brand.add(new CategoryModel("Apple", "https://images.unsplash.com/photo-1569870499705-504209102861?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=415&q=80"));
        arrayList_top_brand.add(new CategoryModel("Grape", "https://2rdnmg1qbg403gumla1v9i2h-wpengine.netdna-ssl.com/wp-content/uploads/sites/3/2021/11/Benefitsof-Grapes-1305284794-770x533-1-650x428.jpg"));
        arrayList_top_brand.add(new CategoryModel("Avocado", "https://m.media-amazon.com/images/I/71LUriXVE2L._SL1500_.jpg"));
        arrayList_top_brand.add(new CategoryModel("Banana", "https://www.baamboozle.com/game/366786"));
        arrayList_top_brand.add(new CategoryModel("Apple", "https://images.unsplash.com/photo-1569870499705-504209102861?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=415&q=80"));
        arrayList_top_brand.add(new CategoryModel("Grape", "https://2rdnmg1qbg403gumla1v9i2h-wpengine.netdna-ssl.com/wp-content/uploads/sites/3/2021/11/Benefitsof-Grapes-1305284794-770x533-1-650x428.jpg"));


        binding.rvTopBrand.setAdapter(new TopBrandAdapter(this, arrayList_top_brand));

    }


    private void TopBrand2() {
        arrayList_top_brand.add(new CategoryModel("Banana", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"));
        arrayList_top_brand.add(new CategoryModel("Apple", "https://images.unsplash.com/photo-1569870499705-504209102861?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=415&q=80"));
        arrayList_top_brand.add(new CategoryModel("Grape", "https://2rdnmg1qbg403gumla1v9i2h-wpengine.netdna-ssl.com/wp-content/uploads/sites/3/2021/11/Benefitsof-Grapes-1305284794-770x533-1-650x428.jpg"));
        arrayList_top_brand.add(new CategoryModel("Avocado", "https://m.media-amazon.com/images/I/71LUriXVE2L._SL1500_.jpg"));
        arrayList_top_brand.add(new CategoryModel("Banana", "https://www.baamboozle.com/game/366786"));
        arrayList_top_brand.add(new CategoryModel("Apple", "https://images.unsplash.com/photo-1569870499705-504209102861?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=415&q=80"));
        arrayList_top_brand.add(new CategoryModel("Grape", "https://2rdnmg1qbg403gumla1v9i2h-wpengine.netdna-ssl.com/wp-content/uploads/sites/3/2021/11/Benefitsof-Grapes-1305284794-770x533-1-650x428.jpg"));


        rv_view_similar.setAdapter(new ViewSlmilarAdapter(this, arrayList_top_brand));

    }

    private void newMobile() {
        arrayList_banner_faction.add(new CategoryModel("Banana", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"));
        arrayList_banner_faction.add(new CategoryModel("Apple", "https://images.unsplash.com/photo-1569870499705-504209102861?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=415&q=80"));
        arrayList_banner_faction.add(new CategoryModel("Grape", "https://2rdnmg1qbg403gumla1v9i2h-wpengine.netdna-ssl.com/wp-content/uploads/sites/3/2021/11/Benefitsof-Grapes-1305284794-770x533-1-650x428.jpg"));
        arrayList_banner_faction.add(new CategoryModel("Avocado", "https://m.media-amazon.com/images/I/71LUriXVE2L._SL1500_.jpg"));

        binding.rvImageSlide.setAdapter(new SinglePageImageAdapter(this, arrayList_banner_faction));

    }
}