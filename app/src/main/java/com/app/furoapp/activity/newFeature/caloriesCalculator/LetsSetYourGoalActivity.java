package com.app.furoapp.activity.newFeature.caloriesCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.furoapp.R;
import com.app.furoapp.activity.newFeature.StepsTracker.TellUsMoreOnTrackStepsActivity;
import com.app.furoapp.activity.newFeature.StepsTracker.WantToAcivedActivity;
import com.app.furoapp.utils.Constants;
import com.app.furoapp.utils.FuroPrefs;

import static com.app.furoapp.utils.Constants.GENDER;
import static com.app.furoapp.utils.Constants.USER_HEIGHT_IN_CM;
import static com.app.furoapp.utils.Constants.USER_WEIGHT_IN_KG;

public class LetsSetYourGoalActivity extends AppCompatActivity implements View.OnClickListener {
    public LinearLayout llContinue;
    Intent intent;
    private TextView tvLooseWeight, tvMaintainWeight, tvGainWeight, tvNo3, tvNo4, tvNo5, tvSedentary, tvLightActive, tvModeratelyActive, tvVeryActive, tvActive;
    private boolean isAchievedWeightGoal, isNoOfMealSelected, isLevelOfActivity;
    private String getWeightGoalValue, getNoOfMealTakenSelectedValue, getLevelOfActivityValue;
    private String TAG = "LetsSetYourGoalActivity";
    private View vL1St, vL2nd, vL3rd, vL4rth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_set_your_goal);
        initViews();
        clickEvent();
    }

    private void initViews() {
        tvLooseWeight = findViewById(R.id.tvLooseWeight);
        tvMaintainWeight = findViewById(R.id.tvMaintainWeight);
        tvGainWeight = findViewById(R.id.tvGainWeight);
        tvNo3 = findViewById(R.id.tvNo3);
        tvNo4 = findViewById(R.id.tvNo4);
        tvNo5 = findViewById(R.id.tvNo5);
        tvSedentary = findViewById(R.id.tvSedentary);
        tvLightActive = findViewById(R.id.tvLightActive);
        tvModeratelyActive = findViewById(R.id.tvModeratelyActive);
        tvVeryActive = findViewById(R.id.tvVeryActive);
        tvActive = findViewById(R.id.tvActive);
        llContinue = findViewById(R.id.llContinue);

        vL1St = findViewById(R.id.vL1St);
        vL2nd = findViewById(R.id.vL2nd);
        vL3rd = findViewById(R.id.vL3rd);
        vL4rth = findViewById(R.id.vL4rth);
    }

    private void clickEvent() {
        llContinue.setOnClickListener(this);
        tvLooseWeight.setOnClickListener(this);
        tvMaintainWeight.setOnClickListener(this);
        tvGainWeight.setOnClickListener(this);
        tvNo3.setOnClickListener(this);
        tvNo4.setOnClickListener(this);
        tvNo5.setOnClickListener(this);
        tvSedentary.setOnClickListener(this);
        tvLightActive.setOnClickListener(this);
        tvModeratelyActive.setOnClickListener(this);
        tvVeryActive.setOnClickListener(this);
        tvActive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLooseWeight:
                isAchievedWeightGoal = true;
                tvLooseWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvMaintainWeight.setTextColor(Color.parseColor("#979797"));
                tvGainWeight.setTextColor(Color.parseColor("#979797"));

//                tvLooseWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
//                tvMaintainWeight.setBackgroundResource(0);
//                tvGainWeight.setBackgroundResource(0);

                tvLooseWeight.setTextSize(30);
                tvMaintainWeight.setTextSize(20);
                tvGainWeight.setTextSize(20);

                vL1St.setVisibility(View.VISIBLE);
                vL2nd.setVisibility(View.VISIBLE);
                vL3rd.setVisibility(View.GONE);
                vL4rth.setVisibility(View.GONE);

                getWeightGoalValue = tvLooseWeight.getText().toString();
                break;

            case R.id.tvMaintainWeight:
                isAchievedWeightGoal = true;
                tvMaintainWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvLooseWeight.setTextColor(Color.parseColor("#979797"));
                tvGainWeight.setTextColor(Color.parseColor("#979797"));

//                tvMaintainWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
//                tvGainWeight.setBackgroundResource(0);
//                tvLooseWeight.setBackgroundResource(0);
                tvLooseWeight.setTextSize(20);
                tvMaintainWeight.setTextSize(30);
                tvGainWeight.setTextSize(20);

                vL1St.setVisibility(View.GONE);
                vL2nd.setVisibility(View.VISIBLE);
                vL3rd.setVisibility(View.VISIBLE);
                vL4rth.setVisibility(View.GONE);

                getWeightGoalValue = tvMaintainWeight.getText().toString();
                break;

            case R.id.tvGainWeight:
                isAchievedWeightGoal = true;
                tvGainWeight.setTextColor(Color.parseColor("#19CFE6"));
                tvMaintainWeight.setTextColor(Color.parseColor("#979797"));
                tvLooseWeight.setTextColor(Color.parseColor("#979797"));
//                tvGainWeight.setBackgroundResource(R.drawable.bg_set_ur_goal_calorie);
//                tvMaintainWeight.setBackgroundResource(0);
//                tvLooseWeight.setBackgroundResource(0);
                tvLooseWeight.setTextSize(20);
                tvMaintainWeight.setTextSize(20);
                tvGainWeight.setTextSize(30);

                vL1St.setVisibility(View.GONE);
                vL2nd.setVisibility(View.GONE);
                vL3rd.setVisibility(View.VISIBLE);
                vL4rth.setVisibility(View.VISIBLE);

                getWeightGoalValue = tvGainWeight.getText().toString();
                break;
            case R.id.tvNo3:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#19CFE6"));
                tvNo4.setTextColor(Color.parseColor("#979797"));
                tvNo5.setTextColor(Color.parseColor("#979797"));
                getNoOfMealTakenSelectedValue = tvNo3.getText().toString();
                break;
            case R.id.tvNo4:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#979797"));
                tvNo4.setTextColor(Color.parseColor("#19CFE6"));
                tvNo5.setTextColor(Color.parseColor("#979797"));
                getNoOfMealTakenSelectedValue = tvNo4.getText().toString();
                break;
            case R.id.tvNo5:
                isNoOfMealSelected = true;
                tvNo3.setTextColor(Color.parseColor("#979797"));
                tvNo4.setTextColor(Color.parseColor("#979797"));
                tvNo5.setTextColor(Color.parseColor("#19CFE6"));
                getNoOfMealTakenSelectedValue = tvNo5.getText().toString();
                break;
            case R.id.tvSedentary:
                isLevelOfActivity = true;
                tvSedentary.setTextColor(Color.parseColor("#19CFE6"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvSedentary.getText().toString();
                break;
            case R.id.tvLightActive:
                isLevelOfActivity = true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#19CFE6"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvLightActive.getText().toString();
                break;
            case R.id.tvModeratelyActive:
                isLevelOfActivity = true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#19CFE6"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvModeratelyActive.getText().toString();
                break;
            case R.id.tvVeryActive:
                isLevelOfActivity = true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#19CFE6"));
                tvActive.setTextColor(Color.parseColor("#979797"));
                getLevelOfActivityValue = tvVeryActive.getText().toString();
                break;
            case R.id.tvActive:
                isLevelOfActivity = true;
                tvSedentary.setTextColor(Color.parseColor("#979797"));
                tvLightActive.setTextColor(Color.parseColor("#979797"));
                tvModeratelyActive.setTextColor(Color.parseColor("#979797"));
                tvVeryActive.setTextColor(Color.parseColor("#979797"));
                tvActive.setTextColor(Color.parseColor("#19CFE6"));
                getLevelOfActivityValue = tvActive.getText().toString();
                break;
            case R.id.llContinue:
                if (isAchievedWeightGoal) {
                    if (isNoOfMealSelected) {
                        if (isLevelOfActivity) {
                            intent = new Intent(getApplicationContext(), TellUsMoreOnTrackStepsActivity.class);
                            intent.putExtra("CalorieIntakeCalculator", "CalorieIntakeCalculator");
                            FuroPrefs.putString(getApplicationContext(), Constants.WEIGHT_GOAL_VALUE, getWeightGoalValue);
                            FuroPrefs.putString(getApplicationContext(), Constants.NO_OF_MEAL_TAKEN, getNoOfMealTakenSelectedValue);
                            FuroPrefs.putString(getApplicationContext(), Constants.LEVEL_OF_ACTIVITY_VALUE, getLevelOfActivityValue);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Please Select the Level of activity!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please Select the number of meals intake per day!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Select Choose your weight goal!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        clickEvent();
    }
}