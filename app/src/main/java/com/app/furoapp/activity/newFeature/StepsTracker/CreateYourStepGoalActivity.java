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
    public TextView ivBack;
    AgeAdapter ageAdapter;
    StepsCountAdapter stepsCountAdapter;
    private String selectNumber;
    int adapterCurrentPos = 0;
    private NumberPicker picker1;
    private String[] pickerVals;
    private int getnuberVal;


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

    }

    private void setRecyAdapter() {
        stepsCountAdapter = new StepsCountAdapter(getApplicationContext(), ageModelTest, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvStepsCount.setLayoutManager(layoutManager);
        rvStepsCount.setItemAnimator(new DefaultItemAnimator());
        rvStepsCount.setAdapter(stepsCountAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 3000; i <= 20000; i = i + 100) {
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
            Intent intent = new Intent(getApplicationContext(), FqStepsCounterActivity.class);
            intent.putExtra("selectNumber", selectNumber);
            startActivity(intent);
            finish();
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
        adapterCurrentPos = position;
        selectNumber = String.valueOf(number);
        Log.d("selectNumber", selectNumber);
        Log.d(TAG, "stepSelectItem() called with: position = [" + position + "], selectNumber = [" + selectNumber + "]");
    }


}