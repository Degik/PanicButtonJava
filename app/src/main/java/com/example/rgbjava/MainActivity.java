package com.example.rgbjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button buttonContactsList;
    private Button buttonPanic;
    private Button takePhotoButton;
    private Button settingsButton;
    private int PERMISSION_ID = 44;
    private int PERMISSION_ID_CAMERA = 45;
    private final Handler handler = new Handler();
    //
    public LocationManager locationManager;
    private Geo geo;
    private String currentPhotoPath;
    //
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private File file = null;
    private Uri photoUri;
    //
    public static BackupFile backupFile;
    public static User user;
    public static ArrayList<Contact> contacts;


    @SuppressLint({"MissingPermission", "ClickableViewAccessibility"})
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
            if(backupFile.getGpsEnabled()){
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
        }

        User user = new User(backupFile.getFirstName(), backupFile.getLastName(), backupFile.getNumberPhone(), backupFile.getStartTime(), backupFile.getGpsEnabled());
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
        //buttonPanic.setEnabled(false);
        /*buttonPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPanic();
            }
        });*/

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startPanic();
                //Toast.makeText(MainActivity.this, "Comunicazione inviata", Toast.LENGTH_SHORT).show();
            }
        };
        buttonPanic.setEnabled(false);
        buttonPanic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    handler.postDelayed(runnable, user.getStartTime());
                } else {
                    handler.removeCallbacks(runnable);
                }
                return true;
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
        sendEmail();
    }

    private void startIntentPicture(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            file = createImageFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if(file != null){
            photoUri = FileProvider.getUriForFile(this, "com.example.rgbjava.fileprovider", file);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
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
        intentSendEmail.setType("application/image");
        //
        intentSendEmail.putExtra(Intent.EXTRA_STREAM, photoUri);
        if(user.getGpsEnabled()){
            intentSendEmail.putExtra(Intent.EXTRA_TEXT, "La mia posizione è: " + geo.getAddressPos());
        }
        startActivity(Intent.createChooser(intentSendEmail, "Test"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK && data.getData() != null){
            Bundle bundle = data.getExtras();
            Bitmap bitmapImage = (Bitmap) bundle.get("data");
        }
    }

    private File createImageFile() throws IOException {
        String time = new SimpleDateFormat("dd:MM:yyyy_HH:mm:ss").format(new Date());
        String imageFileName = "JPEG_" + time + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean checkCameraPerm(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPerm(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_ID_CAMERA);
    }
}