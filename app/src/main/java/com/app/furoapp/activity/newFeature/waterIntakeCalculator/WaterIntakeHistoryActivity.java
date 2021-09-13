package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.fqTips.TipsResponse;
import com.app.furoapp.activity.newFeature.fqTips.WaterIntakeDatum;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.SelectCupSizeAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.UserGlassSize;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.AllTimeData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.CurrentPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.MonthlyData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.RestorePlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.WeeklyData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.AllTimeDataAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.MonthlyDataAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.adapter.WeeklyDataAdapter;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;
import com.app.furoapp.utils.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.bastanfar.semicirclearcprogressbar.SemiCircleArcProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterIntakeHistoryActivity extends AppCompatActivity {
    public ImageView ivAddCup, ivAddCustomSizeQuntity, ivCancel, ivSetting, ivBackIcon, ivChangeCupSize, ivUpArrow, ivDownArrow, ivHistoryCross;
    private String getAccessToken;
    public TextView tvNosGlassCount, tvTakingWater, tvAddCustomSize, tvRecommendedReamingWater, tvGlassSize, tvChangeCupSize,
            tvTotAmountDrunk, tvNosOfGlasses, tvRecommendedNosOfGlasses, tvDateWithDay;
    public TextView tvRecommendedNosOfWaterGlasses, tvTotWaterAmountDrunk, tvCountNosOfGlass, tvDateWithDays, tvPrizmTips;
    public LinearLayout llCongratsClosedIcon, llHistory;
    public View includePopMenuOfSelectCupSize, includeCongratsPopMenu;
    public RecyclerView rvSelectCupSize;
    public SelectCupSizeAdapter selectCupSizeAdapter;
    List<UserGlassSize> userGlassSizeList = new ArrayList<>();
    private boolean isGlassSelected;
    public String glassSize;
    public ConstraintLayout clWaterIntakeCounter;
    public SwitchCompat switchBtnWeekly, switchBtnMontly, switchBtnAllType;
    private String type;
    public String planId;
    public Date date;
    private SemiCircleArcProgressBar scProgressBar;
    public RecyclerView rvOfDailyWeeklyAllTime;
    public View includePopMenuOfWaterIntakeCounter;
    public WeeklyDataAdapter weeklyDataAdapter;
    public MonthlyDataAdapter monthlyDataAdapter;
    public AllTimeDataAdapter allTimeDataAdapter;
    List<RestorePlanResponse> restorePlanResponseList = new ArrayList<>();
    List<WeeklyData> weeklyDataList = new ArrayList<>();
    List<MonthlyData> monthlyDataList = new ArrayList<>();
    List<AllTimeData> allTimeDataList = new ArrayList<>();
    public int takingWater;
    public int totRecommendedWater;
    public int takenWaterInPercent;
    public int getWaterPercent;
    private Handler tipsHandler = new Handler();
    private List<WaterIntakeDatum> tipsList;
    private int tipsListSize = 0;
    private int tipsStart = 0;
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    long timeSwapBuff = 0L;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_history);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        initViews();
        clickEvent();
        callTipsApi();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        callWeeklyMonthlyAllTimePlanApi("Weekly");
    }

    private void initViews() {
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
        tvPrizmTips = findViewById(R.id.tvPrizmTips);
        ivHistoryCross = findViewById(R.id.ivHistoryCross);

        tvRecommendedNosOfWaterGlasses = findViewById(R.id.tvRecommendedNosOfWaterGlasses);
        tvTotWaterAmountDrunk = findViewById(R.id.tvTotWaterAmountDrunk);
        tvCountNosOfGlass = findViewById(R.id.tvCountNosOfGlass);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
    }


    private void clickEvent() {
        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("Weekly");
                } else if (isChecked == false) {
                    type = "All Time";
                    switchBtnWeekly.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("All Time");

                }
            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnAllType.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("Monthly");
                } else if (isChecked == false) {
                    type = "All Time";
                    switchBtnMontly.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("All Time");

                }
            }
        });

        switchBtnAllType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "All Time";
                    switchBtnWeekly.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("All Time");
                } else if (isChecked == false) {
                    type = "All Time";
                    switchBtnAllType.setChecked(false);
                    callWeeklyMonthlyAllTimePlanApi("All Time");
                }
            }
        });

        ivHistoryCross.setOnClickListener(v -> {
            finish();
        });
    }

    private void callWeeklyMonthlyAllTimePlanApi(String type) {
        Utils.showProgressDialogBar(getApplicationContext());
        loadingProgressBar.setVisibility(View.VISIBLE);
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Utils.dismissProgressDialogBar();
                loadingProgressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (type.equalsIgnoreCase("Weekly")) {
                            setDate(response.body().getCurrentPlan());
                            setWeeklyData("Weekly", response.body().getWeeklyData());
                        } else if (type.equalsIgnoreCase("All Time")) {
                            setDate(response.body().getCurrentPlan());
                            setAllTime("All Time", response.body().getAllTimeData());
                        } else if (type.equalsIgnoreCase("Monthly")) {
                            setDate(response.body().getCurrentPlan());
                            setMonthlyData("Monthly", response.body().getMonthlyData());
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    // Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<RestorePlanResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setWeeklyData(String type, WeeklyData weeklyData) {
        if (weeklyData != null) {
            if (type.equalsIgnoreCase("Weekly")) {
                tvTotWaterAmountDrunk.setText("" + weeklyData.getWeeklyTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + weeklyData.getWeeklyTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + weeklyData.getWeeklyRecommendedGlassOfWater().toString() + " Glasses");
            }
        }
    }

    private void setMonthlyData(String type, MonthlyData monthlyData) {
        if (monthlyData != null) {
            if (type.equalsIgnoreCase("Monthly")) {
                tvTotWaterAmountDrunk.setText("" + monthlyData.getMonthlyTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + monthlyData.getMonthlyTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + monthlyData.getMonthlyRecommendedGlassOfWater().toString() + " Glasses");
            }
        }
    }

    private void setAllTime(String type, AllTimeData allTimeData) {
        if (allTimeData != null) {
            if (type.equalsIgnoreCase("All Time")) {
                tvTotWaterAmountDrunk.setText("" + allTimeData.getAllTimeTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + allTimeData.getAllTimeTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + allTimeData.getAllTimeRecommendedGlassOfWater().toString() + " Glasses");
            }
        }
    }

    private void setDate(CurrentPlan currentPlan) {
        if (currentPlan != null) {
            DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
            try {
                date = dateFormat.parse(currentPlan.getUpdatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
            String getDate = dateFormat1.format(date);
            tvDateWithDays.setText(getDate);
        } else {

        }
    }


    private void callTipsApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            Utils.showProgressDialogBar(getApplicationContext());
            RestClient.getAllTipsData(getAccessToken, new Callback<TipsResponse>() {
                @Override
                public void onResponse(Call<TipsResponse> call, Response<TipsResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {

                        if (response.body().getData() != null
                                && response.body().getData() != null
                                && response.body().getData().getWaterIntakeData() != null
                                && response.body().getData().getWaterIntakeData().size() > 0) {
                            tipsList = response.body().getData().getWaterIntakeData();
                            tipsListSize = tipsList.size();
                            tipsHandler.postDelayed(tipsRunnable, 0);

                        } else {
                            Toast.makeText(getApplicationContext(), "No tips data found", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        if (response.code() == 500) {
                            Toast.makeText(getApplicationContext(), "Internal server error !", Toast.LENGTH_SHORT).show();
                        }
                        if (response.code() == 403) {
                            // Toast.makeText(this, response.code() + "Session expire Please login again", Toast.LENGTH_SHORT).show();
                            getAlertTokenDialog();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TipsResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private Runnable tipsRunnable = new Runnable() {
        public void run() {
            if (tipsList != null && tipsList.size() > 0) {
                if (tipsStart == (tipsListSize - 1)) {
                    tvPrizmTips.setText("" + tipsList.get(tipsStart).getParagraph());
                    tipsStart = 0;
                } else {
                    if (tipsList != null && tipsList.size() > 0) {
                        tvPrizmTips.setText("" + tipsList.get(tipsStart).getParagraph());
                        tipsStart++;
                    }
                }
            }
            tipsHandler.postDelayed(this, 5000);
        }
    };


    private void getAlertTokenDialog() {
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