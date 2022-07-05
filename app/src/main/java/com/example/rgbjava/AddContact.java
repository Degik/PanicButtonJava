package com.example.rgbjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {
    private Button buttonSave;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmail;
    private EditText editPhone;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFirstName = (EditText) findViewById(R.id.textEditFirstName);
                editLastName = (EditText) findViewById(R.id.textEditLastName);
                editEmail = (EditText) findViewById(R.id.editEmail);
                editPhone = (EditText) findViewById(R.id.editPhone);
                if(!(emptyBox(editFirstName) ||  emptyBox(editEmail) || emptyBox(editPhone))){
                    String firstName = editFirstName.getText().toString();
                    String lastName = editLastName.getText().toString();
                    String email = editEmail.getText().toString();
                    String phone = editPhone.getText().toString();
                    // Creo il contatto
                    Contact c = new Contact(firstName, lastName, phone, email);
                    // Aggiungo il contatto alla lista
                    MainActivity.contacts.add(c);
                    // Effettup il backup
                    MainActivity.backupFile.makeBackupContactsList();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Successo").setTitle("Info");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openContactList();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Attenzione").setTitle("Errore");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

    public void openContactList(){
        Intent intentOpenContactList = new Intent(this, ContactsList.class);
        startActivity(intentOpenContactList);
    }

    public boolean emptyBox(EditText editText){
        if(editText.getText().toString().equals("")){
            return true;
        }
        return false;
    }
}