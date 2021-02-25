package Categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.mycloset.MainActivity;
import com.example.mycloset.OotdActivity;
import com.example.mycloset.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;


import com.github.drjacky.imagepicker.ImagePicker;

public class TopsCategory_Activity extends AppCompatActivity {
    private CardView tops_LBL_evening;
    private CardView tops_LBL_work;
    private CardView tops_LBL_casual;
    private CardView tops_LBL_beach;
    private CardView tops_LBL_dresses;
    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;
    private Activity MainActivity;
    private Activity TopsEvening_Activity;
    private Activity TopsWork_Activity ;
    private Activity TopsCasual_Activity;
    private Activity TopsBeach_Activity;
    private Activity TopsDresses_Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tops);
        tops_LBL_evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEveningActivity(TopsEvening_Activity);
            }
        });
        tops_LBL_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkActivity(TopsWork_Activity);
            }
        });
        tops_LBL_casual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCasualActivity(TopsCasual_Activity);
            }
        });
        tops_LBL_beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBeachActivity(TopsBeach_Activity);
            }
        });
        tops_LBL_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDressesActivity(TopsDresses_Activity);
            }
        });
        menu_IMG_ootd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOotdActivity();
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
                openMainActivity(MainActivity);

            }
        });
    }
    private void addPicture() {
        ImagePicker.Companion
                .with(this)
                .crop()
                .cropOval()
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

    private void openOotdActivity() {
        Intent myIntent = new Intent(this, OotdActivity.class);
        startActivity(myIntent);
    }

    private void openEveningActivity(Activity activity) {
        Intent myIntent = new Intent(this, ShoesCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openSportsActivity(Activity activity){
        Intent myIntent = new Intent(this, SportCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openWorkActivity(Activity activity) {
        Intent myIntent = new Intent(this, JacketsCategory_Activity.class);
        startActivity(myIntent);

    }
    private void openCasualActivity(Activity activity) {
        Intent myIntent = new Intent(this, BottomsCategory_Activity.class);
        startActivity(myIntent);
    }


    private void openBeachActivity(Activity activity) {
        Intent myIntent = new Intent(this, AccessoriesCategory_Activity.class);
        startActivity(myIntent);

    }

    private void openDressesActivity(Activity activity) {
        Intent myIntent = new Intent(this, TopsCategory_Activity.class);
        startActivity(myIntent);

    }
    private void openMainActivity(Activity activity) {
        Intent myIntent = new Intent(this, TopsCategory_Activity.class);
        startActivity(myIntent);

    }



    private void findByView(){

        tops_LBL_evening= findViewById(R.id.tops_LBL_evening);
        tops_LBL_work= findViewById(R.id.tops_LBL_work);
        tops_LBL_casual= findViewById(R.id.tops_LBL_casual);
        tops_LBL_beach= findViewById(R.id.tops_LBL_beach);
        tops_LBL_dresses= findViewById(R.id.tops_LBL_dresses);
        menu_IMG_ootd= findViewById(R.id.menu_IMG_ootd);
        menu_IMG_addPic= findViewById(R.id.menu_IMG_addPic);
        menu_IMG_homepage= findViewById(R.id.menu_IMG_homepage);

    }

    }


