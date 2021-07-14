package com.app.furoapp.activity.newFeature.StepsTracker.userStepsGoalModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.app.furoapp.StepCountingServiceFuro;
import com.app.furoapp.utils.FuroPrefs;

public class Restarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Broadcast Listened", "Service tried to stop");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startService(new Intent(context, StepCountingServiceFuro.class));

        } else {

            context.startForegroundService(new Intent(context, StepCountingServiceFuro.class));
            Toast.makeText(context, "startForegroundService", Toast.LENGTH_SHORT).show();
        }
    }
}