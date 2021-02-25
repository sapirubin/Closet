package Categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycloset.OotdActivity;
import com.example.mycloset.R;

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


    }
    private void openOotdActivity(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.OotdActivity.class);
        startActivity(myIntent);






    }

    private void openMenu(Activity activity) {
        Intent myIntent = new Intent(this, com.example.mycloset.MainActivity.class);
        startActivity(myIntent);
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
