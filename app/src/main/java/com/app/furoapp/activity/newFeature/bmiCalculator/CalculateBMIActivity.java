package com.app.furoapp.activity.newFeature.bmiCalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.furoapp.R;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

public class CalculateBMIActivity extends AppCompatActivity {
    public TextView tvRecordedScores, tvHeightRulerValueInCms, tvHeightRulerValueInFeet, tvHeightRulerValueInInch, tvWeightRulerValueInKgs;
    public Button btnStartJanury;
    public String userHeightInCm, userWeightInKg;
    public String getFindBmiType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_b_m_i);

        findViews();
        rulerPickerValue();
        clickEvent();
    }

    private void findViews() {
        tvRecordedScores = findViewById(R.id.tvRecordedScores);
        btnStartJanury = findViewById(R.id.btnStartJanury);
        tvHeightRulerValueInCms = findViewById(R.id.tvHeightRulerValueInCms);
        tvHeightRulerValueInFeet = findViewById(R.id.tvHeightRulerValueInFeet);
        tvHeightRulerValueInInch = findViewById(R.id.tvHeightRulerValueInInch);
        tvWeightRulerValueInKgs = findViewById(R.id.tvWeightRulerValueInKgs);

        getFindBmiType = getIntent().getStringExtra("getFindBmiType");
    }

    private void rulerPickerValue() {
        /*Height Value*/
        final RulerValuePicker heightPicker = findViewById(R.id.heightRulerPicker);
        if (FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM) != null) {
            heightPicker.selectValue(Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM)));
        } else {
            heightPicker.selectValue(156);
        }
//        heightPicker.selectValue(156);
        heightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
                centimeterToFeet(String.valueOf(userHeightInCm));
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userHeightInCm = String.valueOf(selectedValue);
                tvHeightRulerValueInCms.setText(userHeightInCm + " cms ");
                centimeterToFeet(String.valueOf(userHeightInCm));

            }
        });

        /*Weight value*/
        final RulerValuePicker weightPicker = findViewById(R.id.weightRulerPicker);

        if (FuroPrefs.getString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM) != null) {
            weightPicker.selectValue(Integer.parseInt(FuroPrefs.getString(getApplicationContext(), Constants.USER_WEIGHT_IN_KG)));
        } else {
            weightPicker.selectValue(55);
        }
        //weightPicker.selectValue(55);
        weightPicker.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                userWeightInKg = String.valueOf(selectedValue);
                tvWeightRulerValueInKgs.setText(userWeightInKg + " kgs ");
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
            tvHeightRulerValueInFeet.setText("( " + feettt + " ' ");
            tvHeightRulerValueInInch.setText(inchesss + " '' " + ") ");

        }
        return String.format("%d' %d''", feetPart, inchesPart);
    }

    private void clickEvent() {

        tvRecordedScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordDataActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnStartJanury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YourScoreActivity.class);
                intent.putExtra("userHeightInCm", userHeightInCm);
                intent.putExtra("userWeightInKg", userWeightInKg);
                intent.putExtra("getFindBmiType", getFindBmiType);
                /*FuroPrefs.putString(getApplicationContext(), Constants.USER_HEIGHT_IN_CM, String.valueOf(userHeightInCm));
                FuroPrefs.putString(getApplicationContext(), Constants.USER_WEIGHT, String.valueOf(userWeightInKg));*/
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