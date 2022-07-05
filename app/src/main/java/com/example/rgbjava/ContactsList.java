package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ContactsList extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        ListView listView = (ListView) findViewById(R.id.contactsListView);
        ContactsAdapter contactsAdapter = new ContactsAdapter(this, R.layout.layout_adapter_contacts_list, MainActivity.contacts);
        listView.setAdapter(contactsAdapter);
    }

    public void openAdd(){
        Intent intentOpenAdd = new Intent(this, AddContact.class);
        startActivity(intentOpenAdd);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}