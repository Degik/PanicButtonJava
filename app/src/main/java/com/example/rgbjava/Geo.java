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
        //List<Address> list = gc.getFromLocation();
    }
}
