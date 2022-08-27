package com.example.erashop.Fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.erashop.Activity.OnBoardingActivity;
import com.example.erashop.Adapter.AutoImageSliderAdapter;
import com.example.erashop.Adapter.HomeBannerAdapter;
import com.example.erashop.Adapter.HomeCatagoryAdapter;
import com.example.erashop.Adapter.HomePopulerAdapter;
import com.example.erashop.Adapter.HomeRecomendedAdapter;
import com.example.erashop.Adapter.HomeTrendingOfferAdapter;
import com.example.erashop.Adapter.SliderAdapter;
import com.example.erashop.Model.HomeCatagoryModel;
import com.example.erashop.Model.HomeItemModel;
import com.example.erashop.R;
import com.example.erashop.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static String[] array_imgs = new String[] {
            "https://img.freepik.com/free-psd/horizontal-banner-online-fashion-sale_23-2148585404.jpg?w=900&t=st=1660296962~exp=1660297562~hmac=29e6c339f81ad2bddc2925c01e71608a9253dc5f61799bc59fb8e7ea80504b90",
            "https://img.freepik.com/free-psd/sale-banner-template_24972-824.jpg?w=996&t=st=1660296243~exp=1660296843~hmac=43bd834c304f476d23274de08e42de1b8856da1e24659d0ef2885945f4f5bd85",
            "https://img.freepik.com/free-vector/online-shopping-isometric-concept-shopping-cart_107791-317.jpg?w=826&t=st=1660297153~exp=1660297753~hmac=507d520dfbdaf1ff666b8c461de5ac28f0c0a871c91639b9babe50fa45e1f049"
    };

    private static String[] array_banner = new String[] {
            "https://trivenisupermarket.com/img/triveni-indian-supermarket-coming-soon.jpg",
            "https://marcado.in/images/ffff.jpg",
            "https://trivenisupermarket.com/img/what-can-you-get.jpeg"
    };

    private AutoImageSliderAdapter adapter;
    private TextView[] mDots;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private HomeCatagoryAdapter homeCatagoryAdapter;
    private HomePopulerAdapter homePopulerAdapter;
    private HomeBannerAdapter homeBannerAdapter;
    private HomeRecomendedAdapter homeRecomendedAdapter;
    private HomeRecomendedAdapter homeRecomendedAdapter_oil;
    private HomeTrendingOfferAdapter homeTrendingOfferAdapter;
    private List<HomeCatagoryModel> homeCatagoryModels;
    private List<HomeCatagoryModel> homeItemModels;
    private List<HomeCatagoryModel> homeFreshFoodModel;
    private List<HomeCatagoryModel> homeOilModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

//        binding.searchCard.setGravity(0);
        binding.searchCard.bringChildToFront(binding.searchCard);

        initView();

        return binding.getRoot();
    }

    private void initView() {

        adapter = new AutoImageSliderAdapter(getActivity(),array_imgs);
        binding.pagerSlider.setAdapter(adapter);
        binding.pagerSlider.setCurrentItem(0);

        addDotsIndicator(array_imgs.length, 0);

        binding.pagerSlider.addOnPageChangeListener(listener);
//        startAutoSlider(array_imgs.length);

        catagoryList();
        homeCatagoryAdapter = new HomeCatagoryAdapter(getActivity(),homeCatagoryModels);
        binding.rvCatagory.setAdapter(homeCatagoryAdapter);


        homeBannerAdapter = new HomeBannerAdapter(getContext(),array_banner);
        binding.rvPopular.setAdapter(homePopulerAdapter);

        itemList();
        freshFoodList();
        oilList();
        homePopulerAdapter = new HomePopulerAdapter(getContext(),homeItemModels);
        homeRecomendedAdapter = new HomeRecomendedAdapter(getContext(),homeFreshFoodModel);
        homeRecomendedAdapter_oil = new HomeRecomendedAdapter(getContext(),homeOilModel);

        binding.rvRecomended.setAdapter(homeRecomendedAdapter);
        binding.rvPopular.setAdapter(homePopulerAdapter);
        binding.rvBanner.setAdapter(homeBannerAdapter);
        binding.rvOil.setAdapter(homeRecomendedAdapter_oil);

        homeTrendingOfferAdapter = new HomeTrendingOfferAdapter();
        binding.rvTrendingOffer.setAdapter(homeTrendingOfferAdapter);

        final int[] state = new int[1];
        /*binding.nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


            }


        });*/

        /*binding.rvPopular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state[0] = newState;

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy>0 && (state[0] == 0 || state[0] == 2))
                {
                    binding.cardSearch.setVisibility(View.GONE);
                }
                else if (dy <-10)
                {
                    binding.cardSearch.setVisibility(View.VISIBLE);
                }
            }
        });*/

    }



    public void addDotsIndicator(int size, int position)
    {
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

    private List<HomeCatagoryModel> catagoryList()
    {
        homeCatagoryModels = new ArrayList<>();
        homeCatagoryModels.add(new HomeCatagoryModel("Grocery & Staple",R.drawable.image));
        homeCatagoryModels.add(new HomeCatagoryModel("Oil",R.drawable.oil));
        homeCatagoryModels.add(new HomeCatagoryModel("Tea & Coffee",R.drawable.tea_coffee));
        homeCatagoryModels.add(new HomeCatagoryModel("Dairy Milk",R.drawable.dairy_milk));

        return homeCatagoryModels;
    }


    private List<HomeCatagoryModel> itemList()
    {
        homeItemModels = new ArrayList<>();
        homeItemModels.add(new HomeCatagoryModel("Tata Premium Tea",R.drawable.tata_tea));
        homeItemModels.add(new HomeCatagoryModel("Tata Premium Tea",R.drawable.tata_tea));
        homeItemModels.add(new HomeCatagoryModel("Tata Premium Tea",R.drawable.tata_tea));

        return homeItemModels;
    }


    private List<HomeCatagoryModel> freshFoodList()
    {
        homeFreshFoodModel = new ArrayList<>();
        homeFreshFoodModel.add(new HomeCatagoryModel("Banana",R.drawable.banana));
        homeFreshFoodModel.add(new HomeCatagoryModel("Apple",R.drawable.apple));
        homeFreshFoodModel.add(new HomeCatagoryModel("Graps",R.drawable.graps));
        homeFreshFoodModel.add(new HomeCatagoryModel("Avocado",R.drawable.avocado));

        return homeFreshFoodModel;
    }

    private List<HomeCatagoryModel> oilList()
    {
        homeOilModel = new ArrayList<>();
        homeOilModel.add(new HomeCatagoryModel("Fortune Oil",R.drawable.fortune_oil));
        homeOilModel.add(new HomeCatagoryModel("Dalda Kachi Ghani",R.drawable.dalda));
        homeOilModel.add(new HomeCatagoryModel("Mahakosh Oil",R.drawable.mahakosh));
        homeOilModel.add(new HomeCatagoryModel("Fortune Pure Oil",R.drawable.fortune_oil));

        return homeOilModel;
    }

}