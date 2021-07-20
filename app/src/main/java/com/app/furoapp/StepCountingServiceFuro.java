package com.app.furoapp;

/**
 * Service - for Counting the steps in the Background using Step Counter Sensor, and broadcasting Sensor values to Main Activity.
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.os.PowerManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.app.furoapp.R;
import com.app.furoapp.activity.SensorRestarterBroadcastReceiver;
import com.app.furoapp.activity.newFeature.StepsTracker.FqStepsCounterActivity;
import com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel.Restarter;
import com.app.furoapp.utils.FuroPrefs;

import java.util.Date;

// ___ Extend Service class & implement Service lifecycle callback methods. ___ //
public class StepCountingServiceFuro extends Service implements SensorEventListener {

    SensorManager sensorManager;
    Sensor stepCounterSensor;
    Sensor stepDetectorSensor;


    //int currentStepCount;
    int currentStepsDetected;
    PendingIntent notifyPendingIntent;

    int stepCounter,count_Step;
    int newStepCounter;
    String achivedStep;

    boolean serviceStopped; // Boolean variable to control the repeating timer.

    NotificationManager notificationManager;

    // --------------------------------------------------------------------------- \\
    // _ (1) declare broadcasting element variables _ \\
    // Declare an instance of the Intent class.
    Intent intent;
    // A string that identifies what kind of action is taking place.
    private static final String TAG = "StepService";
    public static final String BROADCAST_ACTION = "com.app.furo.step.counter";
    // Create a handler - that will be used to broadcast our data, after a specified amount of time.
    private final Handler handler = new Handler();
    // Declare and initialise counter - for keeping a record of how many times the service carried out updates.
    int counter = 0;
    // _________________________ \\

    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        super.onCreate();

        // --------------------------------------------------------------------------- \\
        // _ (2) create/instantiate intent. _ \\
        // Instantiate the intent declared globally, and pass "BROADCAST_ACTION" to the constructor of the intent.
        intent = new Intent(BROADCAST_ACTION);
        // _________________________ \\
    }
    public StepCountingServiceFuro(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public StepCountingServiceFuro() {
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("Service", "Start");

     count_Step = FuroPrefs.getInt(this, "step_Count", 0);
        float calories = FuroPrefs.getFloat(this, "colories");
        String timmmer = FuroPrefs.getString(getApplication(), "time");
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp::MyWakelockTag");
        wakeLock.acquire();
        showNotification(count_Step, calories, timmmer);

//        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

        //currentStepCount = 0;
        currentStepsDetected = 0;
        stepCounter = 0;
        newStepCounter = 0;

        serviceStopped = false;

        // --------------------------------------------------------------------------- \\
        // _ (3) start handler _ \\
        /////if (serviceStopped == false) {
        // remove any existing callbacks to the handler
        handler.removeCallbacks(updateBroadcastData);
        // call our handler with or without delay.
        handler.post(updateBroadcastData); // 0 seconds
        /////}
        // _________________________ \\

        return START_STICKY;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
        sendBroadcast(broadcastIntent);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(this, Restarter.class);
        sendBroadcast(intent);
        super.onTaskRemoved(rootIntent);
    }

    /** Called when the overall system is running low on memory, and actively running processes should trim their memory usage. */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /////////////////______ Sensor Event. ______//////////////////
    @Override
    public void onSensorChanged(SensorEvent event) {
        // STEP_COUNTER Sensor.
        // * Step Counting does not restart until the device is restarted - therefore, an algorithm for restarting the counting must be implemented.
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int countSteps = (int) event.values[0];

            // -The long way of starting a new step counting sequence.-
            /**
             int tempStepCount = countSteps;
             int initialStepCount = countSteps - tempStepCount; // Nullify step count - so that the step cpuinting can restart.
             currentStepCount += initialStepCount; // This variable will be initialised with (0), and will be incremented by itself for every Sensor step counted.
             stepCountTxV.setText(String.valueOf(currentStepCount));
             currentStepCount++; // Increment variable by 1 - so that the variable can increase for every Step_Counter event.
             */

            // -The efficient way of starting a new step counting sequence.-
            if (stepCounter == 0) { // If the stepCounter is in its initial value, then...
                stepCounter = (int) event.values[0]; // Assign the StepCounter Sensor event value to it.
            }
            newStepCounter = countSteps - stepCounter; // By subtracting the stepCounter variable from the Sensor event value - We start a new counting sequence from 0. Where the Sensor event value will increase, and stepCounter value will be only initialised once.
            // Toast.makeText(this, "FQ_counter - " + String.valueOf(newStepCounter), Toast.LENGTH_LONG).show();

        }

        // STEP_DETECTOR Sensor.
        // * Step Detector: When a step event is detect - "event.values[0]" becomes 1. And stays at 1!
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            int detectSteps = (int) event.values[0];
            currentStepsDetected += detectSteps; //steps = steps + detectSteps; // This variable will be initialised with the STEP_DETECTOR event value (1), and will be incremented by itself (+1) for as long as steps are detected.
            //  Toast.makeText(this, "FQ_counter - " + String.valueOf(currentStepsDetected), Toast.LENGTH_LONG).show();
            int   countStep = currentStepsDetected;
            Float colories = FuroPrefs.getFloat(getApplicationContext(), "colories");
            String timmmer = FuroPrefs.getString(getApplication(), "time");
            int stepCount = (currentStepsDetected + count_Step);
            showNotification(stepCount, colories,timmmer);
        }

        Log.v("Service Counter", String.valueOf(newStepCounter));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    // _________________________ \\


    // --------------------------------------------------------------------------- \\
    // _ Manage notification. _
    private void showNotification(int counter, Float colori, String timer) {

        if (counter == 0) {

        } else {
            Intent notifyIntent = new Intent(this, FqStepsCounterActivity.class);
// Set the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Create the PendingIntent
            notifyIntent.putExtra("counter_s", counter);
            notifyIntent.putExtra("calo", colori);
            notifyIntent.putExtra("time",timer);
            notifyIntent.putExtra("achived",achivedStep);
            notifyIntent.putExtra("hideActivateButton", true);


            notifyPendingIntent = PendingIntent.getActivity(
                    this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            );

        }


        RemoteViews contentView = new RemoteViews(this.getPackageName(), R.layout.collapsenotification);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "");

        String count = String.valueOf(counter);
        contentView.setTextViewText(R.id.content_title, count);
        contentView.setTextViewText(R.id.content_colories, "" + colori);


        notificationBuilder.setSmallIcon(R.mipmap.app_icon);
        notificationBuilder.setColor(Color.parseColor("#6600cc"));
        int colorLED = Color.argb(255, 0, 255, 0);
        notificationBuilder.setLights(colorLED, 500, 500);
        // To  make sure that the Notification LED is triggered.
        notificationBuilder.setPriority(Notification.PRIORITY_LOW);
        notificationBuilder.setOngoing(true);
        notificationBuilder.setSilent(true);
        notificationBuilder.setContent(contentView);

        notificationBuilder.setContentIntent(notifyPendingIntent);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("", "FURO FQ", importance);
            notificationBuilder.setChannelId("");
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel);
        }


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void dismissNotification() {
        notificationManager.cancel(0);
    }
    // ______________________________ \\


    // --------------------------------------------------------------------------- \\
    // _ (4) repeating timer _ \\
    private Runnable updateBroadcastData = new Runnable() {
        public void run() {
            if (!serviceStopped) { // Only allow the repeating timer while service is running (once service is stopped the flag state will change and the code inside the conditional statement here will not execute).
                // Call the method that broadcasts the data to the Activity..
                broadcastSensorValue();
                // Call "handler.postDelayed" again, after a specified delay.
                handler.postDelayed(this, 1000);
            }
        }
    };
    // _________________________ \\

    // --------------------------------------------------------------------------- \\
    // _ (5) add  data to intent _ \\
    private void broadcastSensorValue() {
        Log.d(TAG, "Data to Activity");
        // add step counter to intent.
        intent.putExtra("Counted_Step_Int", newStepCounter);
        intent.putExtra("Counted_Step", String.valueOf(newStepCounter));
        // add step detector to intent.
        intent.putExtra("Detected_Step_Int", currentStepsDetected);
        intent.putExtra("Detected_Step", String.valueOf(currentStepsDetected));
        // call sendBroadcast with that intent  - which sends a message to whoever is registered to receive it.
        sendBroadcast(intent);
    }
    // _________________________ \\


}