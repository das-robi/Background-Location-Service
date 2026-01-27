package com.devrobin.locationservice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PermissionManager {

    //Request Codes
    public static final int REQUEST_LOCATION_PERMISSION_CODE = 100;
    public static final int REQUEST_BACKGROUND_LOCATION_CODE = 101;
    public static final int REQUEST_NOTIFICATION_CODE = 102;


    //Check ForeGround Location

    public static boolean ForegroundLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    //Check Background Location
    public static boolean BackgroundLocationPermission(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)  == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    //Check Notification Permission
    public static boolean NotificationPermission(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            return ContextCompat.checkSelfPermission(context,
                    Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }


    //Request Foreground Location
    public static void RequestForeGroundLocation(Activity activity){

        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, REQUEST_LOCATION_PERMISSION_CODE);

    }

    //Request Background Location
    public static void RequestBackgroundLocation(Activity activity){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    }, REQUEST_BACKGROUND_LOCATION_CODE);
        }

    }

    //Request Notification Permission
    public static void RequestNotification(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.POST_NOTIFICATIONS
                    }, REQUEST_NOTIFICATION_CODE);


        }
    }


    //Check Permission is denied
    public static boolean PermissionDenied(Activity activity, String permission){

        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                && ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_DENIED;

    }
}
