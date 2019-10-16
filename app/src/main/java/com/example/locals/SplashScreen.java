package com.example.locals;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            firebaseAuth= FirebaseAuth.getInstance();
            currentUser=firebaseAuth.getCurrentUser();

            setContentView(R.layout.splash_screen);


            if(currentUser!=null)
            {
                flag=true;
                        }

            splash();




    }
    private void splash(){

        final Thread splash = new Thread() {
            public void run() {
                try {

                    sleep(2000);

                    if(flag==false) {
                        Intent splash_intent = new
                                Intent(SplashScreen.this, Signin_Panel.class);
                        startActivity(splash_intent);
                    }else{
                        Intent mapsIntent=new Intent(SplashScreen.this,Signin_Panel.class);
                        startActivity(mapsIntent);
                    }

                    finish();
                } catch(Exception e) {
                }
            }


        };
        splash.start();

    }

}