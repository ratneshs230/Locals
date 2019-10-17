package com.example.locals;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    GoogleApiClient rGoogleApiClient;
    LocationRequest rlocationRequest;
    Switch rSwitch;
    GoogleMap rMap;
     static public final int REQUEST_LOCATION = 1;
    private FirebaseAuth.AuthStateListener authListener;
        FirebaseAuth rAuth;
        FirebaseDatabase rfirebaseDatabase;
        DatabaseReference rdatabaseReference;
        String userId;
        FirebaseCommService firebaseCommService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


         firebaseCommService=new FirebaseCommService();
         rSwitch=findViewById(R.id.location_switch);
         rAuth=FirebaseAuth.getInstance();
         rfirebaseDatabase=FirebaseDatabase.getInstance();
         rdatabaseReference=rfirebaseDatabase.getReference().child("users");
         FirebaseUser user;
        // user=rAuth.getCurrentUser();
         userId=/*user.getUid(); */"asw121qszcwd";         Log.w("USER_ID : ", userId);





        //if authenticaion of a user fails
        authListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MapsActivity.this, Login.class));
                    finish();
                }
            }
        };



        final Intent intent=new Intent(this,MyLocationService.class );
        final Intent intent1=new Intent(this,FirebaseCommService.class);

        //Switch try
        try {



            Permission_check();

    rSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {                    Log.w("Switch status : ", "Read");
                Permission_check();
                startService(intent);
                addFragment();

                firebaseCommService.getdata();
            } else {
                stopService(intent);
                stopService(intent1);

            }
        }
    });

    }
        catch (Exception e){                     Log.w("Switch log",e+"");
                    }
}

        private void addFragment(){
        Log.w("AddFragment ","Reached");
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            Profile_Fragment profile_fragment=new Profile_Fragment();
            fragmentTransaction.add(R.id.container,profile_fragment);
            fragmentTransaction.commit();
        }



    protected void Permission_check(){


            if (ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            Log.w("permission ","Not Granted");
;                if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    new AlertDialog.Builder(MapsActivity.this)
                            .setMessage("Your Location is needed to put you up on The Global Map")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    getApplicationContext().startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .show();
                }
                else{
                    ActivityCompat.requestPermissions(MapsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION);
            }
            }else{

                Log.w("permission ","Granted");
                //Permissions Already Granted


            }


            LocationManager rlocationmanager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = rlocationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}

            try {
                network_enabled = rlocationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch(Exception ex) {}


            if(!gps_enabled && !network_enabled) {

                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("Join the locals around you")
                        .setMessage("Turn On Location Service")
                        .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();


            }else{

            }


        }

   

    @Override
    public void onMapReady(GoogleMap googleMap) {
        rMap = googleMap;
        buildGoogleApiClient();


    }

    protected synchronized void buildGoogleApiClient(){
https://developer.android.com/training/ts
        rGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        rGoogleApiClient.connect();
    }


    public void onConnected(@Nullable Bundle bundle) {
        try {
            rlocationRequest = new LocationRequest();
            rlocationRequest.setInterval(1000);
            rlocationRequest.setFastestInterval(1000);
            rlocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(rGoogleApiClient, rlocationRequest, (com.google.android.gms.location.LocationListener) this);

        }catch(Exception e){                Log.w("Error : ",""+e);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onConnectionSuspended(int i) {

    }

}