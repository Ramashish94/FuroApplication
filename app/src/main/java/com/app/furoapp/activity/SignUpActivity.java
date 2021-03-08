package com.app.furoapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.BuildConfig;
import com.app.furoapp.R;
import com.app.furoapp.activity.tutorialScreens.LoginWithEmailActivity;
import com.app.furoapp.adapter.AutoSuggestAdapter;
import com.app.furoapp.databinding.ActivitySignUpBinding;
import com.app.furoapp.model.signUp.SignupResponse;
import com.app.furoapp.model.uniqueusername.UniqueUserNameRequest;
import com.app.furoapp.model.uniqueusername.UniqueUserNameResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FileUtils1;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    public static final int REQUEST_IMAGE = 100;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    private static final String TAG1 = SignUpActivity.class.getSimpleName();
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private static GoogleApiClient mGoogleApiClient;
    ActivitySignUpBinding activitySignUpBinding;
    File imageFile;
    MultipartBody.Part image;
    String[] country = {"Male", "Female", "Transgender"};
    List<UniqueUserNameResponse> uniqueUserNameResponses;
    List<UniqueUserNameResponse> uniqueUserNameResponses1;
    String path;
    AutoCompleteTextView uniqueUserName;
    Spinner spin;
    String fbId;
    private String imageUrl;
    private String userGender;
    private TextView gps_status;
    private String state = "";
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;
    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Represents a geographical location.
     */
    private Location mLastLocation;
    private boolean mAddressRequested;
    private AddressResultReceiver mResultReceiver;
    private String mAddressOutput;
    private String address = "";
    private String mCountry = "";
    private String userPlatform;
    private String city = "", googleId;
    private ImageView profilePlus, imgUserProfile;
    private DatePickerDialog dp;
    private Calendar c;
    private EditText signName, signEmail, signPassword, signCountry, signState, signCity, contectNumber;
    private TextView signDateOfBirth, signUpButton;
    private String userNameUnique, id, name, email, pictureurl;
    //Run on UI
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            showSettingDialog();
        }
    };
    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("About GPS", "GPS is Enabled in your device");
                } else {
                    //If GPS turned OFF show Location Dialog
                    new Handler().postDelayed(sendUpdatesToUI, 10);
                    // showSettingDialog();
                    Log.e("About GPS", "GPS is Disabled in your device");
                }

            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        userPlatform = "Android";
        googleId = FuroPrefs.getString(getApplication(), "googl_id");
        fbId = FuroPrefs.getString(getApplication(), "fb_id");
        name = FuroPrefs.getString(getApplication(), "name");
        email = FuroPrefs.getString(getApplication(), "email");
        /* pictureurl = FuroPrefs.getString(getApplication(), "picture");*/

        signName = activitySignUpBinding.ivLoginName;
        signEmail = activitySignUpBinding.etEmail;
        signPassword = activitySignUpBinding.etPassword;
        imgUserProfile = findViewById(R.id.userprofilesignup);
        profilePlus = activitySignUpBinding.imgPlus;
        uniqueUserName = activitySignUpBinding.uniqueUserName;
        signDateOfBirth = activitySignUpBinding.tvDob;
        signCountry = activitySignUpBinding.country;
        signUpButton = activitySignUpBinding.etSignUp;
        signState = activitySignUpBinding.State;
        signCity = activitySignUpBinding.city;
        contectNumber = activitySignUpBinding.mobileNumber;

        if (!fbId.equals("") || !googleId.equals("")) {
            signPassword.setVisibility(View.GONE);
        } else {
            signPassword.setVisibility(View.VISIBLE);
        }


        int resultValue = 0;

        signName.setText(name);
        signEmail.setText(email);
      /*  if (TextUtils.isEmpty(pictureurl)) {


        } else {
            Picasso.with(this).load(pictureurl).error(R.drawable.user_icon)
                    .into(imgUserProfile);
        }
*/


        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested = false;
        mAddressOutput = "";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        spin = findViewById(R.id.spinnersignupactivity);
        //spin.setOnItemSelectedListener(this);

        getContactPermission();
        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnTakePermission();
            }
        });


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.spinner_item, country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                userGender = spin.getSelectedItem().toString();
                Log.i("usergender", userGender);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        fetchAddressButtonHandler();
        initGoogleAPIClient();//Init Google API Client
        checkPermissionsOnPhone();//C


        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getAddress();
        }
        if (TextUtils.isEmpty(mAddressOutput)) {
            getAddress();

        }

        autoSuggestAdapter = new AutoSuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        uniqueUserName.setThreshold(2);
        uniqueUserName.setAdapter(autoSuggestAdapter);
        uniqueUserName.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        userNameUnique = autoSuggestAdapter.getObject(position);
                    }
                });

        uniqueUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(uniqueUserName.getText())) {
                        uniqueUserName(uniqueUserName.getText().toString());

                    }
                }
                return false;
            }
        });


        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);

        setOnClickListner();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validationSignUp();
            }
        });
    }

    private void getContactPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS
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

    public void setOnClickListner() {
        activitySignUpBinding.llDob.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;

            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MINUTE, 13);
                c.set(Calendar.HOUR, 7);
                c.set(Calendar.AM_PM, Calendar.AM);
                c.set(Calendar.MONTH, Calendar.JANUARY);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.YEAR, 1995);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                dp = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dp.getDatePicker().setMinDate(c.getTimeInMillis());
                        activitySignUpBinding.tvDob.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                dp.show();
            }
        });

    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constants.CAPTURE_IMAGE);
            }
        }

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                //   Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getAddress();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }

    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {

        if (TextUtils.isEmpty(mAddressOutput)) {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(mainTextStringId),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(actionStringId), listener).show();
        }

    }
