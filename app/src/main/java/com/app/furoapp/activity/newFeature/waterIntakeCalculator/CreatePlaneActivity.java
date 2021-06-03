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
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.WaterGlassSizeAdapter;
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

public class CreatePlaneActivity extends AppCompatActivity implements WaterGlassSizeAdapter.WaterGlassSizeClickCallBack, AllPlanAdapter.AllPlanClickCallBack {
    public LinearLayout llMorePlan, llRecommended, llOthersPlan;
    public TextView tvShowsMl, tvShowMl2, tvRecommended, tvEveryTime, tvCreatePlan, tvFeelAweSome;
    public ImageView ivClockImg, ivStartJourney, ivCancel, ivOverloadIndicatorMsg;
    public RecyclerView rvCreatePlan;
    public View includeCreatePlanPopUp;
    private String getAccessessToken;
    public RecyclerView rvAllPlan;
    public AllPlanAdapter allPlanAdapter;
    List<AllPlan> allPlanList = new ArrayList<>();
    public String waterGlassSizeInMl;
    public WaterGlassSizeAdapter waterGlassSizeAdapter;
    List<WaterGlassSizeModel> waterGlassSizeModelList = new ArrayList<>();
    public int waterGlassSize;
    private boolean isGlassSizeSelected;
    ConstraintLayout clCreatePlanLayout;

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
    }

    private void callFetchAllPlanApi() {
        Util.isInternetConnected(getApplicationContext());
        Util.showProgressDialog(getApplicationContext());
        RestClient.getFetchAllPlan(getAccessessToken, new Callback<FetchAllPlanResponse>() {
            @Override
            public void onResponse(Call<FetchAllPlanResponse> call, Response<FetchAllPlanResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null && response.body().getStatus() != null) {
                    //recommended plan
                    setRecommendedData(response.body());
                    //set time for recommended plan
                    setTimeForRecommendedPlan(response.body().getAllPlans().get(0));
                    //for all plan
                    notifyAllPlanAdapter(response.body().getAllPlans());
                } else {
                    Toast.makeText(CreatePlaneActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchAllPlanResponse> call, Throwable t) {
                Toast.makeText(CreatePlaneActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecommendedData(FetchAllPlanResponse body) {
        tvShowsMl.setText(body.getGlassSize().getGlassSizeInMl() + " ml");
        if (body.getAllPlans().get(0).getId() == 1) {
            tvEveryTime.setText("Every " + body.getAllPlans().get(0).getRecommendedDurationInMins() + " minutes");
        }
    }

    private void setTimeForRecommendedPlan(AllPlan allPlan) {
        if (allPlan.getId() == 1) {
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
        llOthersPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.VISIBLE);
                clCreatePlanLayout.setClickable(false);
                setCreatePlanAdapter();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.GONE);
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
                startActivity(intent);
            }
        });
    }

    private void callCreatePlanApi() {
        PlaneCreateRequest planeCreateRequest = new PlaneCreateRequest();
        planeCreateRequest.setWater_take_in_ml(String.valueOf(waterGlassSize));
        Util.showProgressDialog(getApplicationContext());
        RestClient.getPlaneCreate(getAccessessToken, planeCreateRequest, new Callback<PlaneCreateResponse>() {
            @Override
            public void onResponse(Call<PlaneCreateResponse> call, Response<PlaneCreateResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null && response.body().getStatus() != null) {
                    Toast.makeText(CreatePlaneActivity.this, "new plan created!", Toast.LENGTH_SHORT).show();
                    callFetchAllPlanApi();
                }
            }

            @Override
            public void onFailure(Call<PlaneCreateResponse> call, Throwable t) {
                Toast.makeText(CreatePlaneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCreatePlanAdapter() {
        waterGlassSizeAdapter = new WaterGlassSizeAdapter(getApplicationContext(), waterGlassSizeModelList, this);
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
        WaterGlassSizeAdapter waterGlassSizeAdapter = new WaterGlassSizeAdapter(getApplicationContext(), waterGlassSizeModels, this);
        rvCreatePlan.setAdapter(waterGlassSizeAdapter);
    }

    @Override
    public void glassSizeSelectItem(int glassSize) {
        waterGlassSize = glassSize;
        isGlassSizeSelected = true;
        Log.d(" Glass Size", String.valueOf(waterGlassSize));

        if (waterGlassSize > 8000) {
            ivOverloadIndicatorMsg.setVisibility(View.VISIBLE);
            tvFeelAweSome.setVisibility(View.GONE);
        } else {
            tvFeelAweSome.setVisibility(View.VISIBLE);
            ivOverloadIndicatorMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void getPlanClickCallBack(Integer id, String waterTakeInMl, String recommendedDurationInMins) {
        String planId = String.valueOf(id);
        String waterTakeMl =waterTakeInMl;
        String recDurationTime= recommendedDurationInMins;
    }
}