package com.example.rgbjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ContactsList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Button buttonAdd;
    private Button buttonRmv;
    private ContactsAdapter contactsAdapter;
    private Contact contact = null;
    private Context context = this;

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
        contactsAdapter = new ContactsAdapter(this, R.layout.layout_adapter_contacts_list, MainActivity.contacts);
        listView.setAdapter(contactsAdapter);
        listView.setOnItemClickListener(this);

        buttonRmv = (Button) findViewById(R.id.rmvContact);
        buttonRmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contact == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Per rimuovere un contatto devi selezionarlo").setTitle("Attenzione");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Sei sicuro di voler eliminare questo contatto? " + getFirstNameLastName()).setTitle("Attenzione");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.contacts.remove(contact);
                            MainActivity.backupFile.makeBackupContactsList();
                            contactsAdapter.notifyDataSetChanged();
                            Toast.makeText(ContactsList.this, "Contatto eliminato", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    public void openAdd(){
        Intent intentOpenAdd = new Intent(this, AddContact.class);
        startActivity(intentOpenAdd);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        contact = (Contact) contactsAdapter.getItem(i);
    }

    public String getFirstNameLastName(){
        return contact.getFirstName() + " " + contact.getLastName();
    }
}