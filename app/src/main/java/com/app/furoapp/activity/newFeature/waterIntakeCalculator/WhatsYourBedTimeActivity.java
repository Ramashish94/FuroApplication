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

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;
import com.app.furoapp.activity.newFeature.notification.NotificationAdapter;
import com.app.furoapp.activity.newFeature.notification.NotificationModelTests;

import java.util.ArrayList;
import java.util.List;

public class WhatsYourBedTimeActivity extends AppCompatActivity implements GlassWaterAdapter.GlassCentringInterface{
    public RecyclerView rvGlassWater, rvBedTime;
    public ImageView ivSave, ivContinue;
    public LinearLayout llClosedIcon, llSecondWaterGlassBg, llGlassTakebg;
    public View includeSecondLayerBgOfGlass;
    public GlassWaterAdapter glassWaterAdapter;
    public WakeUpTimeAdapter wakeUpTimeAdapter;
    List<AgeModelTest>ageModelTestList = new ArrayList<>();
    public NotificationAdapter notificationAdapter;
    List<NotificationModelTests>notificationModelTestsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_your_bed_time);
        findViews();
        clickListener();
        operateWaterTakeGlassRecycler();
        operateBedTimeRecycler();

    }


    private void findViews() {
        rvGlassWater = findViewById(R.id.rvGlassWater);
        rvBedTime = findViewById(R.id.rvBedTime);
        ivSave = findViewById(R.id.ivSave);
        llClosedIcon = findViewById(R.id.llClosedIcon);
        includeSecondLayerBgOfGlass = findViewById(R.id.includeSecondLayerBgOfGlass);
        llGlassTakebg = findViewById(R.id.llGlassTakebg);
        ivContinue = findViewById(R.id.ivContinue);
    }

    private void clickListener() {
        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.GONE);
                llSecondWaterGlassBg.setVisibility(View.GONE);
                llGlassTakebg.setVisibility(View.GONE);
            }
        });

        llClosedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeSecondLayerBgOfGlass.setVisibility(View.GONE);
               // llSecondWaterGlassBg.setVisibility(View.GONE);
                llGlassTakebg.setVisibility(View.GONE);
                ivSave.setVisibility(View.GONE);
            }
        });
    }

    private void operateBedTimeRecycler() {
        wakeUpTimeAdapter = new WakeUpTimeAdapter(getApplicationContext(), ageModelTestList);
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

    private void operateWaterTakeGlassRecycler() {
        glassWaterAdapter = new GlassWaterAdapter(getApplicationContext(), ageModelTestList);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        rvGlassWater.setLayoutManager(layoutManager);
        rvGlassWater.setItemAnimator(new DefaultItemAnimator());
        rvGlassWater.setAdapter(glassWaterAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 0; i <=9; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge("200"+"ml");
            //ageModelTest.setImage(Picasso.with(this).load(String.valueOf(ageModelTest.getImage())).into());
            ageModelTestArrayList.add(ageModelTest);
        }
        WakeUpTimeAdapter wakeUpTimeAdapter = new WakeUpTimeAdapter(getApplicationContext(), ageModelTestArrayList);
        rvGlassWater.setAdapter(wakeUpTimeAdapter);

       /* notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvGlassWater.setLayoutManager(layoutManager);
        rvGlassWater.setItemAnimator(new DefaultItemAnimator());
        rvGlassWater.setAdapter(notificationAdapter);
        List<NotificationModelTests> notificationModelTestsArrayList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            NotificationModelTests notificationModelTests = new NotificationModelTests();
            notificationModelTests.setNotification("meditation and work schedule");
            notificationModelTests.setNotificationReadTime("7 min read");
            notificationModelTestsArrayList.add(notificationModelTests);
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationModelTestsArrayList);
        rvGlassWater.setAdapter(notificationAdapter);*/
    }

    @Override
    public void plusIconClick(int adapterPosition) {

    }
}