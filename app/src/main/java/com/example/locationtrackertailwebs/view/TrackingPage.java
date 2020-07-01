package com.example.locationtrackertailwebs.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.locationtrackertailwebs.DirectionHelper.TaskLoadedCallback;
import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.Constants;
import com.example.locationtrackertailwebs.controler.service.LocationService;
import com.example.locationtrackertailwebs.model.EventBusPojo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class TrackingPage extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static ArrayList<Double> latList = new ArrayList<>();
    public static ArrayList<Double> longList = new ArrayList<>();
    private Chronometer chronometer;
    private Handler handler;
    long tmMilisec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;
    public static String total_time_track;
    private GoogleMap mMap;
    public static ArrayList<Double> serviceLatList;
    public static ArrayList<Double> serviceLongList;
    private Geocoder geocoder;
    private int ACCESS_LOCATION_REQUEST_CODE = 10001;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private  ArrayList<LatLng>  latLngArrayList = new ArrayList<>();
    public static ArrayList<Double> latListEventBus;
    public static ArrayList<Double> lonlistEventBus;
    public static Double latitudeEvent, longitudeEvent;

    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;
    Button stopTracking;
    LatLng latLng;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tmMilisec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tmMilisec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 1000);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_page);
        initializedVariables();
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    TrackingPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            startLocationService();
            tStart = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            chronometer.start();
        }


         MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(TrackingPage.this);


        //click on stop tracking button
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodeForStopButtonClick();
            }
        });

    }

    //stop button click method
    private void methodeForStopButtonClick() {
        total_time_track = min + ":" + sec;
        stopLocationService();
        tmMilisec = 0L;
        tBuff = 0L;
        tStart = 0L;
        tUpdate = 0L;
        sec = 0;
        min = 0;
        milliSec = 0;
        chronometer.setText("00:00:00");
        chronometer.stop();
        Toast.makeText(TrackingPage.this, "total tracked time: " + total_time_track, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initializedVariables() {
        stopTracking = findViewById(R.id.stopTrackingId);
        chronometer = findViewById(R.id.chronometer);
        handler = new Handler();
        latListEventBus = new ArrayList<>();
        lonlistEventBus = new ArrayList<>();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                chronometer.start();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //check foreground service is running or not
    private boolean isLocationServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }


    //start location service
    private void startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        }
    }


    //stop location service
    private void stopLocationService() {
        if (isLocationServiceRunning()) {
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
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        if (lonlistEventBus.size() != 0 && latListEventBus.size() !=0){
            setMarker();
        }

    }

    private void setMarker() {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitudeEvent,
                        longitudeEvent), 30));

        mMap.addPolyline((new PolylineOptions())
                .addAll(latLngArrayList)
                .width(10).color(Color.CYAN)
                .geodesic(true));

        createMarker(latitudeEvent,longitudeEvent, "Location: ");

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                new LatLng(latListEventBus.get(0),
//                        lonlistEventBus.get(0)), 16));
//        mMap.addMarker(place1);
//        mMap.addMarker(place2);
//        mMap.addPolyline((new PolylineOptions())
//                .addAll(latLngArrayList)
//                .width(10).color(Color.BLUE)
//                .geodesic(true));

//        for(int i = 0 ; i < latListEventBus.size() ; i++) {
//            createMarker(latListEventBus.get(i),lonlistEventBus.get(i), "Location: "+ i+1);
//        }
    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusPojo(EventBusPojo eventBusPojo) {

//        latListEventBus = eventBusPojo.getLatlist();
//        lonlistEventBus = eventBusPojo.getLonglist();
        latitudeEvent = eventBusPojo.getLatitude();
        longitudeEvent = eventBusPojo.getLongitude();

        if (latitudeEvent != null && longitudeEvent != null){
            latLng = new LatLng(latitudeEvent, longitudeEvent);
            latLngArrayList.add(latLng);
            if (mMap == null) {
                Log.d("MyMap", "setUpMapIfNeeded");
                MapFragment mapFragment = (MapFragment) getFragmentManager()
                        .findFragmentById(R.id.mapNearBy);
                mapFragment.getMapAsync(TrackingPage.this);
                setMarker();
            }else {
                setMarker();
            }
        }
//        if (latListEventBus.size() != 0 && lonlistEventBus.size() != 0){
//            for (int i=0; i<latListEventBus.size();i++ ){
//                LatLng latLng = new LatLng(latListEventBus.get(i), lonlistEventBus.get(i));
//                latLngArrayList.add(latLng);
//                if (mMap == null) {
//                    Log.d("MyMap", "setUpMapIfNeeded");
//                    MapFragment mapFragment = (MapFragment) getFragmentManager()
//                            .findFragmentById(R.id.mapNearBy);
//                    mapFragment.getMapAsync(TrackingPage.this);
//                    setMarker();
//                }else {
//                    setMarker();
//                }
//            }
//
//        }

    };

    @Override
    protected void onStop() {
        super.onStop();
       // stopLocationUpdates();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
      //  startLocationUpdates();
    }
}