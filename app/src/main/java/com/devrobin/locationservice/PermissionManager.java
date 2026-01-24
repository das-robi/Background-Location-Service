package com.devrobin.locationservice;

import static android.content.pm.PackageManager.*;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

public class PermissionManager {

    private static PermissionManager instance = null;
    private Context context;

    public PermissionManager() {
    }

    public static PermissionManager getInstance(Context context){

        if (instance == null){
            instance = new PermissionManager();
        }

        instance.init(context);
        return instance;
    }

    private void init(Context context) {
        this.context = context;
    }

    boolean checkPermission(String[] permissions){

        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_DENIED){
                return false;
            }
        }

        return true;
    }

    public void askPermission(Activity activity, String[] permission, int requestCode){

        ActivityCompat.requestPermissions(activity, permission, requestCode);

    }

    boolean handlerPermission(Activity activity, int[] grantResults){

        if (grantResults.length > 0){
            boolean allPermissionGranted = true;

            for (int gratResult : grantResults){

                if (gratResult == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity, "Permission Denided", Toast.LENGTH_SHORT).show();
                    allPermissionGranted = false;
                    break;
                }
            }

            return allPermissionGranted;
        }

        return false;
    }
}
