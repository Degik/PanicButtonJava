package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button buttonContactsList;
    private Button buttonPanic;
    private Button settingsButton;
    private BackupFile backupFile;
    public static User user;
    public static ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backupFile = new BackupFile(getApplicationContext());
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
    }

    public void opencContactsList(){ // Per aprire la lista dei contatti
        Intent intentContactsList = new Intent(this, ContactsList.class);
        startActivity(intentContactsList);
    }

    public void openApplicationSettings(){
        Intent intentApplicationSettings = new Intent(this, ApplitcationSettings.class);
        startActivity(intentApplicationSettings);
    }

}