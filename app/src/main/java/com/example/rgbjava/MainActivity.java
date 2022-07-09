package com.example.rgbjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GnssAntennaInfo;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button buttonContactsList;
    private Button buttonPanic;
    private Button settingsButton;
    private int PERMISSION_ID = 44;
    //
    public LocationManager locationManager;
    private Geo geo;
    //
    public static BackupFile backupFile;
    public static User user;
    public static ArrayList<Contact> contacts;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backupFile = new BackupFile(getApplicationContext());
        boolean firstStart = backupFile.getFirstStart();

        // Inserire form per il primo login
        if (firstStart) {
            contacts = new ArrayList<Contact>();
            backupFile.makeBackupContactsList();
            openFirstStep();
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(checkPerm()){
                geo = new Geo(this, locationManager);
                Thread threadGps = new Thread(geo);
                threadGps.setName("GeoTracker");
                threadGps.start();
            } else {
                requestPerm();
            }
        }

        User user = new User(backupFile.getFirstName(), backupFile.getLastName(), backupFile.getNumberPhone());
        contacts = backupFile.getContactList();

        buttonContactsList = (Button) findViewById(R.id.contactsListButton);
        buttonContactsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencContactsList(); // Apri contatsList
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openApplicationSettings();
            }
        });

        buttonPanic = (Button) findViewById(R.id.panicButton);
        buttonPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    public void opencContactsList() { // Per aprire la lista dei contatti
        Intent intentContactsList = new Intent(this, ContactsList.class);
        startActivity(intentContactsList);
    }

    public void openApplicationSettings() {
        Intent intentApplicationSettings = new Intent(this, ApplitcationSettings.class);
        startActivity(intentApplicationSettings);
    }

    public void openFirstStep() {
        Intent intentFirstStep = new Intent(this, FirstStep.class);
        startActivity(intentFirstStep);
    }

    private boolean checkPerm(){
        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat
                        .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED;
    }

    private void requestPerm(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private void stopTrackGps(){
        geo.setStop();
    }

    private startPanic(){
        
    }
}