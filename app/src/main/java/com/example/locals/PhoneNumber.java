package com.example.locals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneNumber extends AppCompatActivity implements View.OnClickListener {
    private EditText phn_no;
    private Button next_btn;
    String phonenumber;
    TextView tologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_phone_number);
                    tologin=findViewById(R.id.tologinpage);
                    phn_no=findViewById(R.id.phn_no_entry);
                    next_btn=findViewById(R.id.next_btn);
                    tologin.setOnClickListener(this);
                    next_btn.setOnClickListener(this);
            }

    @Override
    public void onClick(View v) {
        if(v==tologin) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
        if(v==next_btn) {
            phonenumber=phn_no.getText().toString().trim();
            Intent intent = new Intent(getApplicationContext(), otpActivity.class);
            intent.putExtra("phonenumber", phonenumber);
            Log.w("next ", " Button TOuched");
            startActivity(intent);
        }
        }



    }

