package com.app.furoapp.activity.newFeature.StepsTracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.counter.Padometer;
import com.app.furoapp.activity.newFeature.StepsTracker.adapter.StepsCountAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.adapter.AgeAdapter;
import com.app.furoapp.activity.newFeature.bmiCalculator.AgeModelTest;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateYourStepGoalActivity extends AppCompatActivity implements StepsCountAdapter.StepsClickCallBack {
    private static final String TAG = "CreateYourStepGoalActivity";
    List<AgeModelTest> ageModelTest = new ArrayList<>();
    public RecyclerView rvStepsCount;
    public ImageView ivContinue;
    public TextView ivBack, tvRecommendedText, tvRecommended;
    AgeAdapter ageAdapter;
    StepsCountAdapter stepsCountAdapter;
    private int selectNumber;
    int adapterCurrentPos = 0;
    //    private NumberPicker picker1;
    private NumberPicker numberPicker;
    private String[] pickerVals;
    private int getnuberVal;
    private boolean isSelectedCustomGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_goal_activity);
        findViews();
        clickEvent();
        setRecyAdapter();
        numberPicker();
    }

    private void numberPicker() {
       /* int start = 1000;
        String[] numbers = new String[20];
        for (int i = 0; i <= 20-1; i++) {
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
        });*/

        // Set divider color
        numberPicker.setDividerColor(ContextCompat.getColor(this, R.color.vertical_line_color));
        numberPicker.setDividerColorResource(R.color.vertical_line_color);

        // Set formatter
        numberPicker.setFormatter(getString(R.string.number_picker_formatter));
        numberPicker.setFormatter(R.string.number_picker_formatter);

        // Set selected text color
        numberPicker.setSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        numberPicker.setSelectedTextColorResource(R.color.colorAccent);

        // Set selected text size
        numberPicker.setSelectedTextSize(getResources().getDimension(R.dimen.selected_text_size));
        numberPicker.setSelectedTextSize(R.dimen.selected_text_size);

        // Set selected typeface
        numberPicker.setSelectedTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setSelectedTypeface(getString(R.string.roboto_light));
        numberPicker.setSelectedTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setSelectedTypeface(R.string.roboto_light);

        // Set text color
        numberPicker.setTextColor(ContextCompat.getColor(this, R.color.dark_grey));
        numberPicker.setTextColorResource(R.color.dark_grey);

        // Set text size
        numberPicker.setTextSize(getResources().getDimension(R.dimen.text_size));
        numberPicker.setTextSize(R.dimen.text_size);

        // Set typeface
        numberPicker.setTypeface(Typeface.create(getString(R.string.roboto_light), Typeface.NORMAL));
        numberPicker.setTypeface(getString(R.string.roboto_light), Typeface.NORMAL);
        numberPicker.setTypeface(getString(R.string.roboto_light));
        numberPicker.setTypeface(R.string.roboto_light, Typeface.NORMAL);
        numberPicker.setTypeface(R.string.roboto_light);

        // Set value
        int NUMBER_OF_VALUES = 30; //num of values in the picker
        int PICKER_RANGE = 1000;
        String[] displayedValues = new String[NUMBER_OF_VALUES];
        //Populate the array
        for (int i = 0; i < NUMBER_OF_VALUES; i++) {
            displayedValues[i] = String.valueOf(PICKER_RANGE * (i + 1));/** */
        }
//       numberPicker.setMinValue(3000);
        numberPicker.setMaxValue(displayedValues.length);
        numberPicker.setDisplayedValues(displayedValues);
        tvRecommendedText.setText("Your step count is under 5000. This may not be enough. Increase the number of steps!");
       /* numberPicker.setMinValue(1000);
        numberPicker.setMaxValue(15000);
        numberPicker.setValue(4000);*/

        // Set string values
//        String[] data = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
//        numberPicker.setMinValue(1);
//        numberPicker.setMaxValue(data.length);
//        numberPicker.setDisplayedValues(data);

        // Set fading edge enabled
        numberPicker.setFadingEdgeEnabled(true);

        // Set scroller enabled
        numberPicker.setScrollerEnabled(true);

        // Set wrap selector wheel
        numberPicker.setWrapSelectorWheel(true);

        // Set accessibility description enabled
        numberPicker.setAccessibilityDescriptionEnabled(true);

        // OnClickListener
        numberPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click on current value");
            }
        });

        // OnValueChangeListener
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
            }
        });

        // OnScrollListener
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(NumberPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    selectNumber = picker.getValue() * 1000;
                    if (selectNumber >= 5000 && selectNumber <= 8000) {
                        tvRecommended.setVisibility(View.VISIBLE);
                    } else {
                        tvRecommended.setVisibility(View.INVISIBLE);
                    }
                    Log.d(TAG, String.format(Locale.US, "newVal: %d", selectNumber/*picker.getValue() * 1000*/));

                    if (selectNumber < 5000) {
                        tvRecommendedText.setText("Your step count is under 5000. This may not be enough. Increase the number of steps!");
                    } else if (selectNumber >= 5000 && selectNumber <= 12000) {
                        tvRecommendedText.setText("Great! You are good to go!");
                    } else if (selectNumber >= 12000) {
                        tvRecommendedText.setText("Your step count is exceeding 12,000 steps.This may result into exhaustion. Decrease the number of steps!");
                    }
                }
            }
        });


    }


    private void findViews() {
        rvStepsCount = findViewById(R.id.rvStepsCount);
        ivContinue = findViewById(R.id.ivContinue);
        ivBack = findViewById(R.id.ivBack);
//        picker1 = findViewById(R.id.numberpicker_main_picker);
        numberPicker = findViewById(R.id.number_picker);
        tvRecommendedText = findViewById(R.id.tvRecommendedText);
        tvRecommended = findViewById(R.id.tvRecommended);

    }

    private void setRecyAdapter() {
        stepsCountAdapter = new StepsCountAdapter(getApplicationContext(), ageModelTest, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvStepsCount.setLayoutManager(layoutManager);
        rvStepsCount.setItemAnimator(new DefaultItemAnimator());
        rvStepsCount.setAdapter(stepsCountAdapter);
        List<AgeModelTest> ageModelTestArrayList = new ArrayList<>();
        for (int i = 1000; i <= 30000; i = i + 100) {
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
//            if (isSelectedCustomGoal) {
                Intent intent = new Intent(getApplicationContext(), Padometer.class);
                intent.putExtra("selectNumber", String.valueOf(selectNumber));
                FuroPrefs.putInt(getApplicationContext(), Constants.ACHIVED_VAL, (selectNumber));
                startActivity(intent);
//                Toast.makeText(this, "" + selectNumber, Toast.LENGTH_SHORT).show();
                finish();
//            } else {
//                Toast.makeText(this, "Please select your goal!", Toast.LENGTH_SHORT).show();
//            }
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
        isSelectedCustomGoal = true;
        adapterCurrentPos = position;
        selectNumber = Integer.parseInt(number);
        Log.d("selectNumber", String.valueOf(selectNumber));
        Log.d(TAG, "stepSelectItem() called with: position = [" + position + "], selectNumber = [" + selectNumber + "]");

        if (selectNumber < 5000) {
            tvRecommendedText.setText("Your step count is under 5000. This may not be enough. Increase the number of steps!");
        } else if (selectNumber >= 5000 && selectNumber <= 12000) {
            tvRecommendedText.setText("Great! You are good to go!");
        } else if (selectNumber >= 12100) {
            tvRecommendedText.setText("Your step count is exceeding 12,000 steps.This may result into exhaustion. Decrease the number of steps!");
        }

    }


}