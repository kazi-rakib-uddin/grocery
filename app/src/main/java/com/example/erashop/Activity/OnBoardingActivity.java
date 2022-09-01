package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.erashop.Adapter.SliderAdapter;
import com.example.erashop.R;
import com.example.erashop.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

    public static ActivityOnBoardingBinding binding;
    private SliderAdapter adapter;
    public static TextView[] mDots;
    public static int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        initView();


    }

    private void initView() {

        adapter = new SliderAdapter(this);
        binding.viewPager.setAdapter(adapter);

        addDotsIndicator(0);

        binding.viewPager.addOnPageChangeListener(listener);



    }

    public void addDotsIndicator(int position)
    {
        mDots = new TextView[3];
        binding.linDots.removeAllViews();

        for (int i=0; i<mDots.length; i++)
        {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.yellow));

            binding.linDots.addView(mDots[i]);

        }

        if (mDots.length > 0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.purple_500));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            mCurrentPage = position;

            if (position ==0)
            {
                SliderAdapter.txt_next.setText("Next");
            }
            else if (position == OnBoardingActivity.mDots.length -1)
            {
                SliderAdapter.txt_next.setText("Finish");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}