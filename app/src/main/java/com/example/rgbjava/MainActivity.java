package com.example.rgbjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private Button takePhotoButton;
    private Button settingsButton;
    private int PERMISSION_ID = 44;
    private int PERMISSION_ID_CAMERA = 45;
    //
    public LocationManager locationManager;
    private Geo geo;
    private String currentPhotoPath;
    //
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap bitmapImage;
    private File file = null;
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
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        buttonPanic.setEnabled(false);
        buttonPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startPanic();
            }
        });

        takePhotoButton = (Button) findViewById(R.id.buttonPhoto);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCameraPerm()){
                    startIntentPicture();
                    buttonPanic.setEnabled(true);
                } else {
                    requestCameraPerm();
                }
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
        //sendEmail();
    }

    private void startIntentPicture(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            file = createImageFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(file != null){
            Uri photoUri = FileProvider.getUriForFile(this, "com.example.rgbjava.fileprovider", file);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*private void sendEmail(){
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
        intentSendEmail.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(intentSendEmail, "Test"));
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            bitmapImage = (Bitmap) extras.get("data");
            /*
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
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }*/
        }
    }

    private File createImageFile() throws IOException {
        String time = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + time + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean checkCameraPerm(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPerm(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA}, PERMISSION_ID_CAMERA);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == PERMISSION_ID){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permesso consentito", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Permesso negato", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        showMessageOKCancel("Devi consentirmi di accedere alla tua fotocamera",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestCameraPerm();
                                        }
                                    }
                                });
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Acconsenti", okListener)
                .setNegativeButton("Nega", null)
                .create()
                .show();
    }*/
}