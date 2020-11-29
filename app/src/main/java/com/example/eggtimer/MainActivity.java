package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView countDownText;
    int minTime = 10;
    int maxTime = 600;
    MediaPlayer mediaPlayer;
    Button timerButton;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int progress){
        int minutes = progress/60;
        int seconds = progress - (minutes*60);
        String secondString = Integer.toString(seconds);
        String minutesString ;
        if(minutes <= 9){
            minutesString = "0"+Integer.toString(minutes);
        }
        else{
            minutesString = Integer.toString(minutes);
        }
        if(seconds <=9){
            secondString = "0" + Integer.toString(seconds);
        }
        countDownText.setText(minutesString+":"+secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        countDownText = (TextView) findViewById(R.id.timerTextView);
        mediaPlayer = MediaPlayer.create(this,R.raw.horn);
        timerButton = (Button) findViewById(R.id.timerButton);
        timerSeekBar.setMax(maxTime);
        timerSeekBar.setProgress(minTime);
        updateTimer(minTime);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress<minTime){ // Minimum Timer Settings
                    timerSeekBar.setProgress(minTime);
                }
                Log.i("Time",Integer.toString(progress));
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void Timer(View view) {
        Log.i("Button Pressed","Nice");

        if(counterIsActive){
            updateTimer(timerSeekBar.getProgress());
            timerSeekBar.setEnabled(true);
            countDownTimer.cancel();
            timerButton.setText("GO");
        }else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            timerButton.setText("STOP");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    updateTimer(timerSeekBar.getProgress());
                    mediaPlayer.start();
                    timerButton.setText("Go!");
                    Log.i("Finished", "Time Exhausted");
                    timerSeekBar.setEnabled(true);
                }
            }.start();
        }
    }
}