package com.app.furoapp.activity.newFeature.StepsTracker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.work.impl.utils.ForceStopRunnable;

import com.app.furoapp.R;
import com.app.furoapp.StepCountingServiceFuro;
import com.app.furoapp.activity.Globals;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.SensorRestarterBroadcastReceiver;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.DataItem;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.TipsResponse;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.Restarter;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FqStepsCounterActivity extends AppCompatActivity {
    public ImageView ivBackIcon, ivSetting, ivLeadBord, ivModified, ivHistory;
    public TextView tvCountsSteps, tvTotNumberOfSteps, tvTimes, tvCalories, tvDistance, tvActivateStepsCounter, tvDiActivate, tvMarkLap, tvStop, tvPrizmTips;
    public View includeCongratsStepsTrack, incudeAlertDialog;
    public double magnitudePrevious = 0;
    private Integer stepCount = -1;
    PowerManager.WakeLock wakeLock ;
    PowerManager pm;
    public SensorManager sensorManager;
    Intent mServiceIntent;
    private StepCountingServiceFuro mYourService;
    public Sensor sensorAcceleroMeter;
    public static final String TAG = "AddNewSlotActivity";
    private float getDistanceMiAndKm;
    private Handler customHandler = new Handler();
    private Handler tipsHandler = new Handler();
    private List<DataItem> tipsList;
    private int tipsListSize = 0;
    private int tipsStart = 0;
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
    public LinearLayout llFqStepCounter, llMarkLapAndStop, llCongratsClosedIcon, llModifiedAlert;
    public String selectNumberAchievedVal;
    private Integer getStepsCount;
    private boolean isServiceStopped;
    public Intent intent;
    public String countedStep;
    public String DetectedStep;
    public Integer getCountSteps;
    public Integer getDetectedSteps;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private ImageView btn_Cancel;
    private TextView tvContinue, tvModified;
    public SwitchCompat swBtnInKm;
    private String distancetype;
    private boolean isSwitchedChecked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fq_steps_counter);


        mYourService = new StepCountingServiceFuro();
        mServiceIntent = new Intent(this, mYourService.getClass());
        if (!isMyServiceRunning(mYourService.getClass())) {
            startService(mServiceIntent);
        }
        initViews();
        //  clickEvent();

        stepsAchivedVal = getIntent().getStringExtra("getAchievedVal");
        selectNumberAchievedVal = getIntent().getStringExtra("selectNumber");
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        Intent intentq = getIntent();
        if (intentq != null) {
            String userCount = String.valueOf(intentq.getIntExtra("counter_s", 0));
            String userCal = String.valueOf(intentq.getFloatExtra("calo", 0.0f));
            boolean isActivate = intentq.getBooleanExtra("hideActivateButton", false);
            String time = intentq.getStringExtra("time");

            if (isActivate == true) {
                tvActivateStepsCounter.setVisibility(View.INVISIBLE);
                tvDiActivate.setVisibility(View.VISIBLE);

            }


            tvCalories.setText("" + userCal);
            tvTimes.setText(time);

            tvCountsSteps.setText(userCount);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAcceleroMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        setdata();


        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACTIVITY_RECOGNITION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // ___ instantiate intent ___ \\
                            //  Instantiate the intent declared globally - which will be passed to startService and stopService.
                            intent = new Intent(FqStepsCounterActivity.this, StepCountingServiceFuro.class);

                            //  init(); // Call view initialisation method.
                            clickEvent();

                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        callTipsApi();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");

                return true;

            }
        }
        Log.i("Service status", "Not running");
        return false;
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
        incudeAlertDialog = findViewById(R.id.incudeAlertDialog);
        llFqStepCounter = findViewById(R.id.llFqStepCounter);
        tvStop = findViewById(R.id.tvStop);
        tvMarkLap = findViewById(R.id.tvMarkLap);
        llMarkLapAndStop = findViewById(R.id.llMarkLapAndStop);
        llCongratsClosedIcon = findViewById(R.id.llCongratsClosedIcon);
        btn_Cancel = findViewById(R.id.btn_cancel);
        tvContinue = findViewById(R.id.tvContinue);
        tvModified = findViewById(R.id.tvModified);
        //llModifiedAlert=findViewById(R.id.llModifiedAlert);
        swBtnInKm = findViewById(R.id.swBtnInKm);
        tvPrizmTips = findViewById(R.id.tvPrizmTips);
        isServiceStopped = true;
    }


    private void clickEvent() {
        ivBackIcon.setOnClickListener(v -> {
            finish();
        });

        ivSetting.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), StepCounterSettingsActivity.class);
            startActivity(intent);
            finish();
        });
        ivLeadBord.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
            startActivity(intent);

        });

