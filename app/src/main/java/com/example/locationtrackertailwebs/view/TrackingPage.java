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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.Constants;
import com.example.locationtrackertailwebs.controler.PermissionManager;
import com.example.locationtrackertailwebs.controler.SetAlertDialog;
import com.example.locationtrackertailwebs.controler.SetGoogleMap;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;
import com.example.locationtrackertailwebs.controler.service.LocationService;
import com.example.locationtrackertailwebs.model.EventBusPojo;
import com.example.locationtrackertailwebs.model.StopServiceEventBus;
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

public class TrackingPage extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Chronometer chronometer;
    private Handler handler;
    long tmMilisec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec,hour;
    public static String total_time_track;
    private GoogleMap mMap;
    private  ArrayList<LatLng>  latLngArrayList = new ArrayList<>();
    public static ArrayList<Double> latListEventBus;
    public static ArrayList<Double> lonlistEventBus;
    public static Double latitudeEvent, longitudeEvent;
    private CheckInternetConnection checkInternetConnection;
    Button stopTracking;
    TextView secTextView;
    LatLng latLng;
    SetGoogleMap setGoogleMap;
    SharedPreferenceConfig sharedPreferenceConfig;
    PermissionManager permissionManager;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (checkInternetConnection.hasConnection()) {
                tmMilisec = SystemClock.uptimeMillis() - tStart;
                tUpdate = tBuff + tmMilisec;
                hour = (int) (tUpdate / 3600000);
                min = (int) (tUpdate - hour * 3600000) / 60000;
                sec = (int) (tUpdate - hour * 3600000 - min * 60000) / 1000;
                String t = (hour < 10 ? "0" + hour : hour) + ":" + (min < 10 ? "0" + min : min);
                String sectv = (sec < 10 ? "0" + sec : sec) + " seconds";
                chronometer.setText(t);
                secTextView.setText(sectv);
                handler.postDelayed(this, 60);
            }else {
                Toast.makeText(TrackingPage.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tracking_page);
        initializedVariables();

        sharedPreferenceConfig.BackButtonStatus(false);
        permissionManager = new PermissionManager(this);
        if (!permissionManager.setLocationPermission()){
            stopTracking.setEnabled(false);
        } else {
            if (checkInternetConnection.hasConnection()) {
                startLocationService();
                tStart = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                chronometer.start();
                stopTracking.setEnabled(true);
            }else {
                Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                stopLocationService();
                tmMilisec = 0L;
                tBuff = 0L;
                tStart = 0L;
                tUpdate = 0L;
                hour = 0;
                sec = 0;
                min = 0;
//        milliSec = 0;
                chronometer.setText("00:00");
                secTextView.setText("00 seconds");
                chronometer.stop();
                Toast.makeText(TrackingPage.this, "total tracked time: " + total_time_track, Toast.LENGTH_SHORT).show();
                stopTracking.setEnabled(false);
            }
        }
        setFragment();
        //click on stop tracking button
        stopTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                methodeForStopButtonClick();
                }else{
                    Toast.makeText(TrackingPage.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //stop button click method
    public void methodeForStopButtonClick() {
        total_time_track =hour + ":" + min + ":" + sec;
        stopLocationService();
        tmMilisec = 0L;
        tBuff = 0L;
        tStart = 0L;
        tUpdate = 0L;
        hour = 0;
        sec = 0;
        min = 0;
//        milliSec = 0;
        chronometer.setText("00:00");
        secTextView.setText("00 seconds");
        chronometer.stop();
        Toast.makeText(TrackingPage.this, "total tracked time: " + total_time_track, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void initializedVariables() {
        stopTracking = findViewById(R.id.stopTrackingId);
        chronometer = findViewById(R.id.chronometer);
        secTextView = findViewById(R.id.secId);
        handler = new Handler();
        latListEventBus = new ArrayList<>();
        lonlistEventBus = new ArrayList<>();
        setGoogleMap = new SetGoogleMap(TrackingPage.this);
        sharedPreferenceConfig = new SharedPreferenceConfig(this);
        checkInternetConnection = new CheckInternetConnection(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkInternetConnection.hasConnection()) {
                    startLocationService();
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    stopTracking.setEnabled(true);
                }else{
                    Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                stopTracking.setEnabled(false);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        if (lonlistEventBus.size() != 0 && latListEventBus.size() !=0){
            setMarker();
            //setGoogleMap.setMarkerAndPolyLine(mMap,null,null,latLngArrayList,longitudeEvent,longitudeEvent,"track");
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

    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusPojo(EventBusPojo eventBusPojo) {
        latitudeEvent = eventBusPojo.getLatitude();
        longitudeEvent = eventBusPojo.getLongitude();

        if (latitudeEvent != null && longitudeEvent != null){
            latLng = new LatLng(latitudeEvent, longitudeEvent);
            setGoogleMap = new SetGoogleMap(TrackingPage.this);
            latLngArrayList.add(latLng);
            if (mMap == null) {
                Log.d("MyMap", "setUpMapIfNeeded");
                setFragment();
                setMarker();
               // setGoogleMap.setMarkerAndPolyLine(mMap,null,null,latLngArrayList,longitudeEvent,longitudeEvent,"track");
            }else {
                setMarker();
               // setGoogleMap.setMarkerAndPolyLine(mMap,null,null,latLngArrayList,longitudeEvent,longitudeEvent,"track");
            }
        }

    }

    @Subscribe
    public void onServiceStopEventBus(StopServiceEventBus stopServiceEventBus){
        if (checkInternetConnection.hasConnection()) {
            methodeForStopButtonClick();
        }else {
            Toast.makeText(this, "There is bo internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFragment() {
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(TrackingPage.this);
    }

    ;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBackPressed() {
        if (checkInternetConnection.hasConnection()) {
            SetAlertDialog setAlertDialog = new SetAlertDialog(this);
            setAlertDialog.setDialog("Are you sure you want to stop tracking and exit?", "track", sharedPreferenceConfig);
        }else {
            finish();
        }
    }
}