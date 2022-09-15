package com.example.erashop.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.erashop.Activity.SearchActivity;
import com.example.erashop.Adapter.AllCatagoryAdapter;
import com.example.erashop.Adapter.AutoImageSliderAdapter;
import com.example.erashop.Adapter.BottomBannerAdapter;
import com.example.erashop.Adapter.TrendingOfferAdapter;
import com.example.erashop.Model.BottomBannerModel;
import com.example.erashop.Adapter.HomeBannerAdapter;
import com.example.erashop.Adapter.HomePopulerAdapter;
import com.example.erashop.Adapter.HomeRecomendedAdapter;
import com.example.erashop.Adapter.HomeTrendingOfferAdapter;
import com.example.erashop.ApiClient.APIClient;
import com.example.erashop.ApiInterface.ApiInterface;
import com.example.erashop.Model.FreshFruitsModel;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.Model.SearchModel;
import com.example.erashop.Model.TopBannerModel;
import com.example.erashop.Model.TrendingOfferModel;
import com.example.erashop.R;
import com.example.erashop.Session.Session;
import com.example.erashop.Utils.ProgressUtils;
import com.example.erashop.Utils.Utils;
import com.example.erashop.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
//    private static String[] top_banner_images = new String[] {};
//    String[] banimage = new String[]{};

    ArrayList<TopBannerModel> topBannerModels = new ArrayList<>();
    ArrayList<FreshFruitsModel> freshFruitsModels = new ArrayList<>();
    ArrayList<FreshFruitsModel> freshFruitsModels2 = new ArrayList<>();

    private static String[] array_banner = new String[] {
            "https://trivenisupermarket.com/img/triveni-indian-supermarket-coming-soon.jpg",
            "https://marcado.in/images/ffff.jpg",
            "https://trivenisupermarket.com/img/what-can-you-get.jpeg"
    };

    private AutoImageSliderAdapter adapter;
    private TextView[] mDots;
//    private Runnable runnable = null;
//    private Handler handler = new Handler();
//    private HomeCatagoryAdapter homeCatagoryAdapter;
    private HomePopulerAdapter homePopulerAdapter;
    private HomeBannerAdapter homeBannerAdapter;
    private HomeRecomendedAdapter homeRecomendedAdapter;
    private HomeRecomendedAdapter homeRecomendedAdapter2;
//    private HomeRecomendedAdapter homeRecomendedAdapter_oil;
    private ArrayList<HomeTrendingOfferAdapter> homeTrendingOfferAdapter=new ArrayList<>();

    ArrayList<BottomBannerModel> bottomBannerModels = new ArrayList<>();
    BottomBannerAdapter bottomBannerAdapter;

    ArrayList<TrendingOfferModel> trendingOfferModels = new ArrayList<>();
    TrendingOfferAdapter trendingOfferAdapter;


//    SearchAdapter searchAdapter;
    ArrayList<SearchModel> searchModels=new ArrayList<>();
    ArrayList<HomeCatagoryModel> TopCategory=new ArrayList<>();


    AllCatagoryAdapter allCatagoryAdapter;

    String cat_id, sub_cat_id;

//    private List<HomeCatagoryModel> homeCatagoryModels;
//    private ArrayList<HomeCatagoryModel> homeItemModels=new ArrayList<>();
//    private ArrayList<HomeCatagoryModel> homeFreshFoodModel=new ArrayList<>();
//    private ArrayList<HomeCatagoryModel> homeOilModel=new ArrayList<>();

    ApiInterface apiInterface;
    Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        apiInterface = APIClient.getApiClient().create(ApiInterface.class);
        session = new Session(getContext());

        setImage();

        cat_id = getActivity().getIntent().getStringExtra("cat_id");
        sub_cat_id = getActivity().getIntent().getStringExtra("sub_cat_id");

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.HomeCatViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new CategoryFragment();
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.framLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        binding.HomeFruitViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        binding.HomeTeaViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        binding.HomeOilViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });

