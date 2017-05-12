package com.example.admin.findatm.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * class PermissionAccessFineLocationUtil
 * Created by naunem on 12/05/2017.
 */

public class PermissionAccessFineLocationUtil {

    public static void askPermissionsAccessLocation(Context context, int requestCode) {
        // Ask for permission with API >= 23.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    && accessFinePermission != PackageManager.PERMISSION_GRANTED) {

                // Permissions.
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};

                // Dialog.
                ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
                return;
            }
        }
    }
}
