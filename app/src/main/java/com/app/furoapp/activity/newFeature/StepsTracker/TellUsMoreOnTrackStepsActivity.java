package com.app.furoapp.activity.newFeature.StepsTracker;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.Data;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataRequest;
import com.app.furoapp.activity.newFeature.StepsTracker.modifiedSavedData.ModifiedSavedDataResponse;
import com.app.furoapp.activity.newFeature.caloriesCalculator.HearYoGoActivity;
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

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TellUsMoreOnTrackStepsActivity extends AppCompatActivity {
    public EditText etAge;
    public TextView tvMale, tvFemale;
    public ImageView ivContinue;
    public String userHeightInCm, userWeightInKg;
    public TextView tvHeightRulerValueInCms, tvHeightRulerValueInFeet, tvHeightRulerValueInInch, tvWeightRulerValueInKgs;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    public Data setModifiedDataVal;
    public TextView tvTellUsMoreSubHeading, tvCalculateMyCalories;  /*add on 13-11-2021,by ramashish or calorie calculator*/
    AlertDialog.Builder builder;
    AppCompatTextView appCmptTvForHeight, appCmptTvForWight;
    private boolean isGenderSelected, isAgeSelected;
    private String userAge;
    private String genderVal;
    private String getAccessToken;
    private AlertDialog alertDialog;
    private AlertDialog dialog;
    private String CalorieIntakeCalculator;
    public Intent intent;
    private LinearLayout llTellUsMore;
    private int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_us_more_on_track_steps);
        initViews();

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        CalorieIntakeCalculator = getIntent().getStringExtra("CalorieIntakeCalculator");     /*for calorie page*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER);
        Log.d("TAG", "Gender" + genderVal);
        getUserAge(FuroPrefs.getString(getApplicationContext(), Constants.D_O_B));


        if (CalorieIntakeCalculator != null) {
            setTextForCalorieCalculator(CalorieIntakeCalculator);
        }

        callModifiedSavedDataApiLandingTime();   /*add on comment 2-12-2021*/
        // setTextForCalorieCalculator();   /*add on 13-11-2021,by ramashish or calorie calculator*/

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
        /*add on 14-11-21,By ramashish*/
        tvTellUsMoreSubHeading = findViewById(R.id.tvTellUsMoreSubHeading);
        appCmptTvForHeight = findViewById(R.id.appCmptTvForHeight);
        appCmptTvForWight = findViewById(R.id.appCmptTvForWight);
        tvCalculateMyCalories = findViewById(R.id.tvCalculateMyCalories);
        llTellUsMore = findViewById(R.id.llTellUsMore);

    }

    private void setTextForCalorieCalculator(String calorieIntakeCalculator) {
        if (calorieIntakeCalculator.equalsIgnoreCase(getIntent().getStringExtra("CalorieIntakeCalculator"))) {
            llTellUsMore.setBackgroundResource(R.drawable.caloriebg3);
            tvTellUsMoreSubHeading.setText("Fill in the necessary data required to analyse your calorie intake. ");
            tvTellUsMoreSubHeading.setTextColor(Color.parseColor("#FFFFFF"));
            tvTellUsMoreSubHeading.setTextSize(16);
            appCmptTvForHeight.setText(" Select your Weight");
            appCmptTvForWight.setText(" Select your Weight");
            ivContinue.setVisibility(View.GONE);
            tvCalculateMyCalories.setVisibility(View.VISIBLE);
        } else {

        }
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
            } else {
                etAge.setText("" + age);
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
                /*add on 22-12-2021,By Ramashish*/
                if (FuroPrefs.getString(getApplicationContext(), Constants.GENDER) != null) {
                    if (FuroPrefs.getString(getApplicationContext(), Constants.GENDER).equalsIgnoreCase("Female")) {
                        tvFemale.setTextColor(Color.parseColor("#40D5E8"));
                        tvMale.setTextColor(Color.parseColor("#979797"));
                        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER);
                        isGenderSelected = true;
                        Log.d("genderVal", genderVal);
                    } else if (FuroPrefs.getString(getApplicationContext(), Constants.GENDER).equalsIgnoreCase("Male")) {
                        tvMale.setTextColor(Color.parseColor("#40D5E8"));
                        tvFemale.setTextColor(Color.parseColor("#979797"));
                        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER);
                        isGenderSelected = true;
                        Log.d("genderVal", genderVal);
                    }
                }
            }
            if (data.getHeight() != null && data.getWeight() != null) {
                rulerPickerValue(data.getHeight(), data.getWeight());
            } else {
                rulerPickerValue(" ", " ");
            }
        }
    }


    private int getUserAge(String dobString) {
        Log.d("TAG", "Age " + dobString);


        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
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
            genderVal = tvFemale.getText().toString();
            Log.d("genderVal", genderVal);
        });
        ivContinue.setOnClickListener(v -> {
            callModifiedSavedDataApi();
        });

        tvCalculateMyCalories.setOnClickListener(v -> {
//            callForCalorieIntakeCalculator();
            callModifiedSavedDataApi();
        });
    }

    private void rulerPickerValue(String height, String weight) {
        /*Height Value*/
        final RulerValuePicker heightPicker = findViewById(R.id.heightRulerPicker);

        if (height != null) {
            if (height.equalsIgnoreCase("null") || height.equalsIgnoreCase(" ")) {
                heightPicker.selectValue(160);
            } else {
                heightPicker.selectValue(Integer.parseInt(height));
            }
        } else {
            heightPicker.selectValue(160);
        }
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " CMs ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " CMs ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }
        });

        /*Weight value*/
        final RulerValuePicker weightPicker = findViewById(R.id.weightRulerPicker);
        if (weight != null) {
            if (weight.equalsIgnoreCase("null") || weight.equalsIgnoreCase(" ")) {
                weightPicker.selectValue(60);
            } else {
                weightPicker.selectValue(Integer.parseInt(weight));
            }
        } else {
            weightPicker.selectValue(60);
        }
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " KGs ");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " KGs ");
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
                                    if (CalorieIntakeCalculator != null) {
                                        intent = new Intent(getApplicationContext(), HearYoGoActivity.class);
//                                        intent.putExtra("userAge", userAge);
//                                        intent.putExtra("genderVal", genderVal);
//                                        intent.putExtra("userHeightInCm", userHeightInCm);
//                                        intent.putExtra("userWeightInKg", userWeightInKg);
                                        //                                        Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(TellUsMoreOnTrackStepsActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(getApplicationContext(), AddNewSlotPreferActivity.class);
                                    }
                                    FuroPrefs.putString(getApplicationContext(), Constants.AGE, userAge);
                                    FuroPrefs.putString(getApplicationContext(), Constants.GENDER, genderVal);
                                    FuroPrefs.putString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM, userHeightInCm);
                                    FuroPrefs.putString(getApplicationContext(), Constants.USER_WEIGHT_IN_KG, userWeightInKg);
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        callModifiedSavedDataApiLandingTime();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        callModifiedSavedDataApiLandingTime();
//
//    }
}