//        binding.nestedScroll.setZ(-30);
        binding.searchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });


        initView();

        return binding.getRoot();
    }

    private void initView() {

        BannerSet();//banner

        Bottom_BannerSet();

        binding.pagerSlider.addOnPageChangeListener(listener);

        fetch_categories();//cat

        homeBannerAdapter = new HomeBannerAdapter(getContext(),array_banner);
        binding.rvPopular.setAdapter(homePopulerAdapter);

        ShowProductList();//tata tea

        fetch_product_by_category();//fruits

        fetch_product_by_category2();//oil

        trending_offers();

//        binding.rvTrendingOffer.setLayoutManager(new LinearLayoutManager(getContext()));


//        homeTrendingOfferAdapter = new HomeTrendingOfferAdapter(getContext());
//        binding.rvTrendingOffer.setAdapter(homeTrendingOfferAdapter);

        final int[] state = new int[1];

        fetch_category_name(binding.cat1, "1");
        fetch_category_name(binding.cat2, "3");
        fetch_category_name(binding.cat3, "1");

    }

    private void BannerSet() {
        Call<String> call = apiInterface.fetch_multiple_banner();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()!=0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                topBannerModels.add(new TopBannerModel(
                                        Utils.banner_images+jsonObject.getString("image_name")
                                ));
                            }
                            adapter = new AutoImageSliderAdapter(getActivity(), topBannerModels);
                            binding.pagerSlider.setAdapter(adapter);
                            binding.pagerSlider.setCurrentItem(0);
                            addDotsIndicator(topBannerModels.size(), 0);

                        }else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void Bottom_BannerSet() {
        Call<String> call = apiInterface.fetch_bottom_multiple_banner();
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
                                bottomBannerModels.add(new BottomBannerModel(
                                        Utils.bottom_banner_images+jsonObject.getString("image_name")
                                ));
                            }
                            bottomBannerAdapter = new BottomBannerAdapter(bottomBannerModels,getContext());
                            binding.rvBanner.setAdapter(bottomBannerAdapter);

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

    public void addDotsIndicator(int size, int position) {
        mDots = new TextView[size];
        binding.linDots.removeAllViews();

        for (int i=0; i<mDots.length; i++)
        {
            mDots[i] = new TextView(getActivity());
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparent));

            binding.linDots.addView(mDots[i]);

        }

        if (mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(adapter.getCount(),position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };




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
                            TopCategory.clear();
                            for (int i=0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                TopCategory.add(new HomeCatagoryModel(jsonObject.getString("name"),
                                        Utils.Category_images+jsonObject.getString("image"),
                                        jsonObject.getString("id")));
                            }
                            allCatagoryAdapter = new AllCatagoryAdapter(getActivity(),TopCategory);
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




    private void ShowProductList() {
        ProgressUtils.showLoadingDialog(getContext());
        Call<String> call = apiInterface.fetch_product_by_category("1");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")) {
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length() != 0) {
                            searchModels.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                searchModels.add(new SearchModel(
                                        jsonObject.getString("category_id"),
                                        jsonObject.getString("sub_category_id"),
                                        jsonObject.getString("id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("discounted_price"),
                                        jsonObject.getString("original_price"),
                                        Utils.product_images+jsonObject.getString("image1"),
                                        jsonObject.getString("discount_percentage")
                                ));
                            }
                            homePopulerAdapter = new HomePopulerAdapter(getContext(),searchModels);
                            binding.rvPopular.setAdapter(homePopulerAdapter);
                            ProgressUtils.cancelLoading();
                        } else {
                            ProgressUtils.cancelLoading();
                            Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        ProgressUtils.cancelLoading();
                    }
                } else {
                    ProgressUtils.cancelLoading();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ProgressUtils.cancelLoading();
            }
        });

    }




    private void fetch_product_by_category() {
        Call<String> call = apiInterface.fetch_product_by_category("3");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()!=0){
                            freshFruitsModels.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                freshFruitsModels.add(new FreshFruitsModel(
                                        jsonObject.getString("category_id"),
                                        jsonObject.getString("sub_category_id"),
                                        jsonObject.getString("id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("discounted_price"),
                                        jsonObject.getString("original_price"),
                                        Utils.product_images+jsonObject.getString("image1"),
                                        jsonObject.getString("discount_percentage")
                                ));
                            }
                            homeRecomendedAdapter = new HomeRecomendedAdapter(getContext(),freshFruitsModels);
                            binding.rvFreshfruits.setAdapter(homeRecomendedAdapter);

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




    private void fetch_product_by_category2() {
        Call<String> call = apiInterface.fetch_product_by_category("1");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()!=0){
                            freshFruitsModels2.clear();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                freshFruitsModels2.add(new FreshFruitsModel(
                                        jsonObject.getString("category_id"),
                                        jsonObject.getString("sub_category_id"),
                                        jsonObject.getString("id"),
                                        jsonObject.getString("product_name"),
                                        jsonObject.getString("discounted_price"),
                                        jsonObject.getString("original_price"),
                                        Utils.product_images+jsonObject.getString("image1"),
                                        jsonObject.getString("discount_percentage")
                                ));
                            }
                            homeRecomendedAdapter2 = new HomeRecomendedAdapter(getContext(),freshFruitsModels2);
                            binding.rvOil.setAdapter(homeRecomendedAdapter2);

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




    private void fetch_category_name(TextView text,String id){
        Call<String> call = apiInterface.fetch_category_name(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        if (jsonObject.getString("rec").equals("1")){
                            text.setText(jsonObject.getString("name"));
                        }else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                }else{

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void trending_offers(){
        Call<String> call = apiInterface.fetch_trending_offers();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if (!res.equals("")){
                    try {
                        JSONArray jsonArray = new JSONArray(res);
                        if (jsonArray.length()!=0){
                            for (int i =0;i< jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                trendingOfferModels.add(new TrendingOfferModel(
                                        jsonObject.getString("product_id"),
                                        jsonObject.getString("product_name"),
                                        Utils.trending_offers_images+jsonObject.getString("product_image"),
                                        Utils.trending_product_title_image+jsonObject.getString("product_title_image"),
                                        jsonObject.getString("trending_status"),
                                        jsonObject.getString("discount_percentage"),
                                        jsonObject.getString("category_id"),
                                        jsonObject.getString("sub_category_id")

                                ));
                            }

                            trendingOfferAdapter = new TrendingOfferAdapter(trendingOfferModels,getContext());
                            binding.rvTrendingOffer.setAdapter(trendingOfferAdapter);

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