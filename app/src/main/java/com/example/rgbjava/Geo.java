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

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class Geo implements Runnable{
    private Geocoder gc;
    private Context mContext;
    private String addressPos;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public Geo(Context context){
        super();
        mContext = context;
    }

    @Override
    public void run(){
        running.set(true);
        while(running.get()){
            gc = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> listAddress = gc.getFromLocation(MainActivity.getLatitude(), MainActivity.getLongitude(), 1);
                addressPos = listAddress.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sleep(30);
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

    public void setStop(){
        running.set(false);
    }
}
