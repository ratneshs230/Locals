package com.example.locals;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyLocationService extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    public MyLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(){
            Log.w("Service Created : ","yes");

            listener=new  LocationListener(){

                @Override
                public void onLocationChanged(Location location){
                    String longit;
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Log.w("USER_ID: ","  "+userId);
                    DatabaseReference ref_uid = FirebaseDatabase.getInstance().getReference("users/Users_Location/uid");
                    ref_uid.setValue(userId);
                    DatabaseReference ref_lat = FirebaseDatabase.getInstance().getReference("users/Users_Location/Latitude");
                    ref_lat.setValue(location.getLatitude());
                    DatabaseReference ref_long = FirebaseDatabase.getInstance().getReference("users/Users_Location/Longitude");
                    ref_long.setValue(location.getLongitude());
                    Log.w("Lat && Long : ",location.getLatitude()+"&"+location.getLongitude()+"");


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle){

                }
                @Override
                public void onProviderEnabled(String s){


                }

                @Override
                public void onProviderDisabled(String s){
                    Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Log.w("Provider ","Disabled");
                }
            };

            locationManager=(LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);




    }

    @Override
    public void onDestroy(){

        super.onDestroy();

        if(locationManager!=null){
            locationManager.removeUpdates(listener);
        }
    }

}
