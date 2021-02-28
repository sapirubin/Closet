package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.mycloset.Activities.TopsCategory_Activity;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class MainActivity extends AppCompatActivity {

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

    private Activity topsCategory_Activity;
    private Activity SportCategory_Activity;
    private Activity BottomsCategory_Activity ;
    private Activity AccessoriesCategory_Activity;
    private Activity JacketsCategory_Activity;
    private Activity ShoesCategory_Activity;
    private Activity ootd_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findByView();

//        Glide.with(this).load(getResources().getIdentifier("main_IMG_homeback", "drawable", this.getPackageName())).into(main_IMG_homeback);

        main_LBL_tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTopsActivity(topsCategory_Activity);
            }
        });
        main_LBL_bottoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomsActivity(BottomsCategory_Activity);
            }
        });
        main_LBL_jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJacketsActivity(JacketsCategory_Activity);
            }
        });
        main_LBL_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAccessoriesActivity(AccessoriesCategory_Activity);
            }
        });
        main_LBL_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSportsActivity(SportCategory_Activity);
            }
        });
        main_LBL_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShoesActivity(ShoesCategory_Activity);
            }
        });
        menu_IMG_ootd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOotdActivity(ootd_Activity);
            }
        });
        menu_IMG_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicture();
            }
        });

        menu_IMG_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity(MainActivity.this);


            }
        });
  }


    private void addPicture() {
        ImagePicker.Companion
                .with(this)
                .crop()
                .cropSquare()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            menu_IMG_addPic.setImageURI(fileUri);

            //You can get File object from intent
            File file = new ImagePicker().Companion.getFile(data);

            //You can also get File Path from intent
            String filePath = new ImagePicker().Companion.getFilePath(data);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, new ImagePicker().Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
    private void openMainActivity(Activity activity) {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    private void openOotdActivity(Activity activity) {
        Intent myIntent = new Intent(this, OotdActivity.class);
        startActivity(myIntent);
    }

    private void openShoesActivity(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.ShoesCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openSportsActivity(Activity activity){
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.SportCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openJacketsActivity(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.JacketsCategory_Activity.class);
        startActivity(myIntent);

    }
    private void openBottomsActivity(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.BottomsCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openAccessoriesActivity(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.Activities.AccessoriesCategory_Activity.class);
        startActivity(myIntent);

    }

    private void openTopsActivity(Activity activity) {
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

