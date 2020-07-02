package com.example.locationtrackertailwebs.controler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.locationtrackertailwebs.model.StopServiceEventBus;
import com.example.locationtrackertailwebs.view.IntroActivity;
import com.example.locationtrackertailwebs.view.MainActivity;
import com.example.locationtrackertailwebs.view.MapActivity;

import org.greenrobot.eventbus.EventBus;

public class SetAlertDialog {
    PermissionManager permissionManager;
    private Context context;
    public SetAlertDialog(){
    }
    public SetAlertDialog(Context context){
        this.context = context;
    }
    public void setDialog(String s, String string, SharedPreferenceConfig sharedPreferenceConfig) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(s);
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (string.equals("back")){
                            ((Activity)context).finish();
                        }else if(string.equals("logout")) {
                            sharedPreferenceConfig.LoginStatus(false);
                            Intent intent = new Intent(context, IntroActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }else if (string.equals("track")){
                            StopServiceEventBus stopServiceEventBus = new StopServiceEventBus();
                            EventBus.getDefault().post(stopServiceEventBus);
                            sharedPreferenceConfig.BackButtonStatus(true);
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);

                        }else if (string.equals("dashboard")){
                            ((Activity)context).finish();
                        }


                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (string.equals("track")){
                            permissionManager = new PermissionManager(context);
                            sharedPreferenceConfig.BackButtonStatus(false);
                            permissionManager.setLocationPermission();
                        }
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
