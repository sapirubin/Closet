package com.example.mycloset.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloset.Models.ListPictures;
import com.example.mycloset.R;
import com.example.mycloset.Models.RecycleAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Outfit_Activity extends AppCompatActivity {
    private ImageView menu_IMG_homepage;
    private Uri uriImage;
    private StorageReference storageRef;
    private FirebaseStorage storage ;
    private FirebaseUser user;
    private String myUri;
    private StorageReference gsReference;
    private FirebaseDatabase database;
    private RecyclerView rv;
    private TextView main_LBL_titleName;

    private RecycleAdapter mAdapter;
    private List<ListPictures> listPictures;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit);
        findByView();


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
        databaseReference= database.getReference(user.getUid()).child("Outfit");

        main_LBL_titleName.setText(user.getDisplayName());

        getImageData();

        menu_IMG_homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void findByView(){
         main_LBL_titleName=findViewById(R.id.main_LBL_titleName);
        menu_IMG_homepage= findViewById(R.id.menu_IMG_homepage);
        rv=findViewById(R.id.rec);

    }


    private void getImageData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listPictures.clear();
                for (DataSnapshot di:dataSnapshot.getChildren()){
                    if (dataSnapshot.exists()){
                    String url = di.getValue().toString();
                    ListPictures listPicture=new ListPictures(url);
                    listPictures.add(listPicture);}
                }

                // ron
                mAdapter = new RecycleAdapter(listPictures,getApplicationContext());
                rv.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemLike(int position) {
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

    private void removeFromPage(ListPictures listPicture) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (ds.getValue().equals(listPicture.getImageUrl())){
                        ds.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
}
