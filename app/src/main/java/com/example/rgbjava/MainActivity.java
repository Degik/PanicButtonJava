package com.example.rgbjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.GnssAntennaInfo;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.MapView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button buttonContactsList;
    private Button buttonPanic;
    private Button settingsButton;
    private int PERMISSION_ID = 44;
    //
    public LocationManager locationManager;
    private Geo geo;
    //
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmapImage;
    private File file;
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

        if(contacts == null){
            contacts = new ArrayList<Contact>();
            backupFile.makeBackupContactsList();
        }

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
                startPanic();
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

    private void startPanic(){
        startCamera();
        sendEmail();
    }

    private void startCamera(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        } catch(ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    private void sendEmail(){
        ArrayList<String> list = new ArrayList<>();
        for(Contact c: contacts){
            list.add(c.getEmail());
        }
        String[] arrayEmail = list.toArray(new String[0]);
        //
        Intent intentSendEmail = new Intent(Intent.ACTION_SEND);
        intentSendEmail.putExtra(Intent.EXTRA_EMAIL, arrayEmail);
        intentSendEmail.putExtra(Intent.EXTRA_SUBJECT, "ho bisogno di aiuto!!");
        intentSendEmail.setType("image/*");
        //
        File myDir = new File(Environment.getExternalStorageDirectory() + "/picture");
        DateFormat format = new SimpleDateFormat("dd_MM_yyyy_H_mm_ss", Locale.getDefault());
        Date curDate = new Date();
        String displayDate = format.format(curDate);
        String fname = displayDate+ "_picture.jpg";
        file = new File(myDir,fname);
        try{
            boolean fileExist = file.createNewFile();
            if(fileExist){
                FileOutputStream outputStream = new FileOutputStream(file);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        //
        intentSendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intentSendEmail, "Test"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            bitmapImage = (Bitmap) data.getExtras().get("data");
        }
    }
}