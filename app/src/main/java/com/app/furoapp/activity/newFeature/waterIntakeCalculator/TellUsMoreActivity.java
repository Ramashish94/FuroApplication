package com.app.furoapp.activity.newFeature.waterIntakeCalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class TellUsMoreActivity extends AppCompatActivity {

    public TextView tvRecordedScores, tvHeightRulerValueInCms, tvHeightRulerValueInFeet, tvHeightRulerValueInInch, tvWeightRulerValueInKgs;
    public ImageView ivContinue;
    public TextView tvExerciseTime, tvhHalfHrs, tvOneHrs, tvOneAndHalfHrs, tvTwoHrs, tvTwoAndHalfHrs, tvThreeHrs, tvThreeHalfHrs, tvFourHrs, tvFourHalfHrs, tvFiveHrs;
    public String getExerciseTime;
    public String getUserWeightInKg;
    public String totExerciseTimeInMin;
    public boolean isWeightSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_us_more);
        findViews();
        rulerPickerValue();
        clickEvent();
    }

    private void findViews() {
        tvRecordedScores = findViewById(R.id.tvRecordedScores);
        tvHeightRulerValueInCms = findViewById(R.id.tvHeightRulerValueInCms);
        tvHeightRulerValueInFeet = findViewById(R.id.tvHeightRulerValueInFeet);
        tvHeightRulerValueInInch = findViewById(R.id.tvHeightRulerValueInInch);
        tvWeightRulerValueInKgs = findViewById(R.id.tvWeightRulerValueInKgs);
        ivContinue = findViewById(R.id.ivContinue);

        tvhHalfHrs = findViewById(R.id.tvhHalfHrs);
        tvOneHrs = findViewById(R.id.tvOneHrs);
        tvOneAndHalfHrs = findViewById(R.id.tvOneAndHalfHrs);
        tvTwoHrs = findViewById(R.id.tvTwoHrs);
        tvTwoAndHalfHrs = findViewById(R.id.tvTwoAndHalfHrs);
        tvThreeHrs = findViewById(R.id.tvThreeHrs);
        tvThreeHalfHrs = findViewById(R.id.tvThreeHalfHrs);
        tvFourHrs = findViewById(R.id.tvFourHrs);
        tvFourHalfHrs = findViewById(R.id.tvFourHalfHrs);
        tvFiveHrs = findViewById(R.id.tvFiveHrs);
        tvExerciseTime = findViewById(R.id.tvExerciseTime);

    }

    private void rulerPickerValue() {
        /*Height Value*/
        final RulerValuePicker heightPicker = findViewById(R.id.heightRulerPicker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                tvHeightRulerValueInCms.setText(selectedValue + " cm ");
                centimeterToFeet(String.valueOf(selectedValue));
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                tvHeightRulerValueInCms.setText(selectedValue + " cm ");
            }
        });

        /*Weight value*/
        final RulerValuePicker weightPicker = findViewById(R.id.weightRulerPicker);
        weightPicker.selectValue(60);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                getUserWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(getUserWeightInKg + " kgs ");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                getUserWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(getUserWeightInKg + " kgs ");
            }
        });
    }

    public String centimeterToFeet(String centimeter) {
        int feetPart = 0;
        int inchesPart = 0;
        if (!TextUtils.isEmpty(centimeter)) {
            double dCentimeter = Double.valueOf(centimeter);
            feetPart = (int) Math.floor((dCentimeter / 2.54) / 12);
            System.out.println((dCentimeter / 2.54) - (feetPart * 12));
            inchesPart = (int) Math.ceil((dCentimeter / 2.54) - (feetPart * 12));
            String inchesss = String.valueOf(inchesPart);
            String feettt = String.valueOf(feetPart);
            tvHeightRulerValueInFeet.setText("(" + feettt + " ' ");
            tvHeightRulerValueInInch.setText(inchesss + " '' " + ")  ");

        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }

    private void clickEvent() {
        tvhHalfHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvhHalfHrs.getText().toString();
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "30";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvOneHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvOneHrs.getText().toString();
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "60";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvOneHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvOneAndHalfHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvOneAndHalfHrs.getText().toString();
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "90";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));

            }
        });
        tvTwoHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvTwoHrs.getText().toString();
                tvTwoHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "120";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvTwoAndHalfHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvTwoAndHalfHrs.getText().toString();
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "150";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvThreeHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvThreeHrs.getText().toString();
                tvThreeHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "180";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvThreeHalfHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvThreeHalfHrs.getText().toString();
                tvThreeHalfHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "210";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });
        tvFourHrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExerciseTime = tvFourHrs.getText().toString();
                tvFourHrs.setTextColor(Color.parseColor("#19CFE6"));
                tvExerciseTime.setText(getExerciseTime + " ");
                totExerciseTimeInMin = "240";
                Log.d("totExerciseTimeInMin", totExerciseTimeInMin);
                tvhHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvOneAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvTwoAndHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvThreeHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFourHalfHrs.setTextColor(Color.parseColor("#6B6B6B"));
                tvFiveHrs.setTextColor(Color.parseColor("#6B6B6B"));
            }
        });

        ivContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WakeUpTimeActivity.class);
                intent.putExtra("totExerciseTimeInMin", totExerciseTimeInMin);
                intent.putExtra("getUserWeightInKg", getUserWeightInKg);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}