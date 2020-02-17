package com.msg91.sendotp.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Mainactivity2 extends AppCompatActivity {
  Location location;
    String address, city, state, country, postalCode, knownName;
    SharedPreferences sh,shp,shh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity2);
        startService();
        shh = getSharedPreferences("Official12", MODE_PRIVATE);
        SharedPreferences.Editor e = shh.edit();
        e.putBoolean("ph12", true);
        e.apply();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        shp = getSharedPreferences("data111", MODE_PRIVATE);
        //Toast.makeText(Mainactivity2 .this,shp.getString("cph",null),Toast.LENGTH_LONG).show();
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //  NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);





        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService();

                handler.postDelayed(this, 10000);
            }


        }, 10000);

               /* Toast.makeText(getApplicationContext(),
                        "Trying to obtain GPS coordinates. Make sure you have location services on.",
                        Toast.LENGTH_SHORT).show();*/

    }

    private void startService() {


        sh=getSharedPreferences("loc",MODE_PRIVATE);
        SharedPreferences.Editor ed=sh.edit();



        LocationManager locationManager = (LocationManager)Mainactivity2.this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(Mainactivity2.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Mainactivity2.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return root;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new Mainactivity2.Listener());
        // Have another for GPS provider just in case.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, new Mainactivity2.Listener());
        // Try to request the location immediately
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (location != null) {
            handleLatLng(location.getLatitude(), location.getLongitude());
            ed.putString("laa",String.valueOf(location.getLatitude()));
            ed.putString("loo",String.valueOf(location.getLongitude()));
            ed.apply();

        }
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", address);
        serviceIntent.putExtra("inputExtr", location.getLatitude());
        serviceIntent.putExtra("inputExt", location.getLongitude());
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    /**
     * Handle lat lng.
     */
    private void handleLatLng(final double latitude, final double longitude) {
        Log.v("TAG", "(" + latitude + "," + longitude + ")");
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(Mainactivity2.this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        country = addresses.get(0).getCountryName();
        postalCode = addresses.get(0).getPostalCode();
        knownName = addresses.get(0).getFeatureName();
       //


    }



    class Listener implements LocationListener {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            handleLatLng(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }}
    private final Runnable refresher = new Runnable() {
        public void run() {
            // some action
        }
    };
}