// ________________ Service Management (Start & Stop Service). ________________ //
        // ___ start Service & register broadcast receiver ___ \\
        tvActivateStepsCounter.setOnClickListener(v -> {
            tvDiActivate.setVisibility(View.VISIBLE);
            tvActivateStepsCounter.setVisibility(View.GONE);


            stepCounterTime();// step counter functionality implimentation
            // start Service.
            FuroPrefs.putFloat(this, "colories", 0);
            FuroPrefs.putString(getApplication(), "time", "0");
            FuroPrefs.putInt(this, "step_Count", 0);
            startService(new Intent(getBaseContext(), StepCountingServiceFuro.class));
            // register our BroadcastReceiver by passing in an IntentFilter. * identifying the message that is broadcasted by using static string "BROADCAST_ACTION".
            registerReceiver(broadcastReceiver, new IntentFilter(StepCountingServiceFuro.BROADCAST_ACTION));
            isServiceStopped = false;


        });


        // ___ unregister receiver & stop service ___ \\
        tvDiActivate.setOnClickListener(v -> {
            tvActivateStepsCounter.setVisibility(View.GONE);
            llMarkLapAndStop.setVisibility(View.GONE);
            tvDiActivate.setVisibility(View.GONE);
            tvActivateStepsCounter.setVisibility(View.VISIBLE);
            customHandler.removeCallbacks(updateTimerThread);


            if (!isServiceStopped) {
                // call unregisterReceiver - to stop listening for broadcasts.
                unregisterReceiver(broadcastReceiver);
                // stop Service.
                stopService(new Intent(getBaseContext(), StepCountingServiceFuro.class));
                isServiceStopped = true;
                FuroPrefs.putFloat(this, "colories", 0.0f);
                FuroPrefs.putInt(this, "step_Count", 0);


            }

            callUserStepGoalApi();
        });

        ivHistory.setOnClickListener(v -> {
            //   Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
            Intent intent = new Intent(getApplicationContext(), StepCounterHistoryActivity.class);
            startActivity(intent);

        });
//        tvMarkLap.setOnClickListener(v -> {
//            stepCounterTime();// step counter functionality implimentation
//            // start Service.
//            startService(new Intent(getBaseContext(), StepCountingServiceFuro.class));
//            // register our BroadcastReceiver by passing in an IntentFilter. * identifying the message that is broadcasted by using static string "BROADCAST_ACTION".
//            registerReceiver(broadcastReceiver, new IntentFilter(StepCountingServiceFuro.BROADCAST_ACTION));
//            isServiceStopped = false;
//
//            llMarkLapAndStop.setVisibility(View.GONE);
//            tvDiActivate.setVisibility(View.VISIBLE);
//        });

