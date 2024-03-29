package com.app.furoapp.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.furoapp.R;
import com.app.furoapp.utils.FuroPrefs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;


public class MyBackgroundLocationService extends Service {
    private static final String TAG = MyBackgroundLocationService.class.getSimpleName();
    private static final String ACTION_CURRENT_LOCATION_BROADCAST ="com.app.furo" ;
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;
    protected String actionReceiver;
    private Location mLastKnownLocation;

    public MyBackgroundLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d(TAG, "onLocationResult: location error");
                    return;
                }

                List<Location> locations = locationResult.getLocations();

                LocationResultHelper helper = new LocationResultHelper(getApplicationContext(), locations);

                //  helper.showNotification();

                helper.saveLocationResults();
                if (locations != null && locations.size() > 0) {
                    mLastKnownLocation=locations.get(0);


                    if (FuroPrefs.getString(MyBackgroundLocationService.this, "tracking").equalsIgnoreCase("STARTED")){
                        if ( Double.parseDouble(FuroPrefs.getString(MyBackgroundLocationService.this, "lastLat"))>0){
                            if (locations.get(0).getLatitude()>-1.0){
                                getDistance(locations.get(0).getLatitude(), locations.get(0).getLongitude(),
                                        Double.parseDouble(FuroPrefs.getString(MyBackgroundLocationService.this, "lastLat")),
                                        Double.parseDouble( FuroPrefs.getString(MyBackgroundLocationService.this, "lastLong"))
                                );
                            }

                        }else{
                            FuroPrefs.putString(MyBackgroundLocationService.this, "lastLat", ""+ locations.get(0).getLatitude());
                            FuroPrefs.putString(MyBackgroundLocationService.this, "lastLong", ""+ locations.get(0).getLongitude());
                        }
                    }
                }

            }
        };
    }


    private void getDistance(double currentLat2, double currentLong2, double lastLat, double lastLong) {
        //   if (ConnectivityReceiver.isConnected()) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {

                Location currentLoc = new Location("");
                currentLoc.setLatitude(currentLat2);
                currentLoc.setLongitude(currentLong2);

                Location lastLoc = new Location("");
                lastLoc.setLatitude(lastLat);
                lastLoc.setLongitude(lastLong);

                return (int) lastLoc.distanceTo(currentLoc);


//                   double theta = lon1 - lon2;
//                   double dist = Math.sin(deg2rad(lat1))
//                           * Math.sin(deg2rad(lat2))
//                           + Math.cos(deg2rad(lat1))
//                           * Math.cos(deg2rad(lat2))
//                           * Math.cos(deg2rad(theta));
//                   dist = Math.acos(dist);
//                   dist = rad2deg(dist);
//                   dist = dist * 60 * 1.1515;
//                   return dist;
            }

            @Override
            protected void onPostExecute(Integer dist) {
                super.onPostExecute(dist);

                Log.d("dist",""+dist);

                if (dist > 5) {
                    try{
                        int distance = FuroPrefs.getInt(MyBackgroundLocationService.this, "tripDistance",0);
                        distance = distance + dist;
                        FuroPrefs.putInt(MyBackgroundLocationService.this, "tripDistance", (int) distance);

                        FuroPrefs.putString(MyBackgroundLocationService.this, "lastLat",  ""+currentLat2);
                        FuroPrefs.putString(MyBackgroundLocationService.this, "lastLong",  ""+currentLong2);

                        String data ="Latitude: "+ String.valueOf(lastLat)+ " Longitude : "+ String.valueOf(lastLong)+" Distance gap "+dist+"\n";
                        //  FuroPrefs.putKey(MyBackgroundLocationService.this, ""+System.currentTimeMillis(), data);

                    }catch (Exception e){
                        e.printStackTrace();

                    }

                    // AppUtils.writeToFile(data, MyBackgroundLocationService.this);


                }


                //FuroPrefs.putKey(MainActivity.this, "totalDist", String.valueOf(TRIPDISTANCE));

            }
        }.execute();
        // }

    }


    private void sendLocationBroadcast(Location sbLocationData) {
        Intent locationIntent = new Intent();
        locationIntent.setAction("my.action");
        // locationIntent.putExtra(SettingsLocationTracker.LOCATION_MESSAGE, sbLocationData);
        sendBroadcast(locationIntent);
        updateLocationToServer();
    }

    private void updateLocationToServer() {

    }

    private void sendCurrentLocationBroadCast(Location sbLocationData) {
        Intent locationIntent = new Intent();
        locationIntent.setAction(ACTION_CURRENT_LOCATION_BROADCAST);
        // locationIntent.putExtra(SettingsLocationTracker.LOCATION_MESSAGE, sbLocationData);
        sendBroadcast(locationIntent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called");

        startForeground(1001, getNotification());

        getLocationUpdates();

        return START_STICKY;
    }

    private Notification getNotification() {

        NotificationCompat.Builder notificationBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="test";
            String description = "test2";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default-channel", name, importance);
            channel.setDescription(description);

            // Don't see these lines in your code...
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),
                "default-channel")
                .setContentTitle("FQ")
                .setContentText("Fq service is running")
                .setSmallIcon(R.mipmap.app_icon_new)
                .setVibrate(null)
                .setAutoCancel(true);


        return notificationBuilder.build();
    }

    private void getLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2*60 * 1000);
        locationRequest.setFastestInterval(30000);

        locationRequest.setMaxWaitTime(2*60 * 1000);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            return;
        }

        mLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
        stopForeground(true);
        mLocationClient.removeLocationUpdates(mLocationCallback);
    }
}