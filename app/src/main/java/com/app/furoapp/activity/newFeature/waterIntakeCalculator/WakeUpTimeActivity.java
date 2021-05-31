package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.ArrayList;
import java.util.List;

public class WakeUpTimeActivity extends AppCompatActivity {
    public ImageView ivContinue, ivSave;
    TimePicker tpWakUpTime;
    RecyclerView rvBedTime, rvGlassWater;
    List<AgeModelTest> ageModelTest = new ArrayList<>();
    WakeUpTimeAdapter wakeUpTimeAdapter;
    private String getHours;
    public float time = (int) 12.01;
    public LinearLayout llClosedIcon, llSecondWaterGlassBg, llGlassTakebg;
    public View includeSecondLayerBgOfGlass;
    public GlassWaterAdapter glassWaterAdapter;
    public ConstraintLayout clWakeAndBedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up_time);

        findViews();
        timePickerEvent();
        clickListener();
        operateRacyData();
    }

    private void findViews() {
        clWakeAndBedTime = findViewById(R.id.clWakeAndBedTime);
        ivContinue = findViewById(R.id.ivContinue);
        tpWakUpTime = findViewById(R.id.tpWakUpTime);
        rvBedTime = findViewById(R.id.rvBedTime);

        includeSecondLayerBgOfGlass = findViewById(R.id.includeSecondLayerBgOfGlass);
        llSecondWaterGlassBg = findViewById(R.id.llSecondWaterGlassBg);
        rvGlassWater = findViewById(R.id.rvGlassWater);
        rvBedTime = findViewById(R.id.rvBedTime);
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
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                //  time.setText("Time is :: " + hourOfDay + " : " + minute); // set the current time in text view
                getHours = hourOfDay + "" + minute;
            }
        });
    }

    private void operateRacyData() {
        wakeUpTimeAdapter = new WakeUpTimeAdapter(getApplicationContext(), ageModelTest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvBedTime.setLayoutManager(layoutManager);
        rvBedTime.setItemAnimator(new DefaultItemAnimator());
        rvBedTime.setAdapter(wakeUpTimeAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (float i = (float) 01.01; i <= 24.01; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge(String.valueOf(i));
            ageModelTestArrayList.add(ageModelTest);
        }
        WakeUpTimeAdapter wakeUpTimeAdapter = new WakeUpTimeAdapter(getApplicationContext(), ageModelTestArrayList);
        rvBedTime.setAdapter(wakeUpTimeAdapter);
    }

    private void clickListener() {
        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.VISIBLE);

                /*Intent intent = new Intent(getApplicationContext(), WhatsYourBedTimeActivity.class);
                startActivity(intent);*/
            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.GONE);

                Toast.makeText(WakeUpTimeActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        llClosedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.GONE);

            }
        });


    }
}