package com.app.furoapp.activity.newFeature.healthCare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.TrackYourStepsStartActivity;
import com.app.furoapp.activity.newFeature.bmiCalculator.FindYourBmiActivity;
import com.app.furoapp.activity.newFeature.waterIntakeCalculator.WaterIntakeStartActivity;

public class HealthCenterDashboardActivity extends AppCompatActivity {
    public LinearLayout llDailyStepsTracker, llWaterIntakeMonitor, llBMICalculator, llCaloriesCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_center);
        initVies();
        ClickEvent();
    }

    private void initVies() {
        llDailyStepsTracker = findViewById(R.id.llDailyStepsTracker);
        llWaterIntakeMonitor = findViewById(R.id.llWaterIntakeMonitor);
        llBMICalculator = findViewById(R.id.llBMIClculator);
        llCaloriesCalculator = findViewById(R.id.llCaloriesCalculator);

    }


    private void ClickEvent() {

        llDailyStepsTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackYourStepsStartActivity.class);
                startActivity(intent);
            }
        });

        llWaterIntakeMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), WaterIntakeStartActivity.class);
                startActivity(intent);

            }
        });

        llBMICalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(HealthCenterDashboardActivity.this, "Working on this ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), FindYourBmiActivity.class);
                startActivity(intent);
            }
        });

        /*llCaloriesCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HealthCenterDashboardActivity.this, "Click Success on calories calculator", Toast.LENGTH_SHORT).show();

               *//* Intent intent =new Intent(getApplicationContext(),CalculateCaloriesActivity.class);
                startActivity(intent);*//*
            }
        });*/
    }

}