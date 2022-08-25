package com.example.erashop.Adapter;

import static com.example.erashop.Activity.OnBoardingActivity.mCurrentPage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.erashop.Activity.LoginActivity;
import com.example.erashop.Activity.OnBoardingActivity;
import com.example.erashop.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public static TextView txt_next;
    public static CardView btn_next;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slider_images = {
            R.drawable.img_screen_1,
            R.drawable.img_screen_2,
            R.drawable.img_screen_3
    };

    public String[] slider_header = {
            "Best Quality Goods",
            "Current Faction Design",
            "Used by Various Artists"
    };

    @Override
    public int getCount() {
        return slider_header.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        RelativeLayout img_rel = view.findViewById(R.id.img_rel);
        TextView txt_title = view.findViewById(R.id.txt_title);
        txt_next = view.findViewById(R.id.txt_next);
        btn_next = view.findViewById(R.id.btn_next);

        img_rel.setBackgroundResource(slider_images[position]);
        txt_title.setText(slider_header[position]);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_next.getText().toString().equals("Next"))
                {
                    OnBoardingActivity.binding.viewPager.setCurrentItem(mCurrentPage +1);
                }
                else
                {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
