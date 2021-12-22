package com.app.furoapp.activity.newFeature.healthCare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.TrackYourStepsStartActivity;
import com.app.furoapp.activity.newFeature.bmiCalculator.FindYourBmiActivity;
import com.app.furoapp.activity.newFeature.caloriesCalculator.CalculateCaloriesStartActivity;
import com.app.furoapp.activity.newFeature.healthCare.healthCentermodel.Bmi;
import com.app.furoapp.activity.newFeature.healthCare.healthCentermodel.HealthCenterResponse;
import com.app.furoapp.activity.newFeature.healthCare.healthCentermodel.StepCounter;
import com.app.furoapp.activity.newFeature.healthCare.healthCentermodel.WaterIntake;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.CreateAHydrationPlaneActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeExistsUser.WaterIntakeExistsUserResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeStartActivity;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skydoves.progressview.ProgressView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthCenterDashboardActivity extends AppCompatActivity {
    public LinearLayout llDailyStepsTracker, llWaterIntakeMonitor, llBMICalculator, llCaloriesCalculator;
    private String getAccessToken;
    private TextView tvTotalSteps, tvSteps, tvNoOfGlass, tvTotNoOfGlass, tvBmiType, tvBmiIndex, tvCaloriesRequiredPerDayGettingValue;
    private ImageView ivBack;
    private int getBmi;
    private int takenWaterPercent;
    private int stepsPrecent;
    private ProgressView stepsProgressView, waterIntakeProgressView;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_center);
        initVies();
        ClickEvent();

        //getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);


        callDashBoardApi();

        setCalorieIntakeCalculatorData();

    }

    private void initVies() {
        llDailyStepsTracker = findViewById(R.id.llDailyStepsTracker);
        llWaterIntakeMonitor = findViewById(R.id.llWaterIntakeMonitor);
        llBMICalculator = findViewById(R.id.llBMIClculator);
        llCaloriesCalculator = findViewById(R.id.llCaloriesCalculator);
        ivBack = findViewById(R.id.ivBack);
        tvTotalSteps = findViewById(R.id.tvTotalSteps);
        tvSteps = findViewById(R.id.tvSteps);
        tvNoOfGlass = findViewById(R.id.tvNoOfGlass);
        tvTotNoOfGlass = findViewById(R.id.tvTotNoOfGlass);
        tvBmiIndex = findViewById(R.id.tvBmiIndex);
        tvBmiType = findViewById(R.id.tvBmiType);
        stepsProgressView = findViewById(R.id.stepsProgressView);
        waterIntakeProgressView = findViewById(R.id.waterIntakeProgressView);
        tvCaloriesRequiredPerDayGettingValue = findViewById(R.id.tvCaloriesRequiredPerDayGettingValue);
    }


    private void ClickEvent() {

        llDailyStepsTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackYourStepsStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        llWaterIntakeMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callWaterIntakeExistsUserApi();

                /*Intent intent = new Intent(getApplicationContext(), WaterIntakeStartActivity.class);
                startActivity(intent);
                finish();*/

            }
        });

        llBMICalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(HealthCenterDashboardActivity.this, "Working on this ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        llCaloriesCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculateCaloriesStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void callDashBoardApi() {
        Utils.showProgressDialogBar(getApplicationContext());
        RestClient.getHealthCenter(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new Callback<HealthCenterResponse>() {
            @Override
            public void onResponse(Call<HealthCenterResponse> call, Response<HealthCenterResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        // set step counter data
                        setStepCounterData(response.body().getStepCounter());
                        //set water intake data
                        setCounterIntake(response.body().getWaterIntake());
                        //set bmi data
                        setBmiData(response.body().getBmi());

                    }
                } else if (response.code() == 500) {
                    Toast.makeText(HealthCenterDashboardActivity.this, "Internal server error !", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(HealthCenterDashboardActivity.this, response.code() + " Session expire PLease login again ! ", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<HealthCenterResponse> call, Throwable t) {
                Toast.makeText(HealthCenterDashboardActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setBmiData(Bmi bmi) {
        if (bmi != null) {
            getBmi = (int) Double.parseDouble("" + bmi.getBmi());
            // tvBmiIndex.setText("" + getBmi);
            if (getBmi < 18.5) {
                tvBmiType.setText("Underweight ");
            } else if (getBmi >= 18.5 && getBmi <= 24.9) {
                tvBmiType.setText("Normal");
            } else if (getBmi >= 25 && getBmi <= 29.9) {
                tvBmiType.setText("Overweight ");
            }/* else if (getBmi >= 30 && getBmi <= 39) {
                tvBmiType.setText("Obese");
            }*/ else if (getBmi >= 30) {
                tvBmiType.setText("Obese");
            } else {

            }

        } else {
            Toast.makeText(this, "No bmi record found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setStepCounterData(StepCounter stepCounter) {
        if (stepCounter != null) {            //&& stepCounter.getCountSteps() != null && stepCounter.getTotalSteps() != null
            tvSteps.setText("" + stepCounter.getCountSteps());
            tvTotalSteps.setText("" + stepCounter.getTotalSteps());
            stepsPrecent = (stepCounter.getCountSteps() * 100 / stepCounter.getTotalSteps());
            stepsProgressView.setProgress(stepsPrecent);     // set percent on progress bar
        } else {
            // Toast.makeText(this, "No steps counter record found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCounterIntake(WaterIntake waterIntake) {
        if (waterIntake != null) {      // && waterIntake.getTakenGlassOfWater() != null && waterIntake.getRecommendedGlassOfWater() != null
            if (waterIntake.getTakenGlassOfWater() != null && waterIntake.getRecommendedGlassOfWater() != null) {
                tvNoOfGlass.setText("" + waterIntake.getTakenGlassOfWater());
                tvTotNoOfGlass.setText("" + waterIntake.getRecommendedGlassOfWater());
                takenWaterPercent = (waterIntake.getTakenGlassOfWater() * 100 / waterIntake.getRecommendedGlassOfWater());
                waterIntakeProgressView.setProgress(takenWaterPercent);     // set percent on progress bar
            } else {

            }

        } else {
            Toast.makeText(this, "No water intake record found !", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCalorieIntakeCalculatorData() {
        if (FuroPrefs.getString(getApplicationContext(), Constants.CALORIES_VALUE) != null) {
            // String calValue = String.valueOf(Double.parseDouble(FuroPrefs.getString(getApplicationContext(), Constants.CALORIES_VALUE)));
            tvCaloriesRequiredPerDayGettingValue.setText("" + FuroPrefs.getString(getApplicationContext(), Constants.CALORIES_VALUE));
            //tvCaloriesRequiredPerDayGettingValue.setText("0000.00");

        } else {
            tvCaloriesRequiredPerDayGettingValue.setText("0000.00");
        }

    }


    private void callWaterIntakeExistsUserApi() {

        RestClient.getWaterIntakeExistsUser(FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN), new Callback<WaterIntakeExistsUserResponse>() {
            @Override
            public void onResponse(Call<WaterIntakeExistsUserResponse> call, Response<WaterIntakeExistsUserResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getIsWaterIntakeDataRequired() == 1) {
                            Intent intent = new Intent(getApplicationContext(), CreateAHydrationPlaneActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if (response.body().getIsWaterIntakeDataRequired() == 0) {
                                Intent intent = new Intent(getApplicationContext(), WaterIntakeStartActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(HealthCenterDashboardActivity.this, response.code() + "Internal server error!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    getAlertTokenDialog();
                }

            }

            @Override
            public void onFailure(Call<WaterIntakeExistsUserResponse> call, Throwable t) {
                Toast.makeText(HealthCenterDashboardActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getAlertTokenDialog() {
        if (FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN) != null) {
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