package com.example.locationtrackertailwebs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

    public static final String TAG = "RegistrationActivity";
    @BindView(R.id.loginId)
    TextView textViewLogin;

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

//                firebaseAuth.createUserWithEmailAndPassword(email, pass)
//                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(RegistrationActivity.this, "User created", Toast.LENGTH_SHORT).show();
//                                    userId = firebaseAuth.getCurrentUser().getUid();
//                                    DocumentReference  documentReference = firestore.collection("users").document(userId);
//                                    Map<String,Object> user = new HashMap<>();
//                                    user.put("firstname", fName);
//                                    user.put("lasttname", lName);
//                                    user.put("email", email);
//                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            Log.d(TAG, "successfully user profile is created for uid-  " +
//                                                    userId);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d(TAG, "onFilure  " +
//                                                    e.toString());
//                                        }
//                                    });
//                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                }else {
//                                    Log.d(TAG, "onFilure  " +
//                                            task.getException());
//                                    Toast.makeText(RegistrationActivity.this, "error ! "+ task.getException(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
            }
        });
    }
}