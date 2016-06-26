package com.example.heman.noteapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Heman on 26/06/2016.
 */
public class LocationClass extends Service implements LocationListener {
    private final Context context;

    boolean gpsenabled = false;
    boolean getlocation = false;
    boolean networkenabled = false;
    double latitude, longitude;
    public static final int REQUEST_LOCATION = 1;

    private static final long Min_distance = 10;
    private static final long Min_time = 1000 * 60 * 1;

    protected LocationManager locationManager;
    Location location;


    protected LocationClass(Context context) {
        this.context = context;
        getCurrentlocation();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public Location getCurrentlocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (gpsenabled && networkenabled) {
            } else {
                this.getlocation = true;

                if (networkenabled) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_time, Min_distance, this);
                }

                if (locationManager != null) {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            if (gpsenabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Min_time, Min_distance, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;

    }


    public double getLatitude()
    {
        if(location == null)
        {
            latitude = location.getLatitude();
        }
        return latitude;
    }
    public double getLongitude()
    {
        if(location == null)
        {
            longitude = location.getLongitude();
        }
        return longitude;
    }
    public boolean getlocation()
    {
        return this.getlocation;
    }


    @Override
    public void onLocationChanged(Location location) {

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
};
