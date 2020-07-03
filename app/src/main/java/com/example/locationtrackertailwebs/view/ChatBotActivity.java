package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonElement;
import java.util.Map;
import com.example.locationtrackertailwebs.R;

public class ChatBotActivity extends AppCompatActivity  {

    private static final int PERMISSION_REQUEST_AUDIO = 0;
    private static final String TAG = "MainActivity";

    private Button listenButton;
    private TextView inputTextView;
    private TextView fulfillmentTextView;
    private TextView actionTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        listenButton = findViewById(R.id.listenButton);
        fulfillmentTextView = findViewById(R.id.fulfillmentText);
        inputTextView = findViewById(R.id.inputText);
        actionTextView = findViewById(R.id.actionText);

    }





}