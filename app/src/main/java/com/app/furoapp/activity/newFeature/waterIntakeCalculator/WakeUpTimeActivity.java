package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.ArrayList;
import java.util.List;

public class WakeUpTimeActivity extends AppCompatActivity {
    public ImageView ivContinue;
    TimePicker timePicker;
    RecyclerView rvBedTime;
    List<AgeModelTest> ageModelTest = new ArrayList<>();
    WakeUpTimeAdapter wakeUpTimeAdapter;
    private String getHours;
    public float time = (int) 12.01;


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
        ivContinue = findViewById(R.id.ivContinue);
        timePicker = findViewById(R.id.timePicker);
        rvBedTime = findViewById(R.id.rvBedTime);
    }

    private void timePickerEvent() {
        timePicker.setIs24HourView(true); // used to display AM/PM mode
        // perform set on time changed listener event
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
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
        for (float i = (float) 01.01; i <=24.01; i++) {
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
                Intent intent = new Intent(getApplicationContext(), WhatsYourBedTimeActivity.class);
                startActivity(intent);
            }
        });
    }
}