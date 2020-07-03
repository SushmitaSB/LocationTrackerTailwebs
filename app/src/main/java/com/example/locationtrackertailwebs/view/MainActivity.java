package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.SetAlertDialog;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity {

        private Button yesButton, history;
        SetAlertDialog setAlertDialog;
        SharedPreferenceConfig sharedPreferenceConfig;
        CheckInternetConnection checkInternetConnection;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initializedVariables();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInternetConnection.hasConnection()) {
                        Intent intent = new Intent(MainActivity.this, TrackingPage.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // stopLocationService();
                    if (checkInternetConnection.hasConnection()) {
                        Intent intent = new Intent(MainActivity.this, TrackingHistory.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    private void initializedVariables() {
        yesButton = findViewById(R.id.button);
        history = findViewById(R.id.buttonStop);
        setAlertDialog = new SetAlertDialog(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(MainActivity.this);
        checkInternetConnection = new CheckInternetConnection(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       finish();
    }






}