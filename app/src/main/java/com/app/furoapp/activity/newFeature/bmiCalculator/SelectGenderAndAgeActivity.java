package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import java.util.ArrayList;
import java.util.List;

public class SelectGenderAndAgeActivity extends AppCompatActivity implements AgeAdapter.AgeClickCallBack {
    public RecyclerView rvAge;
    Button btnStartJanury;
    AgeAdapter ageAdapter;
    public ImageView ivMaleIcon, ivFemaleIcon;
    TextView tvFemale, tvMale;
    List<AgeModelTest> ageModelTest = new ArrayList<>();
    private int selected;
    private String maleVal, femaleVal;
    private String genderVal;
    private String userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender_and_age);
        findViews();
        clickEvent();
        setSavedRecyAdapter();
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
        ageAdapter = new AgeAdapter(getApplicationContext(), ageModelTest, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAge.setLayoutManager(layoutManager);
        rvAge.setItemAnimator(new DefaultItemAnimator());
        rvAge.setAdapter(ageAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 20; i <= 60; i++) {
            AgeModelTest ageModelTest = new AgeModelTest();
            ageModelTest.setAge("" + i);
            ageModelTestArrayList.add(ageModelTest);
        }
        AgeAdapter ageAdapter = new AgeAdapter(getApplicationContext(), ageModelTestArrayList, this);
        rvAge.setAdapter(ageAdapter);
    }

    @Override
    public void ageSelectItem(int age) {
         userAge = String.valueOf(age);
        Log.d("com.app.furoapp.activity.newFeature.ContentEngagementModule.activityDetailsNew.User Age", userAge);
        //Toast.makeText(this, "Age" + age, Toast.LENGTH_SHORT).show();
        //FuroPrefs.putString(getApplicationContext(), Constants.AGE_SELECT, userAge);
    }

    private void clickEvent() {

        ivMaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivMaleIcon.setBackgroundResource(R.drawable.selected_male_icon);
                ivFemaleIcon.setBackgroundResource(R.drawable.unselected_female_icon);
                tvMale.setTextColor(Color.parseColor("#4CCFF9"));
                tvFemale.setTextColor(Color.parseColor("#C4C4C4"));

               /* maleVal = tvMale.getText().toString();
                Log.d("maleVal", maleVal);*/
                genderVal = tvMale.getText().toString();
                Log.d("genderVal", genderVal);
            }
        });

        ivFemaleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivFemaleIcon.setBackgroundResource(R.drawable.selected_female_icon);
                ivMaleIcon.setBackgroundResource(R.drawable.male_sunselected_icon_______);
                tvMale.setTextColor(Color.parseColor("#C4C4C4"));
                tvFemale.setTextColor(Color.parseColor("#4CCFF9"));

                /*femaleVal = tvFemale.getText().toString();
                Log.d("femaleVal", femaleVal);*/
                genderVal = tvFemale.getText().toString();
                Log.d("genderVal", genderVal);
            }
        });

        btnStartJanury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalculateBMIActivity.class);
                FuroPrefs.putString(getApplicationContext(), Constants.GENDER_VAL, genderVal);
                FuroPrefs.putString(getApplicationContext(), Constants.USER_AGE_SELECT, userAge);
                startActivity(intent);
            }
        });
    }
}