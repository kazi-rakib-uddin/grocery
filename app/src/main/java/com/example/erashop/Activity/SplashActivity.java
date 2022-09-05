package com.example.erashop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.erashop.R;
import com.example.erashop.Session.OBSession;
import com.example.erashop.databinding.ActivitySplashBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    Animation topAnimation,bottomAnimation;

    private OBSession obSession;

    ImageView img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        obSession = new OBSession(this);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(!obSession.getInstall().equals("")){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                }
                finish();
            }
        }, 3000);

        topAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_animation);

        img1 = findViewById(R.id.splashImg1);
        img2 = findViewById(R.id.splashImg2);

//        binding.splashImg1.setAnimation(topAnimation);
//        binding.splashImg2.setAnimation(bottomAnimation);

        img1.setAnimation(topAnimation);
        img2.setAnimation(bottomAnimation);

    }
}