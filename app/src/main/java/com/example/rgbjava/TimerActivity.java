package com.example.rgbjava;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        textViewTimer = (TextView) findViewById(R.id.editTextTimerLabel);
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                textViewTimer.setText("" + l / 1000);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();

        buttonStop = (Button) findViewById(R.id.stopTimerButton);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                MainActivity
            }
        });
    }
}