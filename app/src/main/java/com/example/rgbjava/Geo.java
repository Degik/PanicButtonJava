package com.example.rgbjava;

/*
Il Geo è un thread a parte
Si occupa della costante geolocalizzazione dell'utente
 */


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class Geo implements Runnable{
    private Geocoder gc;
    private Context mContext;

    public Geo(Context context){
        super();
        mContext = context;
    }

    @Override
    public void run(){
        gc = new Geocoder(mContext, Locale.getDefault());
        //LocationManager locationManager = (LocationManager)Service
        //List<Address> listAddress = gc.getFromLocation();
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
}
