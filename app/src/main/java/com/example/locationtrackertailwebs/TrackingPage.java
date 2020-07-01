package com.example.locationtrackertailwebs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.locationtrackertailwebs.DirectionHelper.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

public class TrackingPage extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static ArrayList<Double> latList = new ArrayList<>();
    public static ArrayList<Double> longList = new ArrayList<>();
    private Chronometer chronometer;
    private Handler handler;
    long tmMilisec, tStart, tBuff, tUpdate=0L;
    int sec, min, milliSec;
    public static String total_time_track;

    Button stopTracking;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tmMilisec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tmMilisec;
            sec = (int) (tUpdate/1000);
            min = sec/60;
            sec = sec%60;
            milliSec = (int) (tUpdate%1000);
            chronometer.setText(String.format("%02d", min)+":"+String.format("%02d", sec)+":"+String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);
        stopTracking = findViewById(R.id.stopTrackingId);
        chronometer = findViewById(R.id.chronometer);
        handler = new Handler();
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    TrackingPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }else {
            startLocationService();
            tStart = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            chronometer.start();
        }

        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_time_track = min + ":"+ sec;
                stopLocationService();
                tmMilisec =0L;
                tBuff = 0L;
                tStart = 0L;
                tUpdate = 0L;
                sec=0;
                min = 0;
                milliSec = 0;
                chronometer.setText("00:00:00");
                chronometer.stop();
                Toast.makeText(TrackingPage.this, "total tracked time: " + total_time_track, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationService();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null){
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)){
                if (LocationService.class.getName().equals(service.service.getClassName())){
                    if (service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService(){
        if (!isLocationServiceRunning()){
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService(){
        if (isLocationServiceRunning()){
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskDone(Object... values) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}