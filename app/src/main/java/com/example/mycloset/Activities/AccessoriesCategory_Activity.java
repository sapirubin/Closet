package com.example.mycloset.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.ListPictures;
import com.example.mycloset.MainActivity;
import com.example.mycloset.OutfitActivity;
import com.example.mycloset.R;
import com.example.mycloset.RecycleAdapter;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class AccessoriesCategory_Activity extends AppCompatActivity {
    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;
    private ImageView picture_IMG_heart,post_Delete;

    private Uri uriImage;
    private StorageReference storageRef;
    private FirebaseStorage storage ;
    private FirebaseUser user;
    private String myUri;

    private StorageReference gsReference;
    private FirebaseDatabase database;
    private RecyclerView rv;

    private RecycleAdapter mAdapter;
    private List<ListPictures>listPictures;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessories);
        findByView();

        // Create a Cloud Storage reference from the app
        storage= FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        //// RealTime database
        database = FirebaseDatabase.getInstance();
        ///recycleView
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        listPictures=new ArrayList<>();
        databaseReference= database.getReference(user.getUid()).child("accessories");

        //Testing Ron
       // testGetImageData();




        getImageData();


//        //mAdapter = new RecycleAdapter(listPictures,this);



        menu_IMG_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
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


    }

    private void testGetImageData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        ListPictures listPicture=new ListPictures("https://www.israelhayom.co.il/sites/default/files/u79157/15582558969343_b.jpg");
        ListPictures listPicture2=new ListPictures("https://upl.stack.com/wp-content/uploads/2020/03/11113000/Morning-Workout.jpg");


        listPictures.add(listPicture);
        listPictures.add(listPicture2);
        mAdapter = new RecycleAdapter(listPictures,getApplicationContext());
        rv.setAdapter(mAdapter);
    }

    private void getImageData() {
            databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPictures.clear();
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    String url = di.getValue().toString();
                    ListPictures listPicture=new ListPictures(url);
                    listPictures.add(listPicture);
                }

                // ron
                mAdapter = new RecycleAdapter(listPictures,getApplicationContext());
                rv.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemLike(int position) {
                        Log.d("pttt", "position: "+position);
                        saveToOutFit(listPictures.get(position));

                    }

                    @Override
                    public void onItemDelete(int position) {

                        removeFromPage(listPictures.get(position));

                        listPictures.remove(position);
                        //mAdapter = new RecycleAdapter(listPictures,getApplicationContext());
                        //rv.setAdapter(mAdapter);
                    }


                });


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openOotdActivity() {
        Intent myIntent = new Intent(this, OutfitActivity.class);
        startActivity(myIntent);

    }

    private void openMenu() {
//        Intent myIntent = new Intent(this, com.example.mycloset.MainActivity.class);
//        startActivity(myIntent);
        finish();
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
            uploadImage();

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

    private void uploadImage() {

        if (uriImage != null) {
            final String randomKey = UUID.randomUUID().toString();
            StorageReference AccessoriesRef = storageRef.child(user.getUid()).child("accessories").child(randomKey + ".jpg");

            AccessoriesRef.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                   AccessoriesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           myUri = uri.toString();
                           DatabaseReference myRef= databaseReference.child(randomKey);
                          //DatabaseReference myRef = database.getReference(user.getUid()).child("accessories").child(randomKey);
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


        rv=findViewById(R.id.rec);

        picture_IMG_heart=findViewById(R.id.post_like);
        post_Delete=findViewById(R.id.post_delete);

    }

    ///Methods to like and delete

    public void saveToOutFit(ListPictures listPicture) {
        final String randomKey = UUID.randomUUID().toString();
        DatabaseReference myRef = database.getReference(user.getUid()).child("Outfit").child(randomKey);
        myRef.setValue(listPicture.getImageUrl());

    }

    private void removeFromPage(ListPictures listPicture) {
       databaseReference.equalTo(listPicture.imageUrl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Key = snapshot.getKey();
                databaseReference.child(Key).removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });     //how to get the picture id from the database

       // DatabaseReference myRef = database.getReference(user.getUid()).child("Outfit").child(randomKey);
       // myRef.setValue(listPicture.getImageUrl());



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
