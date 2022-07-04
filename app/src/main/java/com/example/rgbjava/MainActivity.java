package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonContactsList;
    private Button buttonPanic;
    private Button settingsButton;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User("Davide", "Bulotta", "3284888702");
        Contact c1 = new Contact("Isa", "Amore", "096799353", "isabellasanseverino@gmail.com");
        Contact c2 = new Contact("Giovanni", "Pino", "3358773361", "giovannipino@gmail.com");
        user.addContact(c1);
        user.addContact(c2);

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
        Intent intentContactsList = new Intent(this, contactsList.class);
        startActivity(intentContactsList);
    }

    public void openApplicationSettings(){
        Intent intentApplicationSettings = new Intent(this, applitcationSettings.class);
        startActivity(intentApplicationSettings);
    }
}