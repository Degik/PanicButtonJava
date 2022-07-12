package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private Button buttonStop;
    private TextView textViewTimer;
    private boolean timerRunning;
    private long timeLeft;
    public static boolean panicEnabled = false;

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
}