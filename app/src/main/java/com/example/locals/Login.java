package com.example.locals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText username;
    EditText password;
    Button login;
    TextView register_page;
    ProgressBar progressBar;
    TextView forgot_pass;
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton google_btn;
    private GoogleApiClient googleApiClient;
    private final int google_signin=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        google_btn=findViewById(R.id.google_sign_in_button);


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            mAuth = FirebaseAuth.getInstance();
            username = findViewById(R.id.login_username);
            password = findViewById(R.id.password);
            forgot_pass = findViewById(R.id.forgot_password);
            login = findViewById(R.id.login_btn);
            register_page = findViewById(R.id.register_page);
            progressBar = findViewById(R.id.progress_bar);
            login.setOnClickListener(this);
            register_page.setOnClickListener(this);
            forgot_pass.setOnClickListener(this);
            try {
                google_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        google_signIN();
                    }
                });
            }catch(Exception e){
                Log.w("error",e);
            }



            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                }
            });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }

    }

    //firebase email login
    public void signin(){
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        try{
        mAuth.signInWithEmailAndPassword(user,pass)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.w("Signin","Successful");
                    FirebaseUser user=mAuth.getCurrentUser();
                    Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(intent);
                }
                    else {

                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                    switch (errorCode) {
                        case "ERROR_INVALID_CUSTOM_TOKEN":
                            Toast.makeText(Login.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_CUSTOM_TOKEN_MISMATCH":
                            Toast.makeText(Login.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_INVALID_CREDENTIAL":
                            Toast.makeText(Login.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_INVALID_EMAIL":
                            Toast.makeText(Login.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                            username.setError("The email address is badly formatted.");
                            username.requestFocus();
                            break;
                        case "ERROR_WRONG_PASSWORD":
                            Toast.makeText(Login.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                            password.setError("password is incorrect ");
                            password.requestFocus();
                            password.setText("");
                            break;
                        case "ERROR_USER_MISMATCH":
                            Toast.makeText(Login.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_REQUIRES_RECENT_":
                            Toast.makeText(Login.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                            Toast.makeText(Login.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_EMAIL_ALREADY_IN_USE":
                            Toast.makeText(Login.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                            username.setError("The email address is already in use by another account.");
                            username.requestFocus();
                            break;
                        case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                            Toast.makeText(Login.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_USER_DISABLED":
                            Toast.makeText(Login.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_USER_TOKEN_EXPIRED":
                            Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_USER_NOT_FOUND":
                            Toast.makeText(Login.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_INVALID_USER_TOKEN":
                            Toast.makeText(Login.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_OPERATION_NOT_ALLOWED":
                            Toast.makeText(Login.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                            break;
                        case "ERROR_WEAK_PASSWORD":
                            Toast.makeText(Login.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                            password.setError("The password is invalid it must 6 characters at least");
                            password.requestFocus();
                            break;



                    }
                }


            }
        });

        }catch (Exception e){
            Log.w("Error in signing "," "+e);
            Toast toast=Toast.makeText(getApplicationContext(),"TRY AGAIN!",Toast.LENGTH_LONG);
            toast.show();
        }



    }
    private void register_page(){
        Intent intent=new Intent(getApplicationContext(),Register.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == google_signin) {



            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(Login.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    public void google_signIN(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, google_signin);
    }
    @Override
    public void onClick(View v) {
        if(v==login){

            signin();

        }
        if(v==register_page){

        register_page();
        }

        if(v==google_btn){
            google_signIN();
        }
        if(v==forgot_pass){
            //run code for forgot password
        }

    }
}
