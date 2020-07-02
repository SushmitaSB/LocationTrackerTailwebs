package com.example.locationtrackertailwebs.controler;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InitRealm extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize realm
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }
}
