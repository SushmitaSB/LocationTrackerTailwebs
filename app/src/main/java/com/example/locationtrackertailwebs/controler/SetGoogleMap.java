package com.example.locationtrackertailwebs.controler;

import android.content.Context;
import android.graphics.Color;

import com.example.locationtrackertailwebs.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class SetGoogleMap {
    private Context context;
    public SetGoogleMap(Context context) {
        this.context = context;
    }

    public void setMarkerAndPolyLine(GoogleMap mMap, ArrayList<Double> latitude, ArrayList<Double> longitude, ArrayList<LatLng> latLngArrayList, Double lat, Double longi, String string){

       if (string.equals("map")){
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                   new LatLng(latitude.get(0),longitude.get(0)), 15));
           int index = latitude.size()-1;
           createMarker(latitude.get(0),longitude.get(0), "Start Location: ", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),mMap);
           createMarker(latitude.get(index),longitude.get(index), "End Location: ", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN),mMap);
       }else {
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                   new LatLng(lat,
                           longi), 15));
           createMarker(lat,longi, "Location: ",BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),mMap);
       }

        mMap.addPolyline((new PolylineOptions())
                .addAll(latLngArrayList)
                .width(10).color(Color.CYAN)
                .geodesic(true));


    }

    protected Marker createMarker(double latitude, double longitude, String title, BitmapDescriptor bitmapDescriptor, GoogleMap mMap) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(bitmapDescriptor));
    }

}
