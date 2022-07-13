package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ApplitcationSettings extends AppCompatActivity {
    private Button buttonSaveSettings;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editNumberPhone;
    private EditText editStartTimer;
    private EditText editRecordingTime;
    private CheckBox checkGpsEnabled;
    private CheckBox checkCameraEnabled;
    private CheckBox checkRecordingEnabled;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applitcation_settings);

        editFirstName = (EditText) findViewById(R.id.TextFirstNameSettings);
        editLastName = (EditText) findViewById(R.id.TextLastNameSettings);
        editNumberPhone = (EditText) findViewById(R.id.TextNumberPhoneSettings);
        editStartTimer = (EditText) findViewById(R.id.TextStartTimerSettings);
        editRecordingTime = (EditText) findViewById(R.id.textRecordingTime);
        checkGpsEnabled = (CheckBox) findViewById(R.id.checkGpsEnabledSettings);
        checkCameraEnabled = (CheckBox) findViewById(R.id.checkCameraEnabledSettings);
        checkRecordingEnabled = (CheckBox) findViewById(R.id.checkRecordingEnabeldSettings);

        editFirstName.setText(MainActivity.backupFile.getFirstName());
        editLastName.setText(MainActivity.backupFile.getLastName());
        editNumberPhone.setText(MainActivity.backupFile.getNumberPhone());
        editStartTimer.setText(Integer.toString(MainActivity.backupFile.getStartTime()));
        editRecordingTime.setText(Integer.toString(MainActivity.backupFile.getRecordingTime()));
        checkGpsEnabled.setChecked(MainActivity.backupFile.getGpsEnabled());
        checkCameraEnabled.setChecked(MainActivity.backupFile.getCameraEnabled());
        checkRecordingEnabled.setChecked(MainActivity.backupFile.getRecordingEnabled());

        buttonSaveSettings = (Button) findViewById(R.id.saveSettings);
        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = editFirstName.getText().toString();
                String lastName = editLastName.getText().toString();
                String numberPhone = editNumberPhone.getText().toString();
                int startTime = Integer.parseInt(editStartTimer.getText().toString());
                int recordingTime = Integer.parseInt(editRecordingTime.getText().toString());
                boolean gpsEnabled = checkGpsEnabled.isChecked();
                boolean cameraEnabled = checkCameraEnabled.isChecked();
                boolean recordingEnabled = checkRecordingEnabled.isChecked();

                if(!(emptyString(firstName) || emptyString(lastName) || emptyString(numberPhone))){
                    MainActivity.backupFile.makeBackupSettings(firstName, lastName, numberPhone, startTime, gpsEnabled, cameraEnabled, recordingEnabled, recordingTime);
                    if(!gpsEnabled){
                        MainActivity.stopTrackGps();
                    }
                    Toast.makeText(ApplitcationSettings.this, "Preferenze salvate", Toast.LENGTH_LONG).show();
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
}