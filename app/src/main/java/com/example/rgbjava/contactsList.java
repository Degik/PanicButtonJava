package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class contactsList extends AppCompatActivity {

    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        buttonAdd = (Button) findViewById(R.id.addContact);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd();
            }
        });

        //ArrayAdapter<Contact> arrayAdapter = new ArrayAdapter<Contact>(this, R.layout.activity_contacts_list, MainActivity.user.getContacts());
        //ListView listView = (ListView) findViewById(R.id._dynamic);
        ContactsAdapter contactsAdapter = new ContactsAdapter(this, MainActivity.user.getContacts());
        ListView listView = (ListView) findViewById(R.id.contactsListView);
        listView.setAdapter(contactsAdapter);
    }

    public void openAdd(){
        Intent intentOpenAdd = new Intent(this, addContact.class);
        startActivity(intentOpenAdd);
    }
}