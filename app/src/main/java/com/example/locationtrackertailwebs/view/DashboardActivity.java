package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.SetAlertDialog;
import com.example.locationtrackertailwebs.controler.SharedPreferenceConfig;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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
    MixpanelAPI mMixpanel;
    CheckInternetConnection checkInternetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializedVariables();
        mMixpanel = MixpanelAPI.getInstance(DashboardActivity.this, "a148e9a83a76399494cb3ebacb0513d0");
        setMethodforMixPanel();
        textViewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(DashboardActivity.this, StudentDetailsActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(DashboardActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        trackingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(DashboardActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chatBotTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection.hasConnection()) {
                    Intent intent = new Intent(DashboardActivity.this, ChatBot.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(DashboardActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
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

        checkInternetConnection = new CheckInternetConnection(DashboardActivity.this);
    }

    @Override
    public void onBackPressed() {
        setAlertDialog.setDialog("Do you Want to exit?","dashboard",sharedPreferenceConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMethodforMixPanel();

// Check a user's opt-out status
// Returns true of user is opted out of tracking locally
       // Boolean hasOptedOutTracking = mMixpanel.hasOptedOutTracking();
//        JSONObject props = new JSONObject();
//        try {
//            props.put("User Type", "Paid");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        mMixpanel.registerSuperProperties(props);
//
//        mMixpanel.identify("13794");
//
//// Ensure all future user profile properties sent from
//// the device will have the distinct_id 13793
//        mMixpanel.getPeople().identify("13794");

    }

    private void setMethodforMixPanel() {
        //mMixpanel.alias("1",null);
       // mMixpanel.optOutTracking();
      //  String id = mMixpanel.getDistinctId();
       // mMixpanel.identify(mMixpanel.getDistinctId());
      //  mMixpanel.getPeople().identify(mMixpanel.getDistinctId());
      //  mMixpanel.getPeople().set("location", "on");
       // mMixpanel.getPeople().set("date", new Date());
        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Female");
            props.put("Logged in", true);
            mMixpanel.track("DashboardActivity - onCreate called - LocatonTracking", props);
        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
            Log.e("MYAPP-Error-Mixpanel",  e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        //mMixpanel.flush();
        super.onDestroy();
    }
}