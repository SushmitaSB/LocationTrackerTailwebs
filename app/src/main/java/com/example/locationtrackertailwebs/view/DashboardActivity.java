package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.SetAlertDialog;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.logoutId)
    TextView logout;
    @BindView(R.id.studenFormId)
     TextView textViewStudent;
    @BindView(R.id.trackingId)
     TextView trackingTextView;
    @BindView(R.id.chatbotId)
     TextView chatBotTv;
    @BindView(R.id.backButtonId)
    ImageView back;
    SetAlertDialog setAlertDialog;
    SharedPreferenceConfig sharedPreferenceConfig;
    private Animation a, b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializedVariables();

        textViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, StudentDetailsActivity.class);
                startActivity(intent);
            }
        });

        trackingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        chatBotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,ChatBot.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialog.setDialog("Are you sure you want to logout?", "logout",sharedPreferenceConfig);
            }
        });
    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        setAlertDialog = new SetAlertDialog(DashboardActivity.this);
        sharedPreferenceConfig = new SharedPreferenceConfig(DashboardActivity.this);
        a = AnimationUtils.loadAnimation(this, R.anim.scale);
        b= AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        b.reset();

        textViewStudent.clearAnimation();
        trackingTextView.clearAnimation();
        chatBotTv.clearAnimation();
        textViewStudent.startAnimation(a);
        trackingTextView.startAnimation(b);
        chatBotTv.startAnimation(a);
    }

    @Override
    public void onBackPressed() {
        setAlertDialog.setDialog("Do you Want to exit?","dashboard",sharedPreferenceConfig);
    }
}