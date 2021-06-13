package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;

public class FqStepsCounterActivity extends AppCompatActivity {
    public ImageView ivBackIcon, ivSetting, ivLeadBord, ivModified, ivHistory;
    public TextView tvCountsSteps, tvTotNumberOfSteps, tvTimes, tvDistance, tvActivateStepsCounter, tvDiActivate;
    public View includeCongratsStepsTrack;
    public double magnitudePrevious = 0;
    private Integer stepCount = 0;
    public SensorManager sensorManager;
    public Sensor sensorAcceleroMeter;
    public static final String TAG = "FqStepsCounterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fq_steps_counter);

        stepCounter();         // step counter functionality implimentation
        initViews();
        clickEvent();
    }

    private void initViews() {
        ivBackIcon = findViewById(R.id.ivSetting);
        ivSetting = findViewById(R.id.ivSetting);
        ivLeadBord = findViewById(R.id.ivLeadBord);
        ivModified = findViewById(R.id.ivModified);
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
        });
        tvDiActivate.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);
        });

    }

    public void stepCounter() {
        Log.d(TAG, "OnCreate: Initialization Sensor service ");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAcceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x_acceleration = event.values[0];
                    float y_acceleration = event.values[1];
                    float z_acceleration = event.values[2];
                    Log.d(TAG, "OnSensorChanged: X: " + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
                    double magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
                    Log.d("magnitude", String.valueOf(+magnitude));
                    double magnitudeDelta = magnitude - magnitudePrevious;
                    Log.d("magnitudeDelta", String.valueOf(magnitudeDelta));
                    magnitudePrevious = magnitude;
                    if (magnitudeDelta > 6) {
                        stepCount++;
                        Log.d("stepCount", String.valueOf(+stepCount));
                    }
                    tvCountsSteps.setText(stepCount.toString());
                }
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
        Log.d("stepCount", String.valueOf(+stepCount));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tvCountsSteps.setText("");
        finish();
    }

}