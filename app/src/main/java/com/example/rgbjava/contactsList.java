package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class contactsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        //ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<Contact>(this, R.layout.activity_contacts_list, MainActivity.user.getContacts());
        //ListView listView = (ListView) findViewById(R.id._dynamic);
        ContactsAdapter contactsAdapter = new ContactsAdapter(this, MainActivity.user.getContacts());
        ListView listView = (ListView) findViewById(R.id.contactsListView);
        listView.setAdapter(contactsAdapter);
    }
}