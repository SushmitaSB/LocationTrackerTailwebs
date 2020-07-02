package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.SetAlertDialog;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity {

        private Button yesButton, history;
        SetAlertDialog setAlertDialog;
        SharedPreferenceConfig sharedPreferenceConfig;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initializedVariables();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TrackingPage.class);
                    startActivity(intent);
                }
            });

            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // stopLocationService();
                    Intent intent = new Intent(MainActivity.this, TrackingHistory.class);
                    startActivity(intent);
                }
            });

        }

    private void initializedVariables() {
        yesButton = findViewById(R.id.button);
        history = findViewById(R.id.buttonStop);
        setAlertDialog = new SetAlertDialog(this);
        sharedPreferenceConfig = new SharedPreferenceConfig(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
       finish();
    }






}