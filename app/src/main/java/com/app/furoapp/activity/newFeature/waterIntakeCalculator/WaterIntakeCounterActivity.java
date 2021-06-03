package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.SettingsActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.FetchGlassAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.adapter.SelectCupSizeAdapter;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.AddUserCup;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.cupCreate.CupCreateResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.customeSizeGlass.CustomGlassSizeRequest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.customeSizeGlass.CustomGlassSizeResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.GlassFetchResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.UserGlassSize;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterIntakeCounterActivity extends AppCompatActivity implements SelectCupSizeAdapter.GlassClickCallBack {
    public ImageView ivAddCup, ivAddCustomSizeCup, ivCancel, ivSetting, ivBackIcon;
    private String getAccessToken;
    public TextView tvNosGlassCount, tvTakingWater, tvAddCustomSize, tvRecommendedReamingWater, tvGlassSize;
    public View includePopMenuOfSelectCupSize;
    public RecyclerView rvSelectCupSize;
    public SelectCupSizeAdapter selectCupSizeAdapter;
    List<UserGlassSize> userGlassSizeList = new ArrayList<>();
    private boolean isGlassSelected;
    private String glassSize;
    public ConstraintLayout clWaterIntakeCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_counter);
        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);
        initViews();
        clickEvent();
        callFetchGlassApi();
        operateRacyData();
    }

    private void initViews() {
        ivAddCup = findViewById(R.id.ivAddCup);
        ivAddCustomSizeCup = findViewById(R.id.ivAddCustomSizeCup);
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
                    callCustomGlassSizeApi();   //calling api .....cup/select-custom-glass-size
                } else {
                    Toast.makeText(getApplicationContext(), "Please select glass size ! ", Toast.LENGTH_SHORT).show();
                }


            }
        });
        ivAddCustomSizeCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includePopMenuOfSelectCupSize.setVisibility(View.VISIBLE);
                clWaterIntakeCounter.setClickable(false);
                callFetchGlassApi();
            }
        });

        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingWaterIntakeActivity.class);
                startActivity(intent);
            }
        });

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callCupCreateApi() {
        Util.showProgressDialog(getApplicationContext());
        RestClient.getCupCreate(getAccessToken, new Callback<CupCreateResponse>() {
            @Override
            public void onResponse(Call<CupCreateResponse> call, Response<CupCreateResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    Toast.makeText(WaterIntakeCounterActivity.this, "Cup created successfully", Toast.LENGTH_SHORT).show();
                    setData(response.body().getAddUserCup());
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
                if (response.code() == 200 && response.body() != null && response.body().getStatus() != null) {

                    if (response.body().getUserGlassSizes() != null) {
                        notifyGlassAdapter(response.body().getUserGlassSizes());
                    }
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

    private void callCustomGlassSizeApi() {
        CustomGlassSizeRequest customGlassSizeRequest = new CustomGlassSizeRequest();
        customGlassSizeRequest.setGlass_size_in_ml(glassSize);
        Util.showProgressDialog(getApplicationContext());
        RestClient.getCustomSizeGlass(getAccessToken, customGlassSizeRequest, new Callback<CustomGlassSizeResponse>() {
            @Override
            public void onResponse(Call<CustomGlassSizeResponse> call, Response<CustomGlassSizeResponse> response) {
                Util.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    callCupCreateApi();
                }
            }

            @Override
            public void onFailure(Call<CustomGlassSizeResponse> call, Throwable t) {
                Toast.makeText(WaterIntakeCounterActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}