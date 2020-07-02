package com.example.locationtrackertailwebs.controler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.locationtrackertailwebs.view.TrackingPage;

public class PermissionManager {
    private Context context;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public PermissionManager(){

    }

    public PermissionManager(Context context){
        this.context = context;
    }

    public boolean setLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    (Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );

            return false;
        }else {
            return true;
        }

    }
}
