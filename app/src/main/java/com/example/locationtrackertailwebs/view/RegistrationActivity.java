package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fName, lName, pass, email, cpass;
                fName = nameEt.getText().toString();
                lName = lastNameEt.getText().toString();
                pass = passEt.getText().toString();
                email = emailEt.getText().toString();
                cpass = conPassEt.getText().toString();

                Validation validation = new Validation(RegistrationActivity.this);
                validation.setSigninValidation(fName, lName, email, pass,cpass,firebaseAuth,firestore,nameEt,lastNameEt,emailEt,passEt,conPassEt);
                if (Validation.reg_status){
                    finish();
                }
            }
        });
    }
}