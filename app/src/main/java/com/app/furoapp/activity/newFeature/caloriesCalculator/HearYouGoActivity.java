package com.app.furoapp.activity.newFeature.caloriesCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

public class HearYouGoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCalorieRequiredValue, tvBreakfastValue, tvLunchValue, tvDinnerValue,
            tvProteinValue, tvCarbsValue, tvFatsValue, tvReset, tvSaveData, tvHeading;
    private int userAge, userHeightInCm, userWeightInKg;
    private String genderVal;
    private Double getBMR;
    private Double getAMR;
    public static final String TAG = "HearYoGoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hear_you_go);
        initViews();
        getDataOfCalorieCalculator();

        clickEvent();
    }

    private void initViews() {
        tvCalorieRequiredValue = findViewById(R.id.tvCalorieRequiredValue);
        tvBreakfastValue = findViewById(R.id.tvBreakfastValue);
        tvLunchValue = findViewById(R.id.tvLunchValue);
        tvDinnerValue = findViewById(R.id.tvDinnerValue);
        tvProteinValue = findViewById(R.id.tvProteinValue);
        tvCarbsValue = findViewById(R.id.tvCarbsValue);
        tvFatsValue = findViewById(R.id.tvFatsValue);
        tvReset = findViewById(R.id.tvReset);
        tvSaveData = findViewById(R.id.tvSaveData);
        tvHeading = findViewById(R.id.tvHeading);
    }

    private void getDataOfCalorieCalculator() {
        userAge = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.AGE)/*getIntent().getStringExtra("userAge")*/);
        genderVal = FuroPrefs.getString(getApplicationContext(), Constants.GENDER).trim()/*getIntent().getStringExtra("genderVal")*/;
        userHeightInCm = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM)/*getIntent().getStringExtra("userHeightInCm")*/);
        userWeightInKg = Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_WEIGHT_IN_KG)/*getIntent().getStringExtra("userWeightInKg")*/);
        Log.d(TAG, "1.Age ->" + userAge + ", 2.gender ->" + genderVal + ", 3.HeightInCm ->" + userHeightInCm + ", 4.WeightInKg ->" + userWeightInKg);
        /*Step 1: Calculate Your BMR*/
        if (genderVal.equalsIgnoreCase("Male")) {
//            For men, BMR = 66.47 + (13.75 x weight in kg) + (5.003 x height in cm) - (6.755 x age in years)
            getBMR = 66.47 + (13.75 * userWeightInKg) + (5.003 * userHeightInCm) - (6.755 * userAge);
        } else {
//            For women, BMR = 655.1 + (9.563 x weight in kg) + (1.850 x height in cm) - (4.676 x age in years)
            getBMR = 655.1 + (9.563 * userWeightInKg) + (1.850 * userHeightInCm) - (4.676 * userAge);
        }
        Log.d(TAG, " BMR -> " + getBMR);

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
        }
        Log.d(TAG, "Level of activity -> " + (FuroPrefs.getString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE)));
        Log.d(TAG, " AMR -> " + getAMR);

        /*Step 3: Weight Goal ----getting calories -- According to choose your weight goal*/
        /*loose weight*/
        if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Loose weight")) {
            tvHeading.setText("calories required per day  \n to loose weight");
            tvCalorieRequiredValue.setText("" + (getAMR - 500));
            /*Step 5: Nutritional Breakdown per meal ---(40/40/20)carbohydrates/protein/fats */
            tvProteinValue.setText("" + (getAMR * 40) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 20) / 100);

            Log.d(TAG, "Choose your weight goal - > " + (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE)));
            Log.d(TAG, "Calorie required - > " + (getAMR - 500));
            Log.d(TAG, "Nutritional Breakdown per meal - > " + "1.Protein ->" + ((getAMR * 40) / 100) + ", 2.Carbs -> " + ((getAMR * 40) / 100) + ", 3.Fats- >" + ((getAMR * 20) / 100));

        } else if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Maintain weight")) {
            tvHeading.setText("" + "calories required per day  \n to maintain weight");
            tvCalorieRequiredValue.setText("" + getAMR);
            /*Step 5: Nutritional Breakdown per meal ---(40/30/30)carbohydrates/protein/fats*/
            tvProteinValue.setText("" + (getAMR * 30) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 30) / 100);

            Log.d(TAG, "Choose your weight goal - > " + (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE)));
            Log.d(TAG, "Calorie required - > " + (getAMR));
            Log.d(TAG, "Nutritional Breakdown per meal - > " + "1. Protein ->" + ((getAMR * 30) / 100) + ", 2. Carbs -> " + ((getAMR * 40) / 100) + ", 3. Fats- >" + ((getAMR * 30) / 100));

        } else if (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE).equalsIgnoreCase("Gain weight")) {
            tvHeading.setText("" + "calories required per day  \n to gain weight");
            tvCalorieRequiredValue.setText("" + (getAMR + ((getAMR * 20) / 100)));
            /*Step 5: Nutritional Breakdown per meal ---(40/30/30)carbohydrates/protein/fats*/
            tvProteinValue.setText("" + (getAMR * 30) / 100);
            tvCarbsValue.setText("" + (getAMR * 40) / 100);
            tvFatsValue.setText("" + (getAMR * 30) / 100);

            Log.d(TAG, "Choose your weight goal - > " + (FuroPrefs.getString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE)));
            Log.d(TAG, "Calorie required - > " + (getAMR + ((getAMR * 20) / 100)));
            Log.d(TAG, "Nutritional Breakdown per meal - > " + "1.Protein ->" + ((getAMR * 30) / 100) + ", 2.Carbs -> " + ((getAMR * 40) / 100) + ", 3.Fats- >" + ((getAMR * 30) / 100));
        }


        /*Step 4: Meal Breakdown*/
        if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("3")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 40) / 100);
            tvDinnerValue.setText("" + (getAMR * 30) / 100);

            Log.d(TAG, "Number of meals taken per day - > " + (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN)));
            Log.d(TAG, "Meal Breakdown - > " + "1.Breakfast ->" + ((getAMR * 30) / 100) + ", 2.Lunch -> " + ((getAMR * 40) / 100) + ", 3.Dinner- >" + ((getAMR * 30) / 100));


        } else if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("4")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 40) / 100);
            tvDinnerValue.setText("" + (getAMR * 25) / 100);

            Log.d(TAG, "Number of meals taken per day - > " + (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN)));
            Log.d(TAG, "Meal Breakdown - > " + "1.Breakfast ->" + ((getAMR * 30) / 100) + ", 2.Lunch -> " + ((getAMR * 40) / 100) + ", 3.Dinner- >" + ((getAMR * 25) / 100));

        } else if (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN).equalsIgnoreCase("5")) {
            tvBreakfastValue.setText("" + (getAMR * 30) / 100);
            tvLunchValue.setText("" + (getAMR * 35) / 100);
            tvDinnerValue.setText("" + (getAMR * 20) / 100);

            Log.d(TAG, "Number of meals taken per day - > " + (FuroPrefs.getString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN)));
            Log.d(TAG, "Meal Breakdown - > " + "1.Breakfast ->" + ((getAMR * 30) / 100) + ", 2.Lunch -> " + ((getAMR * 35) / 100) + ", 3.Dinner- >" + ((getAMR * 20) / 100));

        }


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
                FuroPrefs.putString(getApplicationContext(), Constants.CALORIES_VALUE, tvCalorieRequiredValue.getText().toString().trim()/*String.valueOf(getAMR)*/);
                finish();
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}