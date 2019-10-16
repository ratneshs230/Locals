package com.example.locals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;

public class Signin_Panel extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener{

    private Button phn,mail,fBook;
    private EditText phn_no,mailID,password;
    private ImageButton phnGo,mailGo;
    FirebaseAuth mAuth;
    //Google Login Request Code
    private int RC_SIGN_IN = 7;
    //Google Sign In Client
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    com.google.android.gms.common.SignInButton gooogl_btn;
    String name, email;
    String idToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin__panel);

        phn=findViewById(R.id.withNo);
        mail=findViewById(R.id.mail);
        gooogl_btn=findViewById(R.id.GMail_login);
        fBook=findViewById(R.id.facebook_login);
        phn_no=findViewById(R.id.phone_num);
        mailID=findViewById(R.id.mail_id);
        phnGo=findViewById(R.id.phoneGo);
        mailGo=findViewById(R.id.mailGo);
        password=findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();

        phn.setOnClickListener(this);
        mail.setOnClickListener(this);
        phnGo.setOnClickListener(this);
        mailGo.setOnClickListener(this);
        gooogl_btn.setOnClickListener(this);
        fBook.setOnClickListener(this);



    }


    public void signin_phone(){

       String phonenumber=phn_no.getText().toString().trim();
        Intent intent=new Intent(getApplicationContext(),otpActivity.class);
        intent.putExtra("phonenumber",phonenumber);
        startActivity(intent);

    }

    public void signin_mail(){
        String mail=mailID.getText().toString().trim();
        String pass=password.getText().toString().trim();
        try{
            mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG);

                        toast.show();




                        Intent intent=new Intent(Signin_Panel.this,Register.class);
                        startActivity(intent);

                    } else {
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                        switch (errorCode) {
                            case "ERROR_INVALID_CUSTOM_TOKEN":
                                Toast.makeText(Signin_Panel.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                Toast.makeText(Signin_Panel.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_CREDENTIAL":
                                Toast.makeText(Signin_Panel.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(Signin_Panel.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                mailID.setError("The email address is badly formatted.");
                                mailID.requestFocus();
                                break;
                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(Signin_Panel.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                password.setError("password is incorrect ");
                                password.requestFocus();
                                password.setText("");
                                break;
                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(Signin_Panel.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_REQUIRES_RECENT_":
                                Toast.makeText(Signin_Panel.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                Toast.makeText(Signin_Panel.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(Signin_Panel.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                mailID.setError("The email address is already in use by another account.");
                                mailID.requestFocus();
                                break;
                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(Signin_Panel.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_DISABLED":
                                Toast.makeText(Signin_Panel.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(Signin_Panel.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(Signin_Panel.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(Signin_Panel.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(Signin_Panel.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                break;
                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(Signin_Panel.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
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



    public void signin_gmail(){

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            idToken = account.getIdToken();
            name = account.getDisplayName();
            email = account.getEmail();
            // you can store user data to SharedPreference
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        }else{
            // Google Sign In failed, update UI appropriately
            Log.e("Google Signin", "Login Unsuccessful. "+result);
            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Google Signin", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){

                            Toast.makeText(Signin_Panel.this, "Login successful", Toast.LENGTH_SHORT).show();

                            gotoProfile();
                        }else{
                            Log.w("Google Signin", "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(Signin_Panel.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void gotoProfile(){
        Intent intent = new Intent(Signin_Panel.this, Profile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    public void onClick(View v) {


        if(v==phn){
            phn_no.setVisibility(View.VISIBLE);
            phnGo.setVisibility(View.VISIBLE);

        }
        if(v==phnGo){
            signin_phone();

        }
        if(v==mail){
            mailID.setVisibility(View.VISIBLE);
            mailGo.setVisibility(View.VISIBLE);


        }
        if(v==mailGo){
            signin_mail();


        }
        if(v==gooogl_btn){
            signin_gmail();

        }
        if(v==fBook){


        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
