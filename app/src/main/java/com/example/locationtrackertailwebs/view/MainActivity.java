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
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;

public class MainActivity extends AppCompatActivity {

        private Button yesButton, history;
        TextView logout;
        SharedPreferenceConfig sharedPreferenceConfig;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            yesButton = findViewById(R.id.button);
            history = findViewById(R.id.buttonStop);
            logout = findViewById(R.id.logoutId);

            sharedPreferenceConfig = new SharedPreferenceConfig(MainActivity.this);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDialog("Are you sure you want to logout?", "logout");
                }
            });
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
            private void setDialog(String s, String string) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage(s);
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                if (string.equals("back")){
                                    finish();
                                }else {
                                    sharedPreferenceConfig.LoginStatus(false);
                                    Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                                    startActivity(intent);
                                    finish();
                                }


                            }
                        });

                alertDialogBuilder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        setDialog("Are you sure you want to exit?", "back");
    }






}