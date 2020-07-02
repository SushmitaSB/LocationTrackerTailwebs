package com.example.locationtrackertailwebs.controler;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import com.example.locationtrackertailwebs.model.studentDetails;
import com.example.locationtrackertailwebs.view.LoginActivity;
import com.example.locationtrackertailwebs.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {
    public static  boolean STATUS = false;
    public static  boolean LOGIN_STATUS = false;
    private RealmResults<studentDetails> realmResults;
    private Context context;
    private Realm realm;
    private SharedPreferenceConfig sharedPreferenceConfig;

    private  FirebaseAuth firebaseAuth;
    //constructor
    public RealmManager(Context context, Realm realm) {
        this.context = context;
        this.realm = realm;
        firebaseAuth = FirebaseAuth.getInstance();
    }
    // fetching student details from realm database
    public RealmResults<studentDetails> fetchStudentDetails(){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                sharedPreferenceConfig = new SharedPreferenceConfig(context);
                String userEmail = firebaseAuth.getCurrentUser().getEmail();
                realmResults = realm.where(studentDetails.class)
                        .equalTo(studentDetails.UserId, userEmail).findAll();
            }
        });

        return realmResults;
    }

    //set student data in realm database
    public void setDataInStudentDetails(String name, String sub, String marks){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    String nameConvertedToUpperCase = name.toUpperCase();
                    String subConvertedToUpperCase = sub.toUpperCase();
                    String id = nameConvertedToUpperCase + subConvertedToUpperCase;
                    sharedPreferenceConfig = new SharedPreferenceConfig(context);

                    String userEmail = firebaseAuth.getCurrentUser().getEmail();
                    studentDetails details = realm.where(studentDetails.class).equalTo(studentDetails.ID, id).findFirst();
                    if (null == details) {
                        studentDetails studentDetails = new studentDetails();
                        studentDetails.setId(id);
                        studentDetails.setName(nameConvertedToUpperCase);
                        studentDetails.setSubject(subConvertedToUpperCase);
                        studentDetails.setUser_id(userEmail);
                        studentDetails.setMarks(Float.parseFloat(marks));
                        realm.insertOrUpdate(studentDetails);
                    }else {
                      //  float oldMarks = details.getMarks();
                       // float newMarks = oldMarks + Float.parseFloat(marks);
                        details.setMarks(Float.parseFloat(marks));
                        realm.insertOrUpdate(details);
                    }
                }
            });

    }


}
