package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.AllPlanAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.FetchGlassAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.AllPlan;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.FetchAllPlanResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchAllPlan.RecommendedPlans;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlaneActivity extends AppCompatActivity {
    public LinearLayout llMorePlan, llRecommended, llOthersPlan;
    public TextView tvShowsMl, tvShowMl2, tvRecommended, tvEveryTime;
    public ImageView ivClockImg, ivStartJourney, ivCancel;
    public RecyclerView rvCreatePlan;
    public View includeCreatePlanPopUp;
    private String getAccessessToken;
    public RecyclerView rvAllPlan;
    public AllPlanAdapter allPlanAdapter;
    List<AllPlan> allPlanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plane);

        getAccessessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        findViews();
        callFetchAllPlanApi();
        clickEvent();
        setAllPlanAdapter();
       // setCreatePlanAdapter();
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
                    setRecommendedData(response.body().getRecommendedPlans());
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

    private void setRecommendedData(RecommendedPlans recommendedPlans) {
        tvShowsMl.setText("" + recommendedPlans.getRecommendedWater());
        tvEveryTime.setText("Every " + recommendedPlans.getTimeDuration() + " minutes");
    }

    private void setAllPlanAdapter() {
        allPlanAdapter = new AllPlanAdapter(getApplicationContext(), allPlanList);
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

   /* private void setCreatePlanAdapter() {
        allPlanAdapter = new AllPlanAdapter(getApplicationContext(), allPlanList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvCreatePlan.setLayoutManager(layoutManager);
        rvCreatePlan.setItemAnimator(new DefaultItemAnimator());
        rvCreatePlan.setAdapter(allPlanAdapter);
    }*/


    private void clickEvent() {
        llOthersPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.VISIBLE);
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeCreatePlanPopUp.setVisibility(View.GONE);

            }
        });
    }

}