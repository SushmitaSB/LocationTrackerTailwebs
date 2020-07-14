package com.example.locationtrackertailwebs.controler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.locationtrackertailwebs.view.DashboardActivity;
import com.example.locationtrackertailwebs.view.LoginActivity;
import com.example.locationtrackertailwebs.view.MainActivity;
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

import static com.example.locationtrackertailwebs.view.RegistrationActivity.TAG;


public class Validation {

    private Context context;
    private SharedPreferenceConfig sharedPreferenceConfig;
    private String userId;
    public Validation(Context context) {
        this.context = context;

    }
    //Registration field validation
    public void setSigninValidation(final String fName, final String lName, final String email, String pass, String cpass, final FirebaseAuth firebaseAuth, final FirebaseFirestore firestore,
                                    EditText nameEt, EditText lastNameEt, EditText emailEt, EditText passEt, EditText conPassEt){

        if (!TextUtils.isEmpty(fName) && !TextUtils.isEmpty(lName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass)){
            try {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.matches(emailPattern))
                {
                    if (pass.equals(cpass) ){
                        if (pass.length() >= 6){


                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show();
                                            userId = firebaseAuth.getCurrentUser().getUid();
                                            DocumentReference  documentReference = firestore.collection("users").document(userId);
                                            Map<String,Object> user = new HashMap<>();
                                            user.put("firstname", fName);
                                            user.put("lasttname", lName);
                                            user.put("email", email);
                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "successfully user profile is created for uid-  " +
                                                            userId);
                                                    ((Activity)context).finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "onFilure  " +
                                                            e.toString());
                                                }
                                            });
                                            Intent intent = new Intent(context, LoginActivity.class);
                                            context.startActivity(intent);
                                        }else {
                                            Log.d(TAG, "onFilure  " +
                                                    task.getException());
                                            Toast.makeText(context, "Email is already exist! " , Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }else {
                            Toast.makeText(context, "Password length should be atleast 6", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,"Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context,"Invalid email address", Toast.LENGTH_SHORT).show();

                }

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }else if (TextUtils.isEmpty(fName)){
            nameEt.setError("Please enter your first name");

        }else if (TextUtils.isEmpty(lName)){
            lastNameEt.setError("Please enter your last name");
        }
        else if (TextUtils.isEmpty(email)){

            emailEt.setError("Please enter your email");
        }else if (TextUtils.isEmpty(pass)){
            passEt.setError("Please enter your password");

        }else if (TextUtils.isEmpty(cpass)){
            conPassEt.setError("Please enter your password");

        }

    }


    //StudentForm validation
    public boolean setFormValidation(RealmManager realmManager, String name, String sub, String marks, EditText nameEt, EditText subEt, EditText marksEt){
        boolean status = false;
        if (!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(sub) && !TextUtils.isEmpty(marks) ){
            try {
                if (Float.parseFloat(marks) > 100){
                    marksEt.setError("Marks should not be more than 100");
                }else {
                    realmManager.setDataInStudentDetails(name, sub, marks);
                    status = true;
                }

            }catch (Exception ex){
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                status = false;
            }
        }else if(TextUtils.isEmpty(name)){
            nameEt.setError("Enter student name");
            status = false;
        }else if (TextUtils.isEmpty(sub)){
            subEt.setError("Enter subject name");
            status = false;
        }else if (TextUtils.isEmpty(marks)){
            marksEt.setError("Enter marks");
            status = false;
        }

        return status;
    }



    // method for login validation
public void setLoginValidation( final String email, String pass,final FirebaseAuth firebaseAuth,
                                 EditText emailEt, EditText passEt){
    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
        try {
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        String userEmail = firebaseAuth.getCurrentUser().getEmail();
                        sharedPreferenceConfig = new SharedPreferenceConfig(context);
                        sharedPreferenceConfig.LoginStatus(true);
                        sharedPreferenceConfig.LoginUser(userEmail);
                        Intent intent = new Intent(context, DashboardActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }else {
                        Toast.makeText(context, "Error ! "+ "Please enter valid email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception ex){
            Toast.makeText(context, "Exception: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }else if (TextUtils.isEmpty(email)){
        emailEt.setError("please type your email");
    }else if (TextUtils.isEmpty(pass)){
        passEt.setError("Please type valid password");
    }

}

}
