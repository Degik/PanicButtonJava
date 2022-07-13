package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
/*
Il Geo è un thread a parte
Si occupa della costante geolocalizzazione dell'utente
 */


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class Geo  implements LocationListener, Runnable {
    private Geocoder gc;
    private Context mContext;
    private String addressPos;
    private Location location;
    private LocationManager locationManager;
    private boolean isGpsEnabled;
    private static final long MIN_TIME_UPDATE = 1000 * 30;
    private static final long MIN_DISTANCE_UPDATE = 10;
    private static double longitude;
    private static double latitude;
    public static final AtomicBoolean running = new AtomicBoolean(false);

    public Geo(Context context, LocationManager locationManager){
        super();
        mContext = context;
        this.locationManager = locationManager;
    }

    public void run(){
        running.set(true);
        Looper.prepare();
        while(running.get()){
            getLocation();
            gc = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> listAddress = gc.getFromLocation(latitude, longitude, 1);
                addressPos = listAddress.get(0).getAddressLine(0);
                TimerActivity.setPosAddress(addressPos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println( Thread.currentThread().getName() + ": Ho finito!");
            sleep(5);
        }
        Looper.loop();
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        isGpsEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        //Fare richiesta dei permessi
        if (isGpsEnabled) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                    MIN_TIME_UPDATE,
                    MIN_DISTANCE_UPDATE,
                    this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                updateLatitude();
                updateLongitude();
            }
        }
    }

    public static void sleep(int time){
        for(int i = 0; i < time; i++){
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();;
            }
        }
    }

    private void updateLatitude() {
        latitude = location.getLatitude();
    }

    private void updateLongitude() {
        longitude = location.getLongitude();
    }

    public synchronized void setStop(){
        running.set(false);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
