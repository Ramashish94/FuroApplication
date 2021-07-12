package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.LoginTutorialScreen;
import com.app.furoapp.activity.newFeature.StepsTracker.FqStepsCounterActivity;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.DataItem;
import com.app.furoapp.activity.newFeature.StepsTracker.fqsteps.TipsResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.SelectCupSizeAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.ChangeGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.UserChangeGlassSizeResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.AddUserCup;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.CupCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.notificationSound.WaterIntakeSoundNotificationActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.AllTimeData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.CurrentPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.MonthlyData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.RestorePlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.restorePlanModel.WeeklyData;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.selectCustomSizeGlass.SelectCustomGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.GlassFetchResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.UserGlassSize;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.selectCustomSizeGlass.SelectCustomSizeGlassResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.SelectedPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.WaterIntakeUpdatePlanRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.WaterIntakeUpdatePlanResponse;
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

public class WaterIntakeCounterActivity extends AppCompatActivity implements SelectCupSizeAdapter.GlassClickCallBack {
    public ImageView ivAddCup, ivAddCustomSizeQuntity, ivCancel, ivSetting, ivBackIcon, ivChangeCupSize, ivUpArrow, ivDownArrow;
    private String getAccessToken;
    public TextView tvNosGlassCount, tvTakingWater, tvAddCustomSize, tvRecommendedReamingWater, tvGlassSize, tvChangeCupSize, tvChangeCupSizeAndCustomSize,
            tvTotAmountDrunk, tvNosOfGlasses, tvRecommendedNosOfGlasses, tvDateWithDay;
    public TextView tvRecommendedNosOfWaterGlasses, tvTotWaterAmountDrunk, tvCountNosOfGlass, tvDateWithDays, tvPrizmTips, tvRecommendedWaterIntake;
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
    private List<DataItem> tipsList;
    private int tipsListSize = 0;
    private int tipsStart = 0;
    long timeInMilliseconds = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    long timeSwapBuff = 0L;
    public GoogleSignInClient mGoogleSignInClient;
    public AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_counter);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        planId = getIntent().getStringExtra("planId");

        initViews();
        callDailyWaterIntakeUpdatePlanApi();
        clickEvent();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

    }

    private void initViews() {
        ivAddCup = findViewById(R.id.ivAddCup);
        ivAddCustomSizeQuntity = findViewById(R.id.ivAddCustomSizeQuntity);
        tvNosGlassCount = findViewById(R.id.tvNosGlassCount);
        tvTakingWater = findViewById(R.id.tvTakingWater);
        includePopMenuOfSelectCupSize = findViewById(R.id.includePopMenuOfSelectCupSize);
        ivCancel = findViewById(R.id.ivCancel);
        tvAddCustomSize = findViewById(R.id.tvAddCustomSize);
        rvSelectCupSize = findViewById(R.id.rvSelectCupSize);
        tvRecommendedReamingWater = findViewById(R.id.tvRecommendedReamingWater);
        tvGlassSize = findViewById(R.id.tvGlassSize);
        ivSetting = findViewById(R.id.ivSetting);
        clWaterIntakeCounter = findViewById(R.id.clWaterIntakeCounter);
        ivBackIcon = findViewById(R.id.ivBackIcon);
//        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
//        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
//        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivChangeCupSize = findViewById(R.id.ivChangeCupSize);
        tvChangeCupSize = findViewById(R.id.tvChangeCupSize);
        tvTotAmountDrunk = findViewById(R.id.tvTotAmountDrunk);
        tvRecommendedNosOfGlasses = findViewById(R.id.tvRecommendedNosOfGlasses);
        tvDateWithDay = findViewById(R.id.tvDateWithDay);
        tvNosOfGlasses = findViewById(R.id.tvNosOfGlasses);
        scProgressBar = findViewById(R.id.scProgressBar);
        ivUpArrow = findViewById(R.id.ivUpArrow);
        //ivDownArrow = findViewById(R.id.ivDownArrow);
        rvOfDailyWeeklyAllTime = findViewById(R.id.rvOfDailyWeeklyAllTime);
        // includePopMenuOfWaterIntakeCounter = findViewById(R.id.includePopMenuOfWaterIntakeCounter);
        // tvPrizmTips = findViewById(R.id.tvPrizmTips);
        llHistory = findViewById(R.id.llHistory);
        tvRecommendedNosOfWaterGlasses = findViewById(R.id.tvRecommendedNosOfWaterGlasses);
        tvTotWaterAmountDrunk = findViewById(R.id.tvTotWaterAmountDrunk);
        tvCountNosOfGlass = findViewById(R.id.tvCountNosOfGlass);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
        includeCongratsPopMenu = findViewById(R.id.includeCongratsPopMenu);
        tvRecommendedWaterIntake = findViewById(R.id.tvRecommendedWaterIntake);
        llCongratsClosedIcon = findViewById(R.id.llCongratsClosedIcon);
        tvChangeCupSizeAndCustomSize = findViewById(R.id.tvChangeCupSizeAndCustomSize);
    }

    private void callDailyWaterIntakeUpdatePlanApi() {

        WaterIntakeUpdatePlanRequest waterIntakeUpdatePlanRequest = new WaterIntakeUpdatePlanRequest();
        if (planId != null) {
            waterIntakeUpdatePlanRequest.setPlan_id(planId);
            Util.showProgressDialog(getApplicationContext());
            RestClient.getWaterIntakeUpdatePlan(getAccessToken, waterIntakeUpdatePlanRequest, new Callback<WaterIntakeUpdatePlanResponse>() {
                @Override
                public void onResponse(Call<WaterIntakeUpdatePlanResponse> call, Response<WaterIntakeUpdatePlanResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getStatus() != null) {
                            // set today progress data
                            setGlassSize(response.body());

                            setTodayProgressData(response.body().getSelectedPlan());

                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WaterIntakeUpdatePlanResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something  went wrong !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setGlassSize(WaterIntakeUpdatePlanResponse body) {
        tvGlassSize.setText("( " + body.getGlassSize() + "ml )");
    }

    private void setTodayProgressData(SelectedPlan selectedPlan) {
        if (selectedPlan != null) {
            if (selectedPlan.getTakenGlassOfWater() != null) {
                tvNosGlassCount.setText("" + Integer.parseInt(selectedPlan.getTakenGlassOfWater().toString()));
            }
            if (selectedPlan.getTakenWaterInMl() != null) {
                takingWater = Integer.parseInt(selectedPlan.getTakenWaterInMl().toString());   //for percent
                tvTakingWater.setText("" + takingWater);
            }
            if (selectedPlan.getWaterTakeInMl() != null) {
                totRecommendedWater = Integer.parseInt(selectedPlan.getWaterTakeInMl().toString());    //for percent
                tvRecommendedReamingWater.setText("of " + totRecommendedWater + " ml");
            }
            if (selectedPlan.getTakenWaterInMl() != null) {
                tvTotAmountDrunk.setText("" + selectedPlan.getTakenWaterInMl().toString() + " ml");
            }
            if (selectedPlan.getTakenGlassOfWater() != null) {
                tvNosOfGlasses.setText("" + selectedPlan.getTakenGlassOfWater().toString());
            }
            if (selectedPlan.getRecommendedGlassOfWater() != null) {
                tvRecommendedNosOfGlasses.setText("/" + selectedPlan.getRecommendedGlassOfWater().toString());
            }

            if (selectedPlan.getCreatedAt() != null) {

                DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
                try {
                    date = dateFormat.parse(selectedPlan.getCreatedAt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
                String getDate = dateFormat1.format(date);
                tvDateWithDay.setText(getDate);
            }

            /*Update progress percent*/
            takenWaterInPercent = (takingWater * 100 / totRecommendedWater);
            scProgressBar.setPercent(takenWaterInPercent);     // set percent on progress bar
            if (takingWater == totRecommendedWater || takenWaterInPercent >= 99) {
                includeCongratsPopMenu.setVisibility(View.VISIBLE);
                tvRecommendedWaterIntake.setText("" + totRecommendedWater + "  ml");
                clWaterIntakeCounter.setClickable(false);
            }/* else {
            Toast.makeText(this, "Selected plan will be Null", Toast.LENGTH_SHORT).show();
        }*/

        }


    }


    private void clickEvent() {
        ivAddCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCupCreateApi();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfSelectCupSize.setVisibility(View.GONE);
            }
        });

        tvAddCustomSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGlassSelected) {
                    includePopMenuOfSelectCupSize.setVisibility(View.GONE);
                    callSelectCustomGlassSizeApi();   //calling api .....cup/select-custom-glass-size
                } else {
                    Toast.makeText(getApplicationContext(), "Please select glass size ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivAddCustomSizeQuntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfSelectCupSize.setVisibility(View.VISIBLE);
                clWaterIntakeCounter.setClickable(false);
                tvChangeCupSizeAndCustomSize.setText("What is the general glass size/quantity that you consume every time? ");
                operateRacyData();
                callFetchGlassApi();
            }
        });

        ivChangeCupSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfSelectCupSize.setVisibility(View.VISIBLE);
                clWaterIntakeCounter.setClickable(false);
                tvChangeCupSize.setVisibility(View.VISIBLE);
                tvAddCustomSize.setVisibility(View.GONE);
                tvChangeCupSizeAndCustomSize.setText("Customize the size of your glass. ");
                operateRacyData();
                callFetchGlassApi();
            }
        });

        tvChangeCupSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGlassSelected) {
                    includePopMenuOfSelectCupSize.setVisibility(View.GONE);
                    CallChangeGlassSizeApi();
                    //calling api .....change cup size
                } else {
                    Toast.makeText(getApplicationContext(), "Please select glass size ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), WaterIntakeSoundNotificationActivity.class);/*SettingWaterIntakeActivity*/
                Intent intent = new Intent(getApplicationContext(), SettingWaterIntakeActivity.class);/**/
                startActivity(intent);
                finish();
            }
        });

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
                startActivity(intent);
                finish();
            }
        });


        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterIntakeHistoryActivity.class);
                startActivity(intent);

              /*  includePopMenuOfWaterIntakeCounter.setVisibility(View.VISIBLE);
                clWaterIntakeCounter.setClickable(false);

                switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            type = "Weekly";
                            switchBtnAllType.setChecked(false);
                            switchBtnMontly.setChecked(false);
                            callWeeklyMonthlyAllTimePlanApi("Weekly");
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
                        }
                    }
                });
                callRestorePlanApi();
                callTipsApi();*/
            }
        });

