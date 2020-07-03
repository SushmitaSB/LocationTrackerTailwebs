package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.SetGoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Bundle bundle;
    @BindView(R.id.clearback)
    ImageView imageView;
    SetGoogleMap setGoogleMap;
    //Dummy data
//    private static  LatLng p1 = new LatLng(27.658143, 85.3199503);
//    private static  LatLng p2 = new LatLng(27.667491, 85.3208583);
//    private static  LatLng p3 = new LatLng(27.667591, 85.3608583);
//    private static  LatLng p4 = new LatLng(27.667791, 85.3708583);
//    private static LatLng p5 = new LatLng(27.668991, 85.3808583);
//    private static  LatLng p6 = new LatLng(27.699491, 85.3908583);
//    private static  LatLng p7 = new LatLng(27.867491, 85.4108583);
    ArrayList<Double> latList;
    ArrayList<Double> longList;
    ArrayList<LatLng> latLngArrayList;
    ArrayList<Marker> markerOptionsArrayList;
    private CheckInternetConnection checkInternetConnection;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);
        initializedVariables();
        if (bundle != null){
            latList = (ArrayList<Double>) getIntent().getSerializableExtra("Lat_List");
            longList = (ArrayList<Double>) getIntent().getSerializableExtra("Long_List");
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i=0; i<longList.size();i++ ){
            LatLng latLng = new LatLng(latList.get(i), longList.get(i));
            latLngArrayList.add(latLng);
        }
       // int index = latList.size()-1;
        //place1 = new MarkerOptions().position(new LatLng(latList.get(0), longList.get(0))).title("Location 1");
       // place2 = new MarkerOptions().position(new LatLng(latList.get(index), longList.get(index))).title("Location 2");
       // new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");



    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        latList = new ArrayList<>();
        longList = new ArrayList<>();
        latLngArrayList = new ArrayList<>();
        markerOptionsArrayList = new ArrayList<>();
        i = getIntent();
        bundle=i.getExtras();
        methodForFragmentSetUp();
        setGoogleMap = new SetGoogleMap(MapActivity.this);
        checkInternetConnection = new CheckInternetConnection(MapActivity.this);

    }

    public void methodForFragmentSetUp(){
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        if (checkInternetConnection.hasConnection()) {
            setGoogleMap.setMarkerAndPolyLine(mMap, latList, longList, latLngArrayList, null, null, "map");
        }else {
            Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}