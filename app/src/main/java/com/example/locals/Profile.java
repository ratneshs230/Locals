package com.example.locals;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    TextView name,dob,work,about;
    Button follow,message;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    List<User_Details> user_detailsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name=findViewById(R.id.profilePg_userName);
        dob=findViewById(R.id.profilePG_dob);
        work=findViewById(R.id.profilePG_profession);
        about=findViewById(R.id.profilePG_About);
        follow=findViewById(R.id.profilePG_follow);
        message=findViewById(R.id.profilePG_message_btn);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.child("User_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User_Details info=ds.getValue(User_Details.class);
                    user_detailsList.add(info);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(user_detailsList.size()>0){

            name.setText(user_detailsList.get(0).getName());
            dob.setText(user_detailsList.get(0).getDob());
            work.setText(user_detailsList.get(0).getAge());
            about.setText(user_detailsList.get(0).getEmail());


        }





    }


}