/*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        userGender = spin.getSelectedItem().toString();
        Log.i("usergender",userGender);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

    private void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }

    /**
     * Gets the address for the last known location.
     */
    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location == null) {
                            Log.w(TAG, "onSuccess:null");
                            return;
                        }

                        mLastLocation = location;

                        // Determine whether a Geocoder is available.
                        // Determine whether a Geocoder is available.
                        if (!Geocoder.isPresent()) {
                            showSnackbar(getString(R.string.no_geocoder_available));
                            return;
                        }
                        // If the user pressed the fetch address button before we had the location,
                        // this will be set to true indicating that we should kick off the intent
                        // service after fetching the location.
                        if (mAddressRequested) {
                            startIntentService();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getLastLocation:onFailure", e);
                    }
                });
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            //  Log.i(TAG, "Displaying permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);

        } else {
            //  Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void fetchAddressButtonHandler() {
        if (mLastLocation != null) {
            startIntentService();
            return;
        }

        // If we have not yet retrieved the user location, we process the user's request by setting
        // mAddressRequested to true. As far as the user is concerned, pressing the Fetch Address button
        // immediately kicks off the process of getting the address.
        mAddressRequested = true;
    }

    /* Initiate Google API Client  */
    private void initGoogleAPIClient() {
        //Without Google API Client Auto Location Dialog will not work
        mGoogleApiClient = new GoogleApiClient.Builder(SignUpActivity.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissionsOnPhone() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SignUpActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }

    /*  Show Popup to access User Permission  */
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(SignUpActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    /* Show Location Access Dialog */
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(SignUpActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/

    /* @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           switch (requestCode) {
               // Check for the integer request code originally supplied to startResolutionForResult().
               case REQUEST_CHECK_SETTINGS:
                   switch (resultCode) {
                       case RESULT_OK:
                           Log.e("Settings", "Result OK");
                           //startLocationUpdates();
                           getAddress();
                           break;
                       case RESULT_CANCELED:
                           Log.e("Settings", "Result Cancel");
                           break;
                       case REQUEST_IMAGE:

                           if (resultCode == Activity.RESULT_OK) {
                               Uri uri = data.getParcelableExtra("path");
                               try {
                                   // You can update this bitmap to your server
                                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                                   // loading profile image from local cache
                                   loadProfile(uri.toString());
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                   }
                   break;
           }
       }
   */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
        registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister receiver on destroy
        if (gpsLocationReceiver != null)
            unregisterReceiver(gpsLocationReceiver);
    }

    public void validationSignUp() {
        boolean check = true;

        pictureurl = FuroPrefs.getString(getApplication(), "picture");

        String mobilenumber = contectNumber.getText().toString().trim();
        String userName = signName.getText().toString().trim();
        String userEmail = signEmail.getText().toString().trim();
        String userPassword = signPassword.getText().toString().trim();
        String userDob = signDateOfBirth.getText().toString().trim();
        String userUniName = uniqueUserName.getText().toString().trim();

        if (TextUtils.isEmpty(userName.trim()) || signName.length() == 0) {
            Util.displayToast(this, "Please enter Name");
            check = false;
        }
        if (TextUtils.isEmpty(mobilenumber.trim()) || signName.length() == 0) {
            Util.displayToast(this, "Please enter Mobile number");
            check = false;
        }
        if (TextUtils.isEmpty(userEmail.trim()) || signEmail.length() == 0) {
            Util.displayToast(this, "Please enter valid email");
            check = false;
        }
        if (TextUtils.isEmpty(userDob.trim()) || signDateOfBirth.length() == 0) {
            Util.displayToast(this, "Please enter Date Of Birth");
            check = false;
        }

        if (googleId.equals("") && fbId.equals("")) {
            if (TextUtils.isEmpty(userPassword.trim()) || signPassword.length() == 0) {
                Util.displayToast(this, "Please enter valid password");
                check = false;
            }
        }
        if (TextUtils.isEmpty(mCountry) || signCountry.length() == 0) {
            Util.displayToast(this, "Please enter valid country");
            check = false;
        }
        if (TextUtils.isEmpty(city) || signCity.length() == 0) {
            Util.displayToast(this, "Please enter city");
            check = false;
        }
        if (TextUtils.isEmpty(state) || signCountry.length() == 0) {
            Util.displayToast(this, "Please enter state");
            check = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Util.displayToast(this, "Please enter valid email");
            check = false;
        }
        if (!validName(signName)) {
            Util.displayToast(this, "Special character not allowed in name");// email validation
            check = false;
        }
        if (TextUtils.isEmpty(userGender)) {

            Util.displayToast(this, "Please select gender");
            check = false;

        }
        if (TextUtils.isEmpty(mAddressOutput) || mAddressOutput.equals("")) {
            showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Build intent that displays the App settings screen.
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
            return;
        }

        if (check) {

            if (mCountry == null) {
                mCountry = "";
            }
            if (state == null) {
                state = "";
            }
            if (city == null) {
                city = "";
            }
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), userName);
            RequestBody emailUser = RequestBody.create(MediaType.parse("text/plain"), userEmail);
            RequestBody user_Password = RequestBody.create(MediaType.parse("text/plain"), userPassword);
            RequestBody user_Dob = RequestBody.create(MediaType.parse("text/plain"), userDob);
            RequestBody Gender = RequestBody.create(MediaType.parse("text/plain"), userGender);
            RequestBody userCountry = RequestBody.create(MediaType.parse("text/plain"), mCountry);
            RequestBody userState = RequestBody.create(MediaType.parse("text/plain"), state);
            RequestBody userCity = RequestBody.create(MediaType.parse("text/plain"), city);
            RequestBody userPlatForm = RequestBody.create(MediaType.parse("text/plain"), userPlatform);
            RequestBody userFbid = RequestBody.create(MediaType.parse("text/plain"), fbId);
            RequestBody usergoogleid = RequestBody.create(MediaType.parse("text/plain"), googleId);

            RequestBody user_Name = RequestBody.create(MediaType.parse("text/plain"), userUniName);
            RequestBody userMobile = RequestBody.create(MediaType.parse("text/plain"), mobilenumber);

            if (!TextUtils.isEmpty(imageUrl)) {
                String path = FileUtils1.getPath(this, Uri.parse(imageUrl));
                imageFile = new File(path);
                FuroPrefs.putString(getApplication(), "userImage", imageUrl);
                // Create a request body with file and image media type
                RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
                // Create MultipartBody.Part using file request-body,file name and part name
                image = MultipartBody.Part.createFormData("image", imageFile.getName(), fileReqBody);
                if (Util.isInternetConnected(this)) ;
                Util.showProgressDialog(this);
                RestClient.signUpNewUser(name, emailUser, user_Password, user_Dob, Gender, userCountry, userState, userCity, userPlatForm, userFbid, usergoogleid, user_Name, userMobile, image, new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                        Util.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("200")) {
                                // FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDALERADYIN, true);
                                Intent intent = new Intent(SignUpActivity.this, LoginTutorialScreen.class);
                                String userId = String.valueOf(response.body().getUserId());
                                String image = response.body().getUser().getImage();
                                FuroPrefs.putString(getApplicationContext(), "user_name", userName);
                                FuroPrefs.putString(getApplicationContext(), "my_community_user_id", userId);
                                startActivity(intent);
                                Toast.makeText(SignUpActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();

                            } else if (response.code() == 401) {
                                Toast.makeText(SignUpActivity.this, "Enter Valid Email ", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(SignUpActivity.this, "Mobile number already exists ", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 402) {
                            Toast.makeText(SignUpActivity.this, "Email Already Exist ", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(SignUpActivity.this, LoginWithEmailActivity.class);
                            startActivity(mainIntent);
                            finish();

                        } else if (response.code() == 403) {
                            Toast.makeText(SignUpActivity.this, "Invalid Mobile Number ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {

                        Util.dismissProgressDialog();
                        Toast.makeText(SignUpActivity.this, "Api Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                //Create request body with text description and text media type
//            RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

                if (Util.isInternetConnected(this)) ;
                Util.showProgressDialog(this);
                RestClient.signUpNewUser(name, emailUser, user_Password, user_Dob, Gender, userCountry, userState, userCity, userPlatForm, userFbid, usergoogleid, user_Name, userMobile, image, new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                        Util.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("200")) {
                                //   FuroPrefs.putBoolean(getApplicationContext(), Constants.LOGGEDALERADYIN, true);
                                Intent intent = new Intent(SignUpActivity.this, LoginTutorialScreen.class);
                                String userId = String.valueOf(response.body().getUserId());
                                String image = response.body().getUser().getImage();
                                FuroPrefs.putString(getApplicationContext(), "user_name", userName);
                                FuroPrefs.putString(getApplicationContext(), "my_community_user_id", userId);
                                startActivity(intent);
                                Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 401) {
                                Toast.makeText(SignUpActivity.this, "Enter Valid Email ", Toast.LENGTH_SHORT).show();
                            }
                        } else if (response.code() == 404) {
                            Toast.makeText(SignUpActivity.this, "Mobile Already Exist ", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 402) {
                            Toast.makeText(SignUpActivity.this, "Email Already Exist ", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(SignUpActivity.this, LoginWithEmailActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else if (response.code() == 403) {
                            Toast.makeText(SignUpActivity.this, "Invalid Mobile Number ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Something Went Wrong !!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Util.dismissProgressDialog();
                        Toast.makeText(SignUpActivity.this, "Api Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    ////// profile image from camera and gallery selector code
    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        GlideApp.with(this).load(url).into(imgUserProfile);
        imgUserProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
        imageUrl = url;

    }

    private void loadProfileDefault() {
        Picasso.with(this).load(R.drawable.user_icon)
                .into(imgUserProfile);
        imgUserProfile.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    public void OnTakePermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Toast.makeText(SignUpActivity.this, "Please grant permission to continue...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void launchCameraIntent() {
        Intent intent = new Intent(SignUpActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(SignUpActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void uniqueUserName(String userNameUnique) {

        UniqueUserNameRequest uniqueUserNameRequest = new UniqueUserNameRequest();
        uniqueUserNameRequest.setUsername(userNameUnique);

        RestClient.userUniqueName(uniqueUserNameRequest, new Callback<UniqueUserNameResponse>() {
            @Override
            public void onResponse(Call<UniqueUserNameResponse> call, Response<UniqueUserNameResponse> response) {
                List<String> stringList;
                stringList = response.body().getNewUsername();
                try {
                    JSONObject responseObject = new JSONObject(String.valueOf(response));
                    JSONArray array = responseObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("trackName"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UniqueUserNameResponse> call, Throwable t) {

            }
        });
    }

    private boolean validName(EditText signName) {
        String nameValid = "^[a-zA-Z\\s]+$";

        if (signName.getText().toString().matches(nameValid)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }
        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in RegistrationActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            try {
                JSONObject jsonObject = new JSONObject(mAddressOutput);
                address = jsonObject.optString(Constants.ADDRESS);
                city = jsonObject.optString(Constants.CITY);
                mCountry = jsonObject.optString(Constants.COUNTRY);
                state = jsonObject.optString(Constants.STATE);

                if (city != null) {
                    signCity.setText(city);
                }
                if (mCountry != null) {
                    signCountry.setText(mCountry);
                }
                if (state != null) {
                    signState.setText(state);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Address", mAddressOutput);
            // Reset. Enable the Fetch Address button and stop showing the progress bar.
            mAddressRequested = false;
        }
    }
}






