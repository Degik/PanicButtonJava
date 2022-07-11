package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ApplitcationSettings extends AppCompatActivity {
    private Button buttonSaveSettings;
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editNumberPhone;
    private EditText editStartTimer;
    private CheckBox checkGpsEnabled;
    private CheckBox checkCameraEnabled;
    private CheckBox checkRecordingEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applitcation_settings);

        editFirstName = (EditText) findViewById(R.id.TextFirstNameSettings);
        editLastName = (EditText) findViewById(R.id.TextLastNameSettings);
        editNumberPhone = (EditText) findViewById(R.id.TextNumberPhoneSettings);
        editStartTimer = (EditText) findViewById(R.id.TextStartTimerSettings);
        checkGpsEnabled = (CheckBox) findViewById(R.id.checkGpsEnabledSettings);
        checkCameraEnabled = (CheckBox) findViewById(R.id.checkCameraEnabledSettings);
        checkRecordingEnabled = (CheckBox) findViewById(R.id.checkRecordingEnabeldSettings);

        editFirstName.setText(MainActivity.backupFile.getFirstName());
        editLastName.setText(MainActivity.backupFile.getLastName());
        editNumberPhone.setText(MainActivity.backupFile.getNumberPhone());
        editStartTimer.setText(Integer.toString(MainActivity.backupFile.getStartTime()));
        checkGpsEnabled.setChecked(MainActivity.backupFile.getGpsEnabled());
    }
}