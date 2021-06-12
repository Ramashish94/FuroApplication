package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.AddNewSlotPreferActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.SelectCupSizeAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.ChangeGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.changeGlassSize.UserChangeGlassSizeResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.AddUserCup;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.CupCreateResponse;
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
    public TextView tvNosGlassCount, tvTakingWater, tvAddCustomSize, tvRecommendedReamingWater, tvGlassSize, tvChangeCupSize,
            tvTotAmountDrunk, tvNosOfGlasses, tvRecommendedNosOfGlasses, tvDateWithDay;
    public TextView tvRecommendedNosOfWaterGlasses, tvTotWaterAmountDrunk, tvCountNosOfGlass, tvDateWithDays;
    public LinearLayout llCongratsClosedIcon;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_counter);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        planId = getIntent().getStringExtra("planId");

        initViews();
        callDailyWaterIntakeUpdatePlanApi();
        clickEvent();
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
        switchBtnWeekly = findViewById(R.id.switchBtnWeekly);
        switchBtnMontly = findViewById(R.id.switchBtnMonthly);
        switchBtnAllType = findViewById(R.id.switchBtnAlType);
        ivChangeCupSize = findViewById(R.id.ivChangeCupSize);
        tvChangeCupSize = findViewById(R.id.tvChangeCupSize);
        tvTotAmountDrunk = findViewById(R.id.tvTotAmountDrunk);
        tvRecommendedNosOfGlasses = findViewById(R.id.tvRecommendedNosOfGlasses);
        tvDateWithDay = findViewById(R.id.tvDateWithDay);
        tvNosOfGlasses = findViewById(R.id.tvNosOfGlasses);
        scProgressBar = findViewById(R.id.scProgressBar);
        ivUpArrow = findViewById(R.id.ivUpArrow);
        ivDownArrow = findViewById(R.id.ivDownArrow);
        rvOfDailyWeeklyAllTime = findViewById(R.id.rvOfDailyWeeklyAllTime);
        includePopMenuOfWaterIntakeCounter = findViewById(R.id.includePopMenuOfWaterIntakeCounter);

        tvRecommendedNosOfWaterGlasses = findViewById(R.id.tvRecommendedNosOfWaterGlasses);
        tvTotWaterAmountDrunk = findViewById(R.id.tvTotWaterAmountDrunk);
        tvCountNosOfGlass = findViewById(R.id.tvCountNosOfGlass);
        tvDateWithDays = findViewById(R.id.tvDateWithDays);
        includeCongratsPopMenu = findViewById(R.id.includeCongratsPopMenu);
        llCongratsClosedIcon = findViewById(R.id.llCongratsClosedIcon);
    }

    private void callDailyWaterIntakeUpdatePlanApi() {

        WaterIntakeUpdatePlanRequest waterIntakeUpdatePlanRequest = new WaterIntakeUpdatePlanRequest();
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
        if (takingWater == totRecommendedWater || takenWaterInPercent >= 98) {
            includeCongratsPopMenu.setVisibility(View.VISIBLE);
            clWaterIntakeCounter.setClickable(false);
            finish();
        }/* else {
            Toast.makeText(this, "Selected plan will be Null", Toast.LENGTH_SHORT).show();
        }*/
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
                Intent intent = new Intent(getApplicationContext(), SettingWaterIntakeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchBtnWeekly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Weekly";
                    switchBtnAllType.setChecked(false);
                    switchBtnMontly.setChecked(false);
                    callRestorePlanApi();

                    // callRestorePlanApi(type);
                    // getWeeklyData();
                } /*else {
                    callRestorePlanApi("");*/

                //  getWeeklyData();
                // }
            }
        });

        switchBtnMontly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = "Monthly";
                    switchBtnWeekly.setChecked(false);
                    switchBtnAllType.setChecked(false);
                    callRestorePlanApi();

                    //callRestorePlanApi(type);

                    // getMonthlyData();
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
                    callRestorePlanApi();

                    //callRestorePlanApi(type);
                    // getAlltimeData();
                }
            }
        });

        ivUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfWaterIntakeCounter.setVisibility(View.VISIBLE);
                clWaterIntakeCounter.setClickable(false);
                callRestorePlanApi();
            }
        });

        ivDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfWaterIntakeCounter.setVisibility(View.GONE);
            }
        });

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
                        Toast.makeText(WaterIntakeCounterActivity.this, "Cup size change successfully", Toast.LENGTH_SHORT).show();
                        callDailyWaterIntakeUpdatePlanApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(WaterIntakeCounterActivity.this, "Cup created successfully", Toast.LENGTH_SHORT).show();
                        callDailyWaterIntakeUpdatePlanApi();
                        // setData(response.body().getAddUserCup());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(WaterIntakeCounterActivity.this, "Custom cup size created successfully", Toast.LENGTH_SHORT).show();
                        callCupCreateApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
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
                        setWeeklyData(response.body().getWeeklyData());
                        setAllTimeData(response.body().getAllTimeData());
                        setWeeklyData(response.body().getWeeklyData());
                       /* if (type.equalsIgnoreCase("Weekly")) {
                        } else if (type.equalsIgnoreCase("Monthly")) {
                            setMonthlyData(response.body().getMonthlyData());
                        } else if (type.equalsIgnoreCase("All Time")) {
                        } else {
                        }*/
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
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
        Util.showProgressDialog(getApplicationContext());
        tvTotWaterAmountDrunk.setText("" + allTimeData.getAllTimeTakenWaterInMl().toString() + " ml");
        tvCountNosOfGlass.setText("" + allTimeData.getAllTimeTakenGlassOfWater().toString());
        tvRecommendedNosOfWaterGlasses.setText("/" + allTimeData.getAllTimeRecommendedGlassOfWater().toString() + " Glasses");
        Util.dismissProgressDialog();
    }

    private void setMonthlyData(MonthlyData monthlyData) {
        Util.showProgressDialog(getApplicationContext());
        tvTotWaterAmountDrunk.setText("" + monthlyData.getMonthlyTakenWaterInMl().toString() + " ml");
        tvCountNosOfGlass.setText("" + monthlyData.getMonthlyTakenGlassOfWater().toString());
        tvRecommendedNosOfWaterGlasses.setText("/" + monthlyData.getMonthlyRecommendedGlassOfWater().toString() + " Glasses");
        Util.dismissProgressDialog();

    }

    private void setWeeklyData(WeeklyData weeklyData) {
        Util.showProgressDialog(getApplicationContext());
        tvTotWaterAmountDrunk.setText("" + weeklyData.getWeeklyTakenWaterInMl().toString() + " ml");
        tvCountNosOfGlass.setText("" + weeklyData.getWeeklyTakenGlassOfWater().toString());
        tvRecommendedNosOfWaterGlasses.setText("/" + weeklyData.getWeeklyRecommendedGlassOfWater().toString() + " Glasses");
        Util.dismissProgressDialog();

    }


    private void getWeeklyData() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        setDate(response.body().getCurrentPlan());
                        setWeeklyData(response.body().getWeeklyData());
//                        setAllTimeData(response.body().getAllTimeData());
//                        setWeeklyData(response.body().getWeeklyData());
//                        setMonthlyData(response.body().getMonthlyData());

                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RestorePlanResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getMonthlyData() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        setDate(response.body().getCurrentPlan());
                        //                       setWeeklyData(response.body().getWeeklyData());
//                        setAllTimeData(response.body().getAllTimeData());
//                        setWeeklyData(response.body().getWeeklyData());
                        setMonthlyData(response.body().getMonthlyData());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestorePlanResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAlltimeData() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getRestorePlan(getAccessToken, new Callback<RestorePlanResponse>() {
            @Override
            public void onResponse(Call<RestorePlanResponse> call, Response<RestorePlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        setDate(response.body().getCurrentPlan());
//                        setWeeklyData(response.body().getWeeklyData());
                        setAllTimeData(response.body().getAllTimeData());
//                        setWeeklyData(response.body().getWeeklyData());
//                        setMonthlyData(response.body().getMonthlyData());
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestorePlanResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


}