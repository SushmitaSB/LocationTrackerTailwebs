package com.example.locationtrackertailwebs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceConfig {
    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferenceConfig(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public void LoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login_status", status);
        editor.commit();
    }
    public boolean read_login_status(){
        boolean status = false;
        status = sharedPreferences.getBoolean("login_status",false);
        return status;
    }

    public void LoginUser(int id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("login_user", id);
        editor.commit();
    }

    public int read_login_user(){
        int user = 0;
        user = sharedPreferences.getInt("login_user",0);
        return user;
    }

}
