package com.example.erashop.Adapter;

import static com.example.erashop.Activity.OnBoardingActivity.mCurrentPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.erashop.Activity.LoginActivity;
import com.example.erashop.Activity.OnBoardingActivity;
import com.example.erashop.Model.TopBannerModel;
import com.example.erashop.R;

import java.util.ArrayList;

public class AutoImageSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<TopBannerModel> items;

    public AutoImageSliderAdapter(Context context, ArrayList<TopBannerModel> items) {
        this.context = context;
        this.items = items;
    }



    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout)object;
    }

    public void setItems(ArrayList<TopBannerModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_auto_slider_layout,container,false);

        ImageView image = view.findViewById(R.id.image);

        displayImageOriginal(context, image, items.get(position).getImage() , position);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }


    private static void displayImageOriginal(Context context, ImageView img, String url, int position) {
        try {
            Glide.with(context).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img);
        } catch (Exception e) {
            e.getMessage();
        }
    }



}