//        ivDownArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                includePopMenuOfWaterIntakeCounter.setVisibility(View.GONE);
//            }
//        });

        llCongratsClosedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void CallChangeGlassSizeApi() {
        ChangeGlassSizeRequest changeGlassSizeRequest = new ChangeGlassSizeRequest();
        changeGlassSizeRequest.setGlass_size_in_ml(glassSize);
        RestClient.getChangeCupSize(getAccessToken, changeGlassSizeRequest, new Callback<UserChangeGlassSizeResponse>() {
            @Override
            public void onResponse(Call<UserChangeGlassSizeResponse> call, Response<UserChangeGlassSizeResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        //Toast.makeText(WaterIntakeCounterActivity.this, "Cup size change successfully", Toast.LENGTH_SHORT).show();
                        callDailyWaterIntakeUpdatePlanApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    //Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<UserChangeGlassSizeResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Some thing went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void callCupCreateApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getCupCreate(getAccessToken, new Callback<CupCreateResponse>() {
            @Override
            public void onResponse(Call<CupCreateResponse> call, Response<CupCreateResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        // Toast.makeText(WaterIntakeCounterActivity.this, "Cup created successfully", Toast.LENGTH_SHORT).show();
                        callDailyWaterIntakeUpdatePlanApi();
                        // setData(response.body().getAddUserCup());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    //Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<CupCreateResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setData(AddUserCup addUserCup) {
        tvTakingWater.setText("" + addUserCup.getTakenWaterInMl().toString());
        tvRecommendedReamingWater.setText("of " + addUserCup.getRecommendedGlassOfWater() + " ml");
        tvNosGlassCount.setText(Integer.toString(addUserCup.getTakenGlassOfWater()));
        tvGlassSize.setText("( " + addUserCup.getGlassSizeInMl() + "ml )");
    }

    private void callFetchGlassApi() {
        Util.showProgressDialog(getApplicationContext());
        Util.isInternetConnected(getApplicationContext());
        RestClient.getUserGlassFetch(getAccessToken, new Callback<GlassFetchResponse>() {
            @Override
            public void onResponse(Call<GlassFetchResponse> call, Response<GlassFetchResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        if (response.body().getUserGlassSizes() != null) {
                            notifyGlassAdapter(response.body().getUserGlassSizes());
                        }
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    //  Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<GlassFetchResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void operateRacyData() {
        selectCupSizeAdapter = new SelectCupSizeAdapter(getApplicationContext(), userGlassSizeList, this);
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvSelectCupSize.setLayoutManager(layoutManager);
        rvSelectCupSize.setItemAnimator(new DefaultItemAnimator());
        rvSelectCupSize.setAdapter(selectCupSizeAdapter);
    }

    private void notifyGlassAdapter(List<UserGlassSize> userGlassSizes) {
        userGlassSizeList.clear();
        userGlassSizeList.addAll(userGlassSizes);
        if (userGlassSizeList != null && userGlassSizeList.size() > 0) {
            selectCupSizeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void glassSelectItem(int glassSizeInMl) {
        glassSize = String.valueOf(glassSizeInMl);
        isGlassSelected = true;
        Log.d(" glassSize", glassSize);
    }

    private void callSelectCustomGlassSizeApi() {
        SelectCustomGlassSizeRequest selectCustomGlassSizeRequest = new SelectCustomGlassSizeRequest();
        selectCustomGlassSizeRequest.setGlass_size_in_ml(glassSize);
        Util.showProgressDialog(getApplicationContext());
        RestClient.getCustomSizeGlass(getAccessToken, selectCustomGlassSizeRequest, new Callback<SelectCustomSizeGlassResponse>() {
            @Override
            public void onResponse(Call<SelectCustomSizeGlassResponse> call, Response<SelectCustomSizeGlassResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        // Toast.makeText(WaterIntakeCounterActivity.this, "Custom cup size created successfully", Toast.LENGTH_SHORT).show();
                        callCupCreateApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    //Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    getAlertTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<SelectCustomSizeGlassResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callRestorePlanApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        setDate(response.body().getCurrentPlan());
                        //setWeeklyData(response.body().getWeeklyData());
                        setAllTimeData(response.body().getAllTimeData());
                        //setWeeklyData(response.body().getWeeklyData());
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
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDate(CurrentPlan currentPlan) {
        DateFormat dateFormat = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            date = dateFormat.parse(currentPlan.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM, EEE");
        String getDate = dateFormat1.format(date);
        tvDateWithDays.setText(getDate);
    }

    private void setAllTimeData(AllTimeData allTimeData) {

        tvTotWaterAmountDrunk.setText("" + allTimeData.getAllTimeTakenWaterInMl().toString() + " ml");
        tvCountNosOfGlass.setText("" + allTimeData.getAllTimeTakenGlassOfWater().toString());
        tvRecommendedNosOfWaterGlasses.setText("/" + allTimeData.getAllTimeRecommendedGlassOfWater().toString() + " Glasses");
    }

    private void callWeeklyMonthlyAllTimePlanApi(String type) {
        Utils.showProgressDialogBar(getApplicationContext());
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Utils.dismissProgressDialogBar();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (type.equals("Weekly")) {
                            setDate(response.body().getCurrentPlan());
                            setWeeklyData("Weekly", response.body().getWeeklyData());
                        } else if (type.equals("All Time")) {
                            setDate(response.body().getCurrentPlan());
                            setAllTime("All Time", response.body().getAllTimeData());
                        } else if (type.equals("Monthly")) {
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
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMonthlyData(String monthly, MonthlyData monthlyData) {
        if (monthlyData != null) {
            if (type.equalsIgnoreCase("Monthly")) {
                tvTotWaterAmountDrunk.setText("" + monthlyData.getMonthlyTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + monthlyData.getMonthlyTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + monthlyData.getMonthlyRecommendedGlassOfWater().toString() + " Glasses");
            }
        }
    }

    private void setWeeklyData(String weekly, WeeklyData weeklyData) {
        if (weeklyData != null) {
            if (type.equalsIgnoreCase("Weekly")) {
                tvTotWaterAmountDrunk.setText("" + weeklyData.getWeeklyTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + weeklyData.getWeeklyTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + weeklyData.getWeeklyRecommendedGlassOfWater().toString() + " Glasses");
            }
        }
    }

    private void setAllTime(String all_time, AllTimeData allTimeData) {
        if (allTimeData != null) {
            if (type.equalsIgnoreCase("All Time")) {
                tvTotWaterAmountDrunk.setText("" + allTimeData.getAllTimeTakenWaterInMl().toString() + " ml");
                tvCountNosOfGlass.setText("" + allTimeData.getAllTimeTakenGlassOfWater().toString());
                tvRecommendedNosOfWaterGlasses.setText("/" + allTimeData.getAllTimeRecommendedGlassOfWater().toString() + " Glasses");
            }
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
                        //   Log.d(TAG, "onResponse() called with: , response = [" + response.body() + "]");
                        if (response.body().getData() != null && response.body().getData().getData() != null && response.body().getData().getData().size() > 0) {
                            tipsList = response.body().getData().getData();
                            tipsListSize = tipsList.size();
                            tipsHandler.postDelayed(tipsRunnable, 0);
                        } else {
                            Toast.makeText(getApplicationContext(), "No tips data found ", Toast.LENGTH_SHORT).show();

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
                    tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
                    tipsStart = 0;
                } else {
                    if (tipsList != null && tipsList.size() > 0) {
                        tvPrizmTips.setText(tipsList.get(tipsStart).getParagraph());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
        startActivity(intent);
        finish();
    }

}