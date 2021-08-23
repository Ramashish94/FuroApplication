package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataResponse;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TellUsMoreOnTrackStepsActivity extends AppCompatActivity {
    public EditText etAge;
    public TextView tvMale, tvFemale;
    public ImageView ivContinue;
    public String userHeightInCm, userWeightInKg;
    public TextView tvHeightRulerValueInCms, tvHeightRulerValueInFeet, tvHeightRulerValueInInch, tvWeightRulerValueInKgs;
    private boolean isGenderSelected, isAgeSelected;
    private String userAge;
    private String genderVal;
    private String getAccessToken;
    AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    public Data setModifiedDataVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_us_more_on_track_steps);
        initViews();

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        callModifiedSavedDataApiLandingTime();
        rulerPickerValue();
        clickEvent();

    }

    private void initViews() {
        etAge = findViewById(R.id.etAge);
        tvMale = findViewById(R.id.tvMale);
        tvFemale = findViewById(R.id.tvFemale);
        ivContinue = findViewById(R.id.ivContinue);
        tvHeightRulerValueInCms = findViewById(R.id.tvHeightRulerValueInCms);
        tvHeightRulerValueInFeet = findViewById(R.id.tvHeightRulerValueInFeet);
        tvHeightRulerValueInInch = findViewById(R.id.tvHeightRulerValueInInch);
        tvWeightRulerValueInKgs = findViewById(R.id.tvWeightRulerValueInKgs);


    }

    private void callModifiedSavedDataApiLandingTime() {

//        ModifiedSavedDataRequest modifiedSavedDataRequest = new ModifiedSavedDataRequest();
//        modifiedSavedDataRequest.setAge("");
//        modifiedSavedDataRequest.setGender("");
//        modifiedSavedDataRequest.setHeight("");
//        modifiedSavedDataRequest.setWeight("");

        RestClient.getModifiedSavedDataLandingTime(getAccessToken, new Callback<ModifiedSavedDataResponse>() {
            @Override
            public void onResponse(Call<ModifiedSavedDataResponse> call, Response<ModifiedSavedDataResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getData() != null) {
                        setModifiedDataVal = response.body().getData();
                        setModifiedData(setModifiedDataVal);
                        //setModifiedData(response.body().getData());
                    } else {
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    getAlertTokenExpire();
                    //Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Token expire pls login again !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModifiedSavedDataResponse> call, Throwable t) {
                Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setModifiedData(Data data) {
        if (data != null) {
            if (data.getAge() != null) {
                etAge.setText(data.getAge());
            }
            if (data.getGender() != null) {
                if (data.getGender().equalsIgnoreCase("Female")) {
                    tvFemale.setTextColor(Color.parseColor("#40D5E8"));
                    tvMale.setTextColor(Color.parseColor("#979797"));
                    genderVal = data.getGender();
                    isGenderSelected = true;
                    Log.d("genderVal", genderVal);
                } else if (data.getGender().equalsIgnoreCase("Male")) {
                    tvMale.setTextColor(Color.parseColor("#40D5E8"));
                    tvFemale.setTextColor(Color.parseColor("#979797"));
                    genderVal = data.getGender();
                    isGenderSelected = true;
                    Log.d("genderVal", genderVal);
                }
            } else {
            }
            if (data.getHeight() != null) {
                userHeightInCm = data.getHeight();
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            } else {
            }

            if (data.getWeight() != null) {
                userWeightInKg = data.getWeight();
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
            } else {
            }
        }
    }


    private void clickEvent() {
        tvMale.setOnClickListener(v -> {
            isGenderSelected = true;
            tvMale.setTextColor(Color.parseColor("#40D5E8"));
            tvFemale.setTextColor(Color.parseColor("#979797"));
            genderVal = tvMale.getText().toString();
            Log.d("genderVal", genderVal);
        });
        tvFemale.setOnClickListener(v -> {
            isGenderSelected = true;
            tvMale.setTextColor(Color.parseColor("#979797"));
            tvFemale.setTextColor(Color.parseColor("#40D5E8"));
            genderVal = tvMale.getText().toString();
            Log.d("genderVal", genderVal);
        });
        ivContinue.setOnClickListener(v -> {
            callModifiedSavedDataApi();
        });
    }

    private void rulerPickerValue() {
        /*Height Value*/
        final RulerValuePicker heightPicker = findViewById(R.id.heightRulerPicker);
        if (setModifiedDataVal != null && setModifiedDataVal.getHeight() != null) {
            heightPicker.selectValue(Integer.parseInt(setModifiedDataVal.getHeight()));
        } else {
            heightPicker.selectValue(160);
        }
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }
        });

        /*Weight value*/
        final RulerValuePicker weightPicker = findViewById(R.id.weightRulerPicker);
        if (setModifiedDataVal != null && setModifiedDataVal.getWeight() != null) {
            weightPicker.selectValue(Integer.parseInt(setModifiedDataVal.getWeight()));
        } else {
            weightPicker.selectValue(70);
        }
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
            }
        });
    }

    public String centimeterToFeet(String centimeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centimeter)) {
            double dCentimeter = Double.valueOf(centimeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
            String inchesss = String.valueOf(inchesPart);
            String feettt = String.valueOf(feetPart);
            tvHeightRulerValueInFeet.setText("(" + feettt + " ' ");
            tvHeightRulerValueInInch.setText(inchesss + " '' " + ")  ");

        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }

    public boolean inputValidation() {
        userAge = etAge.getText().toString().trim();
        Log.d("user Age", userAge);
        if (userAge.isEmpty()) {
            etAge.setError("Please enter your age !");
            return false;
        }/* else {
            etAge.setError(null);
        }*/
        return true;
    }

    private void callModifiedSavedDataApi() {
        if (inputValidation()) {
            if (isGenderSelected) {
                ModifiedSavedDataRequest modifiedSavedDataRequest = new ModifiedSavedDataRequest();
                modifiedSavedDataRequest.setAge(userAge);
                modifiedSavedDataRequest.setGender(genderVal);
                modifiedSavedDataRequest.setHeight(userHeightInCm);
                modifiedSavedDataRequest.setWeight(userWeightInKg);
                if (Util.isInternetConnected(getApplicationContext())) {
                    Util.showProgressDialog(getApplicationContext());
                    RestClient.getModifiedSavedData(getAccessToken, modifiedSavedDataRequest, new Callback<ModifiedSavedDataResponse>() {
                        @Override
                        public void onResponse(Call<ModifiedSavedDataResponse> call, Response<ModifiedSavedDataResponse> response) {
                            Util.dismissProgressDialog();
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AddNewSlotPreferActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else if (response.code() == 500) {
                                Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 403) {
                                getAlertTokenExpire();
                                //Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Token expire pls login again !", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ModifiedSavedDataResponse> call, Throwable t) {
                            Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(this, "Please provide Gender !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please provided Age !", Toast.LENGTH_SHORT).show();
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


}