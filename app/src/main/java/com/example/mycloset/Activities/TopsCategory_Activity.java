package com.example.mycloset.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.Models.ListPictures;
import com.example.mycloset.Models.RecycleAdapter;
import com.example.mycloset.R;
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

import static com.example.mycloset.Models.Finals.TOPS;

public class TopsCategory_Activity extends AppCompatActivity {

    private ImageView menu_IMG_ootd;
    private ImageView menu_IMG_addPic;
    private ImageView menu_IMG_homepage;

    private Uri uriImage;
    private StorageReference storageRef;
    private FirebaseStorage storage ;
    private FirebaseUser user;
    private String myUri;

    private StorageReference gsReference;
    private FirebaseDatabase database;
    private RecyclerView rv;

    private RecycleAdapter mAdapter;
    private List<ListPictures> listPictures;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tops);
        findByView();
        // Create a Cloud Storage reference from the app
        storage= FirebaseStorage.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = storage.getReference();
        //// RealTime database
        database = FirebaseDatabase.getInstance();
        ///recycleView
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration decoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(decoration);
        listPictures=new ArrayList<>();
        databaseReference= database.getReference(user.getUid()).child(TOPS);
        getImageData();

        menu_IMG_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        menu_IMG_ootd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                openOutfitActivity();
            }
        });
        menu_IMG_addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPicture();
            }
        });


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
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openOutfitActivity() {
        Intent myIntent = new Intent(this, Outfit_Activity.class);
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
            StorageReference AccessoriesRef = storageRef.child(user.getUid()).child(TOPS).child(randomKey + ".jpg");

            AccessoriesRef.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    AccessoriesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            myUri = uri.toString();
                            databaseReference.child(randomKey).setValue(myUri);
                            //DatabaseReference myRef = database.getReference(user.getUid()).child("accessories").child(randomKey);
                            //myRef.setValue();
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

    }

    ///Methods to like and delete
    public void saveToOutFit(ListPictures listPicture) {
        //final String randomKey = UUID.randomUUID().toString();
        DatabaseReference myRef = database.getReference(user.getUid()).child("Outfit");//.child(randomKey);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean isExist =false;
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String key = ds.getKey();
                        if (ds.getValue().equals(listPicture.getImageUrl())){
                            //ds.getRef().removeValue();
                            isExist=true;
                        }
                    }

                }
                if(isExist==false) {
                    myRef.push().setValue(listPicture.getImageUrl());}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeFromPage(ListPictures listPicture) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (ds.getValue().equals(listPicture.getImageUrl())){
                        ds.getRef().removeValue();

                        //delete Picture from Storage
                        storageRef.child(user.getUid()).child(TOPS).child(key + ".jpg").delete();
                        Log.d("pttt","key"+ key+"deleted");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        DatabaseReference myRef = database.getReference(user.getUid()).child("Outfit");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String key = ds.getKey();
                        if (ds.getValue().equals(listPicture.getImageUrl())){
                            ds.getRef().removeValue();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
