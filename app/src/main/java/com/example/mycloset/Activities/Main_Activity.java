package com.example.mycloset.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mycloset.R;

public class Main_Activity extends AppCompatActivity {

    private CardView main_LBL_tops;
    private CardView main_LBL_bottoms;
    private CardView main_LBL_jackets;
    private CardView main_LBL_shoes;
    private CardView main_LBL_accessories;
    private CardView main_LBL_sports;

    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;
    private ImageView main_IMG_homeback;
    private ScrollView main_ScrollView;
    private CardView main_Card_tops,main_Card_bottoms,main_Card_jackets,main_Card_shoes,main_Card_accessories,main_Card_sports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findByView();



        Glide.with(this).load(R.drawable.main_img_homeback).into(new CustomTarget<Drawable>() {
           @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Drawable> transition) {
               main_ScrollView.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }

        });



   Glide.with(this).load(getResources().getIdentifier("main_IMG_homeback", "drawable", this.getPackageName())).into(main_IMG_homeback);

        main_LBL_tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopsActivity();
            }
        });
        main_LBL_bottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomsActivity();
            }
        });
        main_LBL_jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJacketsActivity();
            }
        });
        main_LBL_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessoriesActivity();
            }
        });
        main_LBL_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSportsActivity();
            }
        });
        main_LBL_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShoesActivity();
            }
        });
        menu_IMG_ootd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOutFitActivity();
            }
        });
  }


    private void openOutFitActivity() {
        Intent myIntent = new Intent(this, Outfit_Activity.class);
        startActivity(myIntent);
    }

    private void openShoesActivity() {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.ShoesCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openSportsActivity(){
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.SportCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openJacketsActivity() {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.JacketsCategory_Activity.class);
        startActivity(myIntent);

    }
    private void openBottomsActivity() {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.BottomsCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openAccessoriesActivity() {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.AccessoriesCategory_Activity.class);
        startActivity(myIntent);

    }

    private void openTopsActivity() {
        Intent myIntent = new Intent(this,com.example.mycloset.Activities.TopsCategory_Activity.class);
        startActivity(myIntent);

    }


    private void findByView(){

        main_LBL_tops= findViewById(R.id.main_LBL_tops);
        main_LBL_bottoms= findViewById(R.id.main_LBL_bottoms);
        main_LBL_shoes= findViewById(R.id.main_LBL_shoes);
        main_LBL_accessories= findViewById(R.id.main_LBL_accessories);
        main_LBL_sports= findViewById(R.id.main_LBL_sports);
        main_LBL_jackets= findViewById(R.id.main_LBL_jackets);
        menu_IMG_ootd= findViewById(R.id.menu_IMG_ootd);
        menu_IMG_addPic= findViewById(R.id.menu_IMG_addPic);
        main_IMG_homeback= findViewById(R.id.main_IMG_Back);
        menu_IMG_homepage= findViewById(R.id.menu_IMG_homepage);


        main_ScrollView = findViewById(R.id.main_ScrollView);



       //tops=findViewById();
       //bottoms=findViewById();
       //jackets=findViewById();
       //shoes=findViewById();
       //accessories=findViewById();
       //sports=findViewById();
    }
    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("pttt", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "onDestroy");
        super.onDestroy();
    }
}

