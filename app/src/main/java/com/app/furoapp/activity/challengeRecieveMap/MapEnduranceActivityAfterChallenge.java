package com.app.furoapp.activity.challengeRecieveMap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityEnduranceMapBinding;
import com.app.furoapp.databinding.FragmentendurancemapBinding;
import com.app.furoapp.fragment.enduranceMapFragments.EnduranceMapFragmentStart;
import com.app.furoapp.utils.FuroPrefs;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapEnduranceActivityAfterChallenge extends FragmentActivity implements OnMapReadyCallback {


    ActivityEnduranceMapBinding binding;
    SupportMapFragment mapFragment;
    Double lattitude, longitude;
    private long startTime = 0L;
    TextView textViewTime;
    LatLng StartlatLongDistance;
    Context mContext;
    int distance;

    double km;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    LatLng endLatLongDistance;
    long updatedTime = 0L;
    private Polyline polyline_path;
    MarkerOptions source, destination;
    File file;
    private GoogleMap mMapNew;
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    List<LatLng> listOfLocations = new ArrayList<>();

    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    TextView actName;

    MapView mMapView;
    private ImageView imageView;
    String temp;
    String nameActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_endurance_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNew);
        mapFragment.getMapAsync(this);
        textViewTime = findViewById(R.id.timeActivity);
        actName = findViewById(R.id.activityName);


        init();
        startLocationUpdates();
        imageView = findViewById(R.id.imageviewmapNew);
        nameActivity = FuroPrefs.getString(getApplicationContext(), "mapActivity");
        actName.setText(nameActivity);

        binding.StopButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestingLocationUpdates = false;
                stopLocationUpdates();
                CaptureMapScreen();
                handler();


            }
        });


        binding.StartButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MapEnduranceActivityAfterChallenge.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                mRequestingLocationUpdates = true;
                                binding.StartButtonNew.setVisibility(View.GONE);
                                binding.StopButtonNew.setVisibility(View.VISIBLE);
                                startLocationUpdates();
                                startTime = SystemClock.uptimeMillis();
                                customHandler.postDelayed(updateTimerThread, 0);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                if (response.isPermanentlyDenied()) {
                                    openSettings();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }


    public static EnduranceMapFragmentStart newInstance(String name) {
        EnduranceMapFragmentStart fragment = new EnduranceMapFragmentStart();
        Bundle args = new Bundle();
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mSettingsClient = LocationServices.getSettingsClient(getApplicationContext());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                findLatitudeAndLongitude();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }


    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(MapEnduranceActivityAfterChallenge.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());


                    }
                })
                .addOnFailureListener(MapEnduranceActivityAfterChallenge.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {

                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapEnduranceActivityAfterChallenge.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);


                        }


                    }
                });
    }


    public void stopLocationUpdates() {

        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Your Activity stopped!", Toast.LENGTH_SHORT).show();


                    }
                });

        StartlatLongDistance = listOfLocations.get(0);

        Log.i("startlat", String.valueOf(StartlatLongDistance));

        // find last element
        endLatLongDistance = listOfLocations.get(listOfLocations.size() - 1);
        Log.i("endlat", String.valueOf(endLatLongDistance));

        CalculationByDistance();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }


    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }


    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    private void findLatitudeAndLongitude() {
        Log.d("Data", "Yes");
        if (mCurrentLocation != null) {
            lattitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            Log.d("Data", lattitude + "  " + longitude);

            LatLng latLng = new LatLng(lattitude, longitude);
            listOfLocations.add(latLng);

            StartlatLongDistance = listOfLocations.get(0);


            // find last element
            endLatLongDistance = listOfLocations.get(listOfLocations.size() - 1);

            addCameraToMap(latLng);
            drawPolyline(latLng);


        }


    }

    public double CalculationByDistance() {

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(StartlatLongDistance.latitude);
        startPoint.setLongitude(StartlatLongDistance.longitude);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(endLatLongDistance.latitude);
        endPoint.setLongitude(endLatLongDistance.longitude);
        distance= (int) startPoint.distanceTo(endPoint);


        return distance;
    }

   /* public double CalculationByDistance() {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartlatLongDistance.latitude;
        Log.i("lat1", String.valueOf(lat1));
        double lat2 = endLatLongDistance.latitude;
        Log.i("lat2", String.valueOf(lat2));
        double lon1 = StartlatLongDistance.longitude;
        Log.i("lon1", String.valueOf(lon1));
        double lon2 = endLatLongDistance.longitude;
        Log.i("lon2", String.valueOf(lon2));
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
         km = valueResult / 1000;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        //FuroPrefs.putString(getApplicationContext(), "Distanceinm", String.valueOf(kmInDec));
        double meter = kmInDec % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));

        Log.i("distance", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }*/

    public void drawPolyline(LatLng latLng) {

        if (mMapNew == null) {
            return;
        }

        PolylineOptions options = new PolylineOptions()
                .width(18)
                .color(getApplicationContext().getResources().getColor(R.color.light_blue));
        for (int z = 0; z < listOfLocations.size(); z++) {
            LatLng point = listOfLocations.get(z);
            options.add(point);
        }
        mMapNew.clear();
        mMapNew.addPolyline(options);
        mMapNew.addMarker(new MarkerOptions().position(latLng)
                .title("Marker in Sydney"));
        mMapNew.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lattitude, longitude), 18.0f));


    }

    private void addCameraToMap(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(400)
                .build();
        if (mMapNew != null)
            mMapNew.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMapNew = googleMap;

        googleMap.setMyLocationEnabled(true);

    }


    public void handler() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                Intent mainIntent = new Intent(MapEnduranceActivityAfterChallenge.this, PreviewCardActivityRecieve.class);
                mainIntent.putExtra("Distanceinm",distance);
                FuroPrefs.putString(getApplicationContext(), "mapScreenshot", temp);
                startActivity(mainIntent);
                finish();
                binding.StartButtonNew.setVisibility(View.VISIBLE);
                binding.StopButtonNew.setVisibility(View.GONE);

            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    public void CaptureMapScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                // TODO Auto-generated method stub
                bitmap = snapshot;
                try {

                    long time = System.currentTimeMillis();
                    FileOutputStream out = new FileOutputStream("/mnt/sdcard/"
                            + "MyMapScreen" + time
                            + ".png");

                    temp = "/mnt/sdcard/"
                            + "MyMapScreen" + time
                            + ".png";
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();

                    // above "/mnt ..... png" => is a storage path (where image will be stored) + name of image you can customize as per your Requirement


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mMapNew.snapshot(callback);

    }


    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            textViewTime.setText("" + mins + ":"
                    + String.format("%02d", secs));
            String timerValue = textViewTime.getText().toString().trim();
            FuroPrefs.putString(getApplication(), "time", timerValue);
            customHandler.postDelayed(this, 0);


        }

    };


}


