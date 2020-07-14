package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.CheckInternetConnection;
import com.example.locationtrackertailwebs.controler.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = "RegistrationActivity";

    @BindView(R.id.firstNameET)
    EditText nameEt;

    @BindView(R.id.lastNameET)
    EditText lastNameEt;

    @BindView(R.id.emailid)
    EditText emailEt;

    @BindView(R.id.passId)
    EditText passEt;

    @BindView(R.id.conPassId)
    EditText conPassEt;

    @BindView(R.id.btId)
    Button button;

    @BindView(R.id.clear)
    ImageView imageViewClear;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;
    private MixpanelAPI mixpanelAPI;

    private  CheckInternetConnection checkInternetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
       // final String trackingDistinctId = getTrackingDistinctId();

        initializedVariables();
        imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, IntroActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fName, lName, pass, email, cpass;
                fName = nameEt.getText().toString();
                lName = lastNameEt.getText().toString();
                pass = passEt.getText().toString();
                email = emailEt.getText().toString();
                cpass = conPassEt.getText().toString();
                if (checkInternetConnection.hasConnection()) {
                Validation validation = new Validation(RegistrationActivity.this);
                validation.setSigninValidation(fName, lName, email, pass, cpass, firebaseAuth, firestore, nameEt, lastNameEt, emailEt, passEt, conPassEt);
            }else {
                    Toast.makeText(RegistrationActivity.this, "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mixpanelAPI = MixpanelAPI.getInstance(this, "e43755ee573638205f4ad2501fcead66");
        mixpanelAPI.getPeople().set("open date", new Date());
        mixpanelAPI.identify(mixpanelAPI.getDistinctId());
        mixpanelAPI.track("onResume_Register");
        mixpanelAPI.flush();
       // mixpanelAPI.people.set("open date", new Date());

    }

    private void initializedVariables() {
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        checkInternetConnection = new CheckInternetConnection(RegistrationActivity.this);
    }
}