package com.example.locals;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Register extends AppCompatActivity implements View.OnClickListener , DatePickerDialog.OnDateSetListener{

    EditText username,password;
    Button register_btn;
    TextView tologin;
    EditText firstname;
    TextView dob_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private EditText phon_no;
    String age;

    List<User_Details> user_detailsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        firstname=findViewById(R.id.display_name);
        phon_no=findViewById(R.id.phn_no);
        dob_btn=findViewById(R.id.dob_btn);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.password);
        register_btn = findViewById(R.id.register_btn);
        tologin = findViewById(R.id.login_page);

        register_btn.setOnClickListener(this);
        tologin.setOnClickListener(this);

        dob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Pick Date Of Birth");

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view,int year,int month,int day){

        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());


        age=getAge(year,month,day);

        dob_btn.setText(currentDate);


    }
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    private void update_user(){
        String first=firstname.getText().toString().trim();
        String dob=dob_btn.getText().toString().trim();
        String email=username.getText().toString().trim();
        Log.w("Update_Users ","Reached");

        User_Details user_details=new User_Details();
        user_details.setUserId(databaseReference.child("User_Details").push().getKey());
        user_details.setName(first);

        user_details.setAge(age);
        user_details.setDob(dob);
        user_details.setEmail(email);
        Log.w("UserDetails : ",""+user_details);

       databaseReference.child("User_Details").child(user_details.getUserId()).setValue(user_details);



    }

    private void register_user(){
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        final String phn_no=phon_no.getText().toString().trim();

        if(phn_no.isEmpty() || phn_no.length()<10){
        phon_no.setError("Valid number is required");
        phon_no.requestFocus();
        return;

        }


        //TODO: Checks for correct entry
        if(TextUtils.isEmpty(user)){
            //email empty
            Toast toast=Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            //password is empty
            Toast toast=Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
       try{
            mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG);
                        progressDialog.dismiss();

                        toast.show();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        update_user();



                        Intent intent=new Intent(Register.this,Profile.class);
                        intent.putExtra("phonenumber",phn_no);
                        startActivity(intent);

                    } else {
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                        switch (errorCode) {
                            case "ERROR_INVALID_CUSTOM_TOKEN":
                                Toast.makeText(Register.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                Toast.makeText(Register.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_CREDENTIAL":
                                Toast.makeText(Register.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(Register.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                username.setError("The email address is badly formatted.");
                                username.requestFocus();
                                break;
                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(Register.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                password.setError("password is incorrect ");
                                password.requestFocus();
                                password.setText("");
                                break;
                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(Register.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_REQUIRES_RECENT_":
                                Toast.makeText(Register.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                Toast.makeText(Register.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(Register.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                username.setError("The email address is already in use by another account.");
                                username.requestFocus();
                                break;
                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(Register.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_DISABLED":
                                Toast.makeText(Register.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(Register.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(Register.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(Register.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(Register.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(Register.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                password.setError("The password is invalid it must 6 characters at least");
                                password.requestFocus();
                                break;


                        }
                    }
                }
            });

        }catch (Exception e){
            Toast toast=Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void tologin_page(){
        progressDialog.setMessage("Wait for a sec...");
        progressDialog.show();
        Intent intent=new Intent(Register.this,Login.class);
        startActivity(intent);
        //hello
    }
    @Override
    public void onClick(View v) {
        if(v==register_btn){
            register_user();

        }
        if(v==tologin){
            tologin_page();
        }

    }
}
