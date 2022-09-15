package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.erashop.Adapter.SinglePageImageAdapter;
import com.example.erashop.Adapter.TopBrandAdapter;
import com.example.erashop.Adapter.ViewSlmilarAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.CategoryModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.ActivitySingleProductBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProduct extends AppCompatActivity {
    ArrayList<CategoryModel> arrayList_top_brand = new ArrayList<>();
    ArrayList<CategoryModel> arrayList_banner_faction = new ArrayList<>();
    ArrayList<CategoryModel> images = new ArrayList<>();
    ActivitySingleProductBinding binding;
    BottomSheetDialog bottomSheetDialog;
    RecyclerView rv_view_similar;
    ImageView img_cancel;

    String isWishlisted = "";

    String[] product_images = new String[]{};

    ApiInterface apiInterface;
    Session session;

    String cat_id = "", sub_cat_id = "", product_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingleProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        session = new Session(SingleProduct.this);
        apiInterface = APIClient.getApiClient().create(ApiInterface.class);


        product_id = getIntent().getStringExtra("product_id");
        cat_id = getIntent().getStringExtra("cat_id");
        sub_cat_id = getIntent().getStringExtra("sub_cat_id");

        fetchProductDetails();
        fetchProductImages();

        binding.txtDisc.setPaintFlags(binding.txtDisc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        newMobile();
        TopBrand();

        binding.singleBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

        binding.IncDec.setVisibility(View.GONE);
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.IncDecText.setText("1");
                binding.addBtn.setVisibility(View.GONE);
                binding.IncDec.setVisibility(View.VISIBLE);
                IncreaseDecrease();
            }
        });

        binding.wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wishlist();
            }
        });


    }

    private void fetchProductDetails() {
        ProgressUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.fetch_single_product_details(product_id, sub_cat_id, cat_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")) {
                            binding.productName.setText(jsonObject.getString("product_name"));
                            binding.quantity.setText(jsonObject.getString("quantity"));
                            binding.txtDisc.setText(String.format("₹%s", jsonObject.getString("original_price")));
                            binding.priceTxt.setText(String.format("₹%s", jsonObject.getString("discounted_price")));
                            binding.discountPercentage.setText(String.format("%s%% Off", jsonObject.getString("discount_percentage")));
                            binding.description.setText(jsonObject.getString("description"));
                        } else {

                        }
                        ProgressUtils.cancelLoading();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SingleProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                } else {

                }
                ProgressUtils.cancelLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SingleProduct.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchProductImages() {
        Call<String> call = apiInterface.fetch_product_images(product_id, sub_cat_id, cat_id);
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
                                images.add(new CategoryModel("", Utils.product_images + jsonObject.getString("image1")));
                                images.add(new CategoryModel("", Utils.product_images + jsonObject.getString("image2")));
                                images.add(new CategoryModel("", Utils.product_images + jsonObject.getString("image3")));
                                images.add(new CategoryModel("", Utils.product_images + jsonObject.getString("image4")));
                            }
                            binding.rvImageSlide.setAdapter(new SinglePageImageAdapter(SingleProduct.this, images));
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SingleProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void IncreaseDecrease() {
        final int[] count = {1};

        binding.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] += 1;
                binding.IncDecText.setText("" + count[0]);
            }
        });

        binding.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] == 1) {
                    binding.addBtn.setVisibility(View.VISIBLE);
                    binding.IncDec.setVisibility(View.GONE);
                } else {
                    count[0] -= 1;
                    binding.IncDecText.setText("" + count[0]);
                }
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

    private void Wishlist() {
        binding.wishList.setImageResource(R.drawable.img_red_wishlist);
    }

    private void checkWishListed(String res){
        if (res.equals("Y")){
            binding.wishList.setImageResource(R.drawable.img_red_wishlist);
        }else{
            binding.wishList.setImageResource(R.drawable.img_wishlist);
        }
    }

}