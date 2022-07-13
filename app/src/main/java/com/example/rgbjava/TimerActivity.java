package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private Button buttonStop;
    private TextView textViewTimer;
    public static boolean panicEnabled = false;
    public static String posAddress;
    public static Uri uriFilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        textViewTimer = (TextView) findViewById(R.id.editTextTimerLabel);
        countDownTimer = new CountDownTimer(MainActivity.backupFile.getStartTime(), 1000) {
            @Override
            public void onTick(long l) {
                textViewTimer.setText("" + l / 1000);
            }

            @Override
            public void onFinish() {
                textViewTimer.setVisibility(View.GONE);
                buttonStop.setVisibility(View.GONE);
                panicEnabled = true;
                startMain();
            }
        };
        countDownTimer.start();

        buttonStop = (Button) findViewById(R.id.stopTimerButton);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                startMain();
            }
        });
    }

    private void startMain(){
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }

    public static boolean getPanicEnabled(){
        return panicEnabled;
    }

    public static void setPanicDisabled(){
        panicEnabled = false;
    }

    public static void setPosAddress(String pos){
        posAddress = pos;
    }

    public static String getPosAddress(){
        return posAddress;
    }

    public static void setFilePhoto(Uri file){
        uriFilePhoto = file;
    }

    public static Uri getUriFilePhoto(){
        return uriFilePhoto;
    }
}