package com.example.mycloset.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycloset.ListPictures;
import com.example.mycloset.OotdActivity;
import com.example.mycloset.PictureItem;
import com.example.mycloset.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AccessoriesCategory_Activity extends AppCompatActivity {
    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;
    private Activity MainActivity;
    private Activity OotdActivity;

  // private ImageView main_IMG_homeback;
    private Uri uriImage;
    private StorageReference storageRef;
    private FirebaseStorage storage ;
    private FirebaseUser user;
    private String myUri;
    /// image adapter
    // Create a reference with an initial file path and name
   private StorageReference pathReference;

    // Create a reference to a file from a Cloud Storage URI
    private StorageReference gsReference;
    private FirebaseDatabase database;
    private UploadTask uploadTask;
    private RecyclerView rv;
    private List<ListPictures>listPictures;
    private DatabaseReference databaseReference;

    ///lIST


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        findByView();

         user = FirebaseAuth.getInstance().getCurrentUser();

// Create a Cloud Storage reference from the app
        storage= FirebaseStorage.getInstance();
       //  storageRef = storage.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads");

//// RealTime
         database = FirebaseDatabase.getInstance();

        //  main_IMG_homeback= findViewById(R.id.main_IMG_back1);
       // Glide.with(this).load(R.drawable.main_IMG_homeback).into(main_IMG_homeback);

        setContentView(R.layout.activity_accessories);
        rv=findViewById(R.id.rec);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        listPictures=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("myImages");
        getImageData();



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

       // download();

    }
    private void getImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    ListPictures articleList=di.getValue(ListPictures.class);
                    listPictures.add(articleList);
                }
                ListPictures adapter=new ListPictures(listPictures,getApplicationContext());
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    private void download() {
//
//
//        DatabaseReference myRef = database.getReference(user.getUid()).child("accessories");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("TAG", "Value is: " + value);
//             //   Glide.with(getApplicationContext()).load(value).into(testimg);
//                Glide.with(getApplicationContext())
//                        .load(myRef)
//                        .into(testimg);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("TAG", "Failed to read value.", error.toException());
//            }
//        });
//
//        pathReference = storageRef.child(user.getUid()).child("accessories");//.child("b2303f3f-b46d-45f4-9625-35de5de3a146.jpg");
//       // Glide.with(this).load(pathReference).into(testimg);
//        pathReference.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//                testimg.setImageBitmap(bitmap);
//            }
//        });
//
//
//
//    }

    private void openOotdActivity(Activity activity) {
        Intent myIntent = new Intent(this, OotdActivity.class);
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

            uriImage = data.getData();
            //
            uploadImage();
            //Image Uri will not be null for RESULT_OK
            // imgProfile.setImageURI(uriImage);
           // menu_IMG_homepage.setImageURI();

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

    private void uploadPicture() {
//        ///GET USER ID FROM FIREBASE
//
//
//        final String randomeKey= UUID.randomUUID().toString();
//        // Create a reference to "mountains.jpg"
//        StorageReference AccessoriessRef = storageRef.child(user.getUid()).child("accessories").child(randomeKey+".jpg");
//
//       AccessoriessRef.putFile(uriImage);
//        Toast.makeText(getApplicationContext(),"You added picture successfully",Toast.LENGTH_LONG);

//        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl= taskSnapshot.getStorage().getDownloadUrl().getResult();
//                final String image= downloadUrl.toString();
//            }
//        });


       // AccessoriessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
         //   @Override
          //  public void onSuccess(Uri uri) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference(user.getUid()).child("accessories");
//                myRef.setValue(uri.toString());
        //             }
    //    });

        // Write a message to the RealTimedatabase
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference(user.getUid()).child("accessories");
//        myRef.setValue(        AccessoriessRef.getDownloadUrl().getResult().toString());

    }
    private void uploadImage() {
//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setMessage("Uploading");
//        pd.show();

        if (uriImage != null) {
            final String randomeKey = UUID.randomUUID().toString();
            StorageReference AccessoriessRef = storageRef.child(user.getUid()).child("accessories").child(randomeKey + ".jpg");

           AccessoriessRef.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   AccessoriessRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           myUri = uri.toString();
                           DatabaseReference myRef = database.getReference(user.getUid()).child("accessories").child(randomeKey);
                           myRef.setValue(myUri);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception exception) {
                       }
                   });
               }
           });
        }
    }

    private void findByView(){

        menu_IMG_ootd= findViewById(R.id.menu_IMG_ootd);
        menu_IMG_addPic= findViewById(R.id.menu_IMG_addPic);
        menu_IMG_homepage= findViewById(R.id.menu_IMG_homepage);
      //  testimg=findViewById(R.id.testimg);


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
