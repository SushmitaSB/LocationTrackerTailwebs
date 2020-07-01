package com.example.locationtrackertailwebs.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.locationtrackertailwebs.R;
import com.example.locationtrackertailwebs.controler.Validation;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText emailEt;
    @BindView(R.id.input_password)
    EditText passEt;
    @BindView(R.id.btn_login)
    Button button;
    @BindView(R.id.link_signup)
    LinearLayout linearLayout;
    private String email, password;
    private Validation validation;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEt.getText().toString();
                password = passEt.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();
                Validation validation = new Validation(LoginActivity.this);
                validation.setLoginValidation(email,password,firebaseAuth,emailEt,passEt);
//                if (Validation.log_status){
//                    finish();
//                }
            }
        });

    }
}