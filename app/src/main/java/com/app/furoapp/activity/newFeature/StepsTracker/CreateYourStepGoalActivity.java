package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.StepsCountAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.adapter.AgeAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;

import java.util.ArrayList;
import java.util.List;

public class CreateYourStepGoalActivity extends AppCompatActivity implements StepsCountAdapter.StepsClickCallBack {
    private static final String TAG = "CreateYourStepGoalActivity";
    List<AgeModelTest> ageModelTest = new ArrayList<>();
    public RecyclerView rvStepsCount;
    public ImageView ivContinue;
    public TextView ivBack, tvRecommendedText;
    AgeAdapter ageAdapter;
    StepsCountAdapter stepsCountAdapter;
    private int selectNumber;
    int adapterCurrentPos = 0;
    private NumberPicker picker1;
    private String[] pickerVals;
    private int getnuberVal;
    private boolean isSelectedCustomGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_goal_activity);
        findViews();
        clickEvent();
        setRecyAdapter();
        // numberPicker();
    }

    private void numberPicker() {
        int start = 1000;
        String[] numbers = new String[20];
        for (int i = 0; i <= 20; i++) {
            numbers[i] = start + "";
            start = start + 100;
        }
        picker1.setMaxValue(20);
        picker1.setMinValue(1);
        picker1.setDisplayedValues(numbers);
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", String.valueOf(valuePicker1));
            }
        });
    }


    private void findViews() {
        rvStepsCount = findViewById(R.id.rvStepsCount);
        ivContinue = findViewById(R.id.ivContinue);
        ivBack = findViewById(R.id.ivBack);
        picker1 = findViewById(R.id.numberpicker_main_picker);
        tvRecommendedText = findViewById(R.id.tvRecommendedText);

    }

    private void setRecyAdapter() {
        stepsCountAdapter = new StepsCountAdapter(getApplicationContext(), ageModelTest, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvStepsCount.setLayoutManager(layoutManager);
        rvStepsCount.setItemAnimator(new DefaultItemAnimator());
        rvStepsCount.setAdapter(stepsCountAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 1000; i <= 30000; i = i + 100) {
            AgeModelTest ageModelTest = new AgeModelTest();
            // ageModelTest.setAge(String.valueOf(Color.parseColor("#000000")));
            ageModelTest.setAge("" + i);
            ageModelTestArrayList.add(ageModelTest);
        }
        StepsCountAdapter stepsCountAdapter = new StepsCountAdapter(getApplicationContext(), ageModelTestArrayList, this);
        rvStepsCount.setAdapter(stepsCountAdapter);
        rvStepsCount.scrollToPosition(adapterCurrentPos);   // scroll to current position in android

    }

    private void clickEvent() {
        ivContinue.setOnClickListener(v -> {
            if (isSelectedCustomGoal) {
                Intent intent = new Intent(getApplicationContext(), FqStepsCounterActivity.class);
                intent.putExtra("selectNumber", String.valueOf(selectNumber));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Please select your goal!", Toast.LENGTH_SHORT).show();
            }
        });


        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WantToAcivedActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("LongLogTag")
    @Override
    public void stepSelectItem(int position, String number) {
        isSelectedCustomGoal = true;
        adapterCurrentPos = position;
        selectNumber = Integer.parseInt(number);
        Log.d("selectNumber", String.valueOf(selectNumber));
        Log.d(TAG, "stepSelectItem() called with: position = [" + position + "], selectNumber = [" + selectNumber + "]");

        if (selectNumber < 5000) {
            tvRecommendedText.setText("Your step count is under 5000. This may not be enough. Increase the number of steps!");
        } else if (selectNumber >= 5000 && selectNumber <= 12000) {
            tvRecommendedText.setText("Great! You are good to go!");
        } else if (selectNumber >= 12100) {
            tvRecommendedText.setText("Your step count is exceeding 12,000 steps.This may result into exhaustion. Decrease the number of steps!");
        }

    }


}