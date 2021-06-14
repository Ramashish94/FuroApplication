package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.UserStepsGoalResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public String stepsAchivedVal;
    private String getAccessToken;
    private float getCalculateCalories;
    private int secs;
    private int mins;
    private String getTime;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    LinearLayout llFqStepCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fq_steps_counter);


        initViews();
        clickEvent();

        stepsAchivedVal = getIntent().getStringExtra("getAchievedVal");
        stepsAchivedVal = getIntent().getStringExtra("selectNumber");
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAcceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        setdata();
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
        llFqStepCounter = findViewById(R.id.llFqStepCounter);
    }

    private void clickEvent() {
        ivBackIcon.setOnClickListener(v -> {
            finish();
        });

        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            startActivity(intent);
            finish();
        });
        ivLeadBord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LeadBoardActivity.class);
            startActivity(intent);
            finish();
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
            callUserStepGoalApi();
        });

        ivHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setdata() {

        if (stepsAchivedVal != null) {
            tvTotNumberOfSteps.setText("Of " + stepsAchivedVal + " Steps");
        }

       /* if (stepsAchivedVal.equalsIgnoreCase(String.valueOf(stepCount))) {
            includeCongratsStepsTrack.setVisibility(View.VISIBLE);
            llFqStepCounter.setClickable(false);
            finish();
        }*/

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
                getCalculateCalories = calculateCalories(stepCount);
                tvCalories.setText("" + getCalculateCalories);
                Log.d(TAG, "onSensorChanged() called with: Calories = [" + calculateCalories(stepCount) + "]");
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
        if ((sensorManager) != null) {
            sensorManager.unregisterListener(stepDetector, sensorAcceleroMeter);
        }
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

    public float calculateCalories(long steps) {
        float calories = (float) (steps * 0.045);
        return calories;
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            getTime = mins + ":" + String.format("%02d", secs);
            tvTimes.setText("" + getTime + " minutes");/*":" + String.format("%02d", secs)*/
            String timerValue = tvTimes.getText().toString().trim();
            FuroPrefs.putString(getApplication(), "time", timerValue);
            customHandler.postDelayed(this, 0);
        }
    };

    private void callUserStepGoalApi() {
        UserStepsGoalRequest userStepsGoalRequest = new UserStepsGoalRequest();
        userStepsGoalRequest.setTime(getTime);
        userStepsGoalRequest.setTime(String.valueOf(distanceInMeter));
        userStepsGoalRequest.setCalories(String.valueOf(getCalculateCalories));
        userStepsGoalRequest.setCount_steps(String.valueOf(stepCount));
        userStepsGoalRequest.setTotal_steps(stepsAchivedVal);
        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getUserStepsGoal(getAccessToken, userStepsGoalRequest, new Callback<UserStepsGoalResponse>() {
                @Override
                public void onResponse(Call<UserStepsGoalResponse> call, Response<UserStepsGoalResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        Toast.makeText(FqStepsCounterActivity.this, "Steps goal created successfully !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
                        startActivity(intent);
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(FqStepsCounterActivity.this, "Internal server error !", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 403) {
                            Toast.makeText(FqStepsCounterActivity.this, response.code() + "Session expire Please login again", Toast.LENGTH_SHORT).show();
                            getAlertTokenExpire();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserStepsGoalResponse> call, Throwable t) {
                    Toast.makeText(FqStepsCounterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAlertTokenExpire() {

        if (getAccessToken != null) {
            dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.profile_alertdialog_logoutt_new, null);
            dialogBuilder.setView(dialogView);
            dialog = dialogBuilder.create();
            ImageView btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
            TextView text_logout = dialogView.findViewById(R.id.text_logout);
            TextView noiwanttocontinue = dialogView.findViewById(R.id.noiwanttocontinuee);
            LinearLayout llLogOut = dialogView.findViewById(R.id.llLogOut);

            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            noiwanttocontinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            text_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FuroPrefs.clear(getApplicationContext());
                    googleSignOut();
                    Intent intent = new Intent(getApplicationContext(), LoginTutorialScreen.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            dialog.show();
        } else {

        }

    }

    public void googleSignOut() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google Sign Out done.", Toast.LENGTH_SHORT).show();
                        revokeAccess();
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        // Toast.makeText(ActivityMain.this, "Google access revoked.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}