package com.example.mycloset;


import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class Splash_Activity extends AppCompatActivity {

    private final int ANIMATION_DURATION = 2000;
    private ImageView splash_IMG_logo;
    private MaterialButton splash_BTN_start;
    private ImageView splash_IMG_background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        /// background
        splash_IMG_background= findViewById(R.id.splash_IMG_background);
        Glide.with(this).load(getResources().getIdentifier("splash_img_background", "drawable", this.getPackageName())).into(splash_IMG_background);


        findViews();
        initViews();
        startAnimation(splash_IMG_logo);
    }

    private void startAnimation(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        view.setY(-height / 2);


//        view.setScaleX(0.0f);
//        view.setScaleY(0.0f);
//        view.setAlpha(0.0f);
        view.animate()
//                .alpha(1.0f)
//                .scaleY(1.0f)
//                .scaleX(1.0f)
//                .rotation(360)
                .translationY(0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        splash_BTN_start.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) { }

                    @Override
                    public void onAnimationRepeat(Animator animator) { }
                });
    }

    private void initViews() {
        splash_BTN_start.setVisibility(View.INVISIBLE);

        splash_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Splash_Activity.this, Login_Activity.class);
                startActivity(myIntent);
            }
        });
    }

    private void findViews() {
        splash_IMG_logo = findViewById(R.id.splash_IMG_logo);
        splash_BTN_start = findViewById(R.id.splash_BTN_start);
    }
}