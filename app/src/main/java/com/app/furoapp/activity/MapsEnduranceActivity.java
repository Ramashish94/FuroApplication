package com.app.furoapp.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.R;
import com.app.furoapp.databinding.ActivityEnduranceMapBinding;
import com.app.furoapp.databinding.FragmentendurancemapBinding;
import com.app.furoapp.fragment.enduranceMapFragments.EnduranceMapFragmentStart;
import com.app.furoapp.services.MyBackgroundLocationService;
import com.app.furoapp.utils.FullScreenHelper;
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
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.app.furoapp.activity.LocationTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsEnduranceActivity extends FragmentActivity implements OnMapReadyCallback {

    ActivityEnduranceMapBinding binding;
    SupportMapFragment mapFragment;
    Double lattitude, longitude;
    private long startTime = 0L;
    TextView textViewTime;
    LatLng StartlatLongDistance;
    Context mContext;
    //int distance;
    int Radius = 6371;// radius of earth in Km
    double c;

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

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    public LocationTrack locationTrack;
    public int TIMECOUNT = 10000;
    public boolean ISTRAVERSING = true;
    public double longitud, latitude;
    Handler handler = new Handler();
    public String mPath;
    public Bitmap bitmap;
    public Uri uri;
    // public double totDistance;


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

        getPermission();
        startLocationUpdates();
//        locationUpdate();
        imageView = findViewById(R.id.imageviewmapNew);
        nameActivity = FuroPrefs.getString(getApplicationContext(), "subCategory");
        actName.setText(nameActivity);

        binding.StopButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRequestingLocationUpdates = false;
                stopLocationUpdates();
                CaptureMapScreen();

                FuroPrefs.putString(MapsEnduranceActivity.this, "tracking", "STOPPED");
                int distance = (int) FuroPrefs.getFloat(MapsEnduranceActivity.this, "tripDistance");

                handler(distance);
                Toast.makeText(MapsEnduranceActivity.this, " Tot Distance: " + distance, Toast.LENGTH_LONG).show();

                FuroPrefs.putFloat(MapsEnduranceActivity.this, "tripDistance", 0f);

                takeScreenshot();
            }
        });


        binding.StartButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(MapsEnduranceActivity.this)
                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                mRequestingLocationUpdates = true;
                                binding.StartButtonNew.setVisibility(View.GONE);
                                binding.StopButtonNew.setVisibility(View.VISIBLE);
                                FuroPrefs.putString(MapsEnduranceActivity.this, "tracking", "STARTED");
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
                .addOnSuccessListener(MapsEnduranceActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");
                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                    }
                })
                .addOnFailureListener(MapsEnduranceActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {

                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapsEnduranceActivity.this, REQUEST_CHECK_SETTINGS);
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

        Intent intent = new Intent(this, MyBackgroundLocationService.class);
        ContextCompat.startForegroundService(this, intent);
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
        if (listOfLocations.size() > 0) {
            StartlatLongDistance = listOfLocations.get(0);

            Log.i("startlat", String.valueOf(StartlatLongDistance));


            // find last element
            endLatLongDistance = listOfLocations.get(listOfLocations.size() - 1);
            Log.i("endlat", String.valueOf(endLatLongDistance));


            // CalculationByDistance();
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
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

    /*calculate distance 1st method*/
    public double CalculationByDistance() {

        Location startPoint = new Location("locationA");
        startPoint.setLatitude(StartlatLongDistance.latitude);
        startPoint.setLongitude(StartlatLongDistance.longitude);

        Location endPoint = new Location("locationA");
        endPoint.setLatitude(endLatLongDistance.latitude);
        endPoint.setLongitude(endLatLongDistance.longitude);
        int distance = (int) startPoint.distanceTo(endPoint);

        return distance;
    }

    /*calculate distance 2nd method*/
    public double CalculationByDistance2() {
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
        c = 2 * Math.asin(Math.sqrt(a));
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
    }


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }


    public void handler(int distance) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                Intent mainIntent = new Intent(MapsEnduranceActivity.this, PreviewCardActivity.class);
                mainIntent.putExtra("Distanceinm", distance);
                FuroPrefs.putString(getApplicationContext(), "mapScreenshot", temp);
                FuroPrefs.putString(getApplicationContext(), "imgOfMap_SnapShoot", mPath); // comming from 2nd method
                FuroPrefs.putString(getApplicationContext(), "bitmapOfMapScreenShoot", String.valueOf(bitmap));// comming from 2nd method
                // FuroPrefs.putString(getApplicationContext(), "urlOfImgMapScreenshot", String.valueOf(uri));

                startActivity(mainIntent);
                finish();
                binding.StartButtonNew.setVisibility(View.VISIBLE);
                binding.StopButtonNew.setVisibility(View.GONE);

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    // 1s method of capture img
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

    // 2nd method of capture img <snapshoot>
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            textViewTime.setText("" + mins + ":" + String.format("%02d", secs));
            String timerValue = textViewTime.getText().toString().trim();
            FuroPrefs.putString(getApplication(), "time", timerValue);
            customHandler.postDelayed(this, 0);

        }

    };

    private void getPermission() {
        Dexter.withActivity(this)
                .withPermissions(/*Manifest.permission.READ_CONTACTS,*/
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {


                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    /////////////location update ///////////////////////// not use at/////////////////////////////////////////////////////////////////////////
   /* private void locationUpdate() {
        gpsTrackingWithOutServiceClass();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(sendData);
                ISTRAVERSING = false;

                gpsTrackingWithOutServiceClass();
            }
        }, 5000);
    }

    private void gpsTrackingWithOutServiceClass() {
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        locationTrack = new LocationTrack(MapsEnduranceActivity.this);

        if (locationTrack.canGetLocation()) {

            longitud = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();
            Toast.makeText(MapsEnduranceActivity.this, "Longitude:" + Double.toString(longitud) + "\n" +
                    "Latitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            ISTRAVERSING = true;
            handler.postDelayed(sendData, TIMECOUNT);

        } else {

            locationTrack.showSettingsAlert();
        }
    }

    private Runnable sendData = new Runnable() {
        public void run() {
            try {
                //prepare and send the data here..
                // gpsTrackingWithServiceClass();
                if (ISTRAVERSING) {
                    handler.postDelayed(sendData, TIMECOUNT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (MapsEnduranceActivity.this.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MapsEnduranceActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }*/
    /////////////location update//////////////////////


}