//        tvStop.setOnClickListener(v -> {
//            customHandler.removeCallbacks(updateTimerThread);
//            finish();
//        });

        llCongratsClosedIcon.setOnClickListener(v -> {
            includeCongratsStepsTrack.setVisibility(View.GONE);
            finish();
        });

        ivModified.setOnClickListener(v -> {
            //openModifiedAlertDialog();
            incudeAlertDialog.setVisibility(View.VISIBLE);
            llFqStepCounter.setClickable(false);
        });

        btn_Cancel.setOnClickListener(v -> {
            incudeAlertDialog.setVisibility(View.GONE);
        });

        tvContinue.setOnClickListener(v -> {
            incudeAlertDialog.setVisibility(View.GONE);
        });
        tvModified.setOnClickListener(v -> {
            incudeAlertDialog.setVisibility(View.GONE);
            intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void setdata() {

        if (stepsAchivedVal != null) {
            tvTotNumberOfSteps.setText("Of " + stepsAchivedVal + " Steps");
        } else {
            tvTotNumberOfSteps.setText("Of " + selectNumberAchievedVal + " Steps");
        }
        int goal = FuroPrefs.getInt(getApplicationContext(),"achivedSteps",0);
        tvTotNumberOfSteps.setText("/"+goal);
    }

    public void stepCounterTime() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

       /* if (countedStep != null) {
            distanceInMeter = getDistanceRun(countedStep);
            Log.d(TAG, "onSensorChanged() called with: distanceInMeter = [" + distanceInMeter + "]");
            tvDistance.setText("" + distanceInMeter + " meter");
            getCalculateCalories = calculateCalories(countedStep);
            tvCalories.setText("" + getCalculateCalories);
            Log.d(TAG, "onSensorChanged() called with: Calories = [" + calculateCalories((long) getCalculateCalories) + "]");
        }*/
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
            /*":" + String.format("%02d", secs)*/
            String timerValue = tvTimes.getText().toString().trim();
            FuroPrefs.putString(getApplication(), "time", timerValue);


            tvTimes.setText("" + getTime + " min");
            customHandler.postDelayed(this, 0);
        }
    };


    private void callUserStepGoalApi() {
        UserStepsGoalRequest userStepsGoalRequest = new UserStepsGoalRequest();
        userStepsGoalRequest.setTime(getTime);
        userStepsGoalRequest.setDistance(String.valueOf(getDistanceMiAndKm));
        userStepsGoalRequest.setCalories(String.valueOf(getCalculateCalories));
        userStepsGoalRequest.setCount_steps(String.valueOf(DetectedStep));
        if (stepsAchivedVal != null) {
            userStepsGoalRequest.setTotal_steps(stepsAchivedVal);
        } else {
            userStepsGoalRequest.setTotal_steps(selectNumberAchievedVal);
        }

        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getUserStepsGoal(getAccessToken, userStepsGoalRequest, new Callback<UserStepsGoalResponse>() {
                @Override
                public void onResponse(Call<UserStepsGoalResponse> call, Response<UserStepsGoalResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (stepsAchivedVal != null) {
                            if (stepsAchivedVal.equalsIgnoreCase(String.valueOf(stepCount))) {
                                includeCongratsStepsTrack.setVisibility(View.VISIBLE);
                                llFqStepCounter.setClickable(false);
                            }
                        } else if (selectNumberAchievedVal != null) {
                            if (selectNumberAchievedVal.equalsIgnoreCase(String.valueOf(stepCount))) {
                                includeCongratsStepsTrack.setVisibility(View.VISIBLE);
                                llFqStepCounter.setClickable(false);
                            }
                        } else {
                           /* Toast.makeText(FqStepsCounterActivity.this, "Steps goal created successfully !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HistoryDetailsActivity.class);
                            startActivity(intent);*/
                        }
                        Toast.makeText(FqStepsCounterActivity.this, "Data saved Successfully !", Toast.LENGTH_SHORT).show();

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
            View dialogView = inflater.inflate(R.layout.session_expired_layout, null);
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

    // ___ retrieve data from intent & set data to textviews __ \\
    private void updateViews(Intent intentData) {


        // retrieve data out of the intent.
        countedStep = intentData.getStringExtra("Counted_Step");
        DetectedStep = intentData.getStringExtra("Detected_Step");
        Log.d(TAG, String.valueOf(countedStep));
        tvCountsSteps.setText("" + DetectedStep + " ");
        Log.d(TAG, String.valueOf(DetectedStep));

        getCountSteps = Integer.valueOf(countedStep);
        getDetectedSteps = Integer.parseInt(DetectedStep);

        /*added*/
        getCalculateCalories = (float) (getDetectedSteps * 0.045);

        tvCalories.setText("" + getCalculateCalories + " Cal");
        FuroPrefs.putFloat(this, "colories", getCalculateCalories);

        int steps = Integer.parseInt(countedStep);

        FuroPrefs.putInt(this, "step_Count", steps);
        Log.d(TAG, "onSensorChanged() called with: Calories = [" + calculateCalories((long) getCalculateCalories) + "]");


        swBtnInKm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isSwitchedChecked = true;
                    getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100000;
                    tvDistance.setText("" + getDistanceMiAndKm + " km");
                } else {
                    isSwitchedChecked = false;
                    getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100;
                    tvDistance.setText("" + getDistanceMiAndKm + " m");
                }
                //tvDistance.setText("" + getDistanceMiAndKm + " meter");


            }

        });


        if (isSwitchedChecked) {
            getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100000;
            tvDistance.setText("" + getDistanceMiAndKm + " km");
        } else if (isSwitchedChecked == false) {
            getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100;
            tvDistance.setText("" + getDistanceMiAndKm + " m");
        }



    }

    private void getDistance(String distancetype) {
        if (distancetype.equals("Distance")) {
            getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100000;
            tvDistance.setText("" + getDistanceMiAndKm + " km");
        } else {
            getDistanceMiAndKm = (float) (getDetectedSteps * 78) / (float) 100;
            tvDistance.setText("" + getDistanceMiAndKm + " m");
        }
    }


    // ___ create Broadcast Receiver ___ \\
    // create a BroadcastReceiver - to receive the message that is going to be broadcast from the Service
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // call updateUI passing in our intent which is holding the data to display.
            updateViews(intent);
        }
    };
    // ___________________________________________________________________________ \\


    private Runnable tipsRunnable = new Runnable() {
        public void run() {
            if (tipsList != null && tipsList.size() > 0) {
                if (tipsStart == (tipsListSize - 1)) {
                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                    tipsStart = 0;
                } else {
                    if (tipsList != null && tipsList.size() > 0) {
                        tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                        tipsStart++;
                    }
                }
            }
            tipsHandler.postDelayed(this, 5000);
        }
    };


    private void callTipsApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getAllTipsData(getAccessToken, new Callback<TipsResponse>() {
                @Override
                public void onResponse(Call<TipsResponse> call, Response<TipsResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        Log.d(TAG, "onResponse() called with: , response = [" + response.body() + "]");
                        if (response.body().getData() != null && response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            tipsList = response.body().getData().getData();
                            tipsListSize = tipsList.size();
                            tipsHandler.postDelayed(tipsRunnable, 0);
                        } else {
                            Toast.makeText(FqStepsCounterActivity.this, "No tips data found", Toast.LENGTH_SHORT).show();
                        }
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
                public void onFailure(Call<TipsResponse> call, Throwable t) {
                    Toast.makeText(FqStepsCounterActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void openModifiedAlertDialog() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertt_dialog_modified_data_, null);


        dialogBuilder.setView(dialogView);

        final AlertDialog dialog = dialogBuilder.create();


        btn_Cancel = dialogView.findViewById(R.id.btn_cancel);
        tvContinue = dialogView.findViewById(R.id.tvContinue);
        tvModified = dialogView.findViewById(R.id.tvModified);
        llFqStepCounter.setClickable(false);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvModified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FqStepsCounterActivity.super.onBackPressed();
                dialog.dismiss();
                finish();
              /*  finishAffinity();
                System.exit(0);*/
            }
        });
        dialog.show();
    }


    @Override
    protected void onDestroy() {


        FuroPrefs.putFloat(this, "colories", 0.0f);
        FuroPrefs.putInt(this, "step_Count", 0);

        super.onDestroy();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
        startActivity(intent);
        FuroPrefs.putFloat(this, "colories", 0.0f);
        FuroPrefs.putInt(this, "step_Count", 0);
        finish();
    }
    @SuppressLint("InvalidWakeLockTag")
    public void acquirewakeLock() {
        if(Globals.wakelock!=null){
            Globals.wakelock.release();
            Globals.wakelock=null;
        }
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "TrackerService");
        wakeLock.acquire();
        Globals.wakelock=this.wakeLock;
    }
    public void releaseWakelock() {
        wakeLock.release();
    }

}