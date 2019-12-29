package com.wiktor.udemy_stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewTime;
    Button buttonStart;
    Button buttonPause;
    Button buttonReset;

    private int seconds = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.tv_time);
        buttonStart = findViewById(R.id.b_start);
        buttonPause = findViewById(R.id.b_pause);
        buttonReset = findViewById(R.id.b_reset);

        buttonStart.setOnClickListener(this);
        buttonPause.setOnClickListener(this);
        buttonReset.setOnClickListener(this);

        // 2. Получаем сохраненные значения из бандла
        // 3. для того чтобы не выпала ошибка когда бандл = null, т.е. когда активити только что создана задаем проверку
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 1. Кладем в бандл нужные значения для созранения
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_start:
                isRunning = true;
                break;
            case R.id.b_pause:
                isRunning = false;
                break;
            case R.id.b_reset:
                isRunning = false;
                seconds = 0;
                break;
        }
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs);
                textViewTime.setText(time);

                if (isRunning) {
                    seconds++;
                }
                // выполнить этот же метод еще раз, но с задержкой 1сек
                handler.postDelayed(this, 1000);

            }
        });

    }
}
