package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;

import java.util.ArrayList;
import java.util.List;

public class SelectGenderAndAgeActivity extends AppCompatActivity {
    public RecyclerView rvAge;
    Button btnStartJanury;
    AgeAdapter ageAdapter;
    public ImageView ivMaleIcon, ivFemaleIcon;
    TextView tvFemale, tvMale;
    List<AgeModelTest> ageModelTest = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender_and_age);
        findViews();
        setSavedRecyAdapter();
        clickEvent();

    }

    private void findViews() {
        rvAge = findViewById(R.id.rvAge);
        ivMaleIcon = findViewById(R.id.ivMaleIcon);
        ivFemaleIcon = findViewById(R.id.ivFemaleIcon);
        tvFemale = findViewById(R.id.tvFemale);
        tvMale = findViewById(R.id.tvMale);
        btnStartJanury = findViewById(R.id.btnStartJanury);
    }

    private void setSavedRecyAdapter() {
        ageAdapter = new AgeAdapter(getApplicationContext(), ageModelTest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAge.setLayoutManager(layoutManager);
        rvAge.setItemAnimator(new DefaultItemAnimator());
        rvAge.setAdapter(ageAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 0; i <= 40; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge("" + i);
            ageModelTestArrayList.add(ageModelTest);
        }
        AgeAdapter ageAdapter = new AgeAdapter(getApplicationContext(), ageModelTestArrayList);
        rvAge.setAdapter(ageAdapter);
    }


    private void clickEvent() {

        ivMaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMaleIcon.setBackgroundResource(R.drawable.selected_male_icon);
                ivFemaleIcon.setBackgroundResource(R.drawable.unselected_female_icon);
                tvMale.setTextColor(Color.parseColor("#4CCFF9"));
                tvFemale.setTextColor(Color.parseColor("#C4C4C4"));
            }
        });

        ivFemaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFemaleIcon.setBackgroundResource(R.drawable.selected_female_icon);
                ivMaleIcon.setBackgroundResource(R.drawable.male_sunselected_icon_______);
                tvMale.setTextColor(Color.parseColor("#C4C4C4"));
                tvFemale.setTextColor(Color.parseColor("#4CCFF9"));
            }
        });


        btnStartJanury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculateBMIActivity.class);
                startActivity(intent);

            }
        });
    }

}