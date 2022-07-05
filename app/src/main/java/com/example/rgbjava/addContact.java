package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addContact extends AppCompatActivity {
    private Button buttonSave;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmail;
    private EditText editPhone;

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
                editPhone = (EditText) findViewById(R.id)
                openSave();
            }
        });
    }

    public void openSave(){

    }
}