package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    SetAlertDialog setAlertDialog;
    SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        setAlertDialog = new SetAlertDialog(DashboardActivity.this);
        sharedPreferenceConfig = new SharedPreferenceConfig(DashboardActivity.this);

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
                Intent intent = new Intent(DashboardActivity.this,ChatBotActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlertDialog.setDialog("Are you sure you want to logout?", "logout",sharedPreferenceConfig);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setAlertDialog.setDialog("Do you Want to exit?","dashboard",sharedPreferenceConfig);
    }
}