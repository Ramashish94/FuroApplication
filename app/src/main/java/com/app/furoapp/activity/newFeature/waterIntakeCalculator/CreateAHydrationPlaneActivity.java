package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.AllPlanAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.WaterInMlAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.AllPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.FetchAllPlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.GlassSize;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.WaterGlassSizeModel;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAHydrationPlaneActivity extends AppCompatActivity implements WaterInMlAdapter.WaterSizeInMLClickCallBack, AllPlanAdapter.AllPlanClickCallBack {
    public LinearLayout llMorePlan, llRecommended, llOthersPlan;
    public TextView tvShowsMl, tvShowMl2, tvRecommended, tvEveryTime, tvCreatePlan, tvFeelAweSome, tvNosGlass;
    public ImageView ivClockImg, ivStartJourney, ivCancel, ivOverloadIndicatorMsg, ivRecommendedPlan, ivSelRecommendedPlan;
    public RecyclerView rvCreatePlan;
    public View includeCreatePlanPopUp;
    private String getAccessessToken;
    public RecyclerView rvAllPlan;
    public AllPlanAdapter allPlanAdapter;
    List<AllPlan> allPlanList = new ArrayList<>();
    public String waterGlassSizeInMl;
    public WaterInMlAdapter waterInMlAdapter;
    List<WaterGlassSizeModel> waterGlassSizeModelList = new ArrayList<>();
    public int getWaterMlSize;
    private boolean isGlassSizeSelected;
    ConstraintLayout clCreatePlanLayout;
    private int glassSizeInMl;
    private String planId, waterTakeMl, recDurationTime;
    public String selectPlanId;
    public String onGoingPlanId;
    public RadioGroup rdbt_group;
    public RadioButton rdBtnRecommendedPlan;
    private int waterTakenInMl;
    private boolean isPalnSelected;
    private ProgressBar loadingProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plane);

        getAccessessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        findViews();
        callFetchAllPlanApi();
        clickEvent();
        setAllPlanAdapter();
    }

    private void findViews() {
        llRecommended = findViewById(R.id.llRecommended);
        tvShowsMl = findViewById(R.id.tvShowsMl);
        tvShowMl2 = findViewById(R.id.tvShowMl2);
        tvRecommended = findViewById(R.id.tvRecommended);
        tvEveryTime = findViewById(R.id.tvEveryTime);
        ivClockImg = findViewById(R.id.ivClockImg);
        ivStartJourney = findViewById(R.id.ivStartJourney);
        includeCreatePlanPopUp = findViewById(R.id.includeCreatePlanPopUp);
        ivCancel = findViewById(R.id.ivCancel);
        rvAllPlan = findViewById(R.id.rvAllPlan);
        rvCreatePlan = findViewById(R.id.rvCreatePlan);
        llOthersPlan = findViewById(R.id.llOthersPlan);
        tvCreatePlan = findViewById(R.id.tvCreatePlan);
        ivOverloadIndicatorMsg = findViewById(R.id.ivOverloadIndicatorMsg);
        tvFeelAweSome = findViewById(R.id.tvFeelAweSome);
        clCreatePlanLayout = findViewById(R.id.clCreatePlanLayout);
        tvNosGlass = findViewById(R.id.tvNosGlass);
        ivRecommendedPlan = findViewById(R.id.ivRecommendedPlan);
        ivSelRecommendedPlan = findViewById(R.id.ivSelRecommendedPlan);
        rdbt_group = findViewById(R.id.rdbt_group);
        rdBtnRecommendedPlan = findViewById(R.id.rdBtnRecommendedPlan);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

    }

    private void callFetchAllPlanApi() {
        if (Util.isInternetConnected(getApplicationContext())) {
            loadingProgressBar.setVisibility(View.VISIBLE);
            RestClient.getFetchAllPlan(getAccessessToken, new Callback<FetchAllPlanResponse>() {
                @Override
                public void onResponse(Call<FetchAllPlanResponse> call, Response<FetchAllPlanResponse> response) {
                    loadingProgressBar.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getStatus() != null) {
                            if (response.body().getAllPlans() != null && response.body().getAllPlans().size() > 0) {
                                // getGlass size
                                getGlassSize(response.body().getGlassSize());
                                //recommended plan
                                setRecommendedData(response.body().getAllPlans());
                                //for all plan

                                notifyAllPlanAdapter(response.body().getAllPlans());
                                //isSelected plan
                                // isSelectedPlan(response.body().getAllPlans());
                                adapterSelectedItem(response.body().getAllPlans());
                            } else {
                                Toast.makeText(CreateAHydrationPlaneActivity.this, "No any plan will be available. Please create a plan !", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateAHydrationPlaneActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FetchAllPlanResponse> call, Throwable t) {
                    Toast.makeText(CreateAHydrationPlaneActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection ! ", Toast.LENGTH_SHORT).show();
        }
    }


    private void getGlassSize(GlassSize glassSize) {
        if (glassSize != null) {
            glassSizeInMl = Integer.parseInt(glassSize.getGlassSizeInMl());
        }
    }

    private void setRecommendedData(List<AllPlan> allPlans) {
        if (allPlans != null && allPlans.size() > 0) {
            for (AllPlan allPlan : allPlans) {
                if (allPlan.getIsRecomended() == 1) {
                    tvEveryTime.setText("Every " + allPlan.getRecommendedDurationInMins() + " minutes");
                    waterTakenInMl = Integer.parseInt(String.valueOf(allPlan.getWaterTakeInMl()));
                    tvShowsMl.setText("" + waterTakenInMl + " ml");
//                    planId = String.valueOf(allPlan.getId());
                }
            }
        }
    }


    private void setAllPlanAdapter() {
        allPlanAdapter = new AllPlanAdapter(getApplicationContext(), allPlanList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAllPlan.setLayoutManager(layoutManager);
        rvAllPlan.setItemAnimator(new DefaultItemAnimator());
        rvAllPlan.setAdapter(allPlanAdapter);
    }

    private void notifyAllPlanAdapter(List<AllPlan> allPlans) {
        allPlanList.clear();
        allPlanList.addAll(allPlans);
        if (allPlanList != null && allPlanList.size() > 0) {
            allPlanAdapter.notifyDataSetChanged();
        }
    }

    private void clickEvent() {
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.GONE);
            }
        });

        llOthersPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.VISIBLE);
                clCreatePlanLayout.setClickable(false);
                setCreatePlanAdapter();
            }
        });

        tvCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGlassSizeSelected) {
                    includeCreatePlanPopUp.setVisibility(View.GONE);
                    callCreatePlanApi();
                } else {
                    Toast.makeText(CreateAHydrationPlaneActivity.this, "Please select glass size!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivStartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterIntakeCounterActivity.class);
                FuroPrefs.putString(getApplicationContext(), Constants.WATER_INTAKE_PLAN, planId);
                startActivity(intent);
                finish();
            }
        });

        ivRecommendedPlan.setOnClickListener(v -> {
            ivRecommendedPlan.setVisibility(View.GONE);
            ivSelRecommendedPlan.setVisibility(View.VISIBLE);
            allPlanAdapter.setNotifyData(allPlanAdapter);
        });
        ivSelRecommendedPlan.setOnClickListener(v -> {
            ivRecommendedPlan.setVisibility(View.VISIBLE);
            ivSelRecommendedPlan.setVisibility(View.GONE);

        });
    }

    private void setCreatePlanAdapter() {
        waterInMlAdapter = new WaterInMlAdapter(getApplicationContext(), waterGlassSizeModelList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCreatePlan.setLayoutManager(layoutManager);
        rvCreatePlan.setItemAnimator(new DefaultItemAnimator());
        rvCreatePlan.setAdapter(allPlanAdapter);

        List<WaterGlassSizeModel> waterGlassSizeModels = new ArrayList<>();
        for (int i = 1000; i <= 12000; i = i + 100) {
            WaterGlassSizeModel waterGlassSizeModel = new WaterGlassSizeModel();
            waterGlassSizeModel.setGlass_size_in_ml(String.valueOf(i));
            waterGlassSizeModels.add(waterGlassSizeModel);
        }
        WaterInMlAdapter waterInMlAdapter = new WaterInMlAdapter(getApplicationContext(), waterGlassSizeModels, this);
        rvCreatePlan.setAdapter(waterInMlAdapter);
    }

    @Override
    public void getWaterInMlSelect(int pos, int waterInMl) {
        getWaterMlSize = waterInMl;
        isGlassSizeSelected = true;
        Log.d(" Glass Size", String.valueOf(getWaterMlSize));

        if (getWaterMlSize > 8000) {
            ivOverloadIndicatorMsg.setVisibility(View.GONE);
            tvFeelAweSome.setVisibility(View.GONE);
        } else {
            tvFeelAweSome.setVisibility(View.GONE);
            ivOverloadIndicatorMsg.setVisibility(View.GONE);
        }

        int noOfGlass = getWaterMlSize / glassSizeInMl;
        tvNosGlass.setText("" + noOfGlass);
    }


    private void callCreatePlanApi() {
        PlaneCreateRequest planeCreateRequest = new PlaneCreateRequest();
        planeCreateRequest.setWater_take_in_ml(String.valueOf(getWaterMlSize));
        if (Util.isInternetConnected(getApplicationContext())) {
            Util.showProgressDialog(getApplicationContext());
            RestClient.getPlaneCreate(getAccessessToken, planeCreateRequest, new Callback<PlaneCreateResponse>() {
                @Override
                public void onResponse(Call<PlaneCreateResponse> call, Response<PlaneCreateResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().getStatus() != null) {
                            Toast.makeText(CreateAHydrationPlaneActivity.this, "new plan created!", Toast.LENGTH_SHORT).show();
                            callFetchAllPlanApi();
                        }
                    } else if (response.code() == 500) {
                        Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(getApplicationContext(), "Session expired. Please login again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PlaneCreateResponse> call, Throwable t) {
                    Toast.makeText(CreateAHydrationPlaneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check internet connection !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPlanClickCallBack(Integer id, String waterTakeInMl, String recommendedDurationInMins) {
        isPalnSelected = true;
        planId = String.valueOf(id);
        Log.d("planId", planId);
        waterTakeMl = waterTakeInMl;
        Log.d("waterTakeMl", waterTakeMl);
        recDurationTime = recommendedDurationInMins;
        Log.d("recDurationTime", recDurationTime);
//        if (isPalnSelected) {
//            ivSelRecommendedPlan.setVisibility(View.GONE);
//            ivRecommendedPlan.setVisibility(View.VISIBLE);
//        }
        allPlanAdapter.updateItem(id);

    }

    private void adapterSelectedItem(List<AllPlan> allPlans) {
        if (allPlans != null && allPlans.size() > 0) {
            for (AllPlan allPlan : allPlans) {
                if (allPlan.getIsSelected() == 1) {
                    planId = String.valueOf(allPlan.getId());
                    Log.d("planId", planId);
                    waterTakeMl = String.valueOf(allPlan.getWaterTakeInMl());
                    Log.d("waterTakeMl", waterTakeMl);
                    recDurationTime = String.valueOf(allPlan.getRecommendedDurationInMins());
                }
            }
        }
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        callFetchAllPlanApi();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        callFetchAllPlanApi();
    }
}