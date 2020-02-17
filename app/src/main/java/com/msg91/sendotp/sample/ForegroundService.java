package com.msg91.sendotp.sample;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForegroundService extends Service {

SharedPreferences sh,shp;

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public void onCreate() {

        super.onCreate();



        sh=getSharedPreferences("loc",MODE_PRIVATE);
        shp=getSharedPreferences("data111",MODE_PRIVATE);
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String input = intent.getStringExtra("inputExtra");
//        final String inpu = intent.getStringExtra("inputExtr");
//        final String inp = intent.getStringExtra("inputExt");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, Signin.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("YOUR LOCATION")
                .setContentText(input)
                .setSmallIcon(R.drawable.placemap)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);






        //do heavy work on a background thread
        //stopSelf();
//        new CountDownTimer(10000,1000) {
//
//            public void onTick(long timeRemaining) {
//                Toast.makeText(ForegroundService .this,"submitted",Toast.LENGTH_LONG).show();
////                Toast.makeText(getBaseContext(), "" + timeRemaining / 1000,
////                        Toast.LENGTH_SHORT).show();
//            }
//
//            public void onFinish() {
//                // do something
//            }
//
//        }.start( );
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://androidprojectstechsays.000webhostapp.com/C_traker/location.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//If we are getting success from server
                                // Toast.makeText(ForegroundService.this,response,Toast.LENGTH_LONG).show();
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                            }

                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();


                        params.put("add", input);
                        params.put("la", sh.getString("laa",null));
                        params.put("lo", sh.getString("loo",null));
                        params.put("ph", shp.getString("cph",null));


//returning parameter
                        return params;
                    }

                };

// m = Integer.parseInt(ba) - Integer.parseInt(result.getContents());
// balance.setText(m+"");


//Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(ForegroundService.this);
                requestQueue.add(stringRequest);
              //  Toast.makeText(ForegroundService .this,input+sh.getString("laa",null)+sh.getString("loo",null)+shp.getString("cph",null),Toast.LENGTH_LONG).show();
                handler.postDelayed(this, 20000);
            }


        }, 20000);
    ;

        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }



    }

}