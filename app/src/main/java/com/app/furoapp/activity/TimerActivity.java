package com.app.furoapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.app.furoapp.R;


public class TimerActivity extends Activity {
    public static long MINUTES = 1000 * 60;
    public static int MAX_DURATION = 15;

    private int duration_minutes = 1;

    private boolean running = false;

    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    private Resources res;
    private SharedPreferences preferences;

    private LinearLayout drawerLayout;
    private ListView drawerList;
    private String[] drawerItems;
    private Chronometer chronometer;
    private TextView timerDuration;
    private Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        res = getResources();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        preferences = getPreferences(Context.MODE_PRIVATE);

        setContentView(R.layout.activity_timer);

        drawerLayout =  findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.navigation_drawer);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        timerDuration = (TextView) findViewById(R.id.timer_duration);
        timerButton = (Button) findViewById(R.id.timer_button);




      /*  chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                if (elapsedTime >= duration_minutes * MINUTES) {
                    stopTimer();
                }
            }
        });*/

        updateDuration();

        timerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!running)
                    startTimer();
                else
                    stopTimer();

                running = !running;
            }
        });
    }

    private void updateDuration() {
        timerDuration.setText(duration_minutes + " minute timer");
    }

    private void startTimer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        timerButton.setText("timer_button_stop");
    }

    private void resetTimer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        timerButton.setText("timer_button_start");
    }

    private void stopTimer() {
        chronometer.stop();

        boolean sound = preferences.getBoolean("sound", false);
        boolean vibrate = preferences.getBoolean("vibrate", false);

        if (sound) {
            mediaPlayer.start();
        }

        if (vibrate) {
            vibrator.vibrate(new long[]{0l, 250l, 125l, 250l, 125l, 250l, 125l, 500l}, -1);
        }

        resetTimer();
    }




}
