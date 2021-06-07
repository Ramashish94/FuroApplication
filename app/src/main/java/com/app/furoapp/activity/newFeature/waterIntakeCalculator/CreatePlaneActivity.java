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
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.AllPlanAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.WaterInMlAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.AllPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.FetchAllPlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.PlaneCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.planCreate.WaterGlassSizeModel;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.waterIntakeCounter.WaterIntakeUpdatePlanRequest;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlaneActivity extends AppCompatActivity implements WaterInMlAdapter.WaterSizeInMLClickCallBack, AllPlanAdapter.AllPlanClickCallBack {
    public LinearLayout llMorePlan, llRecommended, llOthersPlan;
    public TextView tvShowsMl, tvShowMl2, tvRecommended, tvEveryTime, tvCreatePlan, tvFeelAweSome, tvNosGlass;
    public ImageView ivClockImg, ivStartJourney, ivCancel, ivOverloadIndicatorMsg;
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
    }

    private void callFetchAllPlanApi() {
        Util.isInternetConnected(getApplicationContext());
        Util.showProgressDialog(getApplicationContext());
        RestClient.getFetchAllPlan(getAccessessToken, new Callback<FetchAllPlanResponse>() {
            @Override
            public void onResponse(Call<FetchAllPlanResponse> call, Response<FetchAllPlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getStatus() != null) {
                        //recommended plan
                        setRecommendedData(response.body());
                        //for all plan
                        notifyAllPlanAdapter(response.body().getAllPlans());
                    } else {
                        Toast.makeText(CreatePlaneActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchAllPlanResponse> call, Throwable t) {
                Toast.makeText(CreatePlaneActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecommendedData(FetchAllPlanResponse body) {
        planId = String.valueOf(body.getAllPlans().get(0).getId());
        tvShowsMl.setText(body.getGlassSize().getGlassSizeInMl() + " ml");
        tvEveryTime.setText("Every " + body.getAllPlans().get(0).getRecommendedDurationInMins() + " minutes");
        /*if (body.getAllPlans().get(0).getIsRecomended() == 1) {
            tvEveryTime.setText("Every " + body.getAllPlans().get(0).getRecommendedDurationInMins() + " minutes");
        }*/
        glassSizeInMl = Integer.parseInt(body.getGlassSize().getGlassSizeInMl());
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
                    Toast.makeText(CreatePlaneActivity.this, "Please select glass size!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivStartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WaterIntakeCounterActivity.class);
                intent.putExtra("planId", planId);
                startActivity(intent);
                finish();
            }
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
            ivOverloadIndicatorMsg.setVisibility(View.VISIBLE);
            tvFeelAweSome.setVisibility(View.GONE);
        } else {
            tvFeelAweSome.setVisibility(View.VISIBLE);
            ivOverloadIndicatorMsg.setVisibility(View.GONE);
        }

        int noOfGlass = getWaterMlSize / glassSizeInMl;
        tvNosGlass.setText("" + noOfGlass);
    }


    private void callCreatePlanApi() {
        PlaneCreateRequest planeCreateRequest = new PlaneCreateRequest();
        planeCreateRequest.setWater_take_in_ml(String.valueOf(getWaterMlSize));
        Util.showProgressDialog(getApplicationContext());
        RestClient.getPlaneCreate(getAccessessToken, planeCreateRequest, new Callback<PlaneCreateResponse>() {
            @Override
            public void onResponse(Call<PlaneCreateResponse> call, Response<PlaneCreateResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200) {
                    if (response.body() != null && response.body().getStatus() != null) {
                        Toast.makeText(CreatePlaneActivity.this, "new plan created!", Toast.LENGTH_SHORT).show();
                        callFetchAllPlanApi();
                    }
                } else if (response.code() == 500) {
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlaneCreateResponse> call, Throwable t) {
                Toast.makeText(CreatePlaneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getPlanClickCallBack(Integer id, String waterTakeInMl, String
            recommendedDurationInMins) {
        planId = String.valueOf(id);
        Log.d("planId", planId);
        waterTakeMl = waterTakeInMl;
        Log.d("waterTakeMl", waterTakeMl);
        recDurationTime = recommendedDurationInMins;
        Log.d("recDurationTime", recDurationTime);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}