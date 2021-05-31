package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.GlassFetchResponse;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.fetchGlass.UserGlassSize;
import com.app.furoapp.retrofit.RestClient;
import com.app.furoapp.retrofit.RetrofitClient;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.app.furoapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WakeUpTimeActivity extends AppCompatActivity implements FetchGlassAdapter.GlassClickCallBack {
    public ImageView ivContinue, ivSave;
    TimePicker tpWakUpTime, tpBedTime;
    RecyclerView rvGlassWater;
    List<UserGlassSize> userGlassSizeList = new ArrayList<>();
    FetchGlassAdapter fetchGlassAdapter;
    private String getMrngHours, getEveningBedHours;
    public float time = (int) 12.01;
    public LinearLayout llClosedIcon, llSecondWaterGlassBg, llGlassTakebg;
    public View includeSecondLayerBgOfGlass;
    public ConstraintLayout clWakeAndBedTime;
    private String getAccessToken;
    private boolean isGlassSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up_time);

        getAccessToken = FuroPrefs.getString(getApplicationContext(), Constants.Get_ACCESS_TOKEN);

        findViews();
        timePickerEvent();
        clickListener();

        callFetchGlassApi();
        operateRacyData();
    }

    private void findViews() {
        clWakeAndBedTime = findViewById(R.id.clWakeAndBedTime);
        ivContinue = findViewById(R.id.ivContinue);
        tpWakUpTime = findViewById(R.id.tpWakUpTime);
        tpBedTime = findViewById(R.id.tpBedTime);

        includeSecondLayerBgOfGlass = findViewById(R.id.includeSecondLayerBgOfGlass);
        llSecondWaterGlassBg = findViewById(R.id.llSecondWaterGlassBg);
        rvGlassWater = findViewById(R.id.rvGlassWater);
        ivSave = findViewById(R.id.ivSave);
        llClosedIcon = findViewById(R.id.llClosedIcon);
        llGlassTakebg = findViewById(R.id.llGlassTakebg);
        ivContinue = findViewById(R.id.ivContinue);
    }

    private void timePickerEvent() {
        tpWakUpTime.setIs24HourView(true); // used to display AM/PM mode
        // perform set on time changed listener event
        tpWakUpTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                // Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                //  time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
                getMrngHours = hourOfDay + "" + minute;
            }
        });

        tpBedTime.setIs24HourView(true); // used to display AM/PM mode
        // perform set on time changed listener event
        tpBedTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                //  time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
                getEveningBedHours = hourOfDay + "" + minute;
            }
        });

    }

    private void clickListener() {
        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.VISIBLE);
                callFetchGlassApi();
            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGlassSelected) {
                    includeSecondLayerBgOfGlass.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), CreatePlaneActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(WakeUpTimeActivity.this, "Please select glass size ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        llClosedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.GONE);
            }
        });

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
                Toast.makeText(WakeUpTimeActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void operateRacyData() {
        fetchGlassAdapter = new FetchGlassAdapter(getApplicationContext(), userGlassSizeList, this);
        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvGlassWater.setLayoutManager(layoutManager);
        rvGlassWater.setItemAnimator(new DefaultItemAnimator());
        rvGlassWater.setAdapter(fetchGlassAdapter);
    }

    private void notifyGlassAdapter(List<UserGlassSize> userGlassSizes) {
        userGlassSizeList.clear();
        userGlassSizeList.addAll(userGlassSizes);
        if (userGlassSizeList != null && userGlassSizeList.size() > 0) {
            fetchGlassAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void glassSelectItem(int glassSizeInMl) {
        String glassSize = String.valueOf(glassSizeInMl);
        isGlassSelected = true;
        Log.d(" glassSize", glassSize);
    }


}