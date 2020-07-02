package com.example.locationtrackertailwebs.controler;

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

    public void BackButtonStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("back_button_status", status);
        editor.commit();
    }

    public boolean read_back_button_status(){
        boolean status = false;
        status = sharedPreferences.getBoolean("back_button_status",false);
        return status;
    }

    public void LoginUser(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login_user", email);
        editor.commit();
    }

    public String read_login_user(){
        String user = "";
        user = sharedPreferences.getString("login_user","");
        return user;
    }

}
