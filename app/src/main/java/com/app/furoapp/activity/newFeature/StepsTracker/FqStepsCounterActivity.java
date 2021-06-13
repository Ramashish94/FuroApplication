package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.FuroPrefs;

public class FqStepsCounterActivity extends AppCompatActivity {
    public ImageView ivBackIcon, ivSetting, ivLeadBord, ivModified, ivHistory;
    public TextView tvCountsSteps, tvTotNumberOfSteps, tvTimes, tvCalories, tvDistance, tvActivateStepsCounter, tvDiActivate;
    public View includeCongratsStepsTrack;
    public double magnitudePrevious = 0;
    private Integer stepCount = 0;
    public SensorManager sensorManager;
    public Sensor sensorAcceleroMeter;
    public static final String TAG = "FqStepsCounterActivity";
    private float distanceInMeter;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    long timeSwapBuff = 0L;
    public SensorEventListener stepDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fq_steps_counter);

        //stepCounter();         // step counter functionality implimentation
        initViews();
        clickEvent();
    }

    private void initViews() {
        ivBackIcon = findViewById(R.id.ivBackIcon);
        ivSetting = findViewById(R.id.ivSetting);
        ivLeadBord = findViewById(R.id.ivLeadBord);
        ivModified = findViewById(R.id.ivModified);
        tvCalories = findViewById(R.id.tvCalories);
        ivHistory = findViewById(R.id.ivHistory);
        tvCountsSteps = findViewById(R.id.tvCountsSteps);
        tvTotNumberOfSteps = findViewById(R.id.tvTotNumberOfSteps);
        tvTimes = findViewById(R.id.tvTimes);
        tvDistance = findViewById(R.id.tvDistance);
        tvActivateStepsCounter = findViewById(R.id.tvActivateStepsCounter);
        tvDiActivate = findViewById(R.id.tvDiActivate);
        includeCongratsStepsTrack = findViewById(R.id.incudeCongratsStepsTrack);
    }

    private void clickEvent() {
        ivBackIcon.setOnClickListener(v -> {
            finish();
        });

        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            startActivity(intent);
        });
        ivLeadBord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LeadBoardActivity.class);
            startActivity(intent);
        });

        tvActivateStepsCounter.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);
            stepCounter();// step counter functionality implimentation
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        });
        tvDiActivate.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);
            customHandler.removeCallbacks(updateTimerThread);
        });

    }

    public void stepCounter() {
        Log.d(TAG, "OnCreate: Initialization Sensor service ");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAcceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x_acceleration = event.values[0];
                    float y_acceleration = event.values[1];
                    float z_acceleration = event.values[2];
                    //Log.d(TAG, "OnSensorChanged: X: " + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
                    double magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                  //  Log.d("magnitude", String.valueOf(+magnitude));
                    double magnitudeDelta = magnitude - magnitudePrevious;
                   // Log.d("magnitudeDelta", String.valueOf(magnitudeDelta));
                    magnitudePrevious = magnitude;
                    if (magnitudeDelta > 6) {
                        stepCount++;
                        Log.d("stepCount", String.valueOf(+stepCount));
                    }
                    tvCountsSteps.setText(stepCount + "");
                }
               /* Sensor sensor = event.sensor;
                float[] values = event.values;
                int value = -1;

                if (values.length > 0) {
                    value = (int) values[0];
                }
                Log.d(TAG, "onSensorChanged() called with: sensor type = [" + sensor.getType() + "]");
                if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                    stepCount++;
                }*/
                distanceInMeter = getDistanceRun(stepCount);
                Log.d(TAG, "onSensorChanged() called with: distanceInMeter = [" + distanceInMeter + "]");
                tvDistance.setText("" + distanceInMeter + " meter");
                tvCountsSteps.setText(stepCount + "");
                Log.d(TAG, "onSensorChanged() called with: event = [" + event + "]");
                tvCalories.setText("" + getCalculateCalories(stepCount));
                Log.d(TAG, "onSensorChanged() called with: Calories = [" + getCalculateCalories(stepCount) + "]");


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector, sensorAcceleroMeter, sensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "OnCreate: Registered sensorAcceleroMeter listener ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        Log.d("stepCount", String.valueOf(+stepCount));
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        Log.d("stepCount", String.valueOf(+stepCount));
        editor.apply();
        // distanceInMeter = getDistanceRun(stepCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
        Log.d("stepCount", String.valueOf(+stepCount));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(stepDetector, sensorAcceleroMeter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tvCountsSteps.setText("");
        finish();
    }

    //function to determine the distance run in kilometers/meter using average step length for men and number of steps
    public float getDistanceRun(long steps) {
        float distance = (float) (steps * 78) / (float) 100;//distance in km 100000
        return distance;
    }

    public float getCalculateCalories(long steps) {
        float calories = (float) (steps * 0.045);
        return calories;
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            tvTimes.setText("" + mins + ":" + String.format("%02d", secs)+" minutes");/*":" + String.format("%02d", secs)*/
            String timerValue = tvTimes.getText().toString().trim();
            FuroPrefs.putString(getApplication(), "time", timerValue);
            customHandler.postDelayed(this, 0);

        }

    };
}