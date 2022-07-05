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

public class FirstStep extends AppCompatActivity {
    private Button buttonSave;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editNumberPhone;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_step);

        buttonSave = (Button) findViewById(R.id.saveButton);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String numberPhone = editNumberPhone.getText().toString();
                if(!(emptyString(firstName) || emptyString(lastName) || emptyString(numberPhone))){
                    MainActivity.backupFile.makeBackupSettings(firstName, lastName, numberPhone);

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

    private boolean emptyString(String s){
        if(s.equals("")){
            return true;
        }
        return false;
    }

    private void openMain(){
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }
}