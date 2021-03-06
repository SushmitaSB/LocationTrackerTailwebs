package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.PermissionManager;
import com.example.locationtrackertailwebs.controler.adapter.SliderAdapter;
import com.example.locationtrackertailwebs.controler.ZoomOutPageTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.getStarted)
    Button bt;

    @BindView(R.id.loginLayoutId)
    LinearLayout loginLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private CheckInternetConnection checkInternetConnection;
    private SliderAdapter sliderAdapter;
    PermissionManager permissionManager;
    private String data;
    private Handler handler;
    private int delay = 3000; //milliseconds
    private int page = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            if (sliderAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            viewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        initializedVariables();
        permissionManager.setLocationPermission();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //GetStarted button click
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this, RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });

        //login layout click
        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        handler = new Handler();
        sliderAdapter = new SliderAdapter(IntroActivity.this);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setAdapter(sliderAdapter);
        permissionManager = new PermissionManager(IntroActivity.this);
        checkInternetConnection = new CheckInternetConnection(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}