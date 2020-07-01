package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferenceConfig = new SharedPreferenceConfig(SplashActivity.this);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferenceConfig.read_login_status()){
                    Intent newIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(newIntent);
                }else {
                    Intent newIntent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(newIntent);
                }
                finish();
            }
        },3000);
    }
}