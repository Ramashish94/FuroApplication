package com.app.furoapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.furoapp.StepCountingServiceFuro;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
 @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(SensorRestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startForegroundService(new Intent(context, StepCountingServiceFuro.class));;

    }
}
