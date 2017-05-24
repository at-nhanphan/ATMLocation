package com.example.admin.findatm.utils;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.admin.findatm.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * MyCurrentLocation class used to get current location
 * Created by naunem on 17/05/2017.
 */

public class MyCurrentLocation {

    public static Location getCurrentLocation(Context context) {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                // TODO: 12/05/2017
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        Location locationNetwork;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
            locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (locationNetwork != null) {
                Log.d("ddd", "getCurrentLocation: network");
                return locationNetwork;
            } else {
                Log.d("ddd", "getCurrentLocation: network null");
            }
        } catch (SecurityException ignored) {
            Log.e("ddd", "getCurrentLocation: ", ignored);
        }
        return null;
    }

    public static boolean checkLocationEnabled(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean check;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
            Log.e("ddd", "checkLocationEnabled: ", ignored);
        }

        if (!gps_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.positiveButton), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(context.getResources().getString(R.string.cancelButton), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
            check = false;
        } else {
            check = true;
        }
        return check;
    }
}
