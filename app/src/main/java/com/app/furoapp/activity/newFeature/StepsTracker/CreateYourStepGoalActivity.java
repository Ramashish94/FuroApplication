package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.StepsCountAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_goal_activity);
        findViews();
        clickEvent();
        setRecyAdapter();
    }


    private void findViews() {
        rvStepsCount = findViewById(R.id.rvStepsCount);
        ivContinue = findViewById(R.id.ivContinue);
        ivBack = findViewById(R.id.ivBack);

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