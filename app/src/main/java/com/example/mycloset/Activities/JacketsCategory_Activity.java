package com.example.mycloset.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycloset.OutfitActivity;
import com.example.mycloset.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class JacketsCategory_Activity extends AppCompatActivity {
    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;
    private Activity MainActivity;
    private Activity OotdActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackets);
        findByView();
        menu_IMG_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu(MainActivity);


            }
        });
        menu_IMG_ootd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOotdActivity(OotdActivity);
            }
        });

        menu_IMG_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPicture();
            }
        });

    }
    private void openOotdActivity(Activity activity) {
        Intent myIntent = new Intent(this, OutfitActivity.class);
        startActivity(myIntent);
    }

    private void openMenu(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.MainActivity.class);
        startActivity(myIntent);
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
    private void findByView(){

        menu_IMG_ootd= findViewById(R.id.menu_IMG_ootd);
        menu_IMG_addPic= findViewById(R.id.menu_IMG_addPic);
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
