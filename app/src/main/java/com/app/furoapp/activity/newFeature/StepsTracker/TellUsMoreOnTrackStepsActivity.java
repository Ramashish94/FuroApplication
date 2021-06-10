package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.furoapp.R;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class TellUsMoreOnTrackStepsActivity extends AppCompatActivity {
    public EditText etAge;
    public TextView tvMale, tvFemale;
    public ImageView ivContinue;
    public String userHeightInCm, userWeightInKg;
    public TextView tvHeightRulerValueInCms, tvHeightRulerValueInFeet, tvHeightRulerValueInInch, tvWeightRulerValueInKgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_us_more_on_track_steps);
        initViews();
        clickEvent();
    }

    private void initViews() {
        etAge = findViewById(R.id.etAge);
        tvMale = findViewById(R.id.tvMale);
        tvFemale = findViewById(R.id.tvFemale);
        ivContinue = findViewById(R.id.ivContinue);
        tvHeightRulerValueInCms = findViewById(R.id.tvHeightRulerValueInCms);
        tvHeightRulerValueInFeet = findViewById(R.id.tvHeightRulerValueInFeet);
        tvHeightRulerValueInInch = findViewById(R.id.tvHeightRulerValueInInch);
        tvWeightRulerValueInKgs = findViewById(R.id.tvWeightRulerValueInKgs);

    }

    private void clickEvent() {
        tvMale.setOnClickListener(v -> {
            tvMale.setTextColor(Color.parseColor("#40D5E8"));
            tvFemale.setTextColor(Color.parseColor("#979797"));
        });
        tvFemale.setOnClickListener(v -> {
            tvMale.setTextColor(Color.parseColor("#979797"));
            tvFemale.setTextColor(Color.parseColor("#40D5E8"));
        });
        ivContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddNewSlotPreferActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void rulerPickerValue() {
        /*Height Value*/
        final RulerValuePicker heightPicker = findViewById(R.id.heightRulerPicker);
        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cm ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
            }
        });

        /*Weight value*/
        final RulerValuePicker weightPicker = findViewById(R.id.weightRulerPicker);
        weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kg ");
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
}