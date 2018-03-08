package com.practice.xiaoli.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    CountDownTimer countDownTimer;
    Button controllerButton;
    ImageView eggImage;
    boolean counterIsActive = false;

    public void resetTimer(){
        counterIsActive = false;
        eggImage.setImageResource(R.drawable.egg);
        timerTextView.setVisibility(View.VISIBLE);
        timerTextView.setText(getString(R.string.timer_text_view, "0", "30"));
        timerSeekBar.setProgress(30);
        controllerButton.setText(getString(R.string.controller_button_text, "Go!"));
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
    }

    public void updateText(int secondsLeft){
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String minutesString = Integer.toString(minutes);
        String secondsString = Integer.toString(seconds);
        if(seconds <= 9){
            secondsString = "0" + Integer.toString(seconds);
        }

        String textString = getString(R.string.timer_text_view, minutesString, secondsString);
        timerTextView.setText(textString);
    }

    public void controlTimer(View view){
        if(!counterIsActive) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText(getString(R.string.controller_button_text, "Stop"));
            // if no +100 the result will round down and cause a little delay from 0:01 to 0:00, and no delay when start -> doesn't make sense
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateText((int) l / 1000); // unit of l is miliseconds
                }

                @Override
                public void onFinish() {
                    eggImage.setImageResource(R.drawable.chicken);
                    controllerButton.setText(getString(R.string.controller_button_text, "Restart"));
                    timerTextView.setVisibility(View.INVISIBLE);
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                }
            }.start();
        }else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600); // 10 minutes
        timerSeekBar.setProgress(30); // starts with 30 seconds

        timerTextView = (TextView)findViewById(R.id.timerTextView);
        timerTextView.setText(getString(R.string.timer_text_view, "0", "30"));

        controllerButton = (Button)findViewById(R.id.controllerButton);
        controllerButton.setText(getString(R.string.controller_button_text, "Go!"));

        eggImage = findViewById(R.id.eggImage);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
