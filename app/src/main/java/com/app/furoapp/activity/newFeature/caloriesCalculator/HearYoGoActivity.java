package com.app.furoapp.activity.newFeature.caloriesCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

public class HearYoGoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView CalorieRequiredMaintainWeightValue, tvBreakfastValue, tvLunchValue, tvDinnerValue,
            tvProteinValue, tvCarbsValue, tvFatsValue, tvReset, tvSaveData;
    private int userAge, userHeightInCm, userWeightInKg;
    private String genderVal;
    private Double getBMR;
    private Double getAMR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_yo_go);
        initViews();
        getDataOfCalorieCalculator();

        clickEvent();
    }

    private void initViews() {
        CalorieRequiredMaintainWeightValue = findViewById(R.id.CalorieRequiredMaintainWeightValue);
        tvBreakfastValue = findViewById(R.id.tvBreakfastValue);
        tvLunchValue = findViewById(R.id.tvLunchValue);
        tvDinnerValue = findViewById(R.id.tvDinnerValue);
        tvProteinValue = findViewById(R.id.tvProteinValue);
        tvCarbsValue = findViewById(R.id.tvCarbsValue);
        tvFatsValue = findViewById(R.id.tvFatsValue);
        tvReset = findViewById(R.id.tvReset);
        tvSaveData = findViewById(R.id.tvSaveData);
    }

    private void getDataOfCalorieCalculator() {
        userAge = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.AGE)/*getIntent().getStringExtra("userAge")*/);
        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER)/*getIntent().getStringExtra("genderVal")*/;
        userHeightInCm = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM)/*getIntent().getStringExtra("userHeightInCm")*/);
        userWeightInKg = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_WEIGHT_IN_KG)/*getIntent().getStringExtra("userWeightInKg")*/);
        /*Step 1: Calculate Your BMR*/
        if (genderVal.equalsIgnoreCase("Male")) {
//            For men, BMR = 66.47 + (13.75 x weight in kg) + (5.003 x height in cm) - (6.755 x age in years)
            getBMR = 66.47 + (13.75 * userWeightInKg) + (5.003 * userHeightInCm) - (6.755 * userAge);
        } else {
//            For women, BMR = 655.1 + (9.563 x weight in kg) + (1.850 x height in cm) - (4.676 x age in years)
            getBMR = 655.1 + (9.563 * userWeightInKg) + (1.850 * userHeightInCm) - (4.676 * userAge);
        }

      /*  Step 2: Calculate Your AMR
        Sedentary (little or no exercise): AMR = BMR x 1.2
        Lightly active (exercise 1–3 days/week): AMR = BMR x 1.375
        Moderately active (exercise 3–5 days/week): AMR = BMR x 1.55
        Active (exercise 6–7 days/week): AMR = BMR x 1.725
        Very active (hard exercise 6–7 days/week): AMR = BMR x 1.9*/

        if (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE).equalsIgnoreCase("Sedentary")) {
            getAMR = getBMR * 1.2;
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE).equalsIgnoreCase("Light active")) {
            getAMR = getBMR * 1.375;
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE).equalsIgnoreCase("Moderately  active")) {
            getAMR = getBMR * 1.55;
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE).equalsIgnoreCase("Very active")) {
            getAMR = getBMR * 1.9;
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE).equalsIgnoreCase("Active")) {
            getAMR = getBMR * 1.725;
        } else {

        }

        /*Step 3: Weight Goal ----getting calories -- According to choose your weight goal*/
        /*loose weight*/
        if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Loose weight")) {
            CalorieRequiredMaintainWeightValue.setText("" + (getAMR - 500));
            /*Step 5: Nutritional Breakdown per meal ---(40/40/20)carbohydrates/protein/fats */
            tvProteinValue.setText("" + (getAMR * 40) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 20) / 100);
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Maintain weight")) {
            CalorieRequiredMaintainWeightValue.setText("" + getAMR);
            /*Step 5: Nutritional Breakdown per meal ---(40/30/30)carbohydrates/protein/fats*/
            tvProteinValue.setText("" + (getAMR * 30) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 30) / 100);
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Gain weight")) {
            CalorieRequiredMaintainWeightValue.setText("" + (getAMR + ((getAMR * 20) / 100)));
            /*Step 5: Nutritional Breakdown per meal ---(40/30/30)carbohydrates/protein/fats*/
            tvProteinValue.setText("" + (getAMR * 30) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 30) / 100);
        }

        /*Step 4: Meal Breakdown*/
        if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("3")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 40) / 100);
            tvDinnerValue.setText("" + (getAMR * 30) / 100);
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("4")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 40) / 100);
            tvDinnerValue.setText("" + (getAMR * 25) / 100);
        } else if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("5")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 35) / 100);
            tvDinnerValue.setText("" + (getAMR * 20) / 100);
        }

        /*Step 5: Nutritional Breakdown per meal*/


    }

    private void clickEvent() {
        tvReset.setOnClickListener(this);
        tvSaveData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReset:
                finish();
                break;
            case R.id.tvSaveData:
                FuroPrefs.putString(getApplicationContext(), Constants.CALORIES_VALUE, String.valueOf(getAMR));
                finish();
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}