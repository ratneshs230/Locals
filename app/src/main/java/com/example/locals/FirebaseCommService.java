package com.example.locals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCommService {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference uid_ref;
    private DatabaseReference lat_ref;
    private DatabaseReference lon_ref;

    private List<GetUserCoord> userCoords=new ArrayList<>();
    private List<GetUserCoord> GUCusid=new ArrayList<>();
    private List<GetUserCoord> GUClat=new ArrayList<>();
    private List<GetUserCoord> GUClon=new ArrayList<>();



    public FirebaseCommService() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("users/Users_Location/");


    }
    public void phone_auth(){


    }

    public void getdata(){
        Log.i("getdata","Reached");
        uid_ref=databaseReference.child("uid");
        lat_ref=databaseReference.child("Latitude");
        lon_ref=databaseReference.child("Longitude");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userCoords.clear();
                List<String> key=new ArrayList<>();
                List<String> value=new ArrayList<>();
                for(DataSnapshot keynode : dataSnapshot.getChildren()){
                    key.add(keynode.getKey());

                    Log.i("Datasnapshot",keynode+"");